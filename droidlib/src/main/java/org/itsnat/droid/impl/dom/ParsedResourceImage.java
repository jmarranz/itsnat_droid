package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class ParsedResourceImage extends ParsedResource
{
    protected byte[] imgBytes;

    public ParsedResourceImage(byte[] imgBytes)
    {
        this.imgBytes = imgBytes;
    }

    public byte[] getImgBytes()
    {
        return imgBytes;
    }
}
