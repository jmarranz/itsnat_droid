package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

/**
 * Created by Jose on 09/11/2015.
 */
public abstract class AttrContext
{
    protected Context ctx;
    protected XMLInflater xmlInflater;

    public AttrContext(Context ctx, XMLInflater xmlInflater)
    {
        this.ctx = ctx;
        this.xmlInflater = xmlInflater;
    }

    public Context getContext()
    {
        return ctx;
    }

    public XMLInflater getXMLInflater()
    {
        return xmlInflater;
    }
}
