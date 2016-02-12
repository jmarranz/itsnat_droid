package org.itsnat.droid.impl.dommini;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DMEntityRefNode extends DMNode
{
    protected String entityName;

    public DMEntityRefNode(String entityName)
    {
        this.entityName = entityName;
    }

    public String getEntityName()
    {
        return entityName;
    }
}
