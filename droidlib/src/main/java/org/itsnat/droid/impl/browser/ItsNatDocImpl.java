package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.GenericHttpClient;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroidScriptException;
import org.itsnat.droid.ItsNatView;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.Page;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.ItsNatDocNotItsNatImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.PageNotItsNatImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.UINotification;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Esta clase se accede via script beanshell y representa el "ClientDocument" en el lado Android simétrico a los objetos JavaScript en el modo web
 * Created by jmarranz on 9/06/14.
 */
public abstract class ItsNatDocImpl implements ItsNatDoc, ItsNatDocPublic
{
    protected PageImpl page;
    protected int errorMode;
    protected Handler handler;
    protected FragmentLayoutInserter fragmentLayoutInserter = new FragmentLayoutInserter(this);
    protected ItsNatViewNullImpl nullView = new ItsNatViewNullImpl(this); // Viene a tener el rol del objeto Window en web, útil para registrar eventos unload etc
    protected DroidEventDispatcher eventDispatcher = DroidEventDispatcher.createDroidEventDispatcher(this);

    public ItsNatDocImpl(PageImpl page,int errorMode)
    {
        this.page = page;
        this.errorMode = errorMode;
    }

    public static ItsNatDocImpl createItsNatDoc(PageImpl page,int errorMode)
    {
        if (page instanceof PageItsNatImpl)
            return new ItsNatDocItsNatImpl((PageItsNatImpl)page,errorMode);
        else if (page instanceof PageNotItsNatImpl)
            return new ItsNatDocNotItsNatImpl((PageNotItsNatImpl)page,errorMode);
        return null; // Impossible
    }

    @Override
    public ItsNatView getItsNatView(View view)
    {
        return getItsNatViewImpl(view);
    }

    public ItsNatViewNullImpl getItsNatViewNull()
    {
        return nullView;
    }

    public ItsNatViewImpl getItsNatViewImpl(View view)
    {
        return ItsNatViewImpl.getItsNatView(this, view);
    }

    public GenericHttpClientImpl createGenericHttpClientImpl()
    {
        GenericHttpClientImpl client = new GenericHttpClientImpl(this);
        client.setOnHttpRequestErrorListener(getPageImpl().getOnHttpRequestErrorListener());
        return client;
    }

    @Override
    public GenericHttpClient createGenericHttpClient()
    {
        return createGenericHttpClientImpl();
    }

    public DownloadResourcesHttpClient createDownloadResourcesHttpClient()
    {
        DownloadResourcesHttpClient client = new DownloadResourcesHttpClient(this);
        client.setOnHttpRequestErrorListenerNotFluid(getPageImpl().getOnHttpRequestErrorListener());
        return client;
    }

    public PageImpl getPageImpl()
    {
        return page;
    }

    public String getPageURLBase()
    {
        return page.getURLBase(); // Para la carga de recursos (scripts, imágenes etc)
    }

    public DroidEventDispatcher getDroidEventDispatcher()
    {
        return eventDispatcher;
    }


    @Override
    public View getRootView()
    {
        return page.getInflatedLayoutPageImpl().getRootView();
    }

    @Override
    public View findViewByXMLId(String id)
    {
        return page.getInflatedLayoutPageImpl().findViewByXMLId(id);
    }

    @Override
    public int getResourceIdentifier(String name)
    {
        // Formato esperado: package:type/entry  ej my.app:id/someId  o bien simplemente someId

        String packageName;
        int posPkg = name.indexOf(':');
        if (posPkg != -1)
        {
            packageName = null; // Tiene package el value, ej "android:" delegamos en Resources.getIdentifier() que lo resuelva
            name = name.substring(posPkg + 1);
        }
        else
        {
            packageName = getContext().getPackageName(); // El package es necesario como parámetro sólo cuando no está en la string (recursos compilados)
        }

        String type;
        int posType = name.indexOf('/');
        if (posType != -1)
        {
            type = null; // Se obtiene del name
            name = name.substring(posType + 1);
        }
        else
        {
            type = "id";
        }

        return getResourceIdentifier(name, type, packageName);
    }

    @Override
    public int getResourceIdentifier(String name, String defType, String defPackage)
    {
        // http://developer.android.com/reference/android/content/res/Resources.html#getIdentifier(java.lang.String, java.lang.String, java.lang.String)
        // Formato esperado: package:type/entry  ej my.app:id/someId  o bien type y package vienen dados como parámetros

        Resources res = getContext().getResources();
        int id = res.getIdentifier(name, defType, defPackage);
        if (id > 0)
            return id;

        XMLInflateRegistry layoutService = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLInflateRegistry();
        id = layoutService.findId(name);
        return id;
    }

    @Override
    public void appendFragment(View parentView, String markup)
    {
        insertFragment(parentView,markup,null);
    }

    @Override
    public void insertFragment(View parentView, String markup,View viewRef)
    {
        // Si el fragmento a insertar es suficientemente grande el rendimiento de insertFragment puede ser varias veces superior
        // a hacerlo elemento a elemento, atributo a atributo con la API debido a la lentitud de Beanshell
        // Por ejemplo 78ms con insertFragment (parseando markup) y 179ms con beanshell puro

        fragmentLayoutInserter.insertFragment((ViewGroup) parentView, markup, viewRef);
    }

