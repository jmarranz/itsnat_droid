package org.itsnat.droid.impl.browser;

import android.content.Context;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.OnServerStateLostListener;
import org.itsnat.droid.Page;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.UserData;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;
import org.itsnat.droid.impl.util.UserDataImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.InflateLayoutRequestPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;

/**
 * Created by jmarranz on 4/06/14.
 */
public abstract class PageImpl implements Page
{
    protected final PageRequestResult pageReqResult;
    protected final PageRequestImpl pageRequestCloned; // Nos interesa únicamente para el reload, es un clone del original por lo que podemos tomar datos del mismo sin miedo a cambiarse

    protected final int bitmapDensityReference;
    protected int errorMode;
    protected final XMLInflaterLayoutPage xmlInflaterLayoutPage;
    protected final String uniqueIdForInterpreter;
    protected final Interpreter interp;
    protected final ItsNatDocImpl itsNatDoc;
    protected OnScriptErrorListener scriptErrorListener;
    protected OnEventErrorListener eventErrorListener;
    protected OnServerStateLostListener stateLostListener;
    protected OnHttpRequestErrorListener httpReqErrorListener;
    protected UserDataImpl userData;
    protected boolean dispose;

    public PageImpl(PageRequestImpl pageRequestToClone,PageRequestResult pageReqResult)
    {
        this.pageRequestCloned = pageRequestToClone.clone(); // De esta manera conocemos como se ha creado pero podemos reutilizar el PageRequestImpl original
        this.pageReqResult = pageReqResult;

        this.scriptErrorListener = pageRequestToClone.getOnScriptErrorListener();

        HttpRequestResultOKImpl httpReqResult = pageReqResult.getHttpRequestResultOKImpl();

        Integer bitmapDensityReference = httpReqResult.getBitmapDensityReference(); // Sólo está definida en el caso de ItsNat server, por eso se puede pasar por el usuario también a través del PageRequest
        this.bitmapDensityReference = bitmapDensityReference != null ? bitmapDensityReference : pageRequestCloned.getBitmapDensityReference();

        ItsNatDroidImpl itsNatDroid = pageRequestCloned.getItsNatDroidBrowserImpl().getItsNatDroidImpl();
        ItsNatDroidBrowserImpl browser = pageRequestCloned.getItsNatDroidBrowserImpl();

        this.uniqueIdForInterpreter = browser.getUniqueIdGenerator().generateId("i"); // i = interpreter
        this.interp = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(browser.getInterpreter().getNameSpace(), uniqueIdForInterpreter)); // El StringReader está copiado del código fuente de beanshell2 https://code.google.com/p/beanshell2/source/browse/branches/v2.1/src/bsh/Interpreter.java

        int errorMode = pageRequestCloned.getClientErrorMode(); // En una página devuelta por ItsNat Server será reemplazado en el init
        this.itsNatDoc = ItsNatDocImpl.createItsNatDoc(this,errorMode);

        // Definimos pronto el itsNatDoc para que los layout include tengan algún soporte de scripting de ItsNatDoc por ejemplo toast, eval, alert etc antes de inflarlos
        try
        {
            interp.set("itsNatDoc", itsNatDoc);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }

        StringBuilder methods = new StringBuilder();
        methods.append("alert(data){itsNatDoc.alert(data);}");
        methods.append("toast(value,duration){itsNatDoc.toast(value,duration);}");
        methods.append("toast(value){itsNatDoc.toast(value);}");
        methods.append("eval(code){itsNatDoc.eval(code);}");
        itsNatDoc.eval(methods.toString()); // Rarísimo que de error


        InflateLayoutRequestPageImpl inflateLayoutRequest = new InflateLayoutRequestPageImpl(itsNatDroid,this);


        XMLDOMLayout domLayout = pageReqResult.getXMLDOMLayout();

        this.xmlInflaterLayoutPage = (XMLInflaterLayoutPage) inflateLayoutRequest.inflateLayout(domLayout,null,-1,this);

        InflatedLayoutPageImpl inflatedLayoutPage = xmlInflaterLayoutPage.getInflatedLayoutPageImpl();

