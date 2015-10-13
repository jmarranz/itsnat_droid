package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodId;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawableItem extends ClassDescElementDrawableChild<StateListDrawableItem>
{
    public ClassDescStateListDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"selector:item");
    }

    @Override
    public Class<StateListDrawableItem> getDrawableClass()
    {
        return StateListDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createChildElementDrawable(DOMElement domElement,XMLInflaterDrawable inflaterDrawable,ElementDrawable parentChildDrawable,Context ctx)
    {
        return new StateListDrawableItem();
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodDrawable(this, "drawable"));


        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_pressed"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_focused"));
    }

}
