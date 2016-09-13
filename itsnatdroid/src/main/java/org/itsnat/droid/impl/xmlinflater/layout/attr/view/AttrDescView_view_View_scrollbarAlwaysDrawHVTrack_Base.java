package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldFieldMethod;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarAlwaysDrawHVTrack_Base extends AttrDescReflecFieldFieldMethod<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_scrollbarAlwaysDrawHVTrack_Base(ClassDescViewBased parent, String name, String fieldName1, String fieldName2, String methodName, Class<?> field2Class, Class<?> methodClass, Class<?> classParam)
    {
        super(parent,name,"mScrollCache","scrollBar",methodName,MiscUtil.resolveClass("android.view.View$ScrollabilityCache"),MiscUtil.resolveClass("android.widget.ScrollBarDrawable"),boolean.class);
    }


    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final boolean convertedValue = getBoolean(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
        {
            // Delegamos al final para que esté totalmente claro si hay o no scrollbars
            pendingViewPostCreateProcess.addPendingSetAttribsTask(new Runnable()
            {
                @Override
                public void run()
                {
                    callFieldFieldMethod(view, convertedValue);
                }
            });
        }
        else
        {
            callFieldFieldMethod(view, convertedValue);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "false",attrCtx);
    }
}
