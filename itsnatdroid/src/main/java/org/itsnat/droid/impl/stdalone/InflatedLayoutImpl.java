package org.itsnat.droid.impl.stdalone;

import android.view.View;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

/**
 * Created by jmarranz on 28/02/2017.
 */

public class InflatedLayoutImpl implements InflatedLayout
{
    protected InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone;
    protected ItsNatResourcesStandaloneImpl itsNatResourcesStandalone;


    public InflatedLayoutImpl(InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone)
    {
        this.inflatedXMLLayoutStandalone = inflatedXMLLayoutStandalone;
    }


    public ItsNatResourcesStandaloneImpl getItsNatResourcesStandaloneImpl()
    {
        if (itsNatResourcesStandalone == null)
            this.itsNatResourcesStandalone = new ItsNatResourcesStandaloneImpl(this);
        return itsNatResourcesStandalone;
    }

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedXMLLayoutStandalone;
    }

    @Override
    public ItsNatResources getItsNatResources()
    {
        return getItsNatResourcesStandaloneImpl();
    }

    @Override
    public ItsNatDroid getItsNatDroid()
    {
        return inflatedXMLLayoutStandalone.getItsNatDroid();
    }

    @Override
    public View getRootView()
    {
        return inflatedXMLLayoutStandalone.getRootView();
    }

    @Override
    public View findViewByXMLId(String id)
    {
        return inflatedXMLLayoutStandalone.findViewByXMLId(id);
    }
}
