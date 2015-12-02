package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcessChildGridLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.GridLayout_rowSpec;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_row extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_layout_row(ClassDescViewBased parent)
    {
        super(parent,"layout_row");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        // Default: Integer.MIN_VALUE

        final int row = getInteger(attr.getValue(),attrCtx.getContext());

        final PendingViewCreateProcess pendingViewCreateProcess = attrCtx.getPendingViewCreateProcess();
        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                PendingViewCreateProcessChildGridLayout pendingViewCreateProcessGrid = (PendingViewCreateProcessChildGridLayout) pendingViewCreateProcess;
                if (pendingViewCreateProcessGrid.gridLayout_rowSpec == null)
                    pendingViewCreateProcessGrid.gridLayout_rowSpec = new GridLayout_rowSpec();

                pendingViewCreateProcessGrid.gridLayout_rowSpec.layout_row = row;
            }};

        if (pendingViewCreateProcess != null)
        {
            pendingViewCreateProcess.addPendingLayoutParamsTask(task);
        }
        else
        {
            throw new ItsNatDroidException("Attribute " + getName() + " cannot be changed post creation");
        }

    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // Cannot be changed post creation
    }
}
