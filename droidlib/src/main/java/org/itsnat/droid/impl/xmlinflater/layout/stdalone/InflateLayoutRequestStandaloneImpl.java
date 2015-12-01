package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflater.layout.InflateLayoutRequestImpl;

import java.io.InputStream;
import java.io.Reader;

/**
 * Created by jmarranz on 5/06/14.
 */
public class InflateLayoutRequestStandaloneImpl extends InflateLayoutRequestImpl implements InflateLayoutRequest
{
    protected Context ctx;
    protected String encoding = "UTF-8";
    protected int bitmapDensityReference = DisplayMetrics.DENSITY_XHIGH; // 320 (xhdpi), por ej el Nexus 4
    protected AttrLayoutInflaterListener attrLayoutInflaterListener;
    protected AttrDrawableInflaterListener attrDrawableInflaterListener;

    public InflateLayoutRequestStandaloneImpl(ItsNatDroidImpl itsNatDroid)
    {
        super(itsNatDroid);
    }

    @Override
    public InflateLayoutRequest setContext(Context ctx)
    {
        this.ctx = ctx;
        return this;
    }

    @Override
    public InflateLayoutRequest setEncoding(String encoding)
    {
        this.encoding = encoding;
        return this;
    }

    @Override
    public String getEncoding()
    {
        return encoding;
    }

    @Override
    public InflateLayoutRequest setBitmapDensityReference(int bitmapDensityReference)
    {
        this.bitmapDensityReference = bitmapDensityReference;
        return this;
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return attrLayoutInflaterListener;
    }

    @Override
    public InflateLayoutRequest setAttrLayoutInflaterListener(AttrLayoutInflaterListener inflateLayoutListener)
    {
        this.attrLayoutInflaterListener = inflateLayoutListener;
        return this;
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return attrDrawableInflaterListener;
    }

    @Override
    public InflateLayoutRequest setAttrDrawableInflaterListener(AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
        return this;
    }

    public Context getContext()
    {
        return ctx;
    }

    @Override
    public InflatedLayout inflate(InputStream input)
    {
        String markup = IOUtil.read(input,encoding);
        return inflateLayoutStandalone(markup);
    }

    @Override
    public InflatedLayout inflate(Reader input)
    {
        String markup = IOUtil.read(input);
        return inflateLayoutStandalone(markup);
    }

    private InflatedLayoutImpl inflateLayoutStandalone(String markup)
    {
        XMLDOMRegistry xmlDOMRegistry = getItsNatDroidImpl().getXMLDOMRegistry();

        boolean loadingPage = true;
        String itsNatServerVersion = null;
        boolean remotePageOrFrag = false;
        AssetManager assetManager = getContext().getResources().getAssets();
        XMLDOMLayout domLayout = xmlDOMRegistry.getXMLDOMLayoutCache(markup, itsNatServerVersion, loadingPage, remotePageOrFrag, assetManager);

        return inflateLayout(domLayout, null, null, null);
    }

}
