package org.itsnat.droid.impl.xmlinflater.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.xmlinflater.BitmapUtil;

/**
 * Created by jmarranz on 19/10/2016.
 */

public class BitmapDrawableUtil
{
    public static Drawable createImageBasedDrawable(byte[] byteArray, int bitmapDensityReference, boolean expectedNinePatch, Resources res)
    {
        if (expectedNinePatch)
            return createNinePatchDrawable(byteArray,bitmapDensityReference,res);

        Bitmap bitmap = BitmapUtil.createBitmap(byteArray,bitmapDensityReference,res);

        byte[] chunk = bitmap.getNinePatchChunk();
        boolean result = NinePatch.isNinePatchChunk(chunk);
        if (result)
        {
            // Raro pero resulta que es un NinePatch (raro porque lo normal es que se especifique la extensión .9.png)
            return createNinePatchDrawable(bitmap,res);
        }
        else
        {
            return new BitmapDrawable(res, bitmap);
        }
    }

    public static NinePatchDrawable createNinePatchDrawable(byte[] byteArray, int bitmapDensityReference, Resources res)
    {
        Bitmap bitmap = BitmapUtil.createBitmap(byteArray,bitmapDensityReference,res);
        return createNinePatchDrawable(bitmap,res);
    }

    public static NinePatchDrawable createNinePatchDrawable(Bitmap bitmap,Resources res)
    {
        byte[] chunk = bitmap.getNinePatchChunk();
        boolean result = NinePatch.isNinePatchChunk(chunk);
        if (!result) throw new ItsNatDroidException("Expected a 9 patch png, put a valid 9 patch in /res/drawable folder, generate the .apk (/build/outputs/apk in Android Studio), decompress as a zip and copy the png file");

        return new NinePatchDrawable(res, bitmap, chunk, new Rect(), "XML 9 patch");
    }
}
