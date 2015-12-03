package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrLayoutContext extends AttrContext
{
    protected PendingViewPostCreateProcess pendingViewPostCreateProcess;
    protected PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks;

    public AttrLayoutContext(Context ctx, XMLInflaterLayout xmlInflaterLayout, PendingViewPostCreateProcess pendingViewPostCreateProcess, PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks)
    {
        super(ctx,xmlInflaterLayout);
        this.pendingViewPostCreateProcess = pendingViewPostCreateProcess;
        this.pendingPostInsertChildrenTasks = pendingPostInsertChildrenTasks;
    }

    public XMLInflaterLayout getXMLInflaterLayout()
    {
        return (XMLInflaterLayout)xmlInflater;
    }

    public PendingViewPostCreateProcess getPendingViewPostCreateProcess()
    {
        return pendingViewPostCreateProcess;
    }

    public PendingPostInsertChildrenTasks getPendingPostInsertChildrenTasks()
    {
        return pendingPostInsertChildrenTasks;
    }
}
