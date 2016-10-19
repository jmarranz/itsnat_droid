package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.AnimationDrawableChildItem;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescAnimationDrawable extends ClassDescElementDrawableBased<AnimationDrawable>
{
    public ClassDescAnimationDrawable(ClassDescDrawableMgr classMgr, ClassDescDrawableContainer parentClass )
    {
        super(classMgr, "animation-list", parentClass);
    }


    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> itemList = elementDrawableRoot.getElementDrawableChildList();

        AnimationDrawable drawable = new AnimationDrawable();

        for (int i = 0; i < itemList.size(); i++)
        {
            AnimationDrawableChildItem item = (AnimationDrawableChildItem) itemList.get(i);

            Integer durationObj = item.getDuration();
            int duration = durationObj != null ? durationObj.intValue() : -1;

            if (duration < 0) {
                throw new ItsNatDroidException("<animation-list><item> tag requires a 'duration' attribute");
            }

            Drawable itemDrawable = item.getDrawable();

            setCallback(itemDrawable,drawable);

            drawable.addFrame(itemDrawable, duration);
        }

        elementDrawableRoot.setDrawable(drawable);


        return elementDrawableRoot;
    }

    @Override
    public void setCallback(Drawable childDrawable, AnimationDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }

    @Override
    public Class<AnimationDrawable> getDrawableOrElementDrawableClass()
    {
        return AnimationDrawable.class;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        // https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable.html
        // https://developer.android.com/guide/topics/resources/animation-resource.html
        // https://developer.android.com/guide/topics/graphics/drawable-animation.html

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "oneshot", "setOneShot", false));
        addAttrDescAN(new AttrDescReflecFieldMethodBoolean(this, "variablePadding", "mAnimationState", MiscUtil.resolveClass(DrawableContainer.class.getName() + "$DrawableContainerState"), "setVariablePadding", false));
        // está arriba:  addAttrDescAN(new AttrDescDrawable_Drawable_visible<AnimationDrawable>(this));
    }

}
