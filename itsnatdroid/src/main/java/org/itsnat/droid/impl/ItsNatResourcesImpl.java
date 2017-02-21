package org.itsnat.droid.impl;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.PercFloat;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

/**
 * Created by jmarranz on 01/04/2016.
 */
public abstract class ItsNatResourcesImpl implements ItsNatResources
{
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final XMLInflaterContext xmlInflaterContext;
    protected final XMLInflaterRegistry xmlInflaterRegistry;

    public ItsNatResourcesImpl(XMLDOMRegistry xmlDOMRegistry, XMLInflaterContext xmlInflaterContext, XMLInflaterRegistry xmlInflaterRegistry)
    {
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.xmlInflaterContext = xmlInflaterContext;
        this.xmlInflaterRegistry = xmlInflaterRegistry;
    }

    protected abstract ResourceDesc prepare(String resourceDescValue, ResourceDesc resourceDesc);

    private ResourceDesc getValuesResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue, resourceDesc);
        return resourceDesc;
    }

    @Override
    public boolean getBoolean(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getBoolean(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getColor(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getColor(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getIdentifier(String resourceDescValue)
    {
        ResourceDesc resourceDesc = ResourceDesc.create(resourceDescValue); // No cacheado
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getIdentifier(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getInteger(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getInteger(resourceDesc,xmlInflaterContext);
    }

    @Override
    public float getFloat(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getFloat(resourceDesc,xmlInflaterContext);
    }

    @Override
    public String getString(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getString(resourceDesc,xmlInflaterContext);
    }

    @Override
    public CharSequence getText(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getString(resourceDesc,xmlInflaterContext);
    }

    @Override
    public CharSequence[] getTextArray(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getTextArray(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getDimensionIntFloor(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionIntFloor(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getDimensionIntRound(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionIntRound(resourceDesc,xmlInflaterContext);
    }

    @Override
    public float getDimensionFloat(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionFloat(resourceDesc,xmlInflaterContext);
    }

    @Override
    public float getDimensionFloatFloor(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionFloatFloor(resourceDesc,xmlInflaterContext);
    }

    @Override
    public float getDimensionFloatRound(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionFloatRound(resourceDesc,xmlInflaterContext);
    }

    @Override
    public int getDimensionWithNameIntRound(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionWithNameIntRound(resourceDesc,xmlInflaterContext);
    }

    @Override
    public String getDimensionOrString(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionOrString(resourceDesc,xmlInflaterContext);
    }

    @Override
    public PercFloat getDimensionPercFloat(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getDimensionPercFloat(resourceDesc,xmlInflaterContext);
    }

    @Override
    public PercFloat getPercFloat(String resourceDescValue)
    {
        ResourceDesc resourceDesc = getValuesResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        return xmlInflaterRegistry.getPercFloat(resourceDesc,xmlInflaterContext);
    }

    // Con cache específico del tipo de datos:

    @Override
    public Animation getAnimation(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimationResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getAnimation(resourceDesc,xmlInflaterContext);
    }

    @Override
    public Animator getAnimator(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getAnimatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getAnimator(resourceDesc,xmlInflaterContext);
    }

    @Override
    public Drawable getDrawable(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getDrawableResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getDrawable(resourceDesc,xmlInflaterContext);
    }

    @Override
    public Interpolator getInterpolator(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getInterpolatorResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getInterpolator(resourceDesc,xmlInflaterContext);
    }

    @Override
    public LayoutAnimationController getLayoutAnimation(String resourceDescValue)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getLayoutAnimationResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);
        return xmlInflaterRegistry.getLayoutAnimation(resourceDesc,xmlInflaterContext);
    }

    @Override
    public View getLayout(String resourceDescValue, ViewGroup viewParent, int indexChild)
    {
        ResourceDesc resourceDesc = xmlDOMRegistry.getLayoutResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
        resourceDesc = prepare(resourceDescValue,resourceDesc);

        return xmlInflaterRegistry.getLayout(resourceDesc,xmlInflaterContext,getXMLInflaterLayout(),viewParent,indexChild,null);
    }


    public abstract XMLInflaterLayout getXMLInflaterLayout();

}
