package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ListPopupWindow;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldMethod;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_Spinner_dropDownVerticalOffset extends AttrDescReflecFieldMethod<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_Spinner_dropDownVerticalOffset(ClassDescViewBased parent)
    {
        super(parent,"dropDownVerticalOffset","mPopup",ListPopupWindow.class,"setVerticalOffset",int.class);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int convertedValue = getDimensionIntFloor(attr.getValue(), attrCtx.getContext());

        try
        {
            callFieldMethod(view, convertedValue);
        }
        catch(ItsNatDroidException ex)
        {
            throw new ItsNatDroidException("Setting the attribute dropDownVerticalOffset is only valid in dropdown mode (not in dialog mode)",ex);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0dp",attrCtx);
    }
}
