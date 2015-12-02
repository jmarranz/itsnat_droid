package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrLayoutContext extends AttrContext
{
    protected PendingViewCreateProcess pendingViewCreateProcess;
    protected PendingPostInsertChildrenTasks pending;

    public AttrLayoutContext(Context ctx, XMLInflaterLayout xmlInflaterLayout, PendingViewCreateProcess pendingViewCreateProcess, PendingPostInsertChildrenTasks pending)
    {
        super(ctx,xmlInflaterLayout);
        this.pendingViewCreateProcess = pendingViewCreateProcess;
        this.pending = pending;
    }

    public XMLInflaterLayout getXMLInflaterLayout()
    {
        return (XMLInflaterLayout)xmlInflater;
    }

    public PendingViewCreateProcess getPendingViewCreateProcess()
    {
        return pendingViewCreateProcess;
    }

    public PendingPostInsertChildrenTasks getPendingPostInsertChildrenTasks()
    {
        return pending;
    }
}
