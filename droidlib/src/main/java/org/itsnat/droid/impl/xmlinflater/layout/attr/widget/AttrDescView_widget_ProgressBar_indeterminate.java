package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 17/09/14.
 */
public class AttrDescView_widget_ProgressBar_indeterminate extends AttrDescReflecMethodBoolean
{
    public AttrDescView_widget_ProgressBar_indeterminate(ClassDescViewBased parent, String name, boolean defaultValue)
    {
        super(parent,name,defaultValue);
    }

    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        OneTimeAttrProcess oneTimeAttrProcess = attrCtx.getOneTimeAttrProcess();
        if (oneTimeAttrProcess != null)
        {
            // setIndeterminate depende de indeterminateOnly que debe definirse antes, si el usuario lo pone después
            // setIndeterminate funcionará mal
            oneTimeAttrProcess.addLastTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_widget_ProgressBar_indeterminate.super.setAttribute(view, attr,attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
    }
}
