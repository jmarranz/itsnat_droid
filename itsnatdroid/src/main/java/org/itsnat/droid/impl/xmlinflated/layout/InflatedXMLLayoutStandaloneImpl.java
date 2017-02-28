package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutStandalone;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedXMLLayoutStandaloneImpl extends InflatedXMLLayoutImpl // implements InflatedLayout
{
    protected XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone;

    public InflatedXMLLayoutStandaloneImpl(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutStandalone domLayout, Context ctx)
    {
        super(itsNatDroid, domLayout,ctx);
    }

    public XMLInflaterLayoutStandalone getXMLInflaterLayoutStandalone()
    {
        return xmlInflaterLayoutStandalone;
    }

    public void setXMLInflaterLayoutStandalone(XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone)
    {
        this.xmlInflaterLayoutStandalone = xmlInflaterLayoutStandalone;
    }


}
