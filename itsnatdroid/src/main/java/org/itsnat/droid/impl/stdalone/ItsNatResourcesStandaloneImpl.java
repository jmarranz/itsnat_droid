package org.itsnat.droid.impl.stdalone;

import android.animation.Animator;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescAsset;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.animator.XMLDOMAnimatorParser;
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
        Context ctx = xmlInflaterLayoutStandalone.getContext();

        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        if (resourceDesc == null)
        {
            resourceDesc = ResourceDesc.create(resourceDescValue);

            if (resourceDesc instanceof ResourceDescAsset)
            {
                ResourceDescAsset resourceDescAsset = (ResourceDescAsset) resourceDesc;

                Resources res = ctx.getResources();
                AssetManager assetManager = res.getAssets();
                Configuration configuration = res.getConfiguration();
                DisplayMetrics displayMetrics = res.getDisplayMetrics();
                XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry, assetManager, configuration, displayMetrics);

                XMLDOMAnimatorParser xmlDOMAnimatorParser = XMLDOMAnimatorParser.createXMLDOMAnimatorParser(xmlDOMParserContext);
                xmlDOMAnimatorParser.prepareResourceDescAssetToLoadResource(resourceDescAsset);
            }
        }

        return xmlInflaterRegistry.getAnimator(resourceDesc,xmlInflaterContext);
    }
}
