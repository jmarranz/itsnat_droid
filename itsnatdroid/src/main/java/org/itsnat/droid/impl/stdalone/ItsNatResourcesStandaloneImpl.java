package org.itsnat.droid.impl.stdalone;

import android.animation.Animator;

import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.animator.InflatedAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

/**
 * Created by jmarranz on 12/04/2016.
 */
public class ItsNatResourcesStandaloneImpl extends ItsNatResourcesImpl
{
    protected XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone;

    public ItsNatResourcesStandaloneImpl(XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone)
    {
        super(xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLDOMRegistry(),
              xmlInflaterLayoutStandalone.getXMLInflaterContext(),
              xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLInflaterRegistry());

        this.xmlInflaterLayoutStandalone = xmlInflaterLayoutStandalone;
        // En este caso PageImpl es null
    }

    public Animator getAnimator(String resourceDescValue)
    {
        /*
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null)
        {
            // XMLInflaterRegistry
            XMLDOMAnimator xmlDOMAnimator = (XMLDOMAnimator) resource.getXMLDOM();
            InflatedAnimator inflatedAnimator = InflatedAnimator.createInflatedAnimator(itsNatDroid, xmlDOMAnimator, ctx, page);

            XMLInflaterAnimator xmlInflaterAnimator = XMLInflaterAnimator.createXMLInflaterAnimator(inflatedAnimator, bitmapDensityReference, attrInflaterListeners);
            return xmlInflaterAnimator.inflateAnimator();


            resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        }
        if (resourceDesc == null) throw newException(resourceDescValue);
        return xmlInflaterRegistry.getAnimator(resourceDesc,xmlInflaterContext);

        */
        return null;
    }
}
