package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
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
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
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

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));


        addAttrDescAN(new AttrDescReflecMethodColor(this, "color", 0));
    }

}
