package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout.IncludeViewGroup;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDesc_Include_layout;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_Include extends ClassDescViewBased
{
    public ClassDescView_Include(ClassDescViewMgr classMgr, ClassDescView_widget_FrameLayout parentClass)
    {
        super(classMgr,"include",parentClass);
    }

    @Override
    protected Class<? extends View> initClass()
    {
        if (clasz == null)
        {
            this.clasz = (Class<View>) MiscUtil.resolveClass(IncludeViewGroup.class.getName());
        }
        return clasz;
    }

    @Override
    protected View createViewObject(Context ctx,int idStyle,PendingPostInsertChildrenTasks pending)
    {
        return new IncludeViewGroup(ctx);
    }

    protected void init()
    {
        super.init();

        addAttrDescNoNS(new AttrDesc_Include_layout(this)); // OJO llamamos a addAttrDescNoNS porque NO tiene namespace
    }
}

