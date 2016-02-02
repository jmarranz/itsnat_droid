package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValues;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesItemNormal;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesStyle;

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
        ClassDescValuesStyle style = new ClassDescValuesStyle(this);
        addClassDesc(style);

        // color, dimen etc
        ClassDescValuesItemNormal color = new ClassDescValuesItemNormal(this,"color");
        addClassDesc(color);

        ClassDescValuesItemNormal dimen = new ClassDescValuesItemNormal(this,"dimen");
        addClassDesc(dimen);
    }
}
