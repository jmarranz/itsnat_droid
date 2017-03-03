package org.itsnat.droid.impl.browser.serveritsnat;

import org.itsnat.droid.ItsNatSession;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.PageRequestImpl;
import org.itsnat.droid.impl.browser.PageRequestResult;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageItsNatImpl;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPageItsNat;

import java.util.LinkedList;

/**
 * Created by Jose on 11/12/2015.
 */
public class PageItsNatImpl extends PageImpl
{
    protected final String itsNatServerVersion;  // NO PUEDE SER NULL
    protected ItsNatSessionImpl itsNatSession; // itsNatSession es null cuando la página no contiene script de inicialización aunque sea generada por ItsNat
    protected String clientId;

    public PageItsNatImpl(PageRequestImpl pageRequestToClone, PageRequestResult pageReqResult,String itsNatServerVersion)
    {
        super(pageRequestToClone, pageReqResult);
        this.itsNatServerVersion = itsNatServerVersion;
    }

    public XMLInflaterLayoutPageItsNat getXMLInflaterLayoutPageItsNat()
    {
        return (XMLInflaterLayoutPageItsNat)xmlInflaterLayoutPage;
    }

    public ItsNatDocPageItsNatImpl getItsNatDocItsNatImpl()
    {
        return (ItsNatDocPageItsNatImpl)itsNatDoc;
    }

    public InflatedXMLLayoutPageItsNatImpl getInflatedXMLLayoutPageItsNatImpl()
    {
        return (InflatedXMLLayoutPageItsNatImpl)getXMLInflaterLayoutPageItsNat().getInflatedXMLLayoutPageItsNatImpl();
    }

    @Override
    public String getItsNatServerVersion()
    {
        return itsNatServerVersion;
    }

    public ItsNatSessionImpl getItsNatSessionImpl()
    {
        return itsNatSession;
    }

    @Override
    public ItsNatSession getItsNatSession()
    {
        return itsNatSession;
    }

    @Override
    public String getId()
    {
        return clientId;
    }

    @Override
    public void finishLoad(PageRequestResult pageReqResult)
    {
        ItsNatDocPageItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();

        InflatedXMLLayoutPageItsNatImpl inflatedLayoutPage = getXMLInflaterLayoutPageItsNat().getInflatedXMLLayoutPageItsNatImpl();

        String loadInitScript = inflatedLayoutPage.getLoadInitScript();

        if (loadInitScript != null) // El caso null es cuando se devuelve un layout sin script inicial (layout sin scripting)
        {
            LinkedList<DOMAttrRemote> attrRemoteListBSParsed = pageReqResult.getAttrRemoteListBSParsed();
            itsNatDoc.evalLoadInitScript(loadInitScript, attrRemoteListBSParsed);
            // pageReqResult se pierde, se pierde también attrRemoteListBSParsed pero no pasa nada pues sólo se usa una vez
        }

        if (getId() != null && itsNatDoc.isEventsEnabled()) // Es página generada por ItsNat con scripting y tiene los eventos enabled
            itsNatDoc.sendLoadEvent();
        else
            dispose(); // En el servidor
    }

    public void setSessionIdAndClientId(String stdSessionId,String sessionToken,String sessionId,String clientId)
    {
        this.clientId = clientId;
        this.itsNatSession = getItsNatDroidBrowserImpl().getItsNatSession(stdSessionId,sessionToken,sessionId);
        itsNatSession.registerPage(this);
    }

    @Override
    public void dispose()
    {
        if (dispose) return;

        super.dispose();

        ItsNatDocPageItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();

        if (getId() != null && itsNatDoc.isEventsEnabled())
            itsNatDoc.sendUnloadEvent();

        if (itsNatSession != null) // itsNatSession es null cuando la página no contiene script de inicialización aunque sea generada por ItsNat
        {
            itsNatSession.disposePage(this);
            getItsNatDroidBrowserImpl().disposeSessionIfEmpty(itsNatSession);
        }
    }
}
