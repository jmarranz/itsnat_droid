package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodInt;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawableItem extends ClassDescElementDrawableChildWithDrawable<StateListDrawableItem>
{
    public ClassDescLevelListDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"level-list:item");
    }

    @Override
    public Class<LevelListDrawableItem> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new LevelListDrawableItem(parentChildDrawable);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodDrawable(this, "drawable"));

        addAttrDesc(new AttrDescDrawableReflecMethodInt(this, "maxLevel"));
        addAttrDesc(new AttrDescDrawableReflecMethodInt(this, "minLevel"));
    }

}
