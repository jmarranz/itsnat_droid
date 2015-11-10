package org.itsnat.droid.impl.xmlinflater.drawable;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrDrawableContext extends AttrContext
{
    public AttrDrawableContext(Context ctx, XMLInflaterDrawable xmlInflaterDrawable)
    {
        super(ctx,xmlInflaterDrawable);
    }

    public XMLInflaterDrawable getXMLInflaterDrawable()
    {
        return (XMLInflaterDrawable)xmlInflater;
    }
}
