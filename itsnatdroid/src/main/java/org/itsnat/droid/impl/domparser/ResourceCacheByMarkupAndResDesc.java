package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 24/04/2016.
 */
public abstract class ResourceCacheByMarkupAndResDesc<TxmlDom extends XMLDOM,TxmlDomParser extends XMLDOMParser> extends ResourceCacheByMarkupAndResDescBase<TxmlDom,TxmlDomParser>
{
    protected ResourceCacheByMarkupAndResDesc()
    {
    }

    @SuppressWarnings("unchecked")
    public ParsedResourceXMLDOM<TxmlDom> buildXMLDOMAndCachingByMarkupAndResDesc(String markup, ResourceDescDynamic resourceDesc, XMLDOMParserContext xmlDOMParserContext)
    {
        // Este método es llamado multihilo, ResourceCache está sincronizado, pero no necesitamos multihilo a t_odo pues el código está preparado para el caso de dos
        // requests simultáneas con el mismo markup.

        // Lo normal es que resourceDesc tenga ResourceDescDynamic.parsedResource a nulo, pues este método se llama para crear el XMLDOM que se pone en el atributo parsedResource
        // de este ResourceDescDynamic "original".
        // Aprovechamos para cachear también ResourceDescDynamic, pues del ResourceDescDynamic "original" via el resource como string obtendremos el XMLDOM del atributo parsedResource
        // cacheByResDescValue también tiene en cuenta la "frecuencia de uso" via timestamp.

        String resourceDescValue = resourceDesc.getResourceDescValue();
        if (cacheByResDescValue.get(resourceDescValue) == null)
            cacheByResDescValue.put(resourceDescValue, resourceDesc); // Lo hacemos antes de cacheByMarkup.get() de esta manera cacheamos también en el caso raro de dos archivos con el mismo markup, por otra parte en el caso de que ya exista se actualiza el timestamp del recurso al hacer el get (recurso recientemente usado)

        TxmlDom cachedXMLDOM = cacheByMarkup.get(markup); // En el caso no nulo el cachedDOMLayout devuelto tiene el timestamp actualizado por el hecho de llamar al get()
        if (cachedXMLDOM == null)
        {
            cachedXMLDOM = createXMLDOMInstance();
            cacheByMarkup.put(markup, cachedXMLDOM); // Cacheamos cuanto antes pues puede haber recursividad

            TxmlDomParser parser = createXMLDOMParserInstance(xmlDOMParserContext);
            parser.parse(markup, cachedXMLDOM);
        }

        ParsedResourceXMLDOM<TxmlDom> resource = new ParsedResourceXMLDOM<TxmlDom>(cachedXMLDOM);
        resourceDesc.setParsedResource(resource);

        return resource;
    }

    protected abstract TxmlDom createXMLDOMInstance();
    protected abstract TxmlDomParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext);
}
