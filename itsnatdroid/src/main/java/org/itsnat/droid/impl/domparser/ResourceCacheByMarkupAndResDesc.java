package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 24/04/2016.
 */
public abstract class ResourceCacheByMarkupAndResDesc<TxmlDom extends XMLDOM,TxmlDomParser extends XMLDOMParser>
{
    protected ResourceCache<TxmlDom> cacheByMarkup = new ResourceCache<TxmlDom>();
    protected ResourceCache<ResourceDescDynamic> cacheByResDescValue = new ResourceCache<ResourceDescDynamic>(); // ResourceDescDynamic contiene el ParsedResource conteniendo el recurso y su localización

    public ResourceDescDynamic getResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return cacheByResDescValue.get(resourceDescValue);
    }

    @SuppressWarnings("unchecked")
    public TxmlDom getXMLDOMCacheByMarkup(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        // Este método DEBE ser multihilo, el objeto layoutCacheByMarkup ya lo es.
        // No pasa nada si por una rarísima casualidad dos Layout idénticos hacen put, quedará el último, ten en cuenta que esto
        // es un caché.

        // Extraemos el markup sin el script de carga porque dos páginas generadas "iguales" SIEMPRE serán diferentes a nivel
        // de markup en el loadInitScript porque el id cambia y algún token aleatorio, sin el loadInitScript podemos conseguir
        // muchos más aciertos de cacheo y acelerar un montón al tener el parseo ya hecho.
        // Si el template no es generado por ItsNat server o bien el scripting está desactivado (itsNatServerVersion puede
        // ser no null pues es un header), loadInitScript será null y no pasa nada markupNoLoadScript[0] es el markup original

        String resourceDescValue = resourceDesc.getResourceDescValue();
        if (cacheByResDescValue.get(resourceDescValue) == null)
            cacheByResDescValue.put(resourceDescValue, resourceDesc); // Lo hacemos antes de cacheByMarkup.get() de esta manera cacheamos también en el caso raro de dos archivos con el mismo markup, por otra parte en el caso de que ya exista se actualiza el timestamp del recurso al hacer el get (recurso recientemente usado)

        TxmlDom cachedXMLDOM = cacheByMarkup.get(markup);
        if (cachedXMLDOM != null)
            return cachedXMLDOM;

        cachedXMLDOM = createXMLDOMInstance();
        cacheByMarkup.put(markup, cachedXMLDOM); // Cacheamos cuanto antes pues puede haber recursividad

        TxmlDomParser parser = createXMLDOMParserInstance(xmlDOMParserContext);
        parser.parse(markup,cachedXMLDOM);
        return cachedXMLDOM;
    }

    public void cleanCaches()
    {
        cacheByMarkup.clear();
        cacheByResDescValue.clear();
    }

    protected abstract TxmlDom createXMLDOMInstance();
    protected abstract TxmlDomParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext);
}
