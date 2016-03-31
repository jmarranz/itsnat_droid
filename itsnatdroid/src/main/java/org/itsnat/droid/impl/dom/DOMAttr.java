package org.itsnat.droid.impl.dom;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class DOMAttr
{
    protected final ResourceDesc resourceDesc;
    protected final String namespaceURI;
    protected final String name;

    protected DOMAttr(String namespaceURI, String name, ResourceDesc resourceDesc)
    {
        if ("".equals(namespaceURI)) throw new ItsNatDroidException("Internal error: empty string not allowed"); // Debe ser null o una cadena no vacía
        this.namespaceURI = namespaceURI;
        this.name = name;
        this.resourceDesc = resourceDesc;
    }

    public static DOMAttr createDOMAttr(String namespaceURI, String name, String value)
    {
        ResourceDesc resourceDesc = ResourceDesc.create(value);
        if (resourceDesc instanceof ResourceDescRemote)
            return new DOMAttrRemote(namespaceURI,name,(ResourceDescRemote)resourceDesc);
        else if (resourceDesc instanceof ResourceDescAsset)
            return new DOMAttrAsset(namespaceURI,name,(ResourceDescAsset)resourceDesc);
        else if (resourceDesc instanceof ResourceDescCompiled)
            return new DOMAttrCompiled(namespaceURI,name,(ResourceDescCompiled)resourceDesc);
        else
            throw MiscUtil.internalError();
    }

    public static DOMAttr createDOMAttrCopy(DOMAttr attr, String newValue)
    {
        return createDOMAttr(attr.getNamespaceURI(),attr.getName(),newValue);
    }

    public void checkEquals(String namespaceURI, String name, String value)
    {
        // Este chequeo nos sirve para quedarnos más tranquilos y cuesta muy poco
        if (!MiscUtil.equalsNullAllowed(this.namespaceURI, namespaceURI)) throw MiscUtil.internalError();
        if (!MiscUtil.equalsNullAllowed(this.name,name)) throw MiscUtil.internalError();
        resourceDesc.checkEquals(value);
    }

    public ResourceDesc getResourceDesc()
    {
        return resourceDesc;
    }

    public String getNamespaceURI()
    {
        return namespaceURI;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return resourceDesc.getResourceDescValue();
    }

}
