package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.io.InputStream;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class _DELETE_AttrDescDrawable<TdrawableOrElementDrawable> extends AttrDesc<ClassDescDrawable,TdrawableOrElementDrawable,AttrDrawableContext> // TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext
{
    public _DELETE_AttrDescDrawable(ClassDescDrawable parent, String name)
    {
        super(parent,name);
    }



    public abstract void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx);

    public void removeAttribute(TdrawableOrElementDrawable target, AttrDrawableContext attrCtx)
    {
        throw new ItsNatDroidException("Unexpected");
    }
}


