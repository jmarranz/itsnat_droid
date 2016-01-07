package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesColor;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesDimen;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

/**
 * Created by jmarranz on 07/01/2016.
 */
public abstract class ClassDescValuesChildNoChildElem<T extends ElementValuesChildNoChildElem> extends ClassDescValues<ElementValuesChildNoChildElem>
{
    public ClassDescValuesChildNoChildElem(ClassDescValuesMgr classMgr,String elemName)
    {
        super(classMgr, elemName, null);
    }

    public abstract ElementValuesChildNoChildElem createElementValuesChildNoChildren(ElementValues parentChildValues, String name, String value);
}
