package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.BitmapUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_BitmapDrawable_tileMode;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescBitmapDrawable extends ClassDescElementDrawableBased<BitmapDrawable>
{
    public ClassDescBitmapDrawable(ClassDescDrawableMgr classMgr,ClassDescElementDrawableBased<? super BitmapDrawable> parent)
    {
        super(classMgr,"bitmap",parent);
    }


    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        Context ctx = xmlInflaterContext.getContext();
        DOMAttr attrSrc = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "src");

        Bitmap bitmap = BitmapUtil.getBitmap(attrSrc.getResourceDesc(),xmlInflaterContext.getBitmapDensityReference(), ctx, classMgr.getXMLInflaterRegistry());
        return new ElementDrawableChildRoot(new BitmapDrawable(ctx.getResources(),bitmap));
    }

    @Override
    public boolean isAttributeIgnored(BitmapDrawable resource, String namespaceURI, String name)
    {
        if (super.isAttributeIgnored(resource,namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && name.equals("src");
    }

    @Override
    public Class<BitmapDrawable> getDrawableOrElementDrawableClass()
    {
        return BitmapDrawable.class;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        // el atributo src se define en otro lugar

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "antialias", "setAntiAlias", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "dither", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "filter", "setFilterBitmap", true));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple<ClassDescBitmapDrawable, BitmapDrawable, AttrDrawableContext>(this, "gravity", GravityUtil.nameValueMap, "fill"));
        // android:mipMap parece que es level 17
        // android:src se procesa en tiempo de creación
        addAttrDescAN(new AttrDescDrawable_BitmapDrawable_tileMode(this, "tileMode"));
        //addAttrDescAN(new AttrDescDrawable_BitmapDrawable_tileMode(this,"tileModeX"));  // API level 21
        //addAttrDescAN(new AttrDescDrawable_BitmapDrawable_tileMode(this,"tileModeY"));  // API level 21
    }

}
