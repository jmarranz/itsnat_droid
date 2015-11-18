package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_Switch_switchTextAppearance;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_Switch_textStyle;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_Switch_typeface;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_Switch extends ClassDescViewBased
{
    public ClassDescView_widget_Switch(ClassDescViewMgr classMgr,ClassDescView_widget_CompoundButton parentClass)
    {
        super(classMgr,"android.widget.Switch",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"switchMinWidth","mSwitchMinWidth",0));
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"switchPadding","mSwitchPadding",0));
        addAttrDesc(new AttrDescView_widget_Switch_switchTextAppearance(this));
        addAttrDesc(new AttrDescReflecMethodCharSequence(this,"textOff",null)); // Adnroid tiene un texto por defecto
        addAttrDesc(new AttrDescReflecMethodCharSequence(this,"textOn",null)); // Adnroid tiene un texto por defecto
        addAttrDesc(new AttrDescView_widget_Switch_textStyle(this));
        addAttrDesc(new AttrDescReflecFieldSetDrawable(this,"thumb","mThumbDrawable",null)); // Android tiene un drawable por defecto
        addAttrDesc(new AttrDescReflecFieldSetDimensionIntRound(this,"thumbTextPadding","mThumbTextPadding",0));
        addAttrDesc(new AttrDescReflecFieldSetDrawable(this,"track","mTrackDrawable",null)); // Android tiene un drawable por defecto
        addAttrDesc(new AttrDescView_widget_Switch_typeface(this));

    }
}

