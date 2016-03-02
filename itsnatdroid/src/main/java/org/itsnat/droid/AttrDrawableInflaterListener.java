package org.itsnat.droid;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrDrawableInflaterListener
{
    public boolean setAttribute(Page page, Drawable obj, String namespace, String name, String value);
    public boolean removeAttribute(Page page, Drawable obj, String namespace, String name);
}
