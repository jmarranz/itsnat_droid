package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.AnimationSet;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationSet extends ClassDescAnimationBased<AnimationSet>
{
    public ClassDescAnimationSet(ClassDescAnimationMgr classMgr, ClassDescAnimation parentClass)
    {
        super(classMgr, "set", parentClass);
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

    protected void init()
    {
        super.init();

        //addAttrDescAN(new AttrDescReflecMethodInt(this, "repeatCount", 0)); // Se puede llamar independientemente de valueFrom y valueTo
        //addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "repeatMode", int.class, repeatModeMap, "restart"));  // "
    }
}
