package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TableRow;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcessChildGridLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.GridLayout_columnSpec;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_column extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_layout_column(ClassDescViewBased parent)
    {
        super(parent,"layout_column");
    }

    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final int column = getInteger(attr.getValue(),attrCtx.getContext());

        final PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                ViewGroup.LayoutParams params = view.getLayoutParams();

                if (params instanceof GridLayout.LayoutParams)
                {
                    PendingViewPostCreateProcessChildGridLayout pendingViewPostCreateProcessGrid = (PendingViewPostCreateProcessChildGridLayout) pendingViewPostCreateProcess;
                    if (pendingViewPostCreateProcessGrid.gridLayout_columnSpec == null) pendingViewPostCreateProcessGrid.gridLayout_columnSpec = new GridLayout_columnSpec();

                    pendingViewPostCreateProcessGrid.gridLayout_columnSpec.layout_column = column;
                }
                else if (params instanceof TableRow.LayoutParams)
                {
                    ((TableRow.LayoutParams) params).column = column;
                }
            }};

        if (pendingViewPostCreateProcess != null)
            pendingViewPostCreateProcess.addPendingLayoutParamsTask(task);
        else
        {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params instanceof GridLayout.LayoutParams)
            {
                throw new ItsNatDroidException("Attribute " + getName() + " cannot be changed post creation");
            }
            else if (params instanceof TableRow.LayoutParams)
            {
                task.run();
                PendingViewPostCreateProcess.onChangedLayoutParams(view);
            }
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof GridLayout.LayoutParams)
        {
            // No hacemos nada, no puede ser cambiado "post-creación"
        }
        else if (params instanceof TableRow.LayoutParams)
        {
            ((TableRow.LayoutParams)params).column = -1;

            PendingViewPostCreateProcess.onChangedLayoutParams(view);
        }

    }
}
