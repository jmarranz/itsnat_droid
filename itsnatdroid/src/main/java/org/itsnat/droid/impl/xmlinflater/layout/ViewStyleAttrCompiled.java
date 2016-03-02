package org.itsnat.droid.impl.xmlinflater.layout;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleAttrCompiled extends ViewStyleAttr
{
    protected int identifier;

    public ViewStyleAttrCompiled(int identifier)
    {
        this.identifier = identifier;
    }

    public int getIdentifier()
    {
        return identifier;
    }
}
