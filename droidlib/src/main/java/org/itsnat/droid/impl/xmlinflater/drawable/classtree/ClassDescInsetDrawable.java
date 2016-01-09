package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescInsetDrawable extends ClassDescDrawableWrapper<InsetDrawable>
{
    public ClassDescInsetDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"inset");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, XMLInflaterDrawable inflaterDrawable)
    {
        Context ctx = inflaterDrawable.getContext();
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, childList);

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        DOMAttr attrInsetTop = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetTop");
        int insetTop = attrInsetTop != null ? xmlInflateRegistry.getDimensionIntFloor(attrInsetTop, inflaterDrawable) : 0;

        DOMAttr attrInsetRight = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetRight");
        int insetRight = attrInsetRight != null ? xmlInflateRegistry.getDimensionIntFloor(attrInsetRight,inflaterDrawable) : 0;

        DOMAttr attrInsetBottom = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetBottom");
        int insetBottom = attrInsetBottom != null ? xmlInflateRegistry.getDimensionIntFloor(attrInsetBottom,inflaterDrawable) : 0;

        DOMAttr attrInsetLeft = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "insetLeft");
        int insetLeft = attrInsetLeft != null ? xmlInflateRegistry.getDimensionIntFloor(attrInsetLeft,inflaterDrawable) : 0;

        elementDrawableRoot.setDrawable(new InsetDrawable(childDrawable,insetLeft, insetTop, insetRight, insetBottom));

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
            return ("drawable".equals(name) || "insetTop".equals(name) || "insetRight".equals(name) || "insetBottom".equals(name) || "insetLeft".equals(name));
        }
        return false;
    }


    @Override
    public Class<InsetDrawable> getDrawableOrElementDrawableClass()
    {
        return InsetDrawable.class;
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescDrawable_Drawable_visible<InsetDrawable>(this));
    }
}
