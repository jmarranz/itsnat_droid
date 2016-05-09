package org.itsnat.droid.impl.xmlinflater.anim;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationAlpha;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationBased;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationRotate;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationScale;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationSet;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationTranslate;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescAnimationMgr extends ClassDescMgr<ClassDescAnimationBased>
{
    public ClassDescAnimationMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    // @SuppressWarnings("unchecked")
    @Override
    public ClassDescAnimationBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        ClassDescAnimation animation = new ClassDescAnimation(this);

        ClassDescAnimationAlpha alpha = new ClassDescAnimationAlpha(this,animation);
        addClassDesc(alpha);

        ClassDescAnimationRotate rotate = new ClassDescAnimationRotate(this,animation);
        addClassDesc(rotate);

        ClassDescAnimationScale scale = new ClassDescAnimationScale(this,animation);
        addClassDesc(scale);

        ClassDescAnimationSet set = new ClassDescAnimationSet(this,animation);
        addClassDesc(set);

        ClassDescAnimationTranslate translate = new ClassDescAnimationTranslate(this,animation);
        addClassDesc(translate);

    }
}
