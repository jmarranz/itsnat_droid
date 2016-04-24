package org.itsnat.droid.impl.browser;

import android.content.res.Resources;
import android.os.AsyncTask;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public void request(ResourceDescRemote resDescRemote,boolean async)
    {
        List<ResourceDescRemote> resDescRemoteList = new LinkedList<ResourceDescRemote>();
        resDescRemoteList.add(resDescRemote);

        request(resDescRemoteList, async);
    }

    public void request(List<ResourceDescRemote> resDescRemoteList,boolean async)
    {
        PageImpl page = getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();
        Resources res = page.getContext().getResources();

        String pageURLBase = getPageURLBase();
        HttpRequestData httpRequestData = new HttpRequestData(page);

        String itsNatServerVersion = page.getItsNatServerVersion();
        Map<String,ParsedResource> urlResDownloadedMap = new HashMap<String,ParsedResource>(); // es temporal, sólo dura durante la request, la cual suele cargar múltiples recursos y a veces de forma repetida
        XMLDOMRegistry xmlDOMRegistry = browser.getItsNatDroidImpl().getXMLDOMRegistry();

        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,res);

        if (async)
        {
            requestAsync(resDescRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        }
        else
        {
            requestSync(resDescRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        }
    }

    private void requestSync(List<ResourceDescRemote> resDescRemoteList,String pageURLBase,HttpRequestData httpRequestData,String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        List<HttpRequestResultOKImpl> resultList;

        try
        {
            resultList = executeInBackground(resDescRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        }
        catch (Exception ex)
        {
            int errorMode = itsNatDoc.getClientErrorMode();
            onFinishError(this,ex,errorListener,errorMode);

            return; // No se puede seguir
        }

        onFinishOk(this,resultList,httpRequestListener,errorListener);
    }

    private void requestAsync(List<ResourceDescRemote> resDescRemoteList,String pageURLBase,HttpRequestData httpRequestData,String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        int errorMode = itsNatDoc.getClientErrorMode();

        HttpDownloadResourcesAsyncTask task = new HttpDownloadResourcesAsyncTask(resDescRemoteList,this,pageURLBase,httpRequestData, httpRequestListener,errorListener,errorMode,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static List<HttpRequestResultOKImpl> executeInBackground(List<ResourceDescRemote> resDescRemoteList,String pageURLBase,HttpRequestData httpRequestData, String itsNatServerVersion,
                                                                    Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext) throws Exception
    {
        // Llamado en multithread en caso de async
        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
        return resDownloader.downloadResources(resDescRemoteList);
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
