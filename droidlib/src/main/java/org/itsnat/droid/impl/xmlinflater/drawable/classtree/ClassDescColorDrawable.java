package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescColorDrawable extends ClassDescElementDrawableRoot<ColorDrawable>
{
    public ClassDescColorDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"color");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        Drawable drawable = new ColorDrawable();
        return new ElementDrawableRoot(drawable);
    }

    @Override
    public Class<ColorDrawable> getDrawableOrElementDrawableClass()
    {
        return ColorDrawable.class;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodColor(this, "color", 0));
    }

}
