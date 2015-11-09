package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;
import android.widget.ListPopupWindow;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldMethod;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_Spinner_dropDownVerticalOffset extends AttrDescViewReflecFieldMethod
{
    public AttrDescView_widget_Spinner_dropDownVerticalOffset(ClassDescViewBased parent)
    {
        super(parent,"dropDownVerticalOffset","mPopup","setVerticalOffset",ListPopupWindow.class,int.class);
    }

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

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "0dp", xmlInflaterLayout, ctx);
    }
}
