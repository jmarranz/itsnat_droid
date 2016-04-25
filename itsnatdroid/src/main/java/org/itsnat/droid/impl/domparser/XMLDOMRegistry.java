package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.animator.ResourceCacheByMarkupAndResDescAnimator;
import org.itsnat.droid.impl.domparser.drawable.ResourceCacheByMarkupAndResDescDrawable;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.domparser.values.ResourceCacheByMarkupAndResDescValues;

/**
 * Created by Jose on 01/12/2015.
 */
public class XMLDOMRegistry
{
    protected ItsNatDroidImpl parent;
    protected ResourceCache<XMLDOMLayout> layoutCacheByMarkup = new ResourceCache<XMLDOMLayout>();
    protected ResourceCache<ResourceDescDynamic> layoutCacheByResDescValue = new ResourceCache<ResourceDescDynamic>();
    protected ResourceCacheByMarkupAndResDescDrawable drawableCache = new ResourceCacheByMarkupAndResDescDrawable();
    protected ResourceCacheByMarkupAndResDescAnimator animatorCache = new ResourceCacheByMarkupAndResDescAnimator();
    // Anim?
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
        layoutCacheByMarkup.clear();
        drawableCache.cleanCaches();
        animatorCache.cleanCaches();
        // Anim ?
        // Menues???
        valuesCache.cleanCaches();
    }

    public ParsedResourceXMLDOM<XMLDOMLayout> buildXMLDOMLayoutAndCachingByMarkupAndResDesc(String markup,ResourceDescDynamic resourceDesc, String itsNatServerVersion, XMLDOMLayoutParser.LayoutType layoutType, XMLDOMParserContext xmlDOMParserContext)
    {
        // Este método DEBE ser multihilo, el objeto layoutCacheByMarkup ya lo es.
        // No pasa nada si por una rarísima casualidad dos Layout idénticos hacen put, quedará el último, ten en cuenta que esto
        // es un caché.

        // Extraemos el markup sin el script de carga porque dos páginas generadas "iguales" SIEMPRE serán diferentes a nivel
        // de markup en el loadInitScript porque el id cambia y algún token aleatorio, sin el loadInitScript podemos conseguir
        // muchos más aciertos de cacheo y acelerar un montón al tener el parseo ya hecho.
        // Si el template no es generado por ItsNat server o bien el scripting está desactivado (itsNatServerVersion puede
        // ser no null pues es un header), loadInitScript será null y no pasa nada markupNoLoadScript[0] es el markup original

        // resourceDesc parámetro PUEDE SER NULO en el caso de XMLDOMLayout, no lo es en los demás tipos

        if (resourceDesc != null)
        {
            String resourceDescValue = resourceDesc.getResourceDescValue();
            if (layoutCacheByResDescValue.get(resourceDescValue) == null)
                layoutCacheByResDescValue.put(resourceDescValue, resourceDesc); // Lo hacemos antes de cacheByMarkup.get() de esta manera cacheamos también en el caso raro de dos archivos con el mismo markup, por otra parte en el caso de que ya exista se actualiza el timestamp del recurso al hacer el get (recurso recientemente usado)
        }


        String[] markupWithoutLoadScript = new String[1];
        String loadScript = null;
        if (itsNatServerVersion != null && layoutType == XMLDOMLayoutParser.LayoutType.PAGE)
            loadScript = XMLDOMLayout.extractLoadScriptMarkup(markup, markupWithoutLoadScript);
        else
            markupWithoutLoadScript[0] = markup;

        XMLDOMLayout cachedXMLDOMLayout = layoutCacheByMarkup.get(markupWithoutLoadScript[0]); // En el caso no nulo el cachedDOMLayout devuelto tiene el timestamp actualizado por el hecho de llamar al get()
        if (cachedXMLDOMLayout == null)
        {
            XMLDOMLayoutParser layoutParser = XMLDOMLayoutParser.createXMLDOMLayoutParser(itsNatServerVersion,layoutType,xmlDOMParserContext);
            cachedXMLDOMLayout = layoutParser.createXMLDOMLayout();
            layoutCacheByMarkup.put(markupWithoutLoadScript[0], cachedXMLDOMLayout); // Cacheamos cuanto antes pues puede haber recursividad

            layoutParser.parse(markup,cachedXMLDOMLayout);
            if (cachedXMLDOMLayout instanceof XMLDOMLayoutPageItsNat)
                ((XMLDOMLayoutPageItsNat)cachedXMLDOMLayout).setLoadInitScript(null); // Que quede claro que no se puede utilizar directamente en el cacheado guardado
        }

        XMLDOMLayout clonedDOMLayout = cachedXMLDOMLayout.partialClone(); // Necesitamos un clone parcial porque el loadInitScript necesitamos alojarlo en un objeto nuevo pues no puede cachearse
        if (clonedDOMLayout instanceof XMLDOMLayoutPageItsNat)
            ((XMLDOMLayoutPageItsNat)clonedDOMLayout).setLoadInitScript(loadScript);

        ParsedResourceXMLDOM<XMLDOMLayout> resource = new ParsedResourceXMLDOM<XMLDOMLayout>(clonedDOMLayout);
        if (resourceDesc != null) // Hay casos en donde no hay resourceDesc porque el origen de la construcción del layout NO es un ResourceDescDynamic
            resourceDesc.setParsedResource(resource);

        return resource;
    }

    public ResourceDescDynamic getLayoutResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return layoutCacheByResDescValue.get(resourceDescValue);
    }

    public ParsedResourceXMLDOM<XMLDOMDrawable> buildXMLDOMDrawableAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        return drawableCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic getDrawableResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return drawableCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
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
        return valuesCache.buildXMLDOMAndCachingByMarkupAndResDesc(markup,resourceDesc,xmlDOMParserContext);
    }

    public ResourceDescDynamic geValuesResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return valuesCache.getResourceDescDynamicCacheByResourceDescValue(resourceDescValue);
    }

}
