package org.itsnat.droid.impl.browser.servernotitsnat;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.XMLDOMLayoutPageDownloader;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMLayoutPageNotItsNatDownloader extends XMLDOMLayoutPageDownloader
{
    public XMLDOMLayoutPageNotItsNatDownloader(XMLDOMLayoutPageNotItsNat xmlDOM,String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        super(xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
    }
}
