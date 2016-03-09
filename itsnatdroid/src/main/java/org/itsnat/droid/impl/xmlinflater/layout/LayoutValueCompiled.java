package org.itsnat.droid.impl.xmlinflater.layout;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class LayoutValueCompiled extends LayoutValue
{
    protected int layoutId;

    public LayoutValueCompiled(int layoutId)
    {
        this.layoutId = layoutId;
    }

    public int getLayoutId()
    {
        return layoutId;
    }
}
