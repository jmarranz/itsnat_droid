package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;
import android.widget.LinearLayout;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_weight extends AttrDescView
{
    public AttrDescView_view_View_layout_weight(ClassDescViewBased parent)
    {
        super(parent,"layout_weight");
    }

    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final float weight = getFloat(attr.getValue(),attrCtx.getContext());

        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
                params.weight = weight;
            }};

        OneTimeAttrProcess oneTimeAttrProcess = attrCtx.getOneTimeAttrProcess();
        if (oneTimeAttrProcess != null)
        {
            oneTimeAttrProcess.addLayoutParamsTask(task);
        }
        else
        {
            task.run();
            view.setLayoutParams(view.getLayoutParams());
        }
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();

        params.weight = 0;

        view.setLayoutParams(view.getLayoutParams());
    }
}
