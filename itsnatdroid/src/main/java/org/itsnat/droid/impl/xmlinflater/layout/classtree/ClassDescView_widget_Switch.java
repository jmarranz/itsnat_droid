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

        // android:showText es level 21
        // android:splitTrack es level 21

        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "switchMinWidth", "mSwitchMinWidth", 0));
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "switchPadding", "mSwitchPadding", 0));
        addAttrDescAN(new AttrDescView_widget_Switch_switchTextAppearance(this));
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "textOff", null)); // Adnroid tiene un texto por defecto
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "textOn", null)); // Adnroid tiene un texto por defecto
        addAttrDescAN(new AttrDescView_widget_Switch_textStyle(this));
        addAttrDescAN(new AttrDescReflecFieldSetDrawable(this, "thumb", "mThumbDrawable", null)); // Android tiene un drawable por defecto
        addAttrDescAN(new AttrDescReflecFieldSetDimensionIntRound(this, "thumbTextPadding", "mThumbTextPadding", 0));
        // android:thumbTint es level 21
        // android:thumbTintMode es level 21
        addAttrDescAN(new AttrDescReflecFieldSetDrawable(this, "track", "mTrackDrawable", null)); // Android tiene un drawable por defecto
        // android:trackTint es level 21
        // android:trackTintMode es level 21
        addAttrDescAN(new AttrDescView_widget_Switch_typeface(this));

    }
}

