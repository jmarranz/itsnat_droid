package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ListPopupWindow;

import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodDrawable;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AutoCompleteTextView_dropDownSelector extends AttrDescReflecFieldMethodDrawable<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_AutoCompleteTextView_dropDownSelector(ClassDescViewBased parent)
    {
        super(parent,"dropDownSelector","mPopup",ListPopupWindow.class,"setListSelector",null); // Hay un background por defecto de Android en ListPopupWindow aunque parece que por defecto se pone un null si no hay atributo
    }

}
