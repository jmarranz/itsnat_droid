package org.itsnat.droid.impl.browser;

import org.itsnat.droid.impl.browser.serveritsnat.XMLDOMLayoutPageItsNatDownloader;
import org.itsnat.droid.impl.browser.servernotitsnat.XMLDOMLayoutPageNotItsNatDownloader;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageFragment;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.MimeUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 25/01/2016.
 */
public abstract class XMLDOMLayoutPageDownloader extends XMLDOMDownloader
{
    public XMLDOMLayoutPageDownloader(XMLDOMLayoutPage xmlDOM,String pageURLBase, HttpRequestData httpRequestData,String itsNatServerVersion,
                        Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
    }

    public static XMLDOMLayoutPageDownloader createXMLDOMLayoutPageDownloader(XMLDOMLayoutPage xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,
                                                          Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        if (xmlDOM instanceof XMLDOMLayoutPageItsNat)
            return XMLDOMLayoutPageItsNatDownloader.createXMLDOMLayoutPageItsNatDownloader((XMLDOMLayoutPageItsNat)xmlDOM,pageURLBase, httpRequestData, itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        else if (xmlDOM instanceof XMLDOMLayoutPageNotItsNat)
            return new XMLDOMLayoutPageNotItsNatDownloader((XMLDOMLayoutPageNotItsNat)xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        else if (xmlDOM instanceof XMLDOMLayoutPageFragment)
            return new XMLDOMLayoutPageFragmentDownloader((XMLDOMLayoutPageFragment)xmlDOM,pageURLBase,httpRequestData, itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        return null; // Internal Error
    }

    public XMLDOMLayoutPage getXMLDOMLayoutPage()
    {
        return (XMLDOMLayoutPage)xmlDOM;
    }

    @Override
    public void downloadRemoteResources() throws Exception
    {
        super.downloadRemoteResources();

        // Tenemos que descargar los <script src="..."> remótamente de forma síncrona (podemos pues estamos en un hilo no UI downloader)
        XMLDOMLayoutPage xmlDOMPage = getXMLDOMLayoutPage();
        List<DOMScript> scriptList = xmlDOMPage.getDOMScriptList();
        if (scriptList != null)
        {
            for (int i = 0; i < scriptList.size(); i++)
            {
                DOMScript script = scriptList.get(i);
                if (script instanceof DOMScriptRemote)
                {
                    DOMScriptRemote scriptRemote = (DOMScriptRemote) script;
                    String code = downloadScriptSync(scriptRemote.getSrc());
                    scriptRemote.setCode(code);
                }
            }
        }

    }

    private String downloadScriptSync(String src)
    {
        String absURL = HttpUtil.composeAbsoluteURL(src,pageURLBase);
        HttpRequestResultOKImpl result = HttpUtil.httpGet(absURL, httpRequestData, null, MimeUtil.MIME_BEANSHELL);
        return result.getResponseText();
    }

}
