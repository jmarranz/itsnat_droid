package org.itsnat.droid.impl.xmlinflated.values;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedValuesPage extends InflatedValues implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedValuesPage(ItsNatDroidImpl itsNatDroid, XMLDOMValues xmlDOMValues, Context ctx, PageImpl page)
    {
        super(itsNatDroid, xmlDOMValues, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
