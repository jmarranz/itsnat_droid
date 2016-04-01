package org.itsnat.droid.impl.browser;

import android.animation.Animator;

import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

/**
 * Created by jmarranz on 01/04/2016.
 */
public class ItsNatResourcesImpl implements ItsNatResources
{
    protected final ItsNatDocImpl itsNatDoc;
    protected final XMLInflaterRegistry xmlInflaterRegistry;

    public ItsNatResourcesImpl(ItsNatDocImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
        this.xmlInflaterRegistry = itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLInflaterRegistry();
    }

    private XMLInflaterLayoutPage getXMLInflaterLayoutPage()
    {
        return itsNatDoc.getPageImpl().getXMLInflaterLayoutPage();
    }

    public Animator getAnimator(String resourceDesc)
    {
        ResourceDesc resourceDescObj = ResourceDesc.create(resourceDesc);
        return xmlInflaterRegistry.getAnimator(resourceDescObj,getXMLInflaterLayoutPage());
    }
}
