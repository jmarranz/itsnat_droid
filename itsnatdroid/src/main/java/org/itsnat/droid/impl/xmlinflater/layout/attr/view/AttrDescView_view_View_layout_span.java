package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;
import android.widget.TableRow;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_span extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_layout_span(ClassDescViewBased parent)
    {
        super(parent,"layout_span");
    }

    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final int convValue = getInteger(attr,attrCtx.getXMLInflaterLayout());

        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                TableRow.LayoutParams params = (TableRow.LayoutParams)view.getLayoutParams();
                params.span = convValue;
            }};

        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
        {
            pendingViewPostCreateProcess.addPendingLayoutParamsTask(task);
        }
        else
        {
            task.run();
            PendingViewPostCreateProcess.onChangedLayoutParams(view);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        TableRow.LayoutParams params = (TableRow.LayoutParams)view.getLayoutParams();

        params.span = 1;

        PendingViewPostCreateProcess.onChangedLayoutParams(view);
    }
}
