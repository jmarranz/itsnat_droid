package org.itsnat.droid.impl.xmlinflated.animator;

import android.animation.Animator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ELIMINAR_ElementAnimator
{
    protected String tagName;
    protected ArrayList<ELIMINAR_ElementAnimator> childElemAnimatorList;
    protected Animator animator;

    public ELIMINAR_ElementAnimator(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagName()
    {
        return tagName;
    }

    public List<ELIMINAR_ElementAnimator> getChildElementAnimatorList()
    {
        return childElemAnimatorList;
    }

    public void initChildElementAnimatorList(int size)
    {
        this.childElemAnimatorList = new ArrayList<ELIMINAR_ElementAnimator>(size);
    }

    public void addChildElementAnimator(ELIMINAR_ElementAnimator child)
    {
        childElemAnimatorList.add(child);
    }

    public Animator getAnimator()
    {
        return animator;
    }

    public void setAnimator(Animator animator)
    {
        this.animator = animator;
    }

    public abstract ELIMINAR_ElementAnimator getRootElementAnimator();
}
