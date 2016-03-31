package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
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
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, childList);

        XMLInflaterRegistry xmlInflaterRegistry = classMgr.getXMLInflaterRegistry();

        DOMAttr attrInsetTop = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "insetTop");
        int insetTop = attrInsetTop != null ? xmlInflaterRegistry.getDimensionIntFloor(attrInsetTop.getResourceDesc(), inflaterDrawable) : 0;

        DOMAttr attrInsetRight = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "insetRight");
        int insetRight = attrInsetRight != null ? xmlInflaterRegistry.getDimensionIntFloor(attrInsetRight.getResourceDesc(),inflaterDrawable) : 0;

        DOMAttr attrInsetBottom = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "insetBottom");
        int insetBottom = attrInsetBottom != null ? xmlInflaterRegistry.getDimensionIntFloor(attrInsetBottom.getResourceDesc(),inflaterDrawable) : 0;

        DOMAttr attrInsetLeft = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "insetLeft");
        int insetLeft = attrInsetLeft != null ? xmlInflaterRegistry.getDimensionIntFloor(attrInsetLeft.getResourceDesc(),inflaterDrawable) : 0;

        elementDrawableRoot.setDrawable(new InsetDrawable(childDrawable,insetLeft, insetTop, insetRight, insetBottom));

        return elementDrawableRoot;
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI))
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
