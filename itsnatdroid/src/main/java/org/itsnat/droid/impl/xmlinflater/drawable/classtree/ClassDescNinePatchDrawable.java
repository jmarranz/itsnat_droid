package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.NinePatchDrawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.BitmapUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.BitmapDrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescNinePatchDrawable extends ClassDescElementDrawableBased<NinePatchDrawable>
{
    public ClassDescNinePatchDrawable(ClassDescDrawableMgr classMgr,ClassDescElementDrawableBased<? super NinePatchDrawable> parent)
    {
        super(classMgr,"nine-patch",parent);
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/Drawable.java#Drawable.createFromXmlInner%28android.content.res.Resources%2Corg.xmlpull.v1.XmlPullParser%2Candroid.util.AttributeSet%29
        // http://stackoverflow.com/questions/5079868/create-a-ninepatch-ninepatchdrawable-in-runtime

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        Context ctx = xmlInflaterContext.getContext();

        DOMAttr attrSrc = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "src");
        if (attrSrc == null) throw new ItsNatDroidException("Missing src attribute in element " + rootElem.getTagName());

        // No necesita escalar pues por definición es "flexible"
        Bitmap bitmap = BitmapUtil.getBitmap(attrSrc.getResourceDesc(),xmlInflaterContext.getBitmapDensityReference(),ctx,classMgr.getXMLInflaterRegistry());

        NinePatchDrawable drawable = BitmapDrawableUtil.createNinePatchDrawable(bitmap,ctx.getResources());
        return new ElementDrawableChildRoot(drawable);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(namespaceURI,name))
            return true;
        return isSrcAttribute(namespaceURI, name); // Se usa al construir el drawable
    }

    private static boolean isSrcAttribute(String namespaceURI,String name)
    {
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && name.equals("src");
    }

    @Override
    public Class<NinePatchDrawable> getDrawableOrElementDrawableClass()
    {
        return NinePatchDrawable.class;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();


        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "dither", true));
    }


}
