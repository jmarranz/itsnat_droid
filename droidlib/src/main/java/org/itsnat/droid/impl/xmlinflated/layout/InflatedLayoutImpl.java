package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.layout.ViewMapByXMLId;

/**
 * Created by jmarranz on 16/06/14.
 */
public abstract class InflatedLayoutImpl extends InflatedXML implements InflatedLayout
{
    protected View rootView;
    protected ViewMapByXMLId viewMapByXMLId;


    public InflatedLayoutImpl(ItsNatDroidImpl itsNatDroid,XMLDOMLayout domLayout,Context ctx)
    {
        super(itsNatDroid,domLayout,ctx);
        // rootView se define a posteriori
    }

    public XMLDOMLayout getXMLDOMLayout()
    {
        return (XMLDOMLayout) xmlDOM;
    }

    public String getAndroidNSPrefix()
    {
        return getXMLDOMLayout().getAndroidNSPrefix();
    }

    public MapLight<String,String> getNamespacesByPrefix()
    {
        return getXMLDOMLayout().getNamespacesByPrefix();
    }

    public String getNamespace(String prefix)
    {
        return getNamespacesByPrefix().get(prefix);
    }

    @Override
    public ItsNatDroid getItsNatDroid()
    {
        return getItsNatDroidImpl();
    }


    @Override
    public View getRootView()
    {
        return rootView;
    }

    public void setRootView(View rootView)
    {
        this.rootView = rootView;
    }


    public ViewMapByXMLId getViewMapByXMLId()
    {
        if (viewMapByXMLId == null) viewMapByXMLId = new ViewMapByXMLId(this);
        return viewMapByXMLId;
    }

    public String unsetXMLId(View view)
    {
        return getViewMapByXMLId().unsetXMLId(view);
    }

    public String getXMLId(View view)
    {
        return getViewMapByXMLId().getXMLId(view);
    }

    public void setXMLId(String id, View view)
    {
        getViewMapByXMLId().setXMLId(id, view);
    }

    @Override
    public View findViewByXMLId(String id)
    {
        if (viewMapByXMLId == null) return null;
        return viewMapByXMLId.findViewByXMLId(id);
    }

    public static int getChildViewIndex(ViewGroup parentView, View view)
    {
        if (view.getParent() != parentView) throw new ItsNatDroidException("View must be a direct child of parent View");
        // Esto es una chapuza pero no hay opción
        int size = parentView.getChildCount();
        for(int i = 0; i < size; i++)
        {
            if (parentView.getChildAt(i) == view)
                return i;
        }
        return -1; // No es hijo directo
    }

}
