package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;
import android.view.ViewConfiguration;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarDefaultDelayBeforeFade
        extends AttrDescReflecMethodInt<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_scrollbarDefaultDelayBeforeFade(ClassDescViewBased parent)
    {
        super(parent,"scrollbarDefaultDelayBeforeFade","setScrollBarDefaultDelayBeforeFade",ViewConfiguration.getScrollDefaultDelay()); // ViewConfiguration.getScrollDefaultDelay() puede ser 300 por ejemplo
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
                    AttrDescView_view_View_scrollbarDefaultDelayBeforeFade.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
    }

}
