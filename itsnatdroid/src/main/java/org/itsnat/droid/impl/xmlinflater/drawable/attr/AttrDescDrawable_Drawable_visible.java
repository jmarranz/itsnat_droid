package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_Drawable_visible extends AttrDesc<ClassDescElementDrawable,Drawable,AttrDrawableContext>
{

    public AttrDescDrawable_Drawable_visible(ClassDescElementDrawable parent)
    {
        super(parent,"visible");
    }

    @Override
    public void setAttribute(Drawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        boolean visible = getBoolean(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        boolean restart = true; // Ni idea
        draw.setVisible(visible, restart);
    }

    @Override
    public void removeAttribute(Drawable target, AttrDrawableContext attrCtx)
    {
        setAttributeToRemove(target, "true", attrCtx);
    }
}
