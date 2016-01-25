package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.Page;
import org.itsnat.droid.impl.browser.serveritsnat.XMLDOMLayoutPageItsNatDownloader;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.util.List;

/**
 * Created by jmarranz on 29/10/14.
 */
public class FragmentLayoutInserter
{
    public ItsNatDocImpl itsNatDoc;

    public FragmentLayoutInserter(ItsNatDocImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
    }

    public void insertPageFragment(ViewGroup parentView, String markup, View viewRef)
    {
        // Llamado desde un page No ItsNat
        setInnerXMLInsertPageFragment(parentView, parentView.getClass().getName(),markup,viewRef);
    }

    public void setInnerXML(ViewGroup parentView, String parentClassName, String markup, View viewRef)
    {
        // parentClassName viene del servidor,  es el className original puesto en el template y se ha usado para el pre-parseo con metadatos de beanshell, evitamos así usar un class name absoluto que es que lo que devuelve parentView.getClass().getName() pues no lo encontraríamos en el caché
        setInnerXMLInsertPageFragment(parentView, parentClassName,markup,viewRef);
    }

    private void setInnerXMLInsertPageFragment(ViewGroup parentView, String parentClassName, String markup, View viewRef)
    {
        // Este método es llamado por el setInnerXML generado por ItsNat Server pero también por los métodos de usuario appendFragment e insertFragment

        // Si el fragmento a insertar es suficientemente grande el rendimiento de setInnerXML puede ser varias veces superior
        // a hacerlo elemento a elemento, atributo a atributo con la API debido a la lentitud de Beanshell
        // Por ejemplo 78ms con setInnerXML (parseando markup) y 179ms con beanshell puro

        PageImpl page = itsNatDoc.getPageImpl();
        InflatedLayoutPageImpl inflatedLayoutPage = page.getInflatedLayoutPageImpl();
        XMLDOMLayoutPage xmlDOMLayoutPageParent = inflatedLayoutPage.getXMLDOMLayoutPage();
        XMLDOMRegistry xmlDOMRegistry = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        AssetManager assetManager = page.getContext().getResources().getAssets();

        XMLDOMLayoutPage xmlDOMLayout = XMLDOMLayoutPageItsNatDownloader.wrapAndParseMarkupFragment(parentClassName, markup, xmlDOMLayoutPageParent, page.getItsNatServerVersion(), xmlDOMRegistry, assetManager);

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

                // Es el caso de llamada por el usuario directamente a insertFragment(...) no es el caso de llamada setInnerXML de BS generado pues se carga en multihilo
                OnHttpRequestListener listener = new OnHttpRequestListener()
                {
                    @Override
                    public void onRequest(Page page,HttpRequestResult response)
                    {
                        String code = response.getResponseText();
                        itsNatDoc.eval(code);
                        scriptRemote.setCode(code);
                    }
                };
                String src = scriptRemote.getSrc();
                itsNatDoc.downloadScript(src,listener); // Se carga asíncronamente sin un orden claro
            }
        }

    }
}
