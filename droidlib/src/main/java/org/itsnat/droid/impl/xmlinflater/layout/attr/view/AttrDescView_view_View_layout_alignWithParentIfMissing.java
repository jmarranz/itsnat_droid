package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;
import android.widget.RelativeLayout;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_alignWithParentIfMissing extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_layout_alignWithParentIfMissing(ClassDescViewBased parent)
    {
        super(parent,"layout_alignWithParentIfMissing");
    }

    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final boolean convValue = getBoolean(attr, attrCtx.getXMLInflater());

        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
                params.alignWithParent = convValue;
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

        params.alignWithParent = false;

        PendingViewPostCreateProcess.onChangedLayoutParams(view);
    }
}
