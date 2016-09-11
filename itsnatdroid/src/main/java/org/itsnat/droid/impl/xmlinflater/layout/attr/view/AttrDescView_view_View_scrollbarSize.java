package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;

import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarSize
        extends AttrDescReflecMethodDimensionIntRound<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_scrollbarSize(ClassDescViewBased parent)
    {
        super(parent,"scrollbarSize","setScrollBarSize",0f);
    }

    @Override
    public void removeAttribute(View target, AttrLayoutContext attrCtx)
    {
        // Redefinimos totalmente porque el defaultValue depende del Context, no es fijo
        Context ctx = attrCtx.getContext();
        float defaultValue = ViewConfiguration.get(ctx).getScaledScrollBarSize(); // Puede ser por ejemplo 20px, dependerá del dispositivo
        callMethod(target, defaultValue);
    }


}
