package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;
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
public class ClassDescInsetDrawable extends ClassDescElementDrawableRoot<InsetDrawable>
{
    public ClassDescInsetDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"inset");
    }

    @Override
    public ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        /*
        <inset
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:drawable="@drawable/drawable_resource"
            android:insetTop="dimension"
            android:insetRight="dimension"
            android:insetBottom="dimension"
            android:insetLeft="dimension" />
        */

        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        Drawable drawable = null;
        DOMAttr attrDrawable = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "drawable");
        if (attrDrawable != null) // Puede ser nulo, en dicho caso el drawable debe estar definido inline como elemento hijo
        {
            drawable = xmlInflateRegistry.getDrawable(attrDrawable, ctx, inflaterDrawable);
        }

        int insetTop = 0;
        DOMAttr attrInsetTop = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetTop");
        if (attrInsetTop != null)
        {
            insetTop = xmlInflateRegistry.getDimensionIntFloor(attrInsetTop.getValue(), ctx);
        }

        int insetRight = 0;
        DOMAttr attrInsetRight = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetRight");
        if (attrInsetRight != null)
        {
            insetRight = xmlInflateRegistry.getDimensionIntFloor(attrInsetRight.getValue(),ctx);
        }

        int insetBottom = 0;
        DOMAttr attrInsetBottom = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetBottom");
        if (attrInsetBottom != null)
        {
            insetBottom = xmlInflateRegistry.getDimensionIntFloor(attrInsetBottom.getValue(),ctx);
        }

        int insetLeft = 0;
        DOMAttr attrInsetLeft = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetLeft");
        if (attrInsetLeft != null)
        {
            insetLeft = xmlInflateRegistry.getDimensionIntFloor(attrInsetLeft.getValue(),ctx);
        }

        // Si el drawable está definido como elemento hijo gana éste por delante del atributo drawable
        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();
        drawable = getChildDrawable(childList);

        if (drawable == null)
            throw new ItsNatDroidException("Drawable is not defined in drawable attribute or as a child element, processing inset of InsetDrawable");

        return new ElementDrawableRoot(new InsetDrawable(drawable,insetLeft, insetTop, insetRight, insetBottom),childList);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("clipOrientation".equals(name) || "drawable".equals(name) || "gravity".equals(name));
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
