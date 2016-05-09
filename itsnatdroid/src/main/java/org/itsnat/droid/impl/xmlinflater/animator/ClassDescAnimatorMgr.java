package org.itsnat.droid.impl.xmlinflater.animator;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorBased;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorObject;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorSet;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorValue;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescAnimatorMgr extends ClassDescMgr<ClassDescAnimatorBased>
{
    public ClassDescAnimatorMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    // @SuppressWarnings("unchecked")
    @Override
    public ClassDescAnimatorBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        ClassDescAnimator animator = new ClassDescAnimator(this);

        ClassDescAnimatorSet set = new ClassDescAnimatorSet(this,animator);
        addClassDesc(set);

        ClassDescAnimatorValue value = new ClassDescAnimatorValue(this,animator);
        addClassDesc(value);

            ClassDescAnimatorObject object = new ClassDescAnimatorObject(this, value);
            addClassDesc(object);

    }
}
