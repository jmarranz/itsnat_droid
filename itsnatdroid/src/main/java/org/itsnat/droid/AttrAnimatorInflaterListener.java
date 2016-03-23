package org.itsnat.droid;

import android.animation.Animator;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrAnimatorInflaterListener
{
    public boolean setAttribute(Page page, Animator obj, String namespace, String name, String value);
    public boolean removeAttribute(Page page, Animator obj, String namespace, String name);
}
