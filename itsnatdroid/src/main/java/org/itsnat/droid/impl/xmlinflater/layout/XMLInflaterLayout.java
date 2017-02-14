package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrResourceInflaterListener;
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
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPageItsNat;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPageNotItsNat;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterLayout extends XMLInflater
{
    public XMLInflaterLayout(InflatedLayoutImpl inflatedXML,int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference,attrResourceInflaterListener);
    }

    public static XMLInflaterLayout inflateLayout(ItsNatDroidImpl itsNatDroid,XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int indexChild,
                                                  int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener,
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


        XMLInflaterLayout xmlInflaterLayout = createXMLInflaterLayout(inflatedLayout, bitmapDensityReference, attrResourceInflaterListener);
        xmlInflaterLayout.inflateLayout(viewParent, indexChild);
        return xmlInflaterLayout;
    }

    private static XMLInflaterLayout createXMLInflaterLayout(InflatedLayoutImpl inflatedLayout,int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        if (inflatedLayout instanceof InflatedLayoutPageImpl)
        {
            if (inflatedLayout instanceof InflatedLayoutPageItsNatImpl)
                return new XMLInflaterLayoutPageItsNat((InflatedLayoutPageItsNatImpl)inflatedLayout,bitmapDensityReference,attrResourceInflaterListener);
            else if (inflatedLayout instanceof InflatedLayoutPageNotItsNatImpl)
                return new XMLInflaterLayoutPageNotItsNat((InflatedLayoutPageNotItsNatImpl)inflatedLayout,bitmapDensityReference,attrResourceInflaterListener);
        }
        else if (inflatedLayout instanceof InflatedLayoutStandaloneImpl)
        {
            return new XMLInflaterLayoutStandalone((InflatedLayoutStandaloneImpl)inflatedLayout,bitmapDensityReference,attrResourceInflaterListener);
        }

        return null; // Internal error
    }

    public InflatedLayoutImpl getInflatedLayoutImpl()
    {
        return (InflatedLayoutImpl)inflatedXML;
    }

    public View inflateLayout(ViewGroup viewParent,int indexChild)
    {
        InflatedLayoutImpl inflatedLayout = getInflatedLayoutImpl();
        XMLDOMLayout domLayout = inflatedLayout.getXMLDOMLayout();

        View rootViewOrViewParent = inflateRootView(domLayout, viewParent, indexChild);
        return rootViewOrViewParent;
    }


    public ClassDescViewBased getClassDescViewBased(DOMElemView domElemView)
    {
        ClassDescViewMgr classDescViewMgr = getInflatedLayoutImpl().getXMLInflaterRegistry().getClassDescViewMgr();
        return classDescViewMgr.get(domElemView);
    }

    private View inflateRootView(XMLDOMLayout xmlDOMLayout,ViewGroup viewParent,int indexChild)
    {
        DOMElemLayout rootDOMView = (DOMElemLayout)xmlDOMLayout.getRootDOMElement();

        DOMElemView newRootDOMElemView;
        if (rootDOMView instanceof DOMElemMerge)
        {
            if (viewParent == null)
                throw new ItsNatDroidException("Only can be used <merge> on external included layouts in a parent layout (needed a parent view)");
            newRootDOMElemView = new DOMElemView((DOMElemMerge)rootDOMView); // Reemplazamos el <merge> por el ViewGroup parent como elemento, conservando los hijos y atributos del <merge> original (el namespace de Android por ejemplo)
            newRootDOMElemView.setTagName(viewParent.getClass().getName());
        }
        else
        {
            if (viewParent != null)
            {
                newRootDOMElemView = new DOMElemView(viewParent.getClass().getName(), null); // Reemplazamos el <View> root (normalmente un ViewGroup pero el root del layout cargado puede ser por ej un Button) por el ViewGroup del layout padre y lo añadimos como hijo (lo que finalmente se hará más tarde), para que se definan bien los Layout Params
                newRootDOMElemView.addChildDOMElement(rootDOMView);
            }
            else
                newRootDOMElemView = (DOMElemView)rootDOMView;
        }

        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = new PendingPostInsertChildrenTasks();

        // El View rootView creado por createRootViewObjectAndFillAttributes es el root del layout a cargar
        // Tres casos de newRootDOMElemView:
        // El newRootDOMElemView puede ser el DOM de <merge> pero reemplazado por un clon básico del ViewGroup (viewParent) pero en el layout a cargar
        //  => rootView será el ViewGroup clone (el merge es substituido)
        // El newRootDOMElemView puede ser el DOM del root del layout a cargar pero reemplazado por un clon básico del ViewGroup (viewParent) pero en el layout a cargar, el root del layout a cargar se añadirá como hijo único como se hará finalmente con el ViewGroup verdad (caso viewPaarent!=null)
        //  => rootView será el ViewGroup clone (el original root es ahora el hijo del ViewGroup clone y no es rootView)
        // El newRootDOMElemView puede ser el DOM del root del layout a cargar pero no hay viewParent.
        //  => rootView será el normal root del layout cargado

        View rootView = createRootViewObjectAndFillAttributes(newRootDOMElemView, pendingPostInsertChildrenTasks);

        processChildViews(newRootDOMElemView, rootView, xmlDOMLayout);

        pendingPostInsertChildrenTasks.executeTasks();


        if (viewParent != null)
        {
            // El root del layout cargado es reemplazado temporalmente por un clon básico del ViewGroup (viewParent), tenemos que deshacer esto
            ViewGroup falseRootViewGroupCloned = (ViewGroup)rootView;
            while (falseRootViewGroupCloned.getChildCount() > 0)
            {
                View child = falseRootViewGroupCloned.getChildAt(0);
                falseRootViewGroupCloned.removeViewAt(0);
                viewParent.addView(child,indexChild);
                indexChild++;
            }
            // A la hora de devolver algo devolvemos viewParent QUE ES NO NULO por una parte porque el <merge> desaparece y puede tener varios hijos y en el otro caso cuando hay un viewParent
            // no nulo ya están insertados los elementos del layout insertado y podemos obtener el root via viewParent.getChild(indexChild). En ressumen cuando viewParent es no nulo sabemos
            // que devuelve viewParent
            getInflatedLayoutImpl().setRootView(viewParent);
            return viewParent;
        }
        else
        {
            // rootView es el normal root del layout cargado que todavía NO está insertado
            getInflatedLayoutImpl().setRootView(rootView);
            return rootView;
        }
        // Como se puede ver inflateRootView(...) devuelve viewParent o si no hay viewParent el root view del layout cargado, esto se arrastra en tod_o el stack de llamadas
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
        List<DOMElement> childElemList = domElemViewParent.getChildDOMElementList();
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
