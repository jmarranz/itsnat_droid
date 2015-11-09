package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layout_alignWithParentIfMissing extends AttrDescView
{
    public AttrDescView_view_View_layout_alignWithParentIfMissing(ClassDescViewBased parent)
    {
        super(parent,"layout_alignWithParentIfMissing");
    }

    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final boolean convValue = getBoolean(attr.getValue(),attrCtx.getContext());

        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
                params.alignWithParent = convValue;
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

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

        params.alignWithParent = false;

        view.setLayoutParams(view.getLayoutParams());
    }
}
