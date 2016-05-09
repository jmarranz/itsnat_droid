package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescCompiled;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
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
        try
        {
            return setAttributeThisClass(draw,attr,attrCtx);
        }
        catch(Exception ex)
        {
            String namespaceURI = attr.getNamespaceURI();
            String name = attr.getName(); // El nombre devuelto no contiene el namespace
            String value = attr.getValue();
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + value + " in object " + draw.getInstanceToSetAttributes(), ex); // draw.getDrawable() devuelve null en este contexto en algunos casos (atributos en objetos item auxiliares)
        }
    }

    private boolean setAttributeThisClass(final DrawableOrElementDrawableWrapper draw,final DOMAttr attr,final AttrDrawableContext attrCtx)
    {
        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();

        //XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();


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
                AttrDesc.processDownloadTask((DOMAttrRemote)attr,task,attrCtx.getXMLInflaterContext());
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
                return parentClass.setAttributeThisClass(draw, attr, attrCtx);
            }
            else // if (parentClass == null) // Esto es para que se llame una sola vez al processAttrCustom al recorrer hacia arriba el árbol
            {
                return processAttrCustom(draw,namespaceURI,name,value,attrCtx.getXMLInflaterContext());
            }
        }

    }

    private boolean processAttrCustom(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name,String value,XMLInflaterContext xmlInflaterContext)
    {
        AttrResourceInflaterListener listener = xmlInflaterContext.getAttrResourceInflaterListener();
        if (listener != null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser nulo
            return listener.setAttribute(page, draw.getDrawable(), namespaceURI, name, value);
        }
        return false;
    }

    public static Bitmap getBitmapNoScale(ResourceDesc resourceDesc,Context ctx,XMLInflaterRegistry xmlInflaterRegistry)
    {
        return getBitmap(resourceDesc, -1, ctx, xmlInflaterRegistry);
    }

    public static Bitmap getBitmap(ResourceDesc resourceDesc,int bitmapDensityReference,Context ctx,XMLInflaterRegistry xmlInflaterRegistry)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/Drawable.java#Drawable.createFromXmlInner%28android.content.res.Resources%2Corg.xmlpull.v1.XmlPullParser%2Candroid.util.AttributeSet%29
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ParsedResourceImage resource = (ParsedResourceImage)resourceDescDyn.getParsedResource();
            byte[] byteArray = resource.getImgBytes();
            Resources res = ctx.getResources();
            return DrawableUtil.createBitmap(byteArray, bitmapDensityReference, res);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            String resourceDescValue = resourceDesc.getResourceDescValue();
            if (XMLInflaterRegistry.isResource(resourceDescValue))
            {
                // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/NinePatchDrawable.java#240
                int resId = xmlInflaterRegistry.getIdentifierCompiled(resourceDescValue,ctx); // Si no se encuentra da error, no devuelve 0
                TypedValue value = new TypedValue();
                Resources res = ctx.getResources();
                InputStream is = res.openRawResource(resId, value);
                return DrawableUtil.createBitmap(is,value,res);
            }

            throw new ItsNatDroidException("Cannot process " + resourceDescValue);
        }
        else throw MiscUtil.internalError();
    }

}
