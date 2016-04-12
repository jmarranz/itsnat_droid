package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

/**
 * Created by Jose on 09/11/2015.
 */
public abstract class AttrContext
{
    protected XMLInflater xmlInflater;

    public AttrContext(XMLInflater xmlInflater)
    {
        this.xmlInflater = xmlInflater;
    }

    public Context getContext()
    {
        return xmlInflater.getContext();
    }

    public XMLInflater getXMLInflater()
    {
        return xmlInflater;
    }

    public XMLInflaterContext getXMLInflaterContext()
    {
        return xmlInflater.getXMLInflaterContext();
    }
}
