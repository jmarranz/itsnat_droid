package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 20/04/2016.
 */
public interface ClassDescCallback<Tdrawable extends Drawable>
{
    public void setCallback(Drawable childDrawable, Tdrawable parentDrawable);
}
