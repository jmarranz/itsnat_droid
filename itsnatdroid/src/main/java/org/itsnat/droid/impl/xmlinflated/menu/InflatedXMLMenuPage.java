package org.itsnat.droid.impl.xmlinflated.menu;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedXMLMenuPage extends InflatedXMLMenu implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedXMLMenuPage(ItsNatDroidImpl itsNatDroid, XMLDOMMenu xmlDOMMenu, Context ctx, PageImpl page)
    {
        // Este constructor puede llegar a ejecutarse en un hilo NO UI, no hacer nada más (YA NO ES VERDAD, REVISAR)
        super(itsNatDroid, xmlDOMMenu, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
