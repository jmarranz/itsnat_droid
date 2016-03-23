package org.itsnat.droid.impl.xmlinflated.animator;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

/**
 * Created by jmarranz on 10/11/14.
 */
public class InflatedAnimatorPage extends InflatedAnimator implements InflatedXMLPage
{
    protected PageImpl page;

    public InflatedAnimatorPage(ItsNatDroidImpl itsNatDroid, XMLDOMAnimator xmlDOMAnimator, Context ctx, PageImpl page)
    {
        super(itsNatDroid, xmlDOMAnimator, ctx);
        this.page = page;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }
}
