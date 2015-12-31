package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
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
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, ctx, childList);

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        DOMAttr attrGravity = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleGravity");
        int gravity = attrGravity != null ? AttrDesc.parseMultipleName(attrGravity.getValue(), GravityUtil.nameValueMap) : Gravity.LEFT; // Valor concreto no puede ser un recurso

        DOMAttr attrScaleHeight = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleHeight");
        float scaleHeight = attrScaleHeight != null ? xmlInflateRegistry.getPercent(attrScaleHeight.getValue(), ctx) : -1;

        DOMAttr attrScaleWidth = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "scaleWidth");
        float scaleWidth = attrScaleWidth != null ? xmlInflateRegistry.getPercent(attrScaleWidth.getValue(),ctx) : -1;

        elementDrawableRoot.setDrawable(new ScaleDrawable(childDrawable,gravity,scaleWidth,scaleHeight));

        return elementDrawableRoot;
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
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
    public Class<ScaleDrawable> getDrawableOrElementDrawableClass()
    {
        return ScaleDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
