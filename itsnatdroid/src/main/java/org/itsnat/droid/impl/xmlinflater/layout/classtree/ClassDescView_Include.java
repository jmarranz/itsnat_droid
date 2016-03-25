package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout._IncludeFakeViewGroup_;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
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
    @SuppressWarnings("unchecked")
    protected Class<View> resolveClass()
    {
        return (Class<View>)MiscUtil.resolveClass(_IncludeFakeViewGroup_.class.getName());
    }

    @Override
    protected boolean setAttributeThisClass(final View view,final DOMAttr attr,final AttrLayoutContext attrCtx)
    {
        // Se redefine totalmente porque necesitamos memorizar los atributos (excepto el layout)
        if (attr.getNamespaceURI() == null && attr.getName().equals("layout"))
            return super.setAttributeThisClass(view, attr, attrCtx);

        ((_IncludeFakeViewGroup_)view).saveAttrib(attr);

        return true; // No los estamos ignorando
    }

    @Override
    protected View createViewObject(int idStyle,PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks,Context ctx)
    {
        return new _IncludeFakeViewGroup_(ctx);
    }

    protected void init()
    {
        super.init();

        addAttrDescNoNS(new AttrDesc_Include_layout(this)); // OJO llamamos a addAttrDescNoNS porque NO tiene namespace
    }
}

