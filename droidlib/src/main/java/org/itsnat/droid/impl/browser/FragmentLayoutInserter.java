package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.impl.browser.serveritsnat.XMLDOMLayoutPageItsNatDownloader;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptInline;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.util.LinkedList;
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

    public void setInnerXMLInsertPageFragment(ViewGroup parentView, String parentClassName, String markup, View viewRef)
    {
        // Este método es llamado por el setInnerXML generado por ItsNat Server pero también por los métodos de usuario appendFragment e insertFragment

        // Si viene del servidor especificado es el className original puesto en el template y se ha usado para el pre-parseo con metadatos de beanshell, evitamos así usar un class name absoluto que es que lo que devuelve parentView.getClass().getName() pues no lo encontraríamos en el caché
        parentClassName = parentClassName != null ? parentClassName : parentView.getClass().getName();

        PageImpl page = itsNatDoc.getPageImpl();
        InflatedLayoutPageImpl inflatedLayoutPage = page.getInflatedLayoutPageImpl();
        XMLDOMLayoutPage xmlDOMLayoutPageParent = inflatedLayoutPage.getXMLDOMLayoutPage();
        XMLDOMRegistry xmlDOMRegistry = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        AssetManager assetManager = page.getContext().getResources().getAssets();

        markup = XMLDOMLayoutPageItsNatDownloader.wrapMarkupFragment(parentClassName, markup, xmlDOMLayoutPageParent);
        XMLDOMLayoutPage xmlDOMLayout = XMLDOMLayoutPageItsNatDownloader.parseMarkupFragment(markup, page.getItsNatServerVersion(), xmlDOMRegistry, assetManager);

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

    private void executeScriptList(LinkedList<DOMScript> scriptList)
    {
        if (scriptList.isEmpty()) return;

        for (DOMScript script : scriptList)
        {
            if (script instanceof DOMScriptInline)
            {
                String code = script.getCode();
                itsNatDoc.eval(code);
            }
            else if (script instanceof DOMScriptRemote)
            {
                DOMScriptRemote scriptRemote = (DOMScriptRemote)script;
                if (scriptRemote.getCode() != null)
                {
                    // Es el caso de llamada por el usuario directamente a insertFragment(...) no es el caso de llamada setInnerXML de BS generado pues se carga en multihilo
                    String src = scriptRemote.getSrc();
                    itsNatDoc.downloadScript(src); // Se carga asíncronamente sin un orden claro
                }
            }
        }

    }
}
