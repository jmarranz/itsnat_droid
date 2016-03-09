package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueCompiled;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetId;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AutoCompleteTextView_completionHintView extends AttrDescReflecFieldSetId<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<View> fieldHintView;

    public AttrDescView_widget_AutoCompleteTextView_completionHintView(ClassDescViewBased parent)
    {
        super(parent,"completionHintView","mHintResource",null); // Android tiene un recurso por defecto

        this.fieldHintView = new FieldContainer<View>(parent.getDeclaredClass(),"mHintView");
    }


    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                // Hay que pasar viewParent = null por ahora dejamos esto PROVISIONAL
                //ViewGroup viewParent = (ViewGroup) view.getParent();
                //int indexChild = viewParent.indexOfChild(view);

                LayoutValue layoutValue = getLayout(attr, attrCtx.getXMLInflaterLayout(), null, -1);
                if (layoutValue instanceof LayoutValueDynamic)
                {
                    View hintView = ((LayoutValueDynamic) layoutValue).getView();
                    TextView textViewChildHint = (TextView)hintView.findViewById(android.R.id.text1);
                    fieldHintView.set(view,textViewChildHint);
                }
                else if (layoutValue instanceof LayoutValueCompiled)
                {
                    int id = ((LayoutValueCompiled) layoutValue).getLayoutId();
                    setField(view,id);
                }
            }

        };
        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
            pendingViewPostCreateProcess.addPendingPostAddViewTask(task);
        else
            task.run();
    }
}
