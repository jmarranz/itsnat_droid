package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDrawable;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawableItem extends ClassDescElementDrawableChildNormal<StateListDrawableItem>
{
    public ClassDescStateListDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"selector:item");
    }

    @Override
    public Class<StateListDrawableItem> getDrawableOrElementDrawableClass()
    {
        return StateListDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createChildElementDrawable(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new StateListDrawableItem(parentChildDrawable);
    }

    protected void init()
    {
        super.init();


        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "constantSize"));

        addAttrDesc(new AttrDescDrawableReflecMethodDrawable(this, "drawable"));

        // Documentados en https://developer.android.com/guide/topics/resources/drawable-resource.html#StateList
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_pressed"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_focused"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_hovered"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_selected"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_checkable"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_checked"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_enabled"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_activated"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_window_focused"));

        // Presentes en source code y en el javadoc de ICS (level 15) pero no en el tutorial
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/StateListDrawable.java
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_active"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_single"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_first"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_middle"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "state_last"));

        // En teoria hay más android.R.attr.state_* en level 15 pero no tenemos ni idea de como funcionan

        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "variablePadding"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "visible"));
    }

}
