package org.itsnat.droid.impl.browser;

import android.animation.Animator;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutStandaloneImpl;

/**
 * Created by jmarranz on 12/04/2016.
 */
public class ItsNatResourcesRemoteImpl extends ItsNatResourcesImpl
{
    public ItsNatResourcesRemoteImpl(ItsNatDocImpl itsNatDoc)
    {
        super(itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry(),
              itsNatDoc.getPageImpl().getXMLInflaterLayoutPage().getXMLInflaterContext(),
              itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLInflaterRegistry());
    }

    private ItsNatDroidException newException(String resourceDescValue)
    {
        return new ItsNatDroidException("Resource " + resourceDescValue + " is still not loaded, maybe you should use an attribute with namespace " + NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE + " for manual load declaration");
    }

    public Animator getAnimator(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null) throw newException(resourceDescValue);
        return xmlInflaterRegistry.getAnimator(resourceDesc,xmlInflaterContext);
    }
}
