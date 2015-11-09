package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_RadioGroup_checkedButton extends AttrDescViewReflecMethodId
{
    public AttrDescView_widget_RadioGroup_checkedButton(ClassDescViewBased parent)
    {
        super(parent, "checkedButton", "check",-1);
    }

    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingPostInsertChildrenTasks pending = attrCtx.getPendingPostInsertChildrenTasks();
        if (pending != null)
        {
            pending.addTask(new Runnable(){
                @Override
                public void run()
                {
                    // El addTask es porque referenciamos un View hijo antes de insertarse
                    // Pero NO es porque el id se referencia antes de insertar el hijo pues ese problema se resuelve con @+id
                    // en el propio checkedButton
                    AttrDescView_widget_RadioGroup_checkedButton.super.setAttribute(view, attr,attrCtx);
                }
            });
        }
        else super.setAttribute(view, attr, attrCtx);
    }
}
