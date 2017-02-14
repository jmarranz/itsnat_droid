package org.itsnat.droid.impl.xmlinflated.drawable;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedXMLDrawablePage extends InflatedXMLDrawable implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedXMLDrawablePage(ItsNatDroidImpl itsNatDroid, XMLDOMDrawable xmlDOMDrawable, Context ctx, PageImpl page)
    {
        // Este constructor puede llegar a ejecutarse en un hilo NO UI, no hacer nada más (YA NO ES VERDAD, REVISAR)
        super(itsNatDroid, xmlDOMDrawable, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
