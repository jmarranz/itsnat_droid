package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.AnimationDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescAnimationDrawable extends ClassDescDrawableContainerBased<AnimationDrawable>
{
    public ClassDescAnimationDrawable(ClassDescDrawableMgr classMgr, ClassDescDrawable<? super AnimationDrawable> parentClass)
    {
        super(classMgr, "animation-list", parentClass);
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        AnimationDrawable drawable = new AnimationDrawable();

        for (int i = 0; i < itemList.size(); i++)
        {
            AnimationDrawableItem item = (AnimationDrawableItem) itemList.get(i);

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

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));

        // https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable.html
        // https://developer.android.com/guide/topics/resources/animation-resource.html
        // https://developer.android.com/guide/topics/graphics/drawable-animation.html

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "oneshot", "setOneShot", false));
        addAttrDescAN(new AttrDescReflecFieldMethodBoolean(this, "variablePadding", "mAnimationState", MiscUtil.resolveClass(DrawableContainer.class.getName() + "$DrawableContainerState"), "setVariablePadding", false));
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<AnimationDrawable>(this));
    }
}
