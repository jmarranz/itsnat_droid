package org.itsnat.droid.impl.browser.serveritsnat;

import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ItsNatDroidScriptException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptInline;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
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

    public void insertFragment(ViewGroup parentView, String markup,View viewRef)
    {
        // Preparamos primero el markup añadiendo un false parentView que luego quitamos, el false parentView es necesario
        // para declarar el namespace android, el false parentView será del mismo tipo que el de verdad para que los
        // LayoutParams se hagan bien.

        PageImpl page = itsNatDoc.getPageImpl();
        InflatedLayoutPageImpl inflatedLayoutPage = page.getInflatedLayoutPageImpl();
        XMLInflaterLayoutPage xmlLayoutInflaterPage = page.getXMLInflaterLayoutPage();

        StringBuilder newMarkup = new StringBuilder();

        newMarkup.append("<" + parentView.getClass().getName());

        MapLight<String, String> namespaceMap = inflatedLayoutPage.getNamespacesByPrefix();
        for (Map.Entry<String, String> entry : namespaceMap.getEntryList())
        {
            newMarkup.append(" xmlns:" + entry.getKey() + "=\"" + entry.getValue() + "\">");
        }

        newMarkup.append(">");
        newMarkup.append(markup);
        newMarkup.append("</" + parentView.getClass().getName() + ">");

        markup = newMarkup.toString();


        XMLDOMRegistry xmlDOMRegistry = inflatedLayoutPage.getItsNatDroidImpl().getXMLDOMRegistry();
        AssetManager assetManager = itsNatDoc.getPageImpl().getContext().getResources().getAssets();

        XMLDOMLayout xmlDOMLayout = xmlDOMRegistry.getXMLDOMLayoutCache(markup, page.getItsNatServerVersion(),false, assetManager);

        DOMElemView rootDOMElemView = (DOMElemView)xmlDOMLayout.getRootElement(); // Gracias al parentView añadido siempre esperamos un DOMView, nunca un DOMMerge

        LinkedList<DOMScript> scriptList = new LinkedList<DOMScript>();

        List<DOMScript> domScriptList = xmlDOMLayout.getDOMScriptList();
        if (domScriptList != null)
            scriptList.addAll(domScriptList);

        ViewGroup falseParentView = (ViewGroup) xmlLayoutInflaterPage.insertFragment(rootDOMElemView,xmlDOMLayout); // Los XML ids, los inlineHandlers etc habrán quedado memorizados
        int indexRef = viewRef != null ? InflatedLayoutImpl.getChildViewIndex(parentView, viewRef) : -1;
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

    private void executeScriptList(LinkedList<DOMScript> scriptList)
    {
        if (scriptList.isEmpty()) return;

        Interpreter interp = itsNatDoc.getPageImpl().getInterpreter();
        for (DOMScript script : scriptList)
        {
            if (script instanceof DOMScriptInline)
            {
                String code = script.getCode();
                try
                {
                    interp.eval(code);
                }
                catch (EvalError ex) { throw new ItsNatDroidScriptException(ex, code); }
                catch (Exception ex) { throw new ItsNatDroidScriptException(ex, code); }
            }
            else if (script instanceof DOMScriptRemote)
            {
                String src = ((DOMScriptRemote)script).getSrc();
                itsNatDoc.downloadScript(src); // Se carga asíncronamente sin un orden claro
            }
        }

    }
}
