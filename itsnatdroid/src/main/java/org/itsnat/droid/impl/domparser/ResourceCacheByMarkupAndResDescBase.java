package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 24/04/2016.
 */
public abstract class ResourceCacheByMarkupAndResDescBase<TxmlDom extends XMLDOM,TxmlDomParser extends XMLDOMParser>
{
    protected ResourceCache<TxmlDom> cacheByMarkup = new ResourceCache<TxmlDom>();
    protected ResourceCache<ResourceDescDynamic> cacheByResDescValue = new ResourceCache<ResourceDescDynamic>(); // ResourceDescDynamic contiene el ParsedResource conteniendo el recurso y su localización

    protected ResourceCacheByMarkupAndResDescBase()
    {
    }

    public ResourceDescDynamic getResourceDescDynamicCacheByResourceDescValue(String resourceDescValue)
    {
        return cacheByResDescValue.get(resourceDescValue);
    }

    public void cleanCaches()
    {
        cacheByMarkup.clear();
        cacheByResDescValue.clear();
    }

}
