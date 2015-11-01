package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescScaleDrawable extends ClassDescElementDrawableRoot<ScaleDrawable>
{

    public ClassDescScaleDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"scale");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        /*
        <scale
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:drawable="@drawable/drawable_resource"
        android:scaleGravity=["top" | "bottom" | "left" | "right" | "center_vertical" |
            "fill_vertical" | "center_horizontal" | "fill_horizontal" |
            "center" | "fill" | "clip_vertical" | "clip_horizontal"]
        android:scaleHeight="percentage"
        android:scaleWidth="percentage" />
        */

        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, ctx, childList);

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        DOMAttr attrGravity = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleGravity");
        int gravity = attrGravity != null ? AttrDesc.parseMultipleName(attrGravity.getValue(), GravityUtil.valueMap) : Gravity.LEFT;

        DOMAttr attrScaleHeight = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleHeight");
        float scaleHeight = attrScaleHeight != null ? xmlInflateRegistry.getPercent(attrScaleHeight.getValue(), ctx) : -1;

        DOMAttr attrScaleWidth = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleWidth");
        float scaleWidth = attrScaleWidth != null ? xmlInflateRegistry.getPercent(attrScaleWidth.getValue(),ctx) : -1;

        elementDrawableRoot.setDrawable(new ScaleDrawable(childDrawable,gravity,scaleWidth,scaleHeight));

        return elementDrawableRoot;
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("drawable".equals(name) || "scaleGravity".equals(name) ||  "scaleHeight".equals(name) ||  "scaleWidth".equals(name));
        }
        return false;
    }


    @Override
    public Class<ClipDrawable> getDrawableOrElementDrawableClass()
    {
        return ClipDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
