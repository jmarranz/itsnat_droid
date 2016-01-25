package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageFragment;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMLayoutPageFragmentDownloader extends XMLDOMLayoutPageDownloader
{
    public XMLDOMLayoutPageFragmentDownloader(XMLDOMLayoutPageFragment xmlDOM,String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        super(xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
    }
}
