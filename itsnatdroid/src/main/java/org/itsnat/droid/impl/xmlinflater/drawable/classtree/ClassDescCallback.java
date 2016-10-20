package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 20/04/2016.
 */
public interface ClassDescCallback
{
    public void setCallback(Drawable childDrawable, Drawable.Callback parentDrawable);
}
