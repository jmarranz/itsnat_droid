package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.graphics.drawable.Drawable;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescViewReflecMethodDrawable extends AttrDescViewReflecMethod
{
    protected String defaultValue;

    public AttrDescViewReflecMethodDrawable(ClassDescViewBased parent, String name, String methodName, String defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescViewReflecMethodDrawable(ClassDescViewBased parent, String name, String defaultValue)
    {
        super(parent, name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return Drawable.class;
    }

    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                Drawable convValue = getDrawable(attr,attrCtx.getContext(),attrCtx.getXMLInflaterLayout());
                callMethod(view, convValue);
            }
        };
        if (DOMAttrRemote.isPendingToDownload(attr))
            processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflaterLayout());
        else
            task.run();
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null) // Para especificar null se ha de usar "@null"
            setToRemoveAttribute(view, defaultValue,attrCtx); // defaultValue puede ser null (ej attr background), también valdría "@null" en el atributo
    }
}