        String loadScript = inflatedLayoutPage.getLoadScript();
        List<String> scriptList = inflatedLayoutPage.getScriptList();

        if (!scriptList.isEmpty())
        {
            for (String code : scriptList)
            {
                itsNatDoc.eval(code);
            }
        }

        finishLoad(loadScript);
    }

    public abstract void finishLoad(String loadScript);

    public ItsNatDroidBrowserImpl getItsNatDroidBrowserImpl()
    {
        return pageRequestCloned.getItsNatDroidBrowserImpl();
    }

    public PageRequestResult getPageRequestResult()
    {
        return pageReqResult;
    }

    public PageRequestImpl getPageRequestClonedImpl()
    {
        return pageRequestCloned;
    }

    @Override
    public ItsNatDroidBrowser getItsNatDroidBrowser()
    {
        return getItsNatDroidBrowserImpl();
    }


    @Override
    public Map<String, List<String>> getRequestProperties()
    {
        return pageRequestCloned.getRequestProperties();
    }

    public RequestPropertyMap getRequestPropertyMapImpl()
    {
        return getPageRequestClonedImpl().getRequestPropertyMap();
    }

    public HttpParams getHttpParams()
    {
        return getPageRequestClonedImpl().getHttpParams();
    }

    @Override
    public int getConnectTimeout()
    {
        return getPageRequestClonedImpl().getConnectTimeout();
    }

    @Override
    public int getReadTimeout()
    {
        return getPageRequestClonedImpl().getReadTimeout();
    }

    @Override
    public HttpRequestResult getHttpRequestResult()
    {
        return getHttpRequestResultOKImpl();
    }

    public HttpRequestResultOKImpl getHttpRequestResultOKImpl()
    {
        return pageReqResult.getHttpRequestResultOKImpl();
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    public int getClientErrorMode()
    {
        return errorMode;
    }

    @Override
    public String getURL()
    {
        return pageRequestCloned.getURL();
    }

    public String getURLBase()
    {
        return pageRequestCloned.getURLBase();
    }

    public abstract String getItsNatServerVersion();

    public InflatedLayoutPageImpl getInflatedLayoutPageImpl()
    {
        return xmlInflaterLayoutPage.getInflatedLayoutPageImpl();
    }

    public XMLInflaterLayoutPage getXMLInflaterLayoutPage()
    {
        return xmlInflaterLayoutPage;
    }

    public Interpreter getInterpreter()
    {
        return interp;
    }


    public Context getContext()
    {
        return pageRequestCloned.getContext();
    }

    public UserData getUserData()
    {
        if (userData == null) this.userData = new UserDataImpl();
        return userData;
    }

    public ItsNatDoc getItsNatDoc()
    {
        return getItsNatDocImpl();
    }

    public ItsNatDocImpl getItsNatDocImpl()
    {
        return itsNatDoc;
    }

    public OnEventErrorListener getOnEventErrorListener()
    {
        return eventErrorListener;
    }

    @Override
    public void setOnEventErrorListener(OnEventErrorListener listener)
    {
        this.eventErrorListener = listener;
    }

    public OnScriptErrorListener getOnScriptErrorListener()
    {
        return scriptErrorListener;
    }

    @Override
    public void setOnScriptErrorListener(OnScriptErrorListener listener)
    {
        this.scriptErrorListener = listener;
    }

    public OnServerStateLostListener getOnServerStateLostListener()
    {
        return stateLostListener;
    }

    public void setOnServerStateLostListener(OnServerStateLostListener listener)
    {
        this.stateLostListener = listener;
    }

    public OnHttpRequestErrorListener getOnHttpRequestErrorListener()
    {
        return httpReqErrorListener;
    }

    public void setOnHttpRequestErrorListener(OnHttpRequestErrorListener listener)
    {
        this.httpReqErrorListener = listener;
    }

    public PageRequest reusePageRequest()
    {
        return pageRequestCloned.clone();
    }

    public void dispose()
    {
        if (dispose) return;
        this.dispose = true;
    }


}
