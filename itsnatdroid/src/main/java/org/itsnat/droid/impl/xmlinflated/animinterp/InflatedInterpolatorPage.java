package org.itsnat.droid.impl.xmlinflated.animinterp;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedInterpolatorPage extends InflatedInterpolator implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedInterpolatorPage(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx, PageImpl page)
    {
        super(itsNatDroid, xmlDOMInterpolator, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
