package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.graphics.drawable.Drawable;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldFieldMethod;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescViewReflecFieldFieldMethodDrawable extends AttrDescViewReflecFieldFieldMethod
{
    public AttrDescViewReflecFieldFieldMethodDrawable(ClassDescViewBased parent, String name, String fieldName1, String fieldName2, String methodName, Class field2Class, Class methodClass, Class paramClass)
    {
        super(parent,name,fieldName1,fieldName2,methodName,field2Class,methodClass,paramClass);
    }

    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                Drawable convValue = getDrawable(attr,attrCtx.getContext(),attrCtx.getXMLInflaterLayout());
                callFieldFieldMethod(view, convValue);
            }
        };
        if (DOMAttrRemote.isPendingToDownload(attr))
            processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflaterLayout());
        else
            task.run();
    }

}
