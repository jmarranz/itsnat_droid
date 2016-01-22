package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptInline;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bsh.Interpreter;

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

    public void setInnerXML(ViewGroup parentView, String parentClassName, String markup, View viewRef)
    {
        // Si viene del servidor especificado es el className original puesto en el template y se ha usado para el pre-parseo con metadatos de beanshell, evitamos así usar un class name absoluto que es que lo que devuelve parentView.getClass().getName() pues no lo encontraríamos en el caché
        parentClassName = parentClassName != null ? parentClassName : parentView.getClass().getName();

        PageImpl page = itsNatDoc.getPageImpl();
        InflatedLayoutPageImpl inflatedLayoutPage = page.getInflatedLayoutPageImpl();
        XMLDOMLayoutPage xmldomLayoutPageParent = inflatedLayoutPage.getXMLDOMLayoutPage();
        XMLDOMRegistry xmlDOMRegistry = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        AssetManager assetManager = page.getContext().getResources().getAssets();

        markup = wrapMarkup(parentClassName,markup,xmldomLayoutPageParent);
        XMLDOMLayoutPage xmlDOMLayout = parseMarkup(markup,page.getItsNatServerVersion(),xmlDOMRegistry,assetManager);

        DOMElemView rootDOMElemView = (DOMElemView)xmlDOMLayout.getRootDOMElement(); // Gracias al parentView añadido siempre esperamos un DOMView, nunca un DOMMerge

        LinkedList<DOMScript> scriptList = new LinkedList<DOMScript>();

        List<DOMScript> domScriptList = xmlDOMLayout.getDOMScriptList();
        if (domScriptList != null)
            scriptList.addAll(domScriptList);

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

        executeScriptList(scriptList);
    }


    public static XMLDOMLayoutPage[] wrapAndParseMarkup(LinkedList<String> classNameListBSParsed,LinkedList<String> xmlMarkupListBSParsed,String itsNatServerVersion,
                                          XMLDOMLayoutPage xmldomLayoutPageParent,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        // Método llamado en multihilo (no UI)

        XMLDOMLayoutPage[] xmldomLayoutPageArr = new XMLDOMLayoutPage[classNameListBSParsed.size()];

        if (classNameListBSParsed.size() != xmlMarkupListBSParsed.size()) throw new ItsNatDroidException("Internal Error");
        // Así se cachea pero sobre to_do se cargan los recursos remotos dentro del markup que trae el setInnerXML()
        Iterator<String> itClassName = classNameListBSParsed.iterator();
        Iterator<String> itMarkup = xmlMarkupListBSParsed.iterator();
        int i = 0;
        while(itClassName.hasNext())
        {
            String className = itClassName.next();
            String markup = itMarkup.next();
            markup = wrapMarkup(className,markup,xmldomLayoutPageParent);
            XMLDOMLayoutPage xmlDOM = parseMarkup(markup,itsNatServerVersion,xmlDOMRegistry,assetManager);
            xmldomLayoutPageArr[i] = xmlDOM;
        }
        return xmldomLayoutPageArr;
    }

    private static String wrapMarkup(String parentClassName,String markup,XMLDOMLayoutPage xmldomLayoutPageParent)
    {
        // Preparamos primero el markup añadiendo un false parentView que luego quitamos, el false parentView es necesario
        // para declarar el namespace android, el false parentView será del mismo tipo que el de verdad para que los
        // LayoutParams se hagan bien.

        StringBuilder newMarkup = new StringBuilder();

        newMarkup.append("<" + parentClassName);
        newMarkup.append(" xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        MapLight<String, String> namespaceMap = xmldomLayoutPageParent.getRootNamespacesByPrefix();
        for (Map.Entry<String, String> entry : namespaceMap.getEntryList())
        {
            newMarkup.append(" xmlns:" + entry.getKey() + "=\"" + entry.getValue() + "\"");
        }
        newMarkup.append(">");
        newMarkup.append(markup);
        newMarkup.append("</" + parentClassName + ">");

        markup = newMarkup.toString();
        return markup;
    }

    private static XMLDOMLayoutPage parseMarkup(String markup,String itsNatServerVersion,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        XMLDOMLayoutPage xmlDOMLayout = (XMLDOMLayoutPage) xmlDOMRegistry.getXMLDOMLayoutCache(markup,itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE_FRAGMENT, assetManager);
        return xmlDOMLayout;
    }


    private void executeScriptList(LinkedList<DOMScript> scriptList)
    {
        if (scriptList.isEmpty()) return;

        Interpreter interp = itsNatDoc.getPageImpl().getInterpreter();
        for (DOMScript script : scriptList)
        {
            if (script instanceof DOMScriptInline)
            {
                String code = script.getCode();
                itsNatDoc.eval(code);
            }
            else if (script instanceof DOMScriptRemote)
            {
                String src = ((DOMScriptRemote)script).getSrc();
                itsNatDoc.downloadScript(src); // Se carga asíncronamente sin un orden claro
            }
        }

    }
}
