package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescCompiled;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.util.MiscUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jmarranz on 19/10/2016.
 */

public class BitmapUtil
{
    public static Bitmap getBitmapNoScale(ResourceDesc resourceDesc, Context ctx, XMLInflaterRegistry xmlInflaterRegistry)
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
            return createBitmap(byteArray, bitmapDensityReference, res);
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
                return createBitmap(is,value,res);
            }

            throw new ItsNatDroidException("Cannot process " + resourceDescValue);
        }
        else throw MiscUtil.internalError();
    }


    public static Bitmap createBitmap(byte[] byteArray,int bitmapDensityReference,Resources res)
    {
        // bitmapDensityReference es necesario para escalar adecuadamente un bitmap (no nine patch)
        // En ItsNat cuando los bitmaps son remotos no hay manera de elegir densidades por lo que
        // las imágenes se definen con una densidad concreta "que valga para todos los dispositivos",
        // los prototipos de layouts por ej se pueden testear poniéndo las imágenes en la carpeta drawable-densidad que se
        // quiera por ejemplo drawable-xhdpi (320 dpi), Android sabe de qué carpeta carga el bitmap y por tanto sabe
        // qué densidad tiene la imagen original de acuerdo a la carpeta (Options.inDensity) y sabe
        // si tiene que escalar o no según la densidad del dispositivo (Options.inTargetDensity)
        // Eso mismo lo tiene que hacer ItsNat para que el resultado sea el mismo que en un layout
        // compilado por ello hay que proporcionar la densidad de referencia usada durante el diseño (Options.inDensity)
        // para que el escalado se haga en memoria de forma programática por parte de ItsNat Droid

        /*
        http://developer.android.com/guide/practices/screens_support.html
        ldpi (low) ~120dpi
        mdpi (medium) ~160dpi
        hdpi (high) ~240dpi
        xhdpi (extra-high) ~320dpi
        xxhdpi (extra-extra-high) ~480dpi
        xxxhdpi (extra-extra-extra-high) ~640dpi
        */

        // http://stackoverflow.com/questions/16773604/android-bitmap-scale-using-bitmapfactory-options
        // https://code.google.com/p/android/issues/detail?id=7538
        // http://stackoverflow.com/questions/8048603/android-scaling-of-images-to-screen-density
        // http://stackoverflow.com/questions/13482946/supporting-multiple-screens-using-bitmap-factory-options

        BitmapFactory.Options options = new BitmapFactory.Options();

        boolean scale = bitmapDensityReference > 0;
        if (scale)
        {
            /*
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
            int bmpWidth = options.outWidth;
            int bmpHeight = options.outHeight;
            */
            options.inScaled = true;
            options.inDensity = bitmapDensityReference;
            options.inTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        // decodeByteArray NO escala las imágenes aunque se use BitmapFactory.Options
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArray), null, options);
    }

    public static Bitmap createBitmap(InputStream is,TypedValue value,Resources res)
    {
        // No hace falta pasar Options ni escalar, ya lo hace Android pues por ejemplo la densidad de referencia la obtiene de donde obtiene el recurso (xhdpi, mdpi etc)
        final Rect padding = new Rect();
        try
        {
            return BitmapFactory.decodeResourceStream(res, value, is, padding, null);
        }
        finally
        {
            try { is.close(); } catch (IOException e) { throw new ItsNatDroidException(e); }
        }
    }


}
