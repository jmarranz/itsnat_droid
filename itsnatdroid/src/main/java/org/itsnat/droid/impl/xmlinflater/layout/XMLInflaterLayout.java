package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemLayout;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutStandalone;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageItsNatImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageNotItsNatImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutStandaloneImpl;
import org.itsnat.droid.impl.xmlinflater.AttrInflaterListeners;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPageItsNat;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPageNotItsNat;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterLayout extends XMLInflater
{
    public XMLInflaterLayout(InflatedLayoutImpl inflatedXML,int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        super(inflatedXML, bitmapDensityReference,attrInflaterListeners);
    }

    public static XMLInflaterLayout inflateLayout(ItsNatDroidImpl itsNatDroid,XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int index,
                                                  int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners,
                                                  Context ctx,PageImpl page)
    {
        InflatedLayoutImpl inflatedLayout;
        if (xmlDOMLayout instanceof XMLDOMLayoutPage)
        {
            if (xmlDOMLayout instanceof XMLDOMLayoutPageItsNat)
                inflatedLayout = new InflatedLayoutPageItsNatImpl((PageItsNatImpl)page,itsNatDroid,(XMLDOMLayoutPageItsNat) xmlDOMLayout,ctx);
            else if (xmlDOMLayout instanceof XMLDOMLayoutPageNotItsNat)
                inflatedLayout = new InflatedLayoutPageNotItsNatImpl(page,itsNatDroid,(XMLDOMLayoutPageNotItsNat) xmlDOMLayout,ctx);
            else
                return null; // Internal Error
        }
        else if (xmlDOMLayout instanceof XMLDOMLayoutStandalone)
                inflatedLayout = new InflatedLayoutStandaloneImpl(itsNatDroid,(XMLDOMLayoutStandalone)xmlDOMLayout, ctx);
        else
            return null; // Internal Error


        XMLInflaterLayout xmlInflaterLayout = createXMLInflaterLayout(inflatedLayout, bitmapDensityReference, attrInflaterListeners);
        xmlInflaterLayout.inflateLayout(viewParent, index);
        return xmlInflaterLayout;
    }

    private static XMLInflaterLayout createXMLInflaterLayout(InflatedLayoutImpl inflatedLayout,int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        if (inflatedLayout instanceof InflatedLayoutPageImpl)
        {
            if (inflatedLayout instanceof InflatedLayoutPageItsNatImpl)
                return new XMLInflaterLayoutPageItsNat((InflatedLayoutPageItsNatImpl)inflatedLayout,bitmapDensityReference,attrInflaterListeners);
            else if (inflatedLayout instanceof InflatedLayoutPageNotItsNatImpl)
                return new XMLInflaterLayoutPageNotItsNat((InflatedLayoutPageNotItsNatImpl)inflatedLayout,bitmapDensityReference,attrInflaterListeners);
        }
        else if (inflatedLayout instanceof InflatedLayoutStandaloneImpl)
        {
            return new XMLInflaterLayoutStandalone((InflatedLayoutStandaloneImpl)inflatedLayout,bitmapDensityReference,attrInflaterListeners);
        }

        return null; // Internal error
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
        ClassDescViewMgr classDescViewMgr = getInflatedLayoutImpl().getXMLInflaterRegistry().getClassDescViewMgr();
        return classDescViewMgr.get(domElemView);
    }

    private View inflateRootView(XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int index)
    {
        DOMElemLayout rootDOMView = (DOMElemLayout)xmlDOMLayout.getRootDOMElement();

        DOMElemView newRootDOMElemView;
        if (rootDOMView instanceof DOMElemMerge)
        {
            if (viewParent == null) throw new ItsNatDroidException("Only can be used <merge> on included layouts");
            newRootDOMElemView = new DOMElemView((DOMElemMerge)rootDOMView); // Reemplazamos el <merge> por el ViewGroup como elemento, conservando los hijos y atributos del <merge> original (el namespace de Android por ejemplo)
            newRootDOMElemView.setTagName(viewParent.getClass().getName());
        }
        else
        {
            if (viewParent != null)
            {
                newRootDOMElemView = new DOMElemView(viewParent.getClass().getName(), null); // Reemplazamos el <View> root por el ViewGroup padre y lo añadimos como hijo, para que se definan bien los Layout Params
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
        return classDesc.createViewObjectAndFillAttributesAndAdd(viewParent,domElemView.getDOMAttributeMap(), -1, this,pendingPostInsertChildrenTasks);
    }


    protected void processChildViews(DOMElemView domElemViewParent, View viewParent, XMLDOM xmlDOMParent)
    {
        LinkedList<DOMElement> childElemList = domElemViewParent.getChildDOMElementList();
        if (childElemList != null)
        {
            ViewGroup viewParentGroup = (ViewGroup)viewParent;
            for (DOMElement childDOMElem : childElemList)
            {
                View childView = inflateNextView((DOMElemLayout)childDOMElem,viewParentGroup,xmlDOMParent);
            }
        }
    }

    public View insertFragment(DOMElemView rootDOMElemViewFragment, XMLDOM xmlDOMParent)
    {
        return inflateNextView(rootDOMElemViewFragment,null,xmlDOMParent);
    }

    private View inflateNextView(DOMElemLayout domElem, ViewGroup viewParent, XMLDOM xmlDOMParent)
    {
        // Es llamado también para insertar fragmentos

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        View view = createViewObjectAndFillAttributesAndAdd(viewParent, (DOMElemView) domElem, pendingPostInsertChildrenTasks);

        processChildViews((DOMElemView)domElem,view,xmlDOMParent);

        pendingPostInsertChildrenTasks.executeTasks();

        return view;
    }

    public void fillIncludeAttributesFromGetLayout(View rootViewChild,ViewGroup viewParent,ArrayList<DOMAttr> includeAttribs)
    {
        String className = rootViewChild.getClass().getName();
        XMLInflaterRegistry xmlInflaterRegistry = getInflatedLayoutImpl().getItsNatDroidImpl().getXMLInflaterRegistry();
        ClassDescViewBased classDesc = xmlInflaterRegistry.getClassDescViewMgr().get(className);
        if (classDesc == null)
            throw new ItsNatDroidException("Not found processor for " + className);

        classDesc.fillIncludeAttributesFromGetLayout(rootViewChild,viewParent,this,includeAttribs);
    }


    public abstract boolean setAttributeInlineEventHandler(View view, DOMAttr attr);
    public abstract boolean removeAttributeInlineEventHandler(View view, String namespaceURI, String name);
}
