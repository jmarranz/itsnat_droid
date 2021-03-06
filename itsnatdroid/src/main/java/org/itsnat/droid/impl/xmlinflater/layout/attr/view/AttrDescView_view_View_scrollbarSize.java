package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
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
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
        {
            // Delegamos al final para que esté totalmente claro si hay o no scrollbars
            pendingViewPostCreateProcess.addPendingSetAttribsTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_view_View_scrollbarSize.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
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
