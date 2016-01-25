package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
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

    protected String getPageURLBase()
    {
        return itsNatDoc.getPageImpl().getPageURLBase();
    }

    public void request(DOMAttrRemote attrRemote,boolean async)
    {
        List<DOMAttrRemote> attrRemoteList = new LinkedList<DOMAttrRemote>();
        attrRemoteList.add(attrRemote);

        request(attrRemoteList, async);
    }

    public void request(List<DOMAttrRemote> attrRemoteList,boolean async)
    {
        if (async)
        {
            requestAsync(attrRemoteList);
        }
        else
        {
            requestSync(attrRemoteList);
        }
    }

    public void requestSync(List<DOMAttrRemote> attrRemoteList)
    {
        PageImpl page = getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();

        // No hace falta clonar porque es síncrona la llamada
        String pageURLBase = getPageURLBase();

        HttpRequestData httpRequestData = new HttpRequestData(page);

        String itsNatServerVersion = page.getItsNatServerVersion();
        XMLDOMRegistry xmlDOMRegistry = browser.getItsNatDroidImpl().getXMLDOMRegistry();

        AssetManager assetManager = page.getContext().getResources().getAssets();

        List<HttpRequestResultOKImpl> resultList;

        try
        {
            resultList = executeInBackground(attrRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,xmlDOMRegistry,assetManager);
        }
        catch (Exception ex)
        {
            int errorMode = itsNatDoc.getClientErrorMode();
            onFinishError(this,ex,errorListener,errorMode);

            return; // No se puede seguir
        }

        onFinishOk(this,resultList,httpRequestListener,errorListener);
    }

    public void requestAsync(List<DOMAttrRemote> attrRemoteList)
    {
        String pageURLBase = getPageURLBase();
        AssetManager assetManager = itsNatDoc.getPageImpl().getContext().getResources().getAssets();
        int errorMode = itsNatDoc.getClientErrorMode();
        HttpDownloadResourcesAsyncTask task = new HttpDownloadResourcesAsyncTask(attrRemoteList,this,method,pageURLBase, httpRequestListener,errorListener,errorMode,assetManager);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static List<HttpRequestResultOKImpl> executeInBackground(List<DOMAttrRemote> attrRemoteList,String pageURLBase,HttpRequestData httpRequestData, String itsNatServerVersion,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager) throws Exception
    {
        // Llamado en multithread en caso de async
        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase,httpRequestData,itsNatServerVersion,xmlDOMRegistry,assetManager);
        return resDownloader.downloadResources(attrRemoteList);
    }

    public static void onFinishOk(DownloadResourcesHttpClient parent,List<HttpRequestResultOKImpl> resultList,OnHttpRequestListener httpRequestListener,OnHttpRequestErrorListener errorListener)
    {
        for (HttpRequestResultOKImpl result : resultList)
        {
            try
            {
                parent.processResult(result, httpRequestListener);
            }
            catch (Exception ex)
            {
                if (errorListener != null)
                {
                    HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : result;
                    errorListener.onError(parent.getPageImpl(), ex, resultError);
                    return; // Paramos en el primer error
                }
                else
                {
                    if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException)ex;
                    else throw new ItsNatDroidException(ex);
                }
            }
        }
    }

    public static void onFinishError(DownloadResourcesHttpClient parent,Exception ex,OnHttpRequestErrorListener errorListener,int errorMode)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = GenericHttpClientBaseImpl.convertException(ex);

        if (errorListener != null)
        {
            errorListener.onError(parent.getPageImpl(),exFinal, result);
        }
        else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocImpl itsNatDoc = parent.getItsNatDocImpl();
                itsNatDoc.showErrorMessage(true, result,exFinal, errorMode);
            }
            else throw exFinal;
        }
    }

}
