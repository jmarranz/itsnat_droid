package org.itsnat.droid.impl.browser;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;

import java.util.LinkedList;

/**
 * Created by jmarranz on 27/10/14.
 */
public class PageRequestResult
{
    protected HttpRequestResultOKImpl httpReqResult;
    protected XMLDOMLayout domLayout;
    protected LinkedList<DOMAttrRemote> attrRemoteListBSParsed;

    public PageRequestResult(HttpRequestResultOKImpl httpReqResult,XMLDOMLayout domLayout)
    {
        this.httpReqResult = httpReqResult;
        this.domLayout = domLayout;
    }

    public HttpRequestResultOKImpl getHttpRequestResultOKImpl()
    {
        return httpReqResult;
    }

    public XMLDOMLayout getXMLDOMLayout()
    {
        return domLayout;
    }

    public LinkedList<DOMAttrRemote> getAttrRemoteListBSParsed()
    {
        return attrRemoteListBSParsed;
    }

    public void setAttrRemoteListBSParsed(LinkedList<DOMAttrRemote> attrRemoteListBSParsed)
    {
        this.attrRemoteListBSParsed = attrRemoteListBSParsed;
    }
}
