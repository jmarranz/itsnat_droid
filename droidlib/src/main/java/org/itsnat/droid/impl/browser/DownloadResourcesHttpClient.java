package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 9/10/14.
 */
public class DownloadResourcesHttpClient extends GenericHttpClientBaseImpl
{
    public DownloadResourcesHttpClient(ItsNatDocImpl itsNatDoc)
    {
        super(itsNatDoc);
    }

    public List<HttpRequestResultOKImpl> request(DOMAttrRemote attrRemote,boolean async)
    {
        List<DOMAttrRemote> attrRemoteList = new LinkedList<DOMAttrRemote>();
        attrRemoteList.add(attrRemote);

        return request(attrRemoteList,async);
    }

    public List<HttpRequestResultOKImpl> request(List<DOMAttrRemote> attrRemoteList,boolean async)
    {
        if (async)
        {
            requestAsync(attrRemoteList);
            return null;
        }
        else return requestSync(attrRemoteList);
    }

    public List<HttpRequestResultOKImpl> requestSync(List<DOMAttrRemote> attrRemoteList)
    {
        PageImpl page = getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();

        // No hace falta clonar porque es síncrona la llamada
        String url = getFinalURL();

        HttpRequestData httpRequestData = new HttpRequestData(page.getPageRequestClonedImpl());

        XMLDOMRegistry xmlDOMRegistry = browser.getItsNatDroidImpl().getXMLDOMRegistry();

        AssetManager assetManager = page.getContext().getResources().getAssets();

        List<HttpRequestResultOKImpl> resultList = new LinkedList<HttpRequestResultOKImpl>();

        try
        {
            HttpResourceDownloader resDownloader =
                    new HttpResourceDownloader(url,httpRequestData,xmlDOMRegistry,assetManager);
            resDownloader.downloadResources(attrRemoteList,resultList);
        }
        catch (Exception ex)
        {
            ItsNatDroidException exFinal = convertException(ex);
            throw exFinal;

            // No usamos aquí el OnEventErrorListener porque la excepción es capturada por un catch anterior que sí lo hace
        }

        for(HttpRequestResultOKImpl result : resultList)
            processResult(result,listener,errorMode);

        return resultList;
    }

    public void requestAsync(List<DOMAttrRemote> attrRemoteList)
    {
        String url = getFinalURL();
        AssetManager assetManager = itsNatDoc.getPageImpl().getContext().getResources().getAssets();
        HttpDownloadResourcesAsyncTask task = new HttpDownloadResourcesAsyncTask(attrRemoteList,this,method,url,listener,errorListener,errorMode,assetManager);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

}
