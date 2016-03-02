package org.itsnat.droid.impl.browser.serveritsnat;

import org.apache.http.HttpResponse;
import org.itsnat.droid.impl.browser.HttpFileCache;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;

import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by jmarranz on 17/01/2016.
 */
public class HttpRequestResultOKBeanshellImpl extends HttpRequestResultOKImpl
{
    protected LinkedList<DOMAttrRemote> attrRemoteListBSParsed;

    public HttpRequestResultOKBeanshellImpl(String url, HttpResponse httpResponse, InputStream input, HttpFileCache httpFileCache, String mimeType, String encoding)
    {
        super(url, httpResponse, input, httpFileCache, mimeType, encoding);
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
