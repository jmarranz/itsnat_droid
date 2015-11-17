package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldFieldMethodDrawable;


/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescView_view_View_scrollbar_Base extends AttrDescReflecFieldFieldMethodDrawable<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_scrollbar_Base(ClassDescViewBased parent, String name, String fieldName1, String fieldName2, String methodName, Class field2Class, Class methodClass, Class paramClass)
    {
        super(parent,name,fieldName1,fieldName2,methodName,field2Class,methodClass,paramClass);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr,final AttrLayoutContext attrCtx)
    {
        OneTimeAttrProcess oneTimeAttrProcess = attrCtx.getOneTimeAttrProcess();
        if (oneTimeAttrProcess != null)
        {
            // Delegamos al final para que esté totalmente claro si hay o no scrollbars
            oneTimeAttrProcess.addLastTask(new Runnable()
            {
                @Override
                public void run()
                {
                    AttrDescView_view_View_scrollbar_Base.super.setAttribute(view, attr,attrCtx);
                }
            });
        }
        else
        {
            super.setAttribute(view, attr, attrCtx);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No se que hacer, el null no es el valor por defecto
    }

}
