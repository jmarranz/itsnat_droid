package org.itsnat.itsnatdroidtest.testact;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.Page;

/**
 * Created by jmarranz on 30/04/2016.
 */
public abstract class TestSetupBase implements AttrResourceInflaterListener
{
    public static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";

    @Override
    public boolean setAttribute(Page page, Object resource, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android " + resource.getClass().getName() + " attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND " + resource.getClass().getName() + " ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, Object resource, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android " + resource.getClass().getName() + " attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND " + resource.getClass().getName() + " ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

}
