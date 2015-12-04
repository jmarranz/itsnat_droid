package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
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
        super(inflatedXML, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener, ctx);
    }

    public static XMLInflaterLayout inflateLayout(ItsNatDroidImpl itsNatDroid,XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,/*ArrayList<DOMAttr> includeAttribs,*/ String[] loadScript, List<String> scriptList,
                                                  int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener, Context ctx,PageImpl page)
    {
        InflatedLayoutImpl inflatedLayout = page != null ?  new InflatedLayoutPageImpl(itsNatDroid, xmlDOMLayout,ctx) :
                                                            new InflatedLayoutStandaloneImpl(itsNatDroid, xmlDOMLayout, ctx);
        XMLInflaterLayout xmlInflaterLayout = createXMLInflaterLayout(inflatedLayout, bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener, ctx, page);
        xmlInflaterLayout.inflateLayout(viewParent,loadScript, scriptList /*,includeAttribs*/);
        return xmlInflaterLayout;
    }

    private static XMLInflaterLayout createXMLInflaterLayout(InflatedLayoutImpl inflatedLayout,int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener, Context ctx,PageImpl page)
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

        return xmlInflaterLayout;
    }

    public InflatedLayoutImpl getInflatedLayoutImpl()
    {
        return (InflatedLayoutImpl)inflatedXML;
    }

    public View inflateLayout(ViewGroup viewParent,String[] loadScript, List<String> scriptList /*,ArrayList<DOMAttr> includeAttribs*/)
    {
        XMLDOMLayout domLayout = getInflatedLayoutImpl().getXMLDOMLayout();
        if (loadScript != null)
            loadScript[0] = domLayout.getLoadScript();

        if (scriptList != null)
            fillScriptList(domLayout,scriptList);

        View rootView = inflateRootView(domLayout,viewParent /*,includeAttribs */);
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

    private View inflateRootView(XMLDOMLayout xmlDOMLayout,ViewGroup viewParent /*,ArrayList<DOMAttr> includeAttribs */)
    {
        DOMElement rootDOMView = xmlDOMLayout.getRootElement();

        DOMView newRootDOMView;
        if (rootDOMView instanceof DOMElemMerge)
        {
            if (viewParent == null) throw new ItsNatDroidException("Only can be used <merge> on included layouts");
            newRootDOMView = new DOMView(rootDOMView); // Reemplazamos el <merge> por el ViewGroup como elemento, conservando los hijos y atributos del <merge> original (el namespace de Android por ejemplo)
            newRootDOMView.setName(viewParent.getClass().getName());
        }
        else
        {
            if (viewParent != null)
            {
                newRootDOMView = new DOMView(viewParent.getClass().getName(), null); // Reemplazamos el <View> root por el ViewGroup padre y lo añadimos como hijo, para que se definan bien los Layout Params
                /*
                if (includeAttribs != null)
                {
                    // Reemplazamos los atributos originales del rootDOMView por los definidos en el <include> de acuerdo como funciona el <include> (sólo se puede aplicar a un single View root en el layout, no hace nada si hay un merge aunque sólo tenga un hijo)
                    // Para ello lo clonamos para no modificarlo por si está cacheado no tocamos el original
                    rootDOMView = rootDOMView.cloneButNotChildren();
                    for(DOMAttr attr : includeAttribs)
                    {
                        DOMAttr existingAttr = rootDOMView.findDOMAttribute(attr.getNamespaceURI(),attr.getName());
                        if (existingAttr != null)
                            existingAttr.setValue(attr.getValue());
                        else
                            rootDOMView.addDOMAttribute(attr); // No existe, lo añadimos
                    }
                }
                */
                newRootDOMView.addChildDOMElement(rootDOMView);
            }
            else
                newRootDOMView = (DOMView)rootDOMView;
        }

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        View rootView = createRootViewObjectAndFillAttributes(newRootDOMView, pendingPostInsertChildrenTasks);

        processChildViews(newRootDOMView, rootView, xmlDOMLayout);

        pendingPostInsertChildrenTasks.executeTasks();

        if (viewParent != null)
        {
            ViewGroup falseParentView = (ViewGroup)rootView;
            while (falseParentView.getChildCount() > 0)
            {
                View child = falseParentView.getChildAt(0);
                falseParentView.removeViewAt(0);
                viewParent.addView(child);
            }
            getInflatedLayoutImpl().setRootView(viewParent); // Corregimos el rootView pues se puso el falseParentView
            return viewParent;
        }
        else return rootView;
    }

    public View createRootViewObjectAndFillAttributes(DOMView rootDOMView,PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        ClassDescViewBased classDesc = getClassDescViewBased(rootDOMView);
        View rootView = createViewObject(classDesc, rootDOMView, pendingPostInsertChildrenTasks);

        getInflatedLayoutImpl().setRootView(rootView); // Lo antes posible porque los inline event handlers lo necesitan, es el root View del template, no el View.getRootView() pues una vez insertado en la actividad de alguna forma el verdadero root cambia

        fillAttributesAndAddView(rootView, classDesc, null, rootDOMView, pendingPostInsertChildrenTasks);

        return rootView;
    }

    public View createViewObjectAndFillAttributesAndAdd(ViewGroup viewParent, DOMView domView, PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        // viewParent es null en el caso de parseo de fragment, por lo que NO tengas la tentación de llamar aquí
        // a setRootView(view); cuando viewParent es null "para reutilizar código"
        ClassDescViewBased classDesc = getClassDescViewBased(domView);
        View view = createViewObject(classDesc,domView,pendingPostInsertChildrenTasks);

        fillAttributesAndAddView(view,classDesc,viewParent, domView,pendingPostInsertChildrenTasks);

        return view;
    }


    private View createViewObject(ClassDescViewBased classDesc,DOMView domView,PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        return classDesc.createViewObject(domView, pendingPostInsertChildrenTasks, getContext());
    }

    private void fillAttributesAndAddView(View view,ClassDescViewBased classDesc,ViewGroup viewParent,DOMView domView,PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        PendingViewPostCreateProcess pendingViewPostCreateProcess = classDesc.createPendingViewPostCreateProcess(view, viewParent);
        AttrLayoutContext attrCtx = new AttrLayoutContext(ctx,this, pendingViewPostCreateProcess, pendingPostInsertChildrenTasks);

        fillViewAttributes(classDesc,view, domView,attrCtx); // Los atributos los definimos después porque el addView define el LayoutParameters adecuado según el padre (LinearLayout, RelativeLayout...)
        classDesc.addViewObject(viewParent, view, -1, pendingViewPostCreateProcess, ctx);

        pendingViewPostCreateProcess.destroy();
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

        attrCtx.getPendingViewPostCreateProcess().executePendingSetAttribsTasks();
    }


    public boolean setAttribute(ClassDescViewBased classDesc, View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        return classDesc.setAttribute(view,attr,attrCtx);
    }

    protected void processChildViews(DOMView domViewParent, View viewParent,XMLDOM xmlDOMParent)
    {
        LinkedList<DOMElement> childElemList = domViewParent.getChildDOMElementList();
        if (childElemList != null)
        {
            ViewGroup viewParentGroup = (ViewGroup)viewParent;
            for (DOMElement childDOMElem : childElemList)
            {
                View childView = inflateNextView(childDOMElem,viewParentGroup,xmlDOMParent);
            }
        }
    }

    public View insertFragment(DOMView rootDOMViewFragment,XMLDOM xmlDOMParent)
    {
        return inflateNextView(rootDOMViewFragment,null,xmlDOMParent);
    }

    private View inflateNextView(DOMElement domElem, ViewGroup viewParent,XMLDOM xmlDOMParent)
    {
        // Es llamado también para insertar fragmentos

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        View view = createViewObjectAndFillAttributesAndAdd( viewParent, (DOMView)domElem, pendingPostInsertChildrenTasks);

        processChildViews((DOMView)domElem,view,xmlDOMParent);

        pendingPostInsertChildrenTasks.executeTasks();

        return view;
    }

    public void fillIncludeAttributesFromGetLayout(View rootViewChild,ViewGroup viewParent,ArrayList<DOMAttr> includeAttribs)
    {
        String className = rootViewChild.getClass().getName();
        XMLInflateRegistry xmlInflateRegistry = getInflatedLayoutImpl().getItsNatDroidImpl().getXMLInflateRegistry();

        ClassDescViewBased classDesc = xmlInflateRegistry.getClassDescViewMgr().get(className);

        PendingViewPostCreateProcess pendingViewPostCreateProcess = classDesc.createPendingViewPostCreateProcess(rootViewChild,viewParent);
        AttrLayoutContext attrCtx = new AttrLayoutContext(ctx,this, pendingViewPostCreateProcess, null);

        for (int i = 0; i < includeAttribs.size(); i++)
        {
            DOMAttr attr = includeAttribs.get(i);
            setAttribute(classDesc,rootViewChild, attr,attrCtx);
        }

        pendingViewPostCreateProcess.executePendingSetAttribsTasks();
        pendingViewPostCreateProcess.executePendingLayoutParamsTasks();
    }

}
