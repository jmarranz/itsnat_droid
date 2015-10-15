package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescDrawableReflecMethod<TdrawableOrElementDrawable> extends AttrDescDrawable<TdrawableOrElementDrawable>
{
    protected MethodContainer method;

    @SuppressWarnings("unchecked")
    public AttrDescDrawableReflecMethod(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName, Class classParam)
    {
        super(parent,name);
        this.method = new MethodContainer(parent.getDrawableOrElementDrawableClass(),methodName,classParam != null ? new Class[]{classParam} : null);
    }

    public AttrDescDrawableReflecMethod(ClassDescDrawableOrElementDrawableChild parent, String name, Class classParam)
    {
        this(parent,name,genMethodName(name),classParam);
    }

    public static String genMethodName(String name)
    {
        return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    protected void callMethod(TdrawableOrElementDrawable drawable, Object convertedValue)
    {
        method.invoke(drawable, convertedValue);
    }

}
