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
public class AttrDescView_view_View_layout_rellayout_byId extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected int selector;

    public AttrDescView_view_View_layout_rellayout_byId(ClassDescViewBased parent, String name, int selector)
    {
        super(parent,name);
        this.selector = selector;
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {

        Runnable task = new Runnable(){
            @Override
            public void run()
            {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
                String value = attr.getValue();
                if (!value.isEmpty())
                {
                    int viewId = getIdentifier(attr.getResourceDesc(), attrCtx.getXMLInflaterLayout());
                    params.addRule(selector, viewId);
                }
                else
                {
                    // El valor vacío del atributo es un caso específico de ItsNatDroid, útil en actualización del Layout via DOM para
                    // indicar que quitamos la regla. removeRule es el método adecuado pero es posterior a 4.0.3
                    params.addRule(selector, 0);
                }
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
        params.addRule(selector, 0);

        PendingViewPostCreateProcess.onChangedLayoutParams(view);
    }
}
