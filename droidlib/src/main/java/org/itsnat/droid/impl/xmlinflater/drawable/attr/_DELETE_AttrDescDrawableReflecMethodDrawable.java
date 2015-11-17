package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescDrawableReflecMethodDrawable<TdrawableOrElementDrawable> extends _DELETED_AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public _DELETE_AttrDescDrawableReflecMethodDrawable(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public _DELETE_AttrDescDrawableReflecMethodDrawable(ClassDescDrawable parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return Drawable.class;
    }

    @Override
    public void setAttribute(final TdrawableOrElementDrawable draw, final DOMAttr attr,final AttrDrawableContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                Drawable convValue = getDrawable(attr,attrCtx.getContext(),attrCtx.getXMLInflaterDrawable());
                callMethod(draw, convValue);
            }
        };
        if (DOMAttrRemote.isPendingToDownload(attr))
            processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflaterDrawable());
        else
            task.run();
    }

}
