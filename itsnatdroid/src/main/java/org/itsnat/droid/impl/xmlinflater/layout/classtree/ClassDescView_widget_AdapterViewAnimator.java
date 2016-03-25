package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AdapterViewAnimator_inoutAnimation;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_AdapterViewAnimator extends ClassDescViewBased
{
    public ClassDescView_widget_AdapterViewAnimator(ClassDescViewMgr classMgr,ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.AdapterViewAnimator",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "animateFirstView", true));
        addAttrDescAN(new AttrDescView_widget_AdapterViewAnimator_inoutAnimation(this,"inAnimation"));
        addAttrDescAN(new AttrDescReflecFieldSetBoolean(this, "loopViews", "mLoopViews", false));
        addAttrDescAN(new AttrDescView_widget_AdapterViewAnimator_inoutAnimation(this,"outAnimation"));
    }
}

