package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_RatingBar extends ClassDescViewBased
{
    public ClassDescView_widget_RatingBar(ClassDescViewMgr classMgr,ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.RatingBar",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "isIndicator", false));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "numStars", 5));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "rating", 0f));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "stepSize", 0.5f));
    }
}

