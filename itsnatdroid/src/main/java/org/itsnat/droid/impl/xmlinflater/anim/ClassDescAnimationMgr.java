package org.itsnat.droid.impl.xmlinflater.anim;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationBased;

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

    @Override
    public ClassDescAnimationBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        /*
        ClassDescAnimator animator = new ClassDescAnimator(this);

        ClassDescAnimatorSet set = new ClassDescAnimatorSet(this,animator);
        addClassDesc(set);

        ClassDescAnimatorValue value = new ClassDescAnimatorValue(this,animator);
        addClassDesc(value);

            ClassDescAnimatorObject object = new ClassDescAnimatorObject(this,value);
            addClassDesc(object);
        */
    }
}
