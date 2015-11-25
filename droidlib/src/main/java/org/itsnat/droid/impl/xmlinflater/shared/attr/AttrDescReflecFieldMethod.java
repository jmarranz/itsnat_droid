package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldMethod<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer<Object> field;
    protected MethodContainer method;

    public AttrDescReflecFieldMethod(TclassDesc parent, String name,String fieldName,Class methodClass,String methodName,Class classParam)
    {
        super(parent,name);
        this.field = new FieldContainer<Object>(parent.getDeclaredClass(),fieldName);
        this.method = new MethodContainer(methodClass,methodName,classParam != null ? new Class[]{classParam} : null);
    }

    protected void callFieldMethod(TattrTarget target, Object convertedValue)
    {
        Object fieldValue = field.get(target);
        method.invoke(fieldValue,convertedValue);
    }
}
