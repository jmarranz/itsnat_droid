package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValues;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesItemNormal;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesStyle;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_COLOR;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_BOOL;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DIMEN;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_STRING;

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
        ClassDescValuesItemNormal bool = new ClassDescValuesItemNormal(this,TYPE_BOOL);
        addClassDesc(bool);

        ClassDescValuesItemNormal color = new ClassDescValuesItemNormal(this,TYPE_COLOR);
        addClassDesc(color);

        ClassDescValuesItemNormal dimen = new ClassDescValuesItemNormal(this,TYPE_DIMEN);
        addClassDesc(dimen);

        ClassDescValuesItemNormal string = new ClassDescValuesItemNormal(this,TYPE_STRING);
        addClassDesc(string);
    }
}
