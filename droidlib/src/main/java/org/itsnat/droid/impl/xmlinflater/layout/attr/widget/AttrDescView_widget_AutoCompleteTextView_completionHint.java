package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AutoCompleteTextView_completionHint extends AttrDescReflecMethodCharSequence<ClassDescViewBased,View,AttrLayoutContext>
{

    public AttrDescView_widget_AutoCompleteTextView_completionHint(ClassDescViewBased parent)
    {
        super(parent,"completionHint","");
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingViewCreateProcess pendingViewCreateProcess = attrCtx.getPendingViewCreateProcess();
        if (pendingViewCreateProcess != null)
        {
            // Necesitamos definir antes de completionHint el atributo completionHintView, pues en setCompletionHint(CharSequence) es cuando
            // carga el layout definido en completionHintView

            pendingViewCreateProcess.addPendingSetAttribsTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_widget_AutoCompleteTextView_completionHint.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else super.setAttribute(view, attr, attrCtx);
    }
}
