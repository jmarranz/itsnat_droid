package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.view.ViewGroup;
import android.widget.ListPopupWindow;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AutoCompleteTextView_completionHint;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetId;
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

        addAttrDesc(new AttrDescView_widget_AutoCompleteTextView_completionHint(this));
        addAttrDesc(new AttrDescReflecFieldSetId(this,"completionHintView","mHintResource",null)); // Android tiene un recurso por defecto
        addAttrDesc(new AttrDescReflecMethodInt(this,"completionThreshold","setThreshold",2));
        addAttrDesc(new AttrDescReflecMethodId(this,"dropDownAnchor",-1));
        addAttrDesc(new AttrDescReflecMethodDimensionWithNameInt(this,"dropDownHeight",(float)ViewGroup.LayoutParams.WRAP_CONTENT));
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this,"dropDownHorizontalOffset",0.0f));
        addAttrDesc(new AttrDescReflecFieldMethodDrawable(this,"dropDownSelector","mPopup","setListSelector",ListPopupWindow.class,null)); // Hay un background por defecto de Android en ListPopupWindow aunque parece que por defecto se pone un null si no hay atributo
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this,"dropDownVerticalOffset",0.0f));
        addAttrDesc(new AttrDescReflecMethodDimensionWithNameInt(this,"dropDownWidth",(float)ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}

