package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout._IncludeFakeViewGroup_;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_view_ViewStub extends ClassDescViewBased
{
    public ClassDescView_view_ViewStub(ClassDescViewMgr classMgr, ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.view.ViewStub",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodId(this, "inflatedId", "setInflatedId", -1));
        addAttrDescAN(new AttrDescReflecMethodId(this, "layout", "setLayoutResource", 0)); // El valor por defecto curiosamente es 0 no -1 (lo normal). // No intentes usar getLayout con "layout" pues hay que llamar a inflate() que lo fastidia y no es posible redefinir ViewStub pues es final
    }
}

