package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.MimeUtil;

import java.util.ArrayList;

/**
 * Created by jmarranz on 25/01/2016.
 */
public abstract class XMLDOMLayoutPageDownloader extends XMLDOMDownloader
{
    public XMLDOMLayoutPageDownloader(XMLDOMLayoutPage xmlDOM)
    {
        super(xmlDOM);
    }

    public XMLDOMLayoutPage getXMLDOMLayoutPage()
    {
        return (XMLDOMLayoutPage)xmlDOM;
    }

    @Override
    public void downloadRemoteResources(String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        super.downloadRemoteResources(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);

        // Tenemos que descargar los <script src="..."> remótamente de forma síncrona (podemos pues estamos en un hilo no UI downloader)
        XMLDOMLayoutPage xmlDOMPage = getXMLDOMLayoutPage();
        ArrayList<DOMScript> scriptList = xmlDOMPage.getDOMScriptList();
        if (scriptList != null)
        {
            for (int i = 0; i < scriptList.size(); i++)
            {
                DOMScript script = scriptList.get(i);
                if (script instanceof DOMScriptRemote)
                {
                    DOMScriptRemote scriptRemote = (DOMScriptRemote) script;
                    String code = downloadScriptSync(scriptRemote.getSrc(), pageURLBase, httpRequestData);
                    scriptRemote.setCode(code);
                }
            }
        }

    }


    private static String downloadScriptSync(String src, String pageURLBase, HttpRequestData httpRequestData)
    {
        src = HttpUtil.composeAbsoluteURL(src,pageURLBase);
        HttpRequestResultOKImpl result = HttpUtil.httpGet(src, httpRequestData, null, MimeUtil.MIME_BEANSHELL);
        return result.getResponseText();
    }
}
