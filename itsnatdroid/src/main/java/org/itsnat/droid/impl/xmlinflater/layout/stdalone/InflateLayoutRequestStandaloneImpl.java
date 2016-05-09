package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
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
    protected AttrResourceInflaterListener attrResourcesInflaterListener;

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


    public AttrResourceInflaterListener getAttrResourceInflaterListener()
    {
        return attrResourcesInflaterListener;
    }

    @Override
    public InflateLayoutRequest setAttrResourceInflaterListener(AttrResourceInflaterListener attrResourcesInflaterListener)
    {
        this.attrResourcesInflaterListener = attrResourcesInflaterListener;
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
        XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone = inflateLayoutStandalone(markup,parentView);
        return xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl();
    }

    @Override
    public InflatedLayout inflate(Reader input,ViewGroup parentView)
    {
        String markup = IOUtil.read(input);
        XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone = inflateLayoutStandalone(markup,parentView);
        return xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl();
    }

    private XMLInflaterLayoutStandalone inflateLayoutStandalone(String markup,ViewGroup parentView)
    {
        Resources res = getContext().getResources();

        XMLDOMRegistry xmlDOMRegistry = getItsNatDroidImpl().getXMLDOMRegistry();
        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,res);

        ParsedResourceXMLDOM<XMLDOMLayout> resourceXMLDOM = xmlDOMRegistry.buildXMLDOMLayoutAndCachingByMarkupAndResDesc(markup,null, null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMParserContext);

        XMLDOMLayout xmlDOMLayout = resourceXMLDOM.getXMLDOM();

        int indexChild = parentView != null ? parentView.getChildCount() - 1 : -1;

        return (XMLInflaterLayoutStandalone)inflateLayout(xmlDOMLayout,parentView,indexChild,null);
    }

}
