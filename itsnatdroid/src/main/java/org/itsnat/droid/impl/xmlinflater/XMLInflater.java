package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflater
{
    protected final InflatedXML inflatedXML;
    protected final XMLInflaterContext xmlInflaterContext;


    protected XMLInflater(InflatedXML inflatedXML,int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        this.inflatedXML = inflatedXML;

        PageImpl page = PageImpl.getPageImpl(inflatedXML); // Puede ser null
        this.xmlInflaterContext = new XMLInflaterContext(inflatedXML.getItsNatDroidImpl(),inflatedXML.getContext(),page,bitmapDensityReference, attrInflaterListeners);
    }

    public InflatedXML getInflatedXML()
    {
        return inflatedXML;
    }

    public XMLInflaterContext getXMLInflaterContext()
    {
        return xmlInflaterContext;
    }

    public Context getContext()
    {
        return xmlInflaterContext.getContext();
    }
}
