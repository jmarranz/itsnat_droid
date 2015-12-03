package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_RadioGroup;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_RadioGroup_checkedButton extends AttrDescReflecMethodId<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_RadioGroup_checkedButton(ClassDescView_widget_RadioGroup parent)
    {
        super(parent, "checkedButton", "check",-1);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = attrCtx.getPendingPostInsertChildrenTasks();
        if (pendingPostInsertChildrenTasks != null)
        {
            pendingPostInsertChildrenTasks.addTask(new Runnable()
            {
                @Override
                public void run()
                {
                    // El addTask es porque referenciamos un View hijo antes de insertarse
                    // Pero NO es porque el id se referencia antes de insertar el hijo pues ese problema se resuelve con @+id
                    // en el propio checkedButton
                    AttrDescView_widget_RadioGroup_checkedButton.super.setAttribute(view, attr, attrCtx);
                }
            });
        }
        else super.setAttribute(view, attr, attrCtx);
    }
}
