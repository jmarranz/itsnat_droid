package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesColor;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesDimen;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesDimen extends ClassDescValuesChildNoChildElem<ElementValuesColor>
{
    public ClassDescValuesDimen(ClassDescValuesMgr classMgr)
    {
        super(classMgr, "dimen");
    }

    @Override
    public ElementValuesChildNoChildElem createElementValuesChildNoChildren(ElementValues parentChildValues, String name, String value)
    {
        return new ElementValuesDimen(parentChildValues, name, value);
    }

}
