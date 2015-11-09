package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrLayoutContext
{
    protected Context ctx;
    protected XMLInflaterLayout xmlInflaterLayout;
    protected OneTimeAttrProcess oneTimeAttrProcess;
    protected PendingPostInsertChildrenTasks pending;

    public AttrLayoutContext(Context ctx, XMLInflaterLayout xmlInflaterLayout, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        this.ctx = ctx;
        this.xmlInflaterLayout = xmlInflaterLayout;
        this.oneTimeAttrProcess = oneTimeAttrProcess;
        this.pending = pending;
    }

    public Context getContext()
    {
        return ctx;
    }

    public XMLInflaterLayout getXMLInflaterLayout()
    {
        return xmlInflaterLayout;
    }

    public OneTimeAttrProcess getOneTimeAttrProcess()
    {
        return oneTimeAttrProcess;
    }

    public PendingPostInsertChildrenTasks getPendingPostInsertChildrenTasks()
    {
        return pending;
    }
}
