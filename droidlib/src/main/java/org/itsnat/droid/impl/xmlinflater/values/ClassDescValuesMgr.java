package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValues;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesChildStyle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescValuesMgr extends ClassDescMgr<ClassDescValues>
{
    public ClassDescValuesMgr(XMLInflateRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescValues get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        ClassDescValuesChildStyle style = new ClassDescValuesChildStyle(this);
        addClassDesc(style);

        // color, dimen etc
        ClassDescValuesChildNoChildElem color = new ClassDescValuesChildNoChildElem(this,"color");
        addClassDesc(color);

        ClassDescValuesChildNoChildElem dimen = new ClassDescValuesChildNoChildElem(this,"dimen");
        addClassDesc(dimen);
    }
}
