package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_textAllCaps extends AttrDescReflecMethodBoolean<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_textAllCaps(ClassDescViewBased parent)
    {
        super(parent,"textAllCaps","setAllCaps",false);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingViewCreateProcess pendingViewCreateProcess = attrCtx.getPendingViewCreateProcess();
        if (pendingViewCreateProcess != null)
        {
            // Delegamos al final porque es necesario que antes se ejecute textIsSelectable="false"
            pendingViewCreateProcess.addPendingSetAttribsTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_widget_TextView_textAllCaps.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
    }

}
