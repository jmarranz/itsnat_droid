package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;

import java.lang.reflect.Constructor;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorNoField extends ClassDescInterpolatorBased<Interpolator>
{
    protected Class<Interpolator> clasz;
    protected Constructor<? extends Interpolator> constructor;

    public ClassDescInterpolatorNoField(ClassDescInterpolatorMgr classMgr,String tagName)
    {
        super(classMgr, tagName, null);
    }

    @Override
    public Class<Interpolator> getDeclaredClass()
    {
        return clasz;
    }

    @Override
    protected void init()
    {
        initClass();

        super.init();
    }

    //@SuppressWarnings("unchecked")
    protected Class<Interpolator> initClass()
    {
        // El motivo de ésto es evitar usar el .class lo que obliga a cargar la clase aunque no se use, así la clase nativa se carga cuando se necesita por primera vez
        if (clasz == null)
        {
            this.clasz = resolveClass();
        }
        return clasz;
    }

    @SuppressWarnings("unchecked")
    protected Class<Interpolator> resolveClass()
    {
        String tagName = getClassOrDOMElemName();
        String className = Character.toUpperCase(tagName.charAt(0)) + tagName.substring(1);

        return (Class<Interpolator>) MiscUtil.resolveClass(className);
    }

    @Override
    protected Interpolator createResourceNative(Context ctx)
    {
        // Para tres clases no hace falta crear dinámicamente los objetos
        String tagName = getClassOrDOMElemName();
        if ("accelerateDecelerateInterpolator".equals(tagName))
            return new AccelerateDecelerateInterpolator(ctx,null);
        else if ("bounceInterpolator".equals(tagName))
            return new BounceInterpolator(ctx,null);
        else if ("linearInterpolator".equals(tagName))
            return new LinearInterpolator(ctx,null);
        else
            throw MiscUtil.internalError();
    }

}

