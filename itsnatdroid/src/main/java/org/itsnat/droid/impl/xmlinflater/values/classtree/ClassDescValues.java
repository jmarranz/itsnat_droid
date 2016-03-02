package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChild;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescValues<T extends ElementValuesChild> extends ClassDesc<ElementValuesChild>
{
    public ClassDescValues(ClassDescValuesMgr classMgr, String tagName, ClassDescValues<? super ElementValuesChild> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescValuesMgr getClassDescValuesMgr()
    {
        return (ClassDescValuesMgr) classMgr;
    }

    public ClassDescValues getParentClassDescValues()
    {
        return (ClassDescValues) getParentClassDesc();
    }

    @Override
    public Class<ElementValuesChild> getDeclaredClass()
    {
        return null; // No se necesita, no se implementa
    }

    @Override
    protected void init()
    {
        // initClass();

        super.init();
    }

}
