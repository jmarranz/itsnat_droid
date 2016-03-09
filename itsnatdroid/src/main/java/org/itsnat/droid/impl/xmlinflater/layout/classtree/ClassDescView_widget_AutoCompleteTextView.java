package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.view.ViewGroup;
import android.widget.ListPopupWindow;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AutoCompleteTextView_completionHint;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AutoCompleteTextView_completionHintView;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionWithNameInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_AutoCompleteTextView extends ClassDescViewBased
{
    public ClassDescView_widget_AutoCompleteTextView(ClassDescViewMgr classMgr, ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.AutoCompleteTextView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_AutoCompleteTextView_completionHint(this));
        addAttrDescAN(new AttrDescView_widget_AutoCompleteTextView_completionHintView(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "completionThreshold", "setThreshold", 2));
        addAttrDescAN(new AttrDescReflecMethodId(this, "dropDownAnchor", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionWithNameInt(this, "dropDownHeight", (float) ViewGroup.LayoutParams.WRAP_CONTENT));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "dropDownHorizontalOffset", 0.0f));
        addAttrDescAN(new AttrDescReflecFieldMethodDrawable(this, "dropDownSelector", "mPopup", ListPopupWindow.class, "setListSelector", null)); // Hay un background por defecto de Android en ListPopupWindow aunque parece que por defecto se pone un null si no hay atributo
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "dropDownVerticalOffset", 0.0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionWithNameInt(this, "dropDownWidth", (float) ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}

