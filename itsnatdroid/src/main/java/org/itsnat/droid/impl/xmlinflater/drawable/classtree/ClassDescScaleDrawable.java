package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescScaleDrawable extends ClassDescDrawableWrapper<ScaleDrawable>
{

    public ClassDescScaleDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"scale");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem, elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, xmlInflaterContext, childList);

        XMLInflaterRegistry xmlInflaterRegistry = classMgr.getXMLInflaterRegistry();

        DOMAttr attrGravity = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "scaleGravity");
        int gravity = attrGravity != null ? AttrDesc.parseMultipleName(attrGravity.getValue(), GravityUtil.nameValueMap) : Gravity.LEFT; // Valor concreto no puede ser un recurso

        DOMAttr attrScaleHeight = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "scaleHeight");
        float scaleHeight = attrScaleHeight != null ? getPercent(xmlInflaterRegistry.getString(attrScaleHeight.getResourceDesc(), xmlInflaterContext)) : -1; // Se puede poner en un values como un <string> o como un <dimen> (aunque luego se procese como un String)

        DOMAttr attrScaleWidth = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "scaleWidth");
        float scaleWidth = attrScaleWidth != null ? getPercent(xmlInflaterRegistry.getString(attrScaleWidth.getResourceDesc(),xmlInflaterContext)) : -1;

        ScaleDrawable drawable = new ScaleDrawable(childDrawable,gravity,scaleWidth,scaleHeight);

        setCallback(childDrawable,drawable);

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

     private static float getPercent(String value)
     {
         // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/ScaleDrawable.java#ScaleDrawable.getPercent%28android.content.res.TypedArray%2Cint%29
         if (value != null)
         {
             if (value.endsWith("%"))
             {
                     String f = value.substring(0, value.length() - 1); // Se quita el %
                     return Float.parseFloat(f) / 100.0f;
             }
         }
         return -1;
     }


    @Override
    public void setCallback(Drawable childDrawable, ScaleDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("drawable".equals(name) || "scaleGravity".equals(name) ||  "scaleHeight".equals(name) ||  "scaleWidth".equals(name));
        }
        return false;
    }


    @Override
    public Class<ScaleDrawable> getDrawableOrElementDrawableClass()
    {
        return ScaleDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
