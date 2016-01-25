package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.browser.serveritsnat.XMLDOMLayoutPageItsNatDownloader;
import org.itsnat.droid.impl.browser.servernotitsnat.XMLDOMLayoutPageNotItsNatDownloader;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageFragment;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;

/**
 * Created by jmarranz on 24/01/2016.
 */
public class XMLDOMDownloader
{
    protected XMLDOM xmlDOM;

    public XMLDOMDownloader(XMLDOM xmlDOM)
    {
        this.xmlDOM = xmlDOM;
    }

    public static XMLDOMDownloader createXMLDOMDownloader(XMLDOM xmlDOM)
    {
        if (xmlDOM instanceof XMLDOMLayoutPage)
        {
            if (xmlDOM instanceof XMLDOMLayoutPageItsNat)
                return new XMLDOMLayoutPageItsNatDownloader((XMLDOMLayoutPageItsNat)xmlDOM);
            else if (xmlDOM instanceof XMLDOMLayoutPageNotItsNat)
                return new XMLDOMLayoutPageNotItsNatDownloader((XMLDOMLayoutPageNotItsNat)xmlDOM);
            else if (xmlDOM instanceof XMLDOMLayoutPageFragment)
                return new XMLDOMLayoutPageFragmentDownloader((XMLDOMLayoutPageFragment)xmlDOM);
            return null; // Internal Error
        }
        else
        {
            return new XMLDOMDownloader(xmlDOM);
        }
    }

    public void downloadRemoteResources(String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        LinkedList<DOMAttrRemote> attrRemoteList = xmlDOM.getDOMAttrRemoteList();
        if (attrRemoteList != null)
        {
            downloadResources(attrRemoteList, pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        }
    }

    protected static void downloadResources(LinkedList<DOMAttrRemote> attrRemoteList,String pageURLBase,HttpRequestData httpRequestData,XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde

        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        resDownloader.downloadResources(attrRemoteList);
    }

}
