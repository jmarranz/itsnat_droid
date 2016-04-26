package org.itsnat.droid.impl.xmlinflated.anim;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedAnimationPage extends InflatedAnimation implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedAnimationPage(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx, PageImpl page)
    {
        super(itsNatDroid, xmlDOMAnimation, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
