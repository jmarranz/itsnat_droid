package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutStandalone;
import org.itsnat.droid.impl.stdalone.ItsNatResourcesStandaloneImpl;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedLayoutStandaloneImpl extends InflatedLayoutImpl implements InflatedLayout
{
    protected XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone;

    public InflatedLayoutStandaloneImpl(ItsNatDroidImpl itsNatDroid,XMLDOMLayoutStandalone domLayout, Context ctx)
    {
        super(itsNatDroid, domLayout,ctx);
    }

    @Override
    public ItsNatResources getItsNatResources()
    {
        return getItsNatResourcesStandaloneImpl();
    }

    public ItsNatResourcesStandaloneImpl getItsNatResourcesStandaloneImpl()
    {
        return getXMLInflaterLayoutStandalone().getItsNatResourcesStandaloneImpl();
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
