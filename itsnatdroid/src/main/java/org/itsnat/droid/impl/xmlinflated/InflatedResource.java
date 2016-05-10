package org.itsnat.droid.impl.xmlinflated;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 10/05/2016.
 */
public abstract class InflatedResource<TnativeResource> extends InflatedXML
{
    protected TnativeResource resource;

    protected InflatedResource(ItsNatDroidImpl itsNatDroid, XMLDOM xmlDOM, Context ctx)
    {
        super(itsNatDroid, xmlDOM, ctx);
    }

    public TnativeResource getRootResource()
    {
        return resource;
    }

    public void setRootResource(TnativeResource resource)
    {
        this.resource = resource;
    }
}
