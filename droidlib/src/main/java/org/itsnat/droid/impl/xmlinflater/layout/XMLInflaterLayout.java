package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.layout.DOMInclude;
import org.itsnat.droid.impl.dom.layout.DOMMerge;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMView;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutStandaloneImpl;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterLayout extends XMLInflater
{
    public XMLInflaterLayout(InflatedLayoutImpl inflatedXML,int bitmapDensityReference,
                             AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener,
                             Context ctx)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener,ctx);
    }

    public static XMLInflaterLayout createXMLInflaterLayout(InflatedLayoutImpl inflatedLayout,int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener, Context ctx,PageImpl page)
    {
        XMLInflaterLayout xmlInflaterLayout = null;

        if (inflatedLayout instanceof InflatedLayoutPageImpl)
        {
            xmlInflaterLayout = new XMLInflaterLayoutPage((InflatedLayoutPageImpl)inflatedLayout,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener,ctx,page);
        }
        else if (inflatedLayout instanceof InflatedLayoutStandaloneImpl)
        {
            xmlInflaterLayout = new XMLInflaterLayoutStandalone((InflatedLayoutStandaloneImpl)inflatedLayout,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener,ctx);
        }

        inflatedLayout.setXMLInflaterLayout(xmlInflaterLayout); // Se necesita después para la inserción de fragments, cambio de atributos etc

        return xmlInflaterLayout; // Internal Error
    }

    public InflatedLayoutImpl getInflatedLayoutImpl()
    {
        return (InflatedLayoutImpl)inflatedXML;
    }

    public View inflateLayout(String[] loadScript, List<String> scriptList)
    {
        XMLDOMLayout domLayout = getInflatedLayoutImpl().getXMLDOMLayout();
        if (loadScript != null)
            loadScript[0] = domLayout.getLoadScript();

        if (scriptList != null)
            fillScriptList(domLayout,scriptList);

        View rootView = inflateRootView(domLayout);
        return rootView;
    }

    private static void fillScriptList(XMLDOMLayout domLayout,List<String> scriptList)
    {
        List<DOMScript> scriptListFromTree = domLayout.getDOMScriptList();
        if (scriptListFromTree != null)
        {
            for (DOMScript script : scriptListFromTree)
                scriptList.add(script.getCode());
        }
    }

    public ClassDescViewBased getClassDescViewBased(DOMView domView)
    {
        String viewName = domView.getName(); // viewName lo normal es que sea un nombre corto por ej RelativeLayout
        ClassDescViewMgr classDescViewMgr = getInflatedLayoutImpl().getXMLInflateRegistry().getClassDescViewMgr();
        return classDescViewMgr.get(viewName);
    }

    private View inflateRootView(XMLDOMLayout domLayout)
    {
        DOMView rootDOMView = (DOMView)domLayout.getRootElement(); // domLayout.getRootView();

        PendingPostInsertChildrenTasks pending = new PendingPostInsertChildrenTasks();

        View rootView = createRootViewObjectAndFillAttributes(rootDOMView,pending);

        processChildViews(rootDOMView,rootView);

        pending.executeTasks();

        return rootView;
    }

    public View createRootViewObjectAndFillAttributes(DOMView rootDOMView,PendingPostInsertChildrenTasks pending)
    {
        ClassDescViewBased classDesc = getClassDescViewBased(rootDOMView);
        View rootView = createViewObject(classDesc, rootDOMView, pending);

        getInflatedLayoutImpl().setRootView(rootView); // Lo antes posible porque los inline event handlers lo necesitan, es el root View del template, no el View.getRootView() pues una vez insertado en la actividad de alguna forma el verdadero root cambia

        fillAttributesAndAddView(rootView, classDesc, null, rootDOMView, pending);

        return rootView;
    }

    public View createViewObjectAndFillAttributesAndAdd(ViewGroup viewParent, DOMView domView, PendingPostInsertChildrenTasks pending)
    {
        // viewParent es null en el caso de parseo de fragment, por lo que NO tengas la tentación de llamar aquí
        // a setRootView(view); cuando viewParent es null "para reutilizar código"
        ClassDescViewBased classDesc = getClassDescViewBased(domView);
        View view = createViewObject(classDesc,domView,pending);

        fillAttributesAndAddView(view,classDesc,viewParent, domView,pending);

        return view;
    }


    private View createViewObject(ClassDescViewBased classDesc,DOMView domView,PendingPostInsertChildrenTasks pending)
    {
        return classDesc.createViewObjectFromParser(getInflatedLayoutImpl(), domView,pending);
    }

    private void fillAttributesAndAddView(View view,ClassDescViewBased classDesc,ViewGroup viewParent,DOMView domView,PendingPostInsertChildrenTasks pending)
    {
        OneTimeAttrProcess oneTimeAttrProcess = classDesc.createOneTimeAttrProcess(view,viewParent);
        AttrLayoutContext attrCtx = new AttrLayoutContext(ctx,this,oneTimeAttrProcess, pending);

        fillViewAttributes(classDesc,view, domView,attrCtx); // Los atributos los definimos después porque el addView define el LayoutParameters adecuado según el padre (LinearLayout, RelativeLayout...)
        classDesc.addViewObject(viewParent, view, -1, oneTimeAttrProcess, ctx);

        oneTimeAttrProcess.destroy();
    }

    private void fillViewAttributes(ClassDescViewBased classDesc, View view, DOMView domView, AttrLayoutContext attrCtx)
    {
        ArrayList<DOMAttr> attribList = domView.getDOMAttributeList();
        if (attribList != null)
        {
            for (int i = 0; i < attribList.size(); i++)
            {
                DOMAttr attr = attribList.get(i);
                setAttribute(classDesc, view, attr,attrCtx);
            }
        }

        attrCtx.getOneTimeAttrProcess().executeLastTasks();
    }

    public boolean setAttribute(ClassDescViewBased classDesc, View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        return classDesc.setAttribute(view,attr,attrCtx);
    }

    protected void processChildViews(DOMView domViewParent, View viewParent)
    {
        LinkedList<DOMElement> childElemList = domViewParent.getChildDOMElementList();
        if (childElemList != null)
        {
            ViewGroup viewParentGroup = (ViewGroup)viewParent;
            for (DOMElement childDOMElem : childElemList)
            {
                View childView = inflateNextView(childDOMElem,viewParentGroup);
            }
        }
    }

    public View insertFragment(DOMView rootDOMViewFragment)
    {
        return inflateNextView(rootDOMViewFragment,null);
    }

    private View inflateNextView(DOMElement domElem, ViewGroup viewParent)
    {
        // Es llamado también para insertar fragmentos
        if (domElem instanceof DOMInclude)
        {
            View[] includeViewList = inflateInclude((DOMInclude)domElem,viewParent);
            // Devolvemos el último por devolver algo
            if (includeViewList != null && includeViewList.length > 0)
                return includeViewList[includeViewList.length - 1];
            return null;
        }
        else
        {
            PendingPostInsertChildrenTasks pending = new PendingPostInsertChildrenTasks();

            View view = createViewObjectAndFillAttributesAndAdd( viewParent, (DOMView)domElem, pending);

            processChildViews((DOMView)domElem,view);

            pending.executeTasks();

            return view;
        }
    }


    public View[] inflateInclude(DOMInclude domElemInc,ViewGroup viewParent)
    {
        int countBefore = viewParent.getChildCount();
        XMLInflateRegistry xmlInflateRegistry = getInflatedLayoutImpl().getXMLInflateRegistry();
        DOMAttr attr = DOMAttr.create(null,"layout",domElemInc.getLayout());
        View resView = xmlInflateRegistry.getLayout(attr, ctx, this,viewParent);
        if (resView != viewParent) throw new ItsNatDroidException("Unexpected"); // Es así, ten en cuenta que el layout incluido puede ser un <merge> con varios views
        int countAfter = viewParent.getChildCount();
        View[] childList = new View[countAfter - countBefore];
        int j = 0;
        for(int i = countBefore; i < countAfter; i++)
        {
            childList[j] = viewParent.getChildAt(i);
            j++;
        }

        return childList;
    }

}
