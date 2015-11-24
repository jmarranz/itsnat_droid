package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldMethodDrawable<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldMethod<TclassDesc,TattrTarget,TattrContext>

{
    protected String defaultValue;

    public AttrDescReflecFieldMethodDrawable(TclassDesc parent, String name, String fieldName, String methodName, Class methodClass, String defaultValue)
    {
        super(parent,name,fieldName,methodName,methodClass,Drawable.class);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(final TattrTarget target, final DOMAttr attr,final TattrContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                Drawable convertedValue = getDrawable(attr,attrCtx.getContext(),attrCtx.getXMLInflater());
                callFieldMethod(target, convertedValue);
            }
        };
        if (DOMAttrRemote.isPendingToDownload(attr))
            processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflater());
        else
            task.run();
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null) // Para especificar null se ha de usar "@null"
            setToRemoveAttribute(target, defaultValue,attrCtx); // defaultValue puede ser null (ej attr background), también valdría "@null" en el atributo
    }
}
