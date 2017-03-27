package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescAsset;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.ResourceDescIntern;
import org.itsnat.droid.impl.dom.ResourceDescLocal;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.stdalone.InflatedLayoutImpl;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    protected String internLocationBase;

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

    public String getInternLocationBase()
    {
        return internLocationBase;
    }

    @Override
    public InflateLayoutRequest setInternLocationBase(String internlocationBase)
    {
        this.internLocationBase = internlocationBase;
        return this;
    }

    public Context getContext()
    {
        return ctx;
    }

    @Override
    public InflatedLayout inflate(InputStream input,String resourceType,ViewGroup parentView,int indexChild)
    {
        String markup = IOUtil.read(input,encoding);
        return inflateLayoutStandalone(markup,resourceType,parentView,indexChild);
    }

    @Override
    public InflatedLayout inflate(Reader input,String resourceType,ViewGroup parentView,int indexChild)
    {
        String markup = IOUtil.read(input);
        return inflateLayoutStandalone(markup,resourceType,parentView,indexChild);
    }

    @Override
    public InflatedLayout inflate(String resourceDescValue,ViewGroup parentView,int indexChild)
    {
        ResourceDescDynamic resourceDesc = (ResourceDescDynamic)ResourceDescLocal.create(resourceDescValue);

        Context ctx = getContext();

        InputStream input;

        try
        {
            if (resourceDesc instanceof ResourceDescAsset)
            {
                String location = resourceDesc.getLocation();
                AssetManager am = ctx.getResources().getAssets();
                input = am.open(location);
            }
            else  if (resourceDesc instanceof ResourceDescIntern)
            {
                String location = resourceDesc.getLocation();
                String internLocationBaseTmp = this.internLocationBase != null ? this.internLocationBase : "intern";
                File rootDir = ctx.getDir(internLocationBaseTmp, Context.MODE_PRIVATE);
                File locationFile = new File(rootDir.getAbsolutePath(),location);
                input = new FileInputStream(locationFile);
            }
            else throw new ItsNatDroidException("Only assets or intern are supported");

        }
        catch (IOException e)
        {
            throw new ItsNatDroidException(e);
        }

        String markup = IOUtil.read(input,encoding);

        return inflateLayoutStandalone(markup,resourceDesc,parentView,indexChild);
    }



    private InflatedLayoutImpl inflateLayoutStandalone(String markup,String resourceType,ViewGroup parentView,int indexChild)
    {
        String resourceDescValue;
        try
        {
            if ("assets".equals(resourceType))
            {
                resourceDescValue = "assets:" + MessageDigest.getInstance("MD5");
            }
            else if ("inner".equals(resourceType))
            {
                resourceDescValue = "inner:" + MessageDigest.getInstance("MD5");
            }
            else throw new ItsNatDroidException("Only assets or inner values are recognized");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new ItsNatDroidException(e);
        }

        ResourceDescDynamic resourceDesc = (ResourceDescDynamic) ResourceDescLocal.create(resourceDescValue);

        return inflateLayoutStandalone(markup, resourceDesc, parentView, indexChild);
    }


    private InflatedLayoutImpl inflateLayoutStandalone(String markup,ResourceDescDynamic resourceDesc,ViewGroup parentView,int indexChild)
    {
        Context ctx = getContext();

        XMLDOMRegistry xmlDOMRegistry = getItsNatDroidImpl().getXMLDOMRegistry();
        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,ctx);

        ParsedResourceXMLDOM<XMLDOMLayout> resourceXMLDOM = xmlDOMRegistry.buildXMLDOMLayoutAndCachingByMarkupAndResDesc(markup,resourceDesc, null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMParserContext);

        XMLDOMLayout xmlDOMLayout = resourceXMLDOM.getXMLDOM();

        XMLInflaterLayoutStandalone xmlInflater = (XMLInflaterLayoutStandalone)XMLInflaterLayoutStandalone.createXMLInflaterLayout(getItsNatDroidImpl(),xmlDOMLayout,getBitmapDensityReference(),getAttrResourceInflaterListener(),ctx,null);

        View rootViewOrViewParent = xmlInflater.inflateLayout(parentView,indexChild);
        InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone = xmlInflater.getInflatedXMLLayoutStandaloneImpl();
        return new InflatedLayoutImpl(itsNatDroid,inflatedXMLLayoutStandalone);
    }

}
