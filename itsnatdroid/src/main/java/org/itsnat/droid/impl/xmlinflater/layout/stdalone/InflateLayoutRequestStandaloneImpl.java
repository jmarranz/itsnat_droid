package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
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

import java.io.InputStream;
import java.io.Reader;

/**
 * Created by jmarranz on 5/06/14.
 */
public class InflateLayoutRequestStandaloneImpl implements InflateLayoutRequest
{
    protected ItsNatDroidImpl itsNatDroid;
    protected Context ctx;
    protected String encoding = "UTF-8";
    protected int bitmapDensityReference = DisplayMetrics.DENSITY_XHIGH; // 320 (xhdpi), por ej el Nexus 4
    protected AttrResourceInflaterListener attrResourcesInflaterListener;

    public InflateLayoutRequestStandaloneImpl(ItsNatDroidImpl itsNatDroid)
    {
        this.itsNatDroid = itsNatDroid;
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
        return xmlInflaterLayoutStandalone.getInflatedXMLLayoutStandaloneImpl();
    }

    @Override
    public InflatedLayout inflate(Reader input,ViewGroup parentView)
    {
        String markup = IOUtil.read(input);
        XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone = inflateLayoutStandalone(markup,parentView);
        return xmlInflaterLayoutStandalone.getInflatedXMLLayoutStandaloneImpl();
    }

    private XMLInflaterLayoutStandalone inflateLayoutStandalone(String markup,ViewGroup parentView)
    {
        Context ctx = getContext();

        XMLDOMRegistry xmlDOMRegistry = getItsNatDroidImpl().getXMLDOMRegistry();
        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,ctx);

        ParsedResourceXMLDOM<XMLDOMLayout> resourceXMLDOM = xmlDOMRegistry.buildXMLDOMLayoutAndCachingByMarkupAndResDesc(markup,null, null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMParserContext);

        XMLDOMLayout xmlDOMLayout = resourceXMLDOM.getXMLDOM();

        int indexChild = parentView != null ? parentView.getChildCount() - 1 : -1;

        XMLInflaterLayoutStandalone xmlInflater = (XMLInflaterLayoutStandalone)XMLInflaterLayoutStandalone.createXMLInflaterLayout(getItsNatDroidImpl(),xmlDOMLayout,getBitmapDensityReference(),getAttrResourceInflaterListener(),ctx,null);

        View rootViewOrViewParent = xmlInflater.inflateLayout(parentView,indexChild);
        return xmlInflater;
    }

}
