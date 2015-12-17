package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.util.MiscUtil;

import java.net.SocketTimeoutException;

/**
 * Created by jmarranz on 9/10/14.
 */
public abstract class GenericHttpClientBaseImpl
{
    protected ItsNatDocImpl itsNatDoc;

    protected OnHttpRequestErrorListener errorListener;
    protected int errorMode = ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS;
    protected String userUrl;
    protected OnHttpRequestListener listener;
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

    public int getErrorMode()
    {
        return errorMode;
    }

    public void setErrorModeNotFluid(int errorMode)
    {
        if (errorMode == ClientErrorMode.NOT_CATCH_ERRORS) throw new ItsNatDroidException("ClientErrorMode.NOT_CATCH_ERRORS is not supported"); // No tiene mucho sentido porque el objetivo es dejar fallar y si el usuario no ha registrado "error listeners" ItsNat Droid deja siempre fallar lanzando la excepción
        this.errorMode = errorMode;
    }

    public void setOnHttpRequestListenerNotFluid(OnHttpRequestListener listener)
    {
        this.listener = listener;
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

    public ItsNatDroidException convertException(Exception ex)
    {
        if (ex instanceof SocketTimeoutException) // Esperamos este error en el caso de timeout
             return new ItsNatDroidException(ex);
        else if (ex instanceof ItsNatDroidException)
            return (ItsNatDroidException)ex;
        else
            return new ItsNatDroidException(ex);
    }

    public void processResult(HttpRequestResultOKImpl result,OnHttpRequestListener listener,int errorMode)
    {
        if (result.isStatusOK())
        {
            if (listener != null) listener.onRequest(itsNatDoc.getPage(),result);
        }
        else // Error del servidor, otro tipo de errores lanzan una excepción
        {
            ItsNatDocImpl itsNatDoc = getItsNatDocImpl();
            itsNatDoc.showErrorMessage(true, result.getResponseText(),errorMode);
            throw new ItsNatDroidServerResponseException(result);
        }
    }

}
