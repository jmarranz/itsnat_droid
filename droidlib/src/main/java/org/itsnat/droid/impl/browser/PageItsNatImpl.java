package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ItsNatSession;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatSessionImpl;

/**
 * Created by Jose on 11/12/2015.
 */
public class PageItsNatImpl extends PageImpl
{
    protected final String itsNatServerVersion;  // Si es null es que la página NO ha sido servida por ItsNat
    protected ItsNatSessionImpl itsNatSession;
    protected String clientId;

    public PageItsNatImpl(PageRequestImpl pageRequestToClone, PageRequestResult pageReqResult,String itsNatServerVersion)
    {
        super(pageRequestToClone, pageReqResult);
        this.itsNatServerVersion = itsNatServerVersion;
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
    public void finishLoad(String loadScript)
    {
        if (loadScript != null) // El caso null es cuando se devuelve un layout sin script inicial (layout sin scripting)
            itsNatDoc.eval(loadScript);

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

        if (getId() != null && itsNatDoc.isEventsEnabled())
            itsNatDoc.sendUnloadEvent();

        if (itsNatSession != null) // itsNatSession es null cuando la página no contiene script de inicialización aunque sea generada por ItsNat
        {
            itsNatSession.disposePage(this);
            getItsNatDroidBrowserImpl().disposeSessionIfEmpty(itsNatSession);
        }
    }
}
