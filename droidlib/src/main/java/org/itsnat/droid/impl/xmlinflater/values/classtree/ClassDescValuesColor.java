package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildNoChildren;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesColor;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesColor extends ClassDescValues<ElementValuesColor>
{
    public ClassDescValuesColor(ClassDescValuesMgr classMgr)
    {
        super(classMgr, "color", null);
    }

    @Override
    public ElementValuesChildNoChildren createElementValuesChildNoChildren(ElementValues parentChildValues, String name, String value)
    {
        return new ElementValuesColor(parentChildValues, name, value);
    }

}
