package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.GenericHttpClient;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDocPage;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidScriptException;
import org.itsnat.droid.ItsNatView;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.Page;
import org.itsnat.droid.impl.ItsNatDocImpl;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.ItsNatDocPageNotItsNatImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.PageNotItsNatImpl;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageImpl;

import bsh.EvalError;

/**
 * Esta clase se accede via script beanshell y representa el "ClientDocument" en el lado Android simétrico a los objetos JavaScript en el modo web
 * Created by jmarranz on 9/06/14.
 */
public abstract class ItsNatDocPageImpl extends ItsNatDocImpl implements ItsNatDocPage, ItsNatDocPagePublic
{
    protected final PageImpl page;

    protected final FragmentLayoutInserter fragmentLayoutInserter = new FragmentLayoutInserter(this);
    protected final ItsNatViewNullImpl nullView = new ItsNatViewNullImpl(this); // Viene a tener el rol del objeto Window en web, útil para registrar eventos unload etc
    protected final DroidEventDispatcher eventDispatcher = DroidEventDispatcher.createDroidEventDispatcher(this); // En el caso de "No ItsNat" hay un limitado soporte de inline event handlers (onclick etc)

    public ItsNatDocPageImpl(PageImpl page)
    {
        super(page.getItsNatDroidBrowserImpl().getItsNatDroidImpl());

        this.page = page;
    }

    public static ItsNatDocPageImpl createItsNatDocPage(PageImpl page)
    {
        if (page instanceof PageItsNatImpl)
            return new ItsNatDocPageItsNatImpl((PageItsNatImpl)page,page.getClientErrorMode());
        else if (page instanceof PageNotItsNatImpl)
            return new ItsNatDocPageNotItsNatImpl((PageNotItsNatImpl)page);
        else
            throw MiscUtil.internalError();
    }

    public XMLDOMParserContext getXMLDOMParserContext()
    {
        return getPageImpl().getXMLDOMParserContext();
    }

    public InflatedXMLLayoutPageImpl getInflatedXMLLayoutPageImpl()
    {
        return page.getInflatedXMLLayoutPageImpl();
    }

    @Override
    public View getRootView()
    {
        return getInflatedXMLLayoutPageImpl().getRootView();
    }

    @Override
    public View findViewByXMLId(String id)
    {
        return getInflatedXMLLayoutPageImpl().findViewByXMLId(id);
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


    public PageImpl getPageImpl()
    {
        return page;
    }



    public DroidEventDispatcher getDroidEventDispatcher()
    {
        return eventDispatcher;
    }


    @Override
    public void appendFragment(View parentView, String markup)
    {
        // Este método NO es llamado directamente por el código interno generado por ItsNat Server, es público para el usuario
        insertFragment(parentView,markup,null);
    }

    @Override
    public void insertFragment(View parentView, String markup,View viewRef)
    {
        // Ver notas en appendFragment
        fragmentLayoutInserter.insertPageFragment((ViewGroup) parentView, markup, viewRef, getXMLDOMParserContext());
    }


    public void showErrorMessage(boolean serverErr, String msg)
    {
        showErrorMessage(serverErr, msg, getClientErrorMode());
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


    @Override
    public Page getPage()
    {
        // Es un método público que puede ser interesante para acceder a info de la página desde beanshell (por ej acceder al Context de la página desde fuera)
        return page;
    }

    public int getClientErrorMode()
    {
        return page.getClientErrorMode();
    }

    @Override
    public Object eval(String code,Object context)
    {
        try
        {
//long start = System.currentTimeMillis();

            return getInterpreter().eval(code); // No se pasa el context porque seía un set. Ver que se puede devolver en "Getting Interfaces from Interpreter" en https://github.com/beanshell/beanshell/wiki/Embedding-BeanShell-in-Your-Application

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

        return null;
    }

    @Override
    public void set(String name, Object value)
    {
        try
        {
             getInterpreter().set(name,value);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }

    @Override
    public void unset(String name)  {
        try
        {
            getInterpreter().unset(name);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }


    @Override
    public void downloadScript(String src)
    {
        // Es llamado desde ItsNat Server por ello se define en la interface ItsNatDocPublic
        OnHttpRequestListener listener = new OnHttpRequestListener()
        {
            @Override
            public void onRequest(Page page,HttpRequestResult response)
            {
                eval(response.getResponseText());
            }
        };

        downloadScript(src,listener);
    }

    public void downloadScript(String src,OnHttpRequestListener listener)
    {
        downloadFile(src, MimeUtil.MIME_BEANSHELL, listener);
    }

    private void downloadFile(String src,String mime,OnHttpRequestListener listener)
    {
        PageImpl page = getPageImpl();
        boolean sync = page.getPageRequestClonedImpl().isSynchronous();

        String absURL = HttpUtil.composeAbsoluteURL(src, page.getPageURLBase());

        GenericHttpClientImpl client = createGenericHttpClientImpl();

        client.setURL(absURL)
               .setOverrideMimeType(mime)
               .setOnHttpRequestListener(listener);
        client.request(!sync);
    }

    public void downloadResources(ResourceDescRemote resDescRemote,final Runnable task)
    {
        OnHttpRequestListener listener = new OnHttpRequestListener()
        {
            @Override
            public void onRequest(Page page,HttpRequestResult response)
            {
                task.run();
            }
        };

        PageImpl page = getPageImpl();
        boolean sync = page.getPageRequestClonedImpl().isSynchronous();

        DownloadResourcesHttpClient client = new DownloadResourcesHttpClient(this);
        client.setOnHttpRequestErrorListenerNotFluid(page.getOnHttpRequestErrorListener());
        client.setOnHttpRequestListenerNotFluid(listener);
        client.request(resDescRemote, !sync);
    }

    @Override
    public ItsNatResourcesImpl createItsNatResourcesImpl()
    {
        return new ItsNatResourcesRemoteImpl(this);
    }

    @Override
    public Context getContext()
    {
        return page.getContext();
    }



}
