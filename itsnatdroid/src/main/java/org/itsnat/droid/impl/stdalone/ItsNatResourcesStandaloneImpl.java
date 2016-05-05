package org.itsnat.droid.impl.stdalone;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.view.animation.Animation;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescAsset;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.animator.XMLDOMAnimatorParser;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

/**
 * Created by jmarranz on 12/04/2016.
 */
public class ItsNatResourcesStandaloneImpl extends ItsNatResourcesImpl
{
    protected final XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone;
    protected final XMLDOMParserContext xmlDOMParserContext;

    public ItsNatResourcesStandaloneImpl(XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone)
    {
        super(xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLDOMRegistry(),
              xmlInflaterLayoutStandalone.getXMLInflaterContext(),
              xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLInflaterRegistry());

        // En este caso PageImpl es null

        this.xmlInflaterLayoutStandalone = xmlInflaterLayoutStandalone;

        Resources res = xmlInflaterLayoutStandalone.getContext().getResources();
        this.xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,res);
    }

    public Context getContext()
    {
        return xmlInflaterLayoutStandalone.getContext();
    }

    private ResourceDesc loadAndCacheResourceDesc(String resourceDescValue)
    {


        ResourceDesc resourceDesc = ResourceDesc.create(resourceDescValue);

        if (resourceDesc instanceof ResourceDescAsset)
        {
            ResourceDescAsset resourceDescAsset = (ResourceDescAsset) resourceDesc;
            XMLDOMAnimatorParser.prepareResourceDescAssetToLoadResource(resourceDescAsset, xmlDOMParserContext);
        }
        else if (resourceDesc instanceof ResourceDescRemote)
        {
            throw new ItsNatDroidException("Remote resource is not allowed in this context, use assets instead");
        }

        return resourceDesc;
    }

    @Override
    public Animator getAnimator(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null)
            resourceDesc = loadAndCacheResourceDesc(resourceDescValue);
        return xmlInflaterRegistry.getAnimator(resourceDesc,xmlInflaterContext);
    }

    @Override
    public Animation getAnimation(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null)
            resourceDesc = loadAndCacheResourceDesc(resourceDescValue);
        return xmlInflaterRegistry.getAnimation(resourceDesc,xmlInflaterContext);
    }

    @Override
    public CharSequence[] getTextArray(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.geValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null)
            resourceDesc = loadAndCacheResourceDesc(resourceDescValue);
        return xmlInflaterRegistry.getTextArray(resourceDesc,xmlInflaterContext);
    }
}
