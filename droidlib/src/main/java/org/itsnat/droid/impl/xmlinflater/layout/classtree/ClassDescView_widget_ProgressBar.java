package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldSetInt;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_indeterminate;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_indeterminateBehavior;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_interpolator;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ProgressBar_progressDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ProgressBar extends ClassDescViewBased
{
    public ClassDescView_widget_ProgressBar(ClassDescViewMgr classMgr,ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.widget.ProgressBar",parentClass);
    }

    protected void init()
    {
        super.init();

        // android:animationResolution es la traca, se procesa por ejemplo en 4.0.3 pero NI RASTRO a partir de 4.1.1 aunque sigue estando en la documentación
        addAttrDesc(new AttrDescView_widget_ProgressBar_indeterminate(this,"indeterminate",true));
        addAttrDesc(new AttrDescView_widget_ProgressBar_indeterminateBehavior(this));
        addAttrDesc(new AttrDescReflecMethodDrawable(this,"indeterminateDrawable",null)); // Android tiene un drawable por defecto
        addAttrDesc(new AttrDescViewReflecFieldSetInt(this,"indeterminateDuration","mDuration",4000));
        addAttrDesc(new AttrDescReflecFieldSetBoolean(this,"indeterminateOnly","mOnlyIndeterminate",false));
        addAttrDesc(new AttrDescView_widget_ProgressBar_interpolator(this));
        addAttrDesc(new AttrDescReflecMethodInt(this,"max",100));
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"maxHeight","mMaxHeight",48));
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"maxWidth","mMaxWidth",48));
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"minHeight","mMinHeight",24));
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"minWidth","mMinWidth",24));
        // android:mirrorForRtl es de un Level superior a 15
        addAttrDesc(new AttrDescReflecMethodInt(this,"progress",0));
        addAttrDesc(new AttrDescView_widget_ProgressBar_progressDrawable(this));
        addAttrDesc(new AttrDescReflecMethodInt(this,"secondaryProgress",0));
    }

}

