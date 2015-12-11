package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatSession;

/**
 * Created by Jose on 11/12/2015.
 */
public class PageNotItsNatImpl extends PageImpl
{
    public PageNotItsNatImpl(PageRequestImpl pageRequestToClone, PageRequestResult pageReqResult)
    {
        super(pageRequestToClone, pageReqResult);
    }

    @Override
    public String getItsNatServerVersion()
    {
        return null;
    }

    @Override
    public ItsNatSession getItsNatSession()
    {
        return null;
    }

    @Override
    public String getId()
    {
        return null;
    }

    @Override
    public void finishLoad(String loadScript)
    {
        if (loadScript != null) throw new ItsNatDroidException("Unexpected");

        dispose(); // En el servidor
    }
}
