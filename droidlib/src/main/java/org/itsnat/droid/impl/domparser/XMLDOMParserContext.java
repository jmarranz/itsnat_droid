package org.itsnat.droid.impl.domparser;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by jmarranz on 24/02/2016.
 */
public class XMLDOMParserContext
{
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Configuration configuration;
    protected final DisplayMetrics displayMetrics;

    public XMLDOMParserContext(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager,Configuration configuration,DisplayMetrics displayMetrics)
    {
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
        this.configuration = configuration;
        this.displayMetrics = displayMetrics;
    }

    public XMLDOMRegistry getXMLDOMRegistry()
    {
        return xmlDOMRegistry;
    }

    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public DisplayMetrics getDisplayMetrics()
    {
        return displayMetrics;
    }
}
