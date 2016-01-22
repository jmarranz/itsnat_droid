package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrCompiledResource;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.io.InputStream;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescDrawable<TelementDrawable> extends ClassDesc<TelementDrawable>
{
    public ClassDescDrawable(ClassDescDrawableMgr classMgr, String elemName, ClassDescDrawable<? super TelementDrawable> parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    public Class<TelementDrawable> getDeclaredClass()
    {
        return getDrawableOrElementDrawableClass();
    }

    public abstract Class<TelementDrawable> getDrawableOrElementDrawableClass();

    @Override
    protected void init()
    {
        // initClass();

        super.init();
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableMgr) classMgr;
    }

    public String getElementName()
    {
        return getClassOrDOMElemName();
    }


    public ClassDescDrawable getParentClassDescDrawable()
    {
        return (ClassDescDrawable) getParentClassDesc();
    }


    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI, String name)
    {
        return false; // es redefinido
    }


    public boolean setAttribute(final DrawableOrElementDrawableWrapper draw,final DOMAttr attr,final AttrDrawableContext attrCtx)
    {
        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();

        try
        {
            if (isAttributeIgnored(draw, namespaceURI, name))
                return true; // Se trata de forma especial en otro lugar

            final AttrDesc<ClassDescDrawable, Object, AttrDrawableContext> attrDesc = this.<ClassDescDrawable, Object, AttrDrawableContext>getAttrDesc(namespaceURI, name);
            if (attrDesc != null)
            {
                Runnable task = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        attrDesc.setAttribute(draw.getInstanceToSetAttributes(), attr, attrCtx);
                    }
                };
                if (DOMAttrRemote.isPendingToDownload(attr)) // No se me ocurre ningún caso (un XML de un drawable una vez cargado no tiene scripts ni estado en el servidor y no lo cambiamos por interacción del usuario) pero por simetría con los layout lo dejamos
                    AttrDesc.processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflaterDrawable());
                else
                    task.run();

                return true;
            }
            else
            {
                // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
                // y tiene prioridad la clase más derivada

                ClassDescDrawable parentClass = getParentClassDescDrawable();
                if (parentClass != null)
                {
                    if (parentClass.setAttribute(draw, attr, attrCtx))
                        return true;
                    return false;
                }
                else // if (parentClass == null) // Esto es para que se llame una sola vez al processAttrCustom al recorrer hacia arriba el árbol
                {
                    return processAttrCustom(draw,namespaceURI,name,value,xmlInflaterDrawable);
                }
            }
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + attr.getValue() + " in object " + draw.getInstanceToSetAttributes(), ex); // draw.getDrawable() devuelve null en este contexto en algunos casos (atributos en objetos item auxiliares)
        }
    }

    private boolean processAttrCustom(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name,String value,XMLInflaterDrawable xmlInflaterDrawable)
    {
        AttrDrawableInflaterListener listener = xmlInflaterDrawable.getAttrDrawableInflaterListener();
        if (listener != null)
        {
            PageImpl page = PageImpl.getPageImpl(xmlInflaterDrawable); // Puede ser nulo
            return listener.setAttribute(page, draw.getDrawable(), namespaceURI, name, value);
        }
        return false;
    }

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
            ParsedResourceImage resource = (ParsedResourceImage)attrDyn.getResource();
            byte[] byteArray = resource.getImgBytes();
            Resources res = ctx.getResources();
            return DrawableUtil.createBitmap(byteArray, bitmapDensityReference, res);
        }
        else if (attr instanceof DOMAttrCompiledResource)
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
