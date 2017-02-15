package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 31/03/2016.
 */
public abstract class ResourceDesc
{
    protected final String resourceDescValue;

    protected ResourceDesc(String resourceDescValue)
    {
        this.resourceDescValue = resourceDescValue;
    }

    public static ResourceDesc create(String resourceDescValue)
    {
        if (ResourceDescRemote.isRemote(resourceDescValue))
            return new ResourceDescRemote(resourceDescValue);
        else if (ResourceDescAsset.isAsset(resourceDescValue))
            return new ResourceDescAsset(resourceDescValue);
        else if (ResourceDescIntern.isIntern(resourceDescValue))
            return new ResourceDescIntern(resourceDescValue);
        else
            return new ResourceDescCompiled(resourceDescValue); // Por ejemplo "wrapcontent"
    }

    public void checkEquals(String resourceDesc)
    {
        // Este chequeo nos sirve para quedarnos más tranquilos y cuesta muy poco
        if (!MiscUtil.equalsNullAllowed(this.resourceDescValue, resourceDesc)) throw MiscUtil.internalError();
    }

    public String getResourceDescValue()
    {
        return resourceDescValue;
    }
}
