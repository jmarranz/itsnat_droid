package org.itsnat.droid;

import android.view.animation.Animation;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrAnimationInflaterListener
{
    public boolean setAttribute(Page page, Animation obj, String namespace, String name, String value);
    public boolean removeAttribute(Page page, Animation obj, String namespace, String name);
}
