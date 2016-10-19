package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescColorDrawable extends ClassDescElementDrawableBased<ColorDrawable>
{
    public ClassDescColorDrawable(ClassDescDrawableMgr classMgr, ClassDescElementDrawableBased<? super ColorDrawable> parent)
    {
        super(classMgr,"color",parent);
    }


    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        Drawable drawable = new ColorDrawable();
        return new ElementDrawableChildRoot(drawable);
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
