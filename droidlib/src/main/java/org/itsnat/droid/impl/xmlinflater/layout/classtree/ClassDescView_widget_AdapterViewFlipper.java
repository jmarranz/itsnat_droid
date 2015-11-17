package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_AdapterViewFlipper extends ClassDescViewBased
{
    public ClassDescView_widget_AdapterViewFlipper(ClassDescViewMgr classMgr,ClassDescView_widget_AdapterViewAnimator parentClass)
    {
        super(classMgr,"android.widget.AdapterViewFlipper",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodBoolean(this,"autoStart",false));
        addAttrDesc(new AttrDescReflecMethodInt(this,"flipInterval",10000));
    }
}

