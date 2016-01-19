package org.itsnat.droid.impl.browser.servernotitsnat;

import org.itsnat.droid.ItsNatSession;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.PageRequestImpl;
import org.itsnat.droid.impl.browser.PageRequestResult;

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
    public void finishLoad()
    {
        dispose(); // En el servidor
    }
}
