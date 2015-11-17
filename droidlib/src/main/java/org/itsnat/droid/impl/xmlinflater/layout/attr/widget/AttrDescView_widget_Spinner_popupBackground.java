package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ListPopupWindow;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethodDrawable;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_Spinner_popupBackground extends AttrDescReflecFieldMethodDrawable<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_Spinner_popupBackground(ClassDescViewBased parent)
    {
        super(parent,"popupBackground","mPopup","setBackgroundDrawable",ListPopupWindow.class,null); // Hay un background por defecto de Android en ListPopupWindow aunque parece que por defecto se pone un null si no hay atributo
    }

    @Override
    protected void callFieldMethod(View view, Object convertedValue)
    {
        try
        {
            super.callFieldMethod(view, convertedValue);
        }
        catch(ItsNatDroidException ex)
        {
            throw new ItsNatDroidException("Setting the attribute popupBackground only is valid in dropdown mode (not in dialog mode)",ex);
        }
    }
}
