package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 9/10/14.
 */
public abstract class GenericHttpClientBaseImpl
{
    protected ItsNatDocImpl itsNatDoc;

    protected OnHttpRequestErrorListener errorListener;
    protected String userUrl;
    protected OnHttpRequestListener httpRequestListener;
    protected String method = "POST"; // Por defecto

    public GenericHttpClientBaseImpl(ItsNatDocImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
    }

    protected GenericHttpClientBaseImpl()
    {
    }

    public PageImpl getPageImpl()
    {
        return itsNatDoc.getPageImpl();
    }

    public ItsNatDocImpl getItsNatDocImpl()
    {
        return itsNatDoc;
    }

    public void setOnHttpRequestListenerNotFluid(OnHttpRequestListener listener)
    {
        this.httpRequestListener = listener;
    }

    public void setOnHttpRequestErrorListenerNotFluid(OnHttpRequestErrorListener httpErrorListener)
    {
        this.errorListener = httpErrorListener;
    }

    public void setMethodNotFluid(String method)
    {
        this.method = method;
    }

    public void setURLNotFluid(String url)
    {
        this.userUrl = url;
    }



    public String getPageURLBase()
    {
        return itsNatDoc.getPageURLBase();
    }

    protected String getFinalURL()
    {
        return MiscUtil.isEmpty(userUrl) ? itsNatDoc.getPageURLBase() : userUrl; // Como se puede ver seguridad de "single server" ninguna
    }

    public static ItsNatDroidException convertException(Exception ex)
    {
        if (ex instanceof ItsNatDroidException)
            return (ItsNatDroidException)ex;
        else
            return new ItsNatDroidException(ex);
    }

    public void processResult(HttpRequestResultOKImpl result,OnHttpRequestListener listener)
    {
        if (listener != null) listener.onRequest(itsNatDoc.getPage(),result);
    }

}
