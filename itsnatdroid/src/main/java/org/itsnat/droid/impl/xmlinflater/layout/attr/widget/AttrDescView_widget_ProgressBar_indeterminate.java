package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 17/09/14.
 */
public class AttrDescView_widget_ProgressBar_indeterminate extends AttrDescReflecMethodBoolean<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ProgressBar_indeterminate(ClassDescViewBased parent, String name, boolean defaultValue)
    {
        super(parent,name,defaultValue);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
        {
            // setIndeterminate depende de indeterminateOnly que debe definirse antes, si el usuario lo pone después
            // setIndeterminate funcionará mal
            pendingViewPostCreateProcess.addPendingSetAttribsTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_widget_ProgressBar_indeterminate.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
    }
}
