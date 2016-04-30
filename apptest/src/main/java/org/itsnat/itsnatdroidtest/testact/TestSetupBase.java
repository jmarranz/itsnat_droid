package org.itsnat.itsnatdroidtest.testact;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import org.itsnat.droid.AttrAnimationInflaterListener;
import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrInterpolatorInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.OnPageLoadListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.Page;

/**
 * Created by jmarranz on 30/04/2016.
 */
public abstract class TestSetupBase implements AttrLayoutInflaterListener,AttrDrawableInflaterListener,AttrAnimationInflaterListener,AttrAnimatorInflaterListener,AttrInterpolatorInflaterListener
{
    public static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";


    @Override
    public boolean setAttribute(Page page, View view, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android layout attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND LAYOUT ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, View view, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android layout attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND LAYOUT ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

    @Override
    public boolean setAttribute(Page page, Drawable obj, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android drawable attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND DRAWABLE ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, Drawable obj, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android drawable attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND DRAWABLE ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

    @Override
    public boolean setAttribute(Page page, Animation obj, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Animation attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND ANIMATION ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, Animation obj, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Animation attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND ANIMATION ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

    @Override
    public boolean setAttribute(Page page, Animator obj, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Animator attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND ANIMATOR ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, Animator obj, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Animator attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND ANIMATOR ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

    @Override
    public boolean setAttribute(Page page, Interpolator obj, String namespace, String name, String value)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Interpolator attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND INTERPOLATOR ATTRIBUTE (setAttribute): " + namespace + " " + name + " " + value);

        return true;
    }

    @Override
    public boolean removeAttribute(Page page, Interpolator obj, String namespace, String name)
    {
        if (NAMESPACE_ANDROID.equals(namespace))
            throw new RuntimeException("Android Interpolator attribute not processed: " + name); // Esto es para detectar que no se está procesando por lo que sea

        System.out.println("NOT FOUND INTERPOLATOR ATTRIBUTE (removeAttribute): " + namespace + " " + name);

        return true;
    }

}
