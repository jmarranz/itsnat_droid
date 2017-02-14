package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLPage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 20/08/14.
 */
public abstract class InflatedXMLLayoutPageImpl extends InflatedXMLLayoutImpl implements InflatedXMLPage
{
    protected PageImpl page;
    protected List<String> scriptList = new LinkedList<String>();

    public InflatedXMLLayoutPageImpl(PageImpl page, ItsNatDroidImpl itsNatDroid, XMLDOMLayoutPage domLayout, Context ctx)
    {
        super(itsNatDroid, domLayout, ctx);
        this.page = page; // NO puede ser nulo
    }

    public XMLDOMLayoutPage getXMLDOMLayoutPage()
    {
        return (XMLDOMLayoutPage)xmlDOM;
    }

    @Override
    public PageImpl getPageImpl()
    {
        return page;
    }

    public List<String> getScriptList()
    {
        return scriptList;
    }

    public void addScript(String code)
    {
        scriptList.add(code);
    }

}
