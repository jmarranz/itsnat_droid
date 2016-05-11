package org.itsnat.droid.impl.xmlinflater.animlayout;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescGridLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimationBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescLayoutAnimationMgr extends ClassDescMgr<ClassDescLayoutAnimationBased>
{
    public ClassDescLayoutAnimationMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescLayoutAnimationBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        ClassDescLayoutAnimation layoutAnimation = new ClassDescLayoutAnimation(this);
        addClassDesc(layoutAnimation);

        ClassDescGridLayoutAnimation gridLayoutAnimation = new ClassDescGridLayoutAnimation(this,layoutAnimation);
        addClassDesc(gridLayoutAnimation);
    }
}
