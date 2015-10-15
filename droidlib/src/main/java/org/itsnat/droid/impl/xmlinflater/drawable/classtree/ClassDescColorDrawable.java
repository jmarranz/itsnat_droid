package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodColor;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescColorDrawable extends ClassDescRootElementDrawable<ColorDrawable>
{
    public ClassDescColorDrawable(ClassDescDrawableOrElementDrawableChildMgr classMgr)
    {
        super(classMgr,"color");
    }

    @Override
    public ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        Drawable drawable = new ColorDrawable();
        return new ElementDrawableRoot(drawable,null);
    }

    @Override
    public Class<ColorDrawable> getDrawableOrElementDrawableClass()
    {
        return ColorDrawable.class;
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodColor(this, "color"));
    }

}
