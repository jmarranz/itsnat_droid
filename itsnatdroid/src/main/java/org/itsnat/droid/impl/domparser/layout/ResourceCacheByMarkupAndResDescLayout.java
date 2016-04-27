package org.itsnat.droid.impl.domparser.layout;

import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.domparser.ResourceCache;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDescBase;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.anim.XMLDOMAnimationParser;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescLayout extends ResourceCacheByMarkupAndResDescBase<XMLDOMLayout,XMLDOMLayoutParser>
{
    public ResourceCacheByMarkupAndResDescLayout()
    {
    }

    public ParsedResourceXMLDOM<XMLDOMLayout> buildXMLDOMAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, String itsNatServerVersion, XMLDOMLayoutParser.LayoutType layoutType, XMLDOMParserContext xmlDOMParserContext)
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
            if (cacheByResDescValue.get(resourceDescValue) == null)
                cacheByResDescValue.put(resourceDescValue, resourceDesc); // Lo hacemos antes de cacheByMarkup.get() de esta manera cacheamos también en el caso raro de dos archivos con el mismo markup, por otra parte en el caso de que ya exista se actualiza el timestamp del recurso al hacer el get (recurso recientemente usado)
        }


        String[] markupWithoutLoadScript = new String[1];
        String loadScript = null;
        if (itsNatServerVersion != null && layoutType == XMLDOMLayoutParser.LayoutType.PAGE)
            loadScript = XMLDOMLayout.extractLoadScriptMarkup(markup, markupWithoutLoadScript);
        else
            markupWithoutLoadScript[0] = markup;

        XMLDOMLayout cachedXMLDOMLayout = cacheByMarkup.get(markupWithoutLoadScript[0]); // En el caso no nulo el cachedDOMLayout devuelto tiene el timestamp actualizado por el hecho de llamar al get()
        if (cachedXMLDOMLayout == null)
        {
            XMLDOMLayoutParser layoutParser = XMLDOMLayoutParser.createXMLDOMLayoutParser(itsNatServerVersion,layoutType,xmlDOMParserContext);
            cachedXMLDOMLayout = layoutParser.createXMLDOMLayout();
            cacheByMarkup.put(markupWithoutLoadScript[0], cachedXMLDOMLayout); // Cacheamos cuanto antes pues puede haber recursividad

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

}
