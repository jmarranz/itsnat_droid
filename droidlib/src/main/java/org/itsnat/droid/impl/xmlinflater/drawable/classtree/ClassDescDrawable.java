package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.page.XMLInflaterDrawablePage;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.io.InputStream;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescDrawable extends ClassDesc<Drawable>
{
    public ClassDescDrawable(ClassDescDrawableMgr classMgr, String elemName, ClassDescDrawable parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableMgr) classMgr;
    }

    public String getElementName()
    {
        return getClassName();
    }

    public static PageImpl getPageImpl(XMLInflaterDrawable xmlInflaterDrawable)
    {
        return (xmlInflaterDrawable instanceof XMLInflaterDrawablePage) ? ((XMLInflaterDrawablePage) xmlInflaterDrawable).getPageImpl() : null;
    }
/*
    protected AttrDescDrawable getAttrDescDrawable(String name)
    {
        return (AttrDescDrawable) getAttrDesc(name);
    }
*/
    public ClassDescDrawable getParentClassDescDrawable()
    {
        return (ClassDescDrawable) getParentClassDesc();
    }


    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI, String name)
    {
        return false; // es redefinido
    }


    public boolean setAttribute(DrawableOrElementDrawableWrapper draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace

        try
        {
            if (isAttributeIgnored(draw, namespaceURI, name))
                return false; // Se trata de forma especial en otro lugar

            if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
            {
                AttrDesc<ClassDescDrawable, Object, AttrDrawableContext> attrDesc = this.<ClassDescDrawable, Object, AttrDrawableContext>getAttrDesc(name);
                if (attrDesc != null)
                {
                    attrDesc.setAttribute(draw.getInstanceToSetAttributes(), attr, attrCtx);
                }
                else
                {
                    // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
                    // y tiene prioridad la clase más derivada

                    ClassDescDrawable parentClass = getParentClassDescDrawable();
                    if (parentClass != null)
                    {
                        parentClass.setAttribute(draw, attr, attrCtx);
                    }
                    else
                    {
                        // No se encuentra opción de proceso custom
                        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
                        AttrDrawableInflaterListener listener = xmlInflaterDrawable.getAttrDrawableInflaterListener();
                        if (listener != null)
                        {
                            PageImpl page = getPageImpl(xmlInflaterDrawable); // Puede ser nulo
                            String value = attr.getValue();
                            listener.setAttribute(page, draw.getDrawable(), namespaceURI, name, value);
                        }
                    }
                }
            }
            else
            {
                // No se encuentra opción de proceso custom
                XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
                AttrDrawableInflaterListener listener = xmlInflaterDrawable.getAttrDrawableInflaterListener();
                if (listener != null)
                {
                    PageImpl page = getPageImpl(xmlInflaterDrawable); // Puede ser nulo
                    String value = attr.getValue();
                    listener.setAttribute(page, draw.getDrawable(), namespaceURI, name, value);
                }
            }
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + attr.getValue() + " in object " + draw.getInstanceToSetAttributes(), ex); // draw.getDrawable() devuelve null en este contexto en algunos casos (atributos en objetos item auxiliares)
        }

        return true;
    }

    public Class<?> getDeclaredClass()
    {
        return getDrawableOrElementDrawableClass();
    }

    public abstract Class<?> getDrawableOrElementDrawableClass();

    public static Bitmap getBitmapNoScale(DOMAttr attr,Context ctx,XMLInflateRegistry xmlInflateRegistry)
    {
        return getBitmap(attr, -1, ctx, xmlInflateRegistry);
    }

    public static Bitmap getBitmap(DOMAttr attr,int bitmapDensityReference,Context ctx,XMLInflateRegistry xmlInflateRegistry)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/Drawable.java#Drawable.createFromXmlInner%28android.content.res.Resources%2Corg.xmlpull.v1.XmlPullParser%2Candroid.util.AttributeSet%29
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            byte[] byteArray = (byte[])attrDyn.getResource();
            Resources res = ctx.getResources();
            return DrawableUtil.createBitmap(byteArray, bitmapDensityReference, res);
        }
        else if (attr instanceof DOMAttrLocalResource)
        {
            String attrValue = attr.getValue();
            if (XMLInflateRegistry.isResource(attrValue))
            {
                // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/NinePatchDrawable.java#240
                int resId = xmlInflateRegistry.getIdentifier(attrValue,ctx);
                if (resId <= 0) return null;
                final TypedValue value = new TypedValue();
                Resources res = ctx.getResources();
                InputStream is = res.openRawResource(resId, value);
                return DrawableUtil.createBitmap(is,value,res);
            }

            throw new ItsNatDroidException("Cannot process " + attrValue);
        }
        else throw new ItsNatDroidException("Internal Error");
    }

}
