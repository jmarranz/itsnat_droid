package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.AnimationDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescAnimationDrawableItem extends ClassDescElementDrawableChildWithDrawable<LayerDrawableItem>
{
    public ClassDescAnimationDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"animation-list:item");
    }

    @Override
    public Class<AnimationDrawableItem> getDrawableOrElementDrawableClass()
    {
        return AnimationDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new AnimationDrawableItem(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDrawable(this, "drawable", "@null"));
        addAttrDesc(new AttrDescReflecMethodInt(this, "duration", -1));

    }

}
