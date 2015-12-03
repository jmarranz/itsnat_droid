package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_LinearLayout_baselineAlignedChildIndex extends AttrDescReflecMethodInt<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_LinearLayout_baselineAlignedChildIndex(ClassDescViewBased parent)
    {
        super(parent,"baselineAlignedChildIndex",-1);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr,final AttrLayoutContext attrCtx)
    {
        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = attrCtx.getPendingPostInsertChildrenTasks();
        if (pendingPostInsertChildrenTasks != null)
        {
            // Necesitamos añadir los children antes para poder referenciarlos por su índice de posición
            pendingPostInsertChildrenTasks.addTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_widget_LinearLayout_baselineAlignedChildIndex.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else super.setAttribute(view, attr, attrCtx);
    }

}
