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
import org.itsnat.droid.impl.dom.layout.DOMElemView;
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

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterLayout extends XMLInflater
{
    public XMLInflaterLayout(InflatedLayoutImpl inflatedXML,int bitmapDensityReference,
                             AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener);
    }

    public static XMLInflaterLayout inflateLayout(ItsNatDroidImpl itsNatDroid,XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int index,
                                                  int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener,
                                                  Context ctx,PageImpl page)
    {
        InflatedLayoutImpl inflatedLayout = page != null ?  new InflatedLayoutPageImpl(page,itsNatDroid, xmlDOMLayout,ctx) :
                                                            new InflatedLayoutStandaloneImpl(itsNatDroid, xmlDOMLayout, ctx);
        XMLInflaterLayout xmlInflaterLayout = createXMLInflaterLayout(inflatedLayout, bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener);
        xmlInflaterLayout.inflateLayout(viewParent, index);
        return xmlInflaterLayout;
    }

    private static XMLInflaterLayout createXMLInflaterLayout(InflatedLayoutImpl inflatedLayout,int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        XMLInflaterLayout xmlInflaterLayout = null;

        if (inflatedLayout instanceof InflatedLayoutPageImpl)
        {
            xmlInflaterLayout = new XMLInflaterLayoutPage((InflatedLayoutPageImpl)inflatedLayout,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener);
        }
        else if (inflatedLayout instanceof InflatedLayoutStandaloneImpl)
        {
            xmlInflaterLayout = new XMLInflaterLayoutStandalone((InflatedLayoutStandaloneImpl)inflatedLayout,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener);
        }

        return xmlInflaterLayout;
    }

    public InflatedLayoutImpl getInflatedLayoutImpl()
    {
        return (InflatedLayoutImpl)inflatedXML;
    }

    public View inflateLayout(ViewGroup viewParent,int index)
    {
        InflatedLayoutImpl inflatedLayout = getInflatedLayoutImpl();
        XMLDOMLayout domLayout = inflatedLayout.getXMLDOMLayout();

        View rootView = inflateRootView(domLayout, viewParent, index);
        return rootView;
    }


    public ClassDescViewBased getClassDescViewBased(DOMElemView domElemView)
    {
        ClassDescViewMgr classDescViewMgr = getInflatedLayoutImpl().getXMLInflateRegistry().getClassDescViewMgr();
        return classDescViewMgr.get(domElemView);
    }

    private View inflateRootView(XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int index /*,ArrayList<DOMAttr> includeAttribs */)
    {
        DOMElement rootDOMView = xmlDOMLayout.getRootElement();

        DOMElemView newRootDOMElemView;
        if (rootDOMView instanceof DOMElemMerge)
        {
            if (viewParent == null) throw new ItsNatDroidException("Only can be used <merge> on included layouts");
            newRootDOMElemView = new DOMElemView(rootDOMView); // Reemplazamos el <merge> por el ViewGroup como elemento, conservando los hijos y atributos del <merge> original (el namespace de Android por ejemplo)
            newRootDOMElemView.setName(viewParent.getClass().getName());
        }
        else
        {
            if (viewParent != null)
            {
                newRootDOMElemView = new DOMElemView(viewParent.getClass().getName(), null); // Reemplazamos el <View> root por el ViewGroup padre y lo añadimos como hijo, para que se definan bien los Layout Params
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
                newRootDOMElemView.addChildDOMElement(rootDOMView);
            }
            else
                newRootDOMElemView = (DOMElemView)rootDOMView;
        }

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        View rootView = createRootViewObjectAndFillAttributes(newRootDOMElemView, pendingPostInsertChildrenTasks);

        processChildViews(newRootDOMElemView, rootView, xmlDOMLayout);

        pendingPostInsertChildrenTasks.executeTasks();

        if (viewParent != null)
        {
            ViewGroup falseParentView = (ViewGroup)rootView;
            while (falseParentView.getChildCount() > 0)
            {
                View child = falseParentView.getChildAt(0);
                falseParentView.removeViewAt(0);
                viewParent.addView(child,index);
                index++;
            }
            getInflatedLayoutImpl().setRootView(viewParent); // Corregimos el rootView pues se puso el falseParentView
            return viewParent;
        }
        else return rootView;
    }

    public View createRootViewObjectAndFillAttributes(DOMElemView rootDOMElemView,PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        ClassDescViewBased classDesc = getClassDescViewBased(rootDOMElemView);
        return classDesc.createRootViewObjectAndFillAttributes(rootDOMElemView, this, pendingPostInsertChildrenTasks);
    }

    public View createViewObjectAndFillAttributesAndAdd(ViewGroup viewParent, DOMElemView domElemView, PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        ClassDescViewBased classDesc = getClassDescViewBased(domElemView);
        return classDesc.createViewObjectAndFillAttributesAndAdd(viewParent,domElemView,this,pendingPostInsertChildrenTasks);
    }


    protected void processChildViews(DOMElemView domElemViewParent, View viewParent,XMLDOM xmlDOMParent)
    {
        LinkedList<DOMElement> childElemList = domElemViewParent.getChildDOMElementList();
        if (childElemList != null)
        {
            ViewGroup viewParentGroup = (ViewGroup)viewParent;
            for (DOMElement childDOMElem : childElemList)
            {
                View childView = inflateNextView(childDOMElem,viewParentGroup,xmlDOMParent);
            }
        }
    }

    public View insertFragment(DOMElemView rootDOMElemViewFragment,XMLDOM xmlDOMParent)
    {
        return inflateNextView(rootDOMElemViewFragment,null,xmlDOMParent);
    }

    private View inflateNextView(DOMElement domElem, ViewGroup viewParent,XMLDOM xmlDOMParent)
    {
        // Es llamado también para insertar fragmentos

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        View view = createViewObjectAndFillAttributesAndAdd( viewParent, (DOMElemView)domElem, pendingPostInsertChildrenTasks);

        processChildViews((DOMElemView)domElem,view,xmlDOMParent);

        pendingPostInsertChildrenTasks.executeTasks();

        return view;
    }

    public void fillIncludeAttributesFromGetLayout(View rootViewChild,ViewGroup viewParent,ArrayList<DOMAttr> includeAttribs)
    {
        String className = rootViewChild.getClass().getName();
        XMLInflateRegistry xmlInflateRegistry = getInflatedLayoutImpl().getItsNatDroidImpl().getXMLInflateRegistry();
        ClassDescViewBased classDesc = xmlInflateRegistry.getClassDescViewMgr().get(className);

        classDesc.fillIncludeAttributesFromGetLayout(rootViewChild,viewParent,this,includeAttribs);
    }

    public abstract boolean setAttributeInlineEventHandler(View view, DOMAttr attr);
    public abstract boolean removeAttributeInlineEventHandler(View view, String namespaceURI, String name);
}
