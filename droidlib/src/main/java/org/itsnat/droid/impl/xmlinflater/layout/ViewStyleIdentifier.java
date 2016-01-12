package org.itsnat.droid.impl.xmlinflater.layout;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleIdentifier extends ViewStyle
{
    protected int identifier;

    public ViewStyleIdentifier(int identifier)
    {
        this.identifier = identifier;
    }

    public int getIdentifier()
    {
        return identifier;
    }
}
