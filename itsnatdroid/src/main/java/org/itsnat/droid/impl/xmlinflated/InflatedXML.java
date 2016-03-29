package org.itsnat.droid.impl.xmlinflated;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXML
{
    protected ItsNatDroidImpl itsNatDroid;
    protected XMLDOM xmlDOM;
    protected Context ctx;

    protected InflatedXML(ItsNatDroidImpl itsNatDroid, XMLDOM xmlDOM, Context ctx)
    {
        // Este constructor puede llegar a ejecutarse en un hilo NO UI, no hacer nada más (YA NO ES VERDAD, REVISAR)
        this.itsNatDroid = itsNatDroid;
        this.xmlDOM = xmlDOM;
        this.ctx = ctx;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return itsNatDroid.getXMLInflaterRegistry();
    }

    public Context getContext()
    {
        return ctx;
    }
}
