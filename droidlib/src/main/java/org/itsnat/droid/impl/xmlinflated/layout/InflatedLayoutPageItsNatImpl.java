package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedLayoutPageItsNatImpl extends InflatedLayoutPageImpl
{
    public InflatedLayoutPageItsNatImpl(PageItsNatImpl page, ItsNatDroidImpl itsNatDroid, XMLDOMLayoutPageItsNat domLayout, Context ctx)
    {
        super(page,itsNatDroid, domLayout, ctx);
    }

    public PageItsNatImpl getPageItsNatImpl()
    {
        return (PageItsNatImpl)page;
    }

    public XMLDOMLayoutPageItsNat getXMLDOMLayoutPageItsNat()
    {
        return (XMLDOMLayoutPageItsNat)xmlDOM;
    }

    public String getLoadInitScript()
    {
        return getXMLDOMLayoutPageItsNat().getLoadInitScript(); // No cambia en el XMLDOMLayoutPageItsNat pues es un semi-clone precisamente con el loadInitScript "soldado"
    }

}
