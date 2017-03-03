package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.Page;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 29/10/14.
 */
public class FragmentLayoutInserter
{
    public ItsNatDocPageImpl itsNatDoc;

    public FragmentLayoutInserter(ItsNatDocPageImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
    }

    public void insertPageFragment(ViewGroup parentView, String markup, View viewRef,XMLDOMParserContext xmlDOMParserContext)
    {
        // Llamado desde un page No ItsNat
        setInnerXMLInsertPageFragment(parentView, parentView.getClass().getName(),markup,viewRef,xmlDOMParserContext);
    }

    public void setInnerXML(ViewGroup parentView, String parentClassName, String markup, View viewRef,XMLDOMParserContext xmlDOMParserContext)
    {
        // parentClassName viene del servidor y puede ser nulo,  es el className original puesto en el template y se envía cuando ha sido usado para el pre-parseo con metadatos de beanshell en el caso de
        // contener el template algún atributo remoto, evitamos así usar un class name absoluto en este caso que es que lo que devuelve parentView.getClass().getName() pues no lo encontraríamos en el caché
        // Si no hay algún atributo remoto no se envía y es nulo porque no se necesita (es llamado el método setInnerXML sin className),

        if (parentClassName == null) parentClassName = parentView.getClass().getName();

        setInnerXMLInsertPageFragment(parentView, parentClassName,markup,viewRef, xmlDOMParserContext);
    }

    private void setInnerXMLInsertPageFragment(ViewGroup parentView, String parentClassName, String markup, View viewRef,XMLDOMParserContext xmlDOMParserContext)
    {
        // Este método es llamado por el setInnerXML generado por ItsNat Server pero también por los métodos de usuario appendFragment e insertFragment

        // Si el fragmento a insertar es suficientemente grande el rendimiento de setInnerXML puede ser varias veces superior
        // a hacerlo elemento a elemento, atributo a atributo con la API debido a la lentitud de Beanshell
        // Por ejemplo 78ms con setInnerXML (parseando markup) y 179ms con beanshell puro

        PageImpl page = itsNatDoc.getPageImpl();
        InflatedXMLLayoutPageImpl inflatedLayoutPage = page.getInflatedXMLLayoutPageImpl();
        XMLDOMLayoutPage xmlDOMLayoutPageParent = inflatedLayoutPage.getXMLDOMLayoutPage();
        Context ctx = itsNatDoc.getContext();

        XMLDOMLayoutPage xmlDOMLayout = wrapAndParseMarkupFragment(parentClassName, markup,xmlDOMLayoutPageParent,page.getItsNatServerVersion(),xmlDOMParserContext);

        DOMElemView rootDOMElemView = (DOMElemView)xmlDOMLayout.getRootDOMElement(); // Gracias al parentView añadido siempre esperamos un DOMView, nunca un DOMMerge

        List<DOMScript> domScriptList = xmlDOMLayout.getDOMScriptList();

        XMLInflaterLayoutPage xmlLayoutInflaterPage = page.getXMLInflaterLayoutPage();
        ViewGroup falseParentView = (ViewGroup) xmlLayoutInflaterPage.insertFragment(rootDOMElemView,xmlDOMLayout); // Los XML ids, los inlineHandlers etc habrán quedado memorizados
        int indexRef = viewRef != null ? parentView.indexOfChild(viewRef) : -1;
        while (falseParentView.getChildCount() > 0)
        {
            View child = falseParentView.getChildAt(0);
            falseParentView.removeViewAt(0);
            if (indexRef >= 0)
            {
                parentView.addView(child, indexRef);
                indexRef++;
            }
            else parentView.addView(child);
        }

        executeScriptList(domScriptList);
    }

    public static XMLDOMLayoutPage wrapAndParseMarkupFragment(String parentClassName, String markup,XMLDOMLayoutPage xmlDOMLayoutPageParent,String itsNatServerVersion,XMLDOMParserContext xmlDOMParserContext)
    {
        // Preparamos primero el markup añadiendo un false parentView que luego quitamos, el false parentView es necesario
        // para declarar el namespace android, el false parentView será del mismo tipo que el de verdad para que los
        // LayoutParams se hagan bien.

        StringBuilder newMarkup = new StringBuilder();

        newMarkup.append("<" + parentClassName);
        newMarkup.append(" xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        MapLight<String, String> namespaceMap = xmlDOMLayoutPageParent.getRootNamespacesByPrefix();
        for (Map.Entry<String, String> entry : namespaceMap.getEntryList())
        {
            newMarkup.append(" xmlns:" + entry.getKey() + "=\"" + entry.getValue() + "\"");
        }
        newMarkup.append(">");
        newMarkup.append(markup);
        newMarkup.append("</" + parentClassName + ">");

        markup = newMarkup.toString();

        XMLDOMRegistry xmlDOMRegistry = xmlDOMParserContext.getXMLDOMRegistry();
        ParsedResourceXMLDOM<XMLDOMLayout> resourceXMLDOM = xmlDOMRegistry.buildXMLDOMLayoutAndCachingByMarkupAndResDesc(markup,null, itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE_FRAGMENT, xmlDOMParserContext);
        XMLDOMLayoutPage xmlDOMLayout = (XMLDOMLayoutPage)resourceXMLDOM.getXMLDOM();
        return xmlDOMLayout;
    }


    private void executeScriptList(List<DOMScript> domScriptList)
    {
        if (domScriptList == null) return;

        for (DOMScript script : domScriptList)
        {
            String code = script.getCode();
            if (code != null)
            {
                itsNatDoc.eval(code);
            }
            else if (script instanceof DOMScriptRemote)
            {
                final DOMScriptRemote scriptRemote = (DOMScriptRemote)script;

                // Es el caso de llamada por el usuario directamente a appendFragment/insertFragment(...) no es el caso de llamada setInnerXML de BS generado pues se carga en multihilo
                OnHttpRequestListener listener = new OnHttpRequestListener()
                {
                    @Override
                    public void onRequest(Page page,HttpRequestResult response)
                    {
                        String code = response.getResponseText();
                        itsNatDoc.eval(code);
                        scriptRemote.setCode(code); // Creo que no sirve para nada pero por si acaso
                    }
                };
                String src = scriptRemote.getSrc();
                itsNatDoc.downloadScript(src,listener); // Se carga asíncronamente sin un orden claro
            }
            else
            {
                // Es un DOMScriptInline con code a null, es imposible
                throw MiscUtil.internalError();
            }
        }

    }
}
