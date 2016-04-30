package org.itsnat.droid;

import android.view.animation.Interpolator;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrInterpolatorInflaterListener
{
    public boolean setAttribute(Page page, Interpolator obj, String namespace, String name, String value);
    public boolean removeAttribute(Page page, Interpolator obj, String namespace, String name);
}
