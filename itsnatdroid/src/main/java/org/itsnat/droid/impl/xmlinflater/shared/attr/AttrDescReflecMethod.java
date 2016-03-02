package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecMethod<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected MethodContainer method;

    //@SuppressWarnings("unchecked")
    public AttrDescReflecMethod(TclassDesc parent, String name, String methodName, Class classParam)
    {
        super(parent,name);
        this.method = new MethodContainer(parent.getDeclaredClass(),methodName,classParam != null ? new Class[]{classParam} : null);
    }

    public AttrDescReflecMethod(TclassDesc parent, String name, Class classParam)
    {
        this(parent,name,genMethodName(name),classParam);
    }

    public static String genMethodName(String name)
    {
        return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    protected void callMethod(TattrTarget target, Object convertedValue)
    {
        method.invoke(target, convertedValue);
    }

}
