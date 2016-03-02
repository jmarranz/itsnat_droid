package org.itsnat.droid.impl.dommini;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DMEntityRefNode extends DMNode
{
    protected String entityName;
    protected String resolvedEntity;

    public DMEntityRefNode(String entityName,String resolvedEntity)
    {
        this.entityName = entityName;
        this.resolvedEntity = resolvedEntity;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public String getResolvedEntity()
    {
        return resolvedEntity;
    }
}
