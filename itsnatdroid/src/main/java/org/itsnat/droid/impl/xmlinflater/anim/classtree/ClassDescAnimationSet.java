package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_AnimationSet_shareInterpolator;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationSet extends ClassDescAnimationBased<AnimationSet>
{
    // From AnimationSet
    private static final int PROPERTY_FILL_AFTER_MASK         = 0x1;
    private static final int PROPERTY_FILL_BEFORE_MASK        = 0x2;
    private static final int PROPERTY_REPEAT_MODE_MASK        = 0x4;
    private static final int PROPERTY_START_OFFSET_MASK       = 0x8;
    private static final int PROPERTY_DURATION_MASK           = 0x20;

    protected FieldContainer<Integer> fieldMFlags;

    public ClassDescAnimationSet(ClassDescAnimationMgr classMgr, ClassDescAnimation parentClass)
    {
        super(classMgr, "set", parentClass);

        this.fieldMFlags = new FieldContainer<Integer>(AnimationSet.class,"mFlags");
    }

    @Override
    public Class<AnimationSet> getDeclaredClass()
    {
        return AnimationSet.class;
    }

    @Override
    protected AnimationSet createAnimationNative(Context ctx)
    {
        return new AnimationSet(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }

    protected void fillAnimationAttributes(Animation animation, DOMElemAnimation domElement, AttrAnimationContext attrCtx)
    {
        super.fillAnimationAttributes(animation,domElement,attrCtx);

        // Ver también AttrDescAnimation_view_animation_AnimationSet_shareInterpolator

        AnimationSet animationSet = (AnimationSet)animation;

        int flags = fieldMFlags.get(animationSet);

        DOMAttr attrDuration = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "duration");
        if (attrDuration != null) flags |= PROPERTY_DURATION_MASK;

        DOMAttr attrFillBefore = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "fillBefore");
        if (attrFillBefore != null) flags |= PROPERTY_FILL_BEFORE_MASK;

        DOMAttr attrFillAfter = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "fillAfter");
        if (attrFillAfter != null) flags |= PROPERTY_FILL_AFTER_MASK;

        DOMAttr attrRepeatMode = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "repeatMode");
        if (attrRepeatMode != null) flags |= PROPERTY_REPEAT_MODE_MASK;

        DOMAttr attrStartOffset = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "startOffset");
        if (attrStartOffset != null) flags |= PROPERTY_START_OFFSET_MASK;

        fieldMFlags.set(animationSet,flags);
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescAnimation_view_animation_AnimationSet_shareInterpolator(this));

    }
}
