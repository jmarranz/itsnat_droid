package org.itsnat.droid.impl.xmlinflated.animlayout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedXMLLayoutAnimationPage extends InflatedXMLLayoutAnimation implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedXMLLayoutAnimationPage(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMLayoutAnimation, Context ctx, PageImpl page)
    {
        super(itsNatDroid, xmlDOMLayoutAnimation, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
