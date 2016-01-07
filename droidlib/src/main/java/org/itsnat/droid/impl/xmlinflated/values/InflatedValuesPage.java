package org.itsnat.droid.impl.xmlinflated.values;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedValuesPage extends InflatedValues
{
    protected PageImpl page;

    public InflatedValuesPage(ItsNatDroidImpl itsNatDroid, XMLDOMValues xmlDOMValues, Context ctx, PageImpl page)
    {
        // Este constructor puede llegar a ejecutarse en un hilo NO UI, no hacer nada más (YA NO ES VERDAD, REVISAR)
        super(itsNatDroid, xmlDOMValues, ctx);
        this.page = page;
    }

    public PageImpl getPageImpl()
    {
        return page;
    }
}
