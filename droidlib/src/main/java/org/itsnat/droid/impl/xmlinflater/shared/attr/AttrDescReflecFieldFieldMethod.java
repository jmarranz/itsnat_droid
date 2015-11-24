package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldFieldMethod<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer<Object> field1;
    protected FieldContainer<Object> field2;
    protected MethodContainer<Void> method;

    public AttrDescReflecFieldFieldMethod(TclassDesc parent, String name, String fieldName1, String fieldName2, String methodName, Class<?> field2Class, Class<?> methodClass, Class<?> paramClass)
    {
        super(parent,name);

        this.field1 = new FieldContainer<Object>(parent.getDeclaredClass(),fieldName1);
        this.field2 = new FieldContainer<Object>(field2Class,fieldName2);
        this.method = new MethodContainer<Void>(methodClass,methodName,new Class[]{paramClass});
    }

    protected void callFieldFieldMethod(TattrTarget target,Object convertedValue)
    {
        Object fieldValue1 = field1.get(target);
        Object fieldValue2 = field2.get(fieldValue1);
        method.invoke(fieldValue2, convertedValue);
    }

}
