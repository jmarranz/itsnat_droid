package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrLayoutContext extends AttrContext
{
    protected OneTimeAttrProcess oneTimeAttrProcess;
    protected PendingPostInsertChildrenTasks pending;

    public AttrLayoutContext(Context ctx, XMLInflaterLayout xmlInflaterLayout, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        super(ctx,xmlInflaterLayout);
        this.oneTimeAttrProcess = oneTimeAttrProcess;
        this.pending = pending;
    }

    public XMLInflaterLayout getXMLInflaterLayout()
    {
        return (XMLInflaterLayout)xmlInflater;
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
