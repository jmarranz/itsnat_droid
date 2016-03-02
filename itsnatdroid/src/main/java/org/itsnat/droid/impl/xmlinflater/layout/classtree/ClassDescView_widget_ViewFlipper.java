package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ViewFlipper extends ClassDescViewBased
{
    public ClassDescView_widget_ViewFlipper(ClassDescViewMgr classMgr,ClassDescView_widget_ViewAnimator parentClass)
    {
        super(classMgr,"android.widget.ViewFlipper",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "autoStart", false));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "flipInterval", 3000));
    }
}

