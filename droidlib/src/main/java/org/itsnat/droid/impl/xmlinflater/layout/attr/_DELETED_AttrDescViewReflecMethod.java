package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class _DELETED_AttrDescViewReflecMethod extends AttrDescView
{
    protected MethodContainer method;

    @SuppressWarnings("unchecked")
    public _DELETED_AttrDescViewReflecMethod(ClassDescViewBased parent, String name, String methodName, Class classParam)
    {
        super(parent,name);
        this.method = new MethodContainer(parent.getDeclaredClass(),methodName,classParam != null ? new Class[]{classParam} : null);
    }

    public _DELETED_AttrDescViewReflecMethod(ClassDescViewBased parent, String name, Class classParam)
    {
        this(parent,name,genMethodName(name),classParam);
    }

    public static String genMethodName(String name)
    {
        return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    protected void callMethod(View view, Object convertedValue)
    {
        method.invoke(view, convertedValue);
    }

}
