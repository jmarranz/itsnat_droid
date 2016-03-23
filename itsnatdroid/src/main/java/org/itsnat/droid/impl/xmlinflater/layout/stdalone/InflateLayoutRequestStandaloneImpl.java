package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.IOUtil;
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
    protected AttrAnimatorInflaterListener attrAnimatorInflaterListener;


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

    public AttrAnimatorInflaterListener getAttrAnimatorInflaterListener()
    {
        return attrAnimatorInflaterListener;
    }

    @Override
    public InflateLayoutRequest setAttrAnimatorInflaterListener(AttrAnimatorInflaterListener attrAnimatorInflaterListener)
    {
        this.attrAnimatorInflaterListener = attrAnimatorInflaterListener;
        return this;
    }

    public Context getContext()
    {
        return ctx;
    }

    @Override
    public InflatedLayout inflate(InputStream input,ViewGroup parentView)
    {
        String markup = IOUtil.read(input,encoding);
        return inflateLayoutStandalone(markup,parentView).getInflatedLayoutImpl();
    }

    @Override
    public InflatedLayout inflate(Reader input,ViewGroup parentView)
    {
        String markup = IOUtil.read(input);
        return inflateLayoutStandalone(markup,parentView).getInflatedLayoutImpl();
    }

    private XMLInflaterLayoutStandalone inflateLayoutStandalone(String markup,ViewGroup parentView)
    {
        Resources res = getContext().getResources();
        AssetManager assetManager = res.getAssets();
        Configuration configuration = res.getConfiguration();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();

        XMLDOMRegistry xmlDOMRegistry = getItsNatDroidImpl().getXMLDOMRegistry();
        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,assetManager,configuration,displayMetrics);

        XMLDOMLayout domLayout = xmlDOMRegistry.getXMLDOMLayoutCache(markup,null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMParserContext);

        int indexChild = parentView != null ? parentView.getChildCount() - 1 : -1;

        return (XMLInflaterLayoutStandalone)inflateLayout(domLayout,parentView,indexChild,null);
    }

}