    @Override
    public void eval(String code)
    {
        eval(code,null);
    }

    public void eval(String code,Object context)
    {
        Interpreter interp = page.getInterpreter();
        try
        {
//long start = System.currentTimeMillis();

            interp.eval(code);

//long end = System.currentTimeMillis();
//System.out.println("LAPSE" + (end - start));
        }
        catch (EvalError ex)
        {
            PageImpl page = getPageImpl();
            OnScriptErrorListener errorListener = page.getOnScriptErrorListener();
            if (errorListener != null)
            {
                errorListener.onError(page, code, ex, context);
            }
            else
            {
                int errorMode = getClientErrorMode();
                if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
                {
                    showErrorMessage(false, ex.getMessage());
                }
                else throw new ItsNatDroidScriptException(ex, code);
            }
        }
        catch (Exception ex)
        {
            PageImpl page = getPageImpl();
            OnScriptErrorListener errorListener = page.getOnScriptErrorListener();
            if (errorListener != null)
            {
                errorListener.onError(page, code, ex, context);
            }
            else
            {
                int errorMode = getClientErrorMode();
                if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
                {
                    showErrorMessage(false, ex.getMessage());
                }
                else throw new ItsNatDroidScriptException(ex, code);
            }
        }
    }


    public void showErrorMessage(boolean serverErr, String msg)
    {
        showErrorMessage(serverErr, msg, errorMode);
    }

    public void showErrorMessage(boolean serverErr,HttpRequestResult result,Exception ex,int errorMode)
    {
        String msg;
        if (result != null) msg = result.getResponseText(); // Normalmente el error del servidor, en el caso de SockectTimeoutException será null y usaremos la exception
        else msg = ex.getMessage();
        showErrorMessage(serverErr,msg,errorMode);
    }

    public void showErrorMessage(boolean serverErr, String msg,int errorMode)
    {
        if (errorMode == ClientErrorMode.NOT_SHOW_ERRORS) return;

        if (serverErr) // Pagina HTML con la excepcion del servidor
        {
            if ((errorMode == ClientErrorMode.SHOW_SERVER_ERRORS) ||
                    (errorMode == ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS)) // 2 = ClientErrorMode.SHOW_SERVER_ERRORS, 4 = ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS
                alert("SERVER ERROR: " + msg);
        }
        else
        {
            if ((errorMode == ClientErrorMode.SHOW_CLIENT_ERRORS) ||
                    (errorMode == ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS)) // 3 = ClientErrorMode.SHOW_CLIENT_ERRORS, 4 = ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS
            {
                // Ha sido un error Beanshell
                alert(msg);
            }
        }
    }



    protected Context getContext()
    {
        return page.getContext();
    }

    @Override
    public Page getPage()
    {
        // Es un método público que puede ser interesante para acceder a info de la página desde beanshell (por ej acceder al Context de la página desde fuera)
        return page;
    }

    public int getClientErrorMode()
    {
        return errorMode;
    }


    @Override
    public void alert(Object value)
    {
        alert("Alert", value);
    }

    @Override
    public void alert(String title,Object value)
    {
        UINotification.alert(title, value, getContext());
    }

    @Override
    public void toast(Object value,int duration)
    {
        UINotification.toast(value, duration, getContext());
    }

    @Override
    public void toast(Object value)
    {
        toast(value, Toast.LENGTH_SHORT);
    }

    @Override
    public void postDelayed(Runnable task,long delay)
    {
        getHandler().postDelayed(task, delay);
    }

    public Handler getHandler()
    {
        if (handler == null) this.handler = new Handler(); // Se asociará (debe) al hilo UI
        return handler;
    }



    @Override
    public void downloadScript(String src)
    {
        OnHttpRequestListener listener = new OnHttpRequestListener()
        {
            @Override
            public void onRequest(Page page,HttpRequestResult response)
            {
                eval(response.getResponseText());
            }
        };

        downloadFile(src, MimeUtil.MIME_BEANSHELL,listener);
    }

    public void downloadFile(String src,String mime,OnHttpRequestListener listener)
    {
        boolean sync = getPageImpl().getPageRequestClonedImpl().isSynchronous();

        GenericHttpClientImpl client = createGenericHttpClientImpl();

        src = HttpUtil.composeAbsoluteURL(src, client.getPageURLBase());

        client.setURL(src)
                .setOverrideMimeType(mime)
                .setOnHttpRequestListener(listener)
                .request(!sync);
    }

    public void downloadResources(DOMAttrRemote attr,final Runnable task)
    {
        OnHttpRequestListener listener = new OnHttpRequestListener()
        {
            @Override
            public void onRequest(Page page,HttpRequestResult response)
            {
                task.run();
            }
        };

        boolean sync = getPageImpl().getPageRequestClonedImpl().isSynchronous();

        DownloadResourcesHttpClient client = createDownloadResourcesHttpClient();
        client.setOnHttpRequestListenerNotFluid(listener);
        client.request(attr, !sync);
    }

}
