package org.itsnat.droid.impl.browser;

import android.animation.Animator;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;

/**
 * Created by jmarranz on 01/04/2016.
 */
public class ItsNatResourcesImpl implements ItsNatResources
{
    protected final ItsNatDocImpl itsNatDoc;
    protected final XMLInflaterRegistry xmlInflaterRegistry;
    protected final XMLDOMRegistry xmlDOMRegistry;

    public ItsNatResourcesImpl(ItsNatDocImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
        ItsNatDroidImpl itsNatDroid = itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl();
        this.xmlInflaterRegistry = itsNatDroid.getXMLInflaterRegistry();
        this.xmlDOMRegistry = itsNatDroid.getXMLDOMRegistry();
    }

    private XMLInflaterLayoutPage getXMLInflaterLayoutPage()
    {
        return itsNatDoc.getPageImpl().getXMLInflaterLayoutPage();
    }

    private ItsNatDroidException newException(String resourceDescValue)
    {
        return new ItsNatDroidException("Resource " + resourceDescValue + " is still not loaded, maybe you should use an attribute with namespace " + NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE + " for manual load declaration");
    }

    public Animator getAnimator(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null) throw newException(resourceDescValue);
        return xmlInflaterRegistry.getAnimator(resourceDesc,getXMLInflaterLayoutPage());
    }
}
