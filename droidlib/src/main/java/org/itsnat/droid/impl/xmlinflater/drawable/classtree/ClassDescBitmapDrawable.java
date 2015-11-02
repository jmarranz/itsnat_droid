package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodMultipleName;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_BitmapDrawable_tileMode;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescBitmapDrawable extends ClassDescElementDrawableRoot<BitmapDrawable>
{
    public ClassDescBitmapDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"bitmap");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        DOMAttr attrSrc = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "src");

        Bitmap bitmap = AttrDescDrawable.getBitmap(attrSrc,true,inflaterDrawable.getBitmapDensityReference(), ctx, classMgr.getXMLInflateRegistry());
        return new ElementDrawableRoot(new BitmapDrawable(ctx.getResources(),bitmap));
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;
        return InflatedXML.XMLNS_ANDROID.equals(namespaceURI) && name.equals("src");
    }

    @Override
    public Class<BitmapDrawable> getDrawableOrElementDrawableClass()
    {
        return BitmapDrawable.class;
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this,"antialias","setAntiAlias"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this,"dither"));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this,"filter","setFilterBitmap"));
        addAttrDesc(new AttrDescDrawableReflecMethodMultipleName<BitmapDrawable>(this,"gravity", GravityUtil.valueMap));
        // android:mipMap parece que es level 17
        // android:src se procesa en tiempo de creación
        addAttrDesc(new AttrDescDrawable_BitmapDrawable_tileMode(this,"tileMode"));
        //addAttrDesc(new AttrDescDrawable_BitmapDrawable_tileMode(this,"tileModeX"));  // API level 21
        //addAttrDesc(new AttrDescDrawable_BitmapDrawable_tileMode(this,"tileModeY"));  // API level 21
    }


}
