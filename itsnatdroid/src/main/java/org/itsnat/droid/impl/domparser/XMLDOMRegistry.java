package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.anim.ResourceCacheByMarkupAndResDescAnimation;
import org.itsnat.droid.impl.domparser.animator.ResourceCacheByMarkupAndResDescAnimator;
import org.itsnat.droid.impl.domparser.drawable.ResourceCacheByMarkupAndResDescDrawable;
import org.itsnat.droid.impl.domparser.layout.ResourceCacheByMarkupAndResDescLayout;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.domparser.values.ResourceCacheByMarkupAndResDescValues;

/**
 * Created by Jose on 01/12/2015.
 */
public class XMLDOMRegistry
{
    protected ItsNatDroidImpl parent;

    protected ResourceCacheByMarkupAndResDescLayout layoutCache = new ResourceCacheByMarkupAndResDescLayout();
    protected ResourceCacheByMarkupAndResDescDrawable drawableCache = new ResourceCacheByMarkupAndResDescDrawable();
    protected ResourceCacheByMarkupAndResDescAnimation animationCache = new ResourceCacheByMarkupAndResDescAnimation();
    protected ResourceCacheByMarkupAndResDescAnimator animatorCache = new ResourceCacheByMarkupAndResDescAnimator();
    // Menues???
    protected ResourceCacheByMarkupAndResDescValues valuesCache = new ResourceCacheByMarkupAndResDescValues();

    public XMLDOMRegistry(ItsNatDroidImpl parent)
    {
        this.parent = parent;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return parent;
    }

    public void cleanCaches()
    {
        layoutCache.cleanCaches();

        drawableCache.cleanCaches();
        animationCache.cleanCaches();
        animatorCache.cleanCaches();
        // Menues???
        valuesCache.cleanCaches();
    }

    public ParsedResourceXMLDOM<XMLDOMLayout> buildXMLDOMLayoutAndCachingByMarkupAndResDesc(String markup,ResourceDescDynamic resourceDesc, String itsNatServerVersion, XMLDOMLayoutParser.LayoutType layoutType, XMLDOMParserContext xmlDOMParserContext)
    {
        return layoutCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,itsNatServerVersion,layoutType,xmlDOMParserContext);
    }

    public ResourceDescDynamic getLayoutResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return layoutCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

    public ParsedResourceXMLDOM<XMLDOMDrawable> buildXMLDOMDrawableAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        return drawableCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic getDrawableResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return drawableCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

    public ParsedResourceXMLDOM<XMLDOMAnimation> buildXMLDOMAnimationAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        return animationCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic getAnimationResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return animationCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

    public ParsedResourceXMLDOM<XMLDOMAnimator> buildXMLDOMAnimatorAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        return animatorCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic getAnimatorResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return animatorCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

    public ParsedResourceXMLDOM<XMLDOMValues> buildXMLDOMValuesAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        // El resourceDesc parámetro tiene una referencia final a la variable requerida, ej :textSize Esto es normal y se cacheará el getResourceDescValue() asociado al objeto
        // pero hay que notar que el markup será siempre el mismo para todas las referencias al contenido del values XML
        return valuesCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic geValuesResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return valuesCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

}
