package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class LayoutValueCompiled extends LayoutValue
{
    protected int layoutId;

    public LayoutValueCompiled(View view,int layoutId)
    {
        super(view);
        this.layoutId = layoutId;
    }

    public int getLayoutId()
    {
        return layoutId;
    }
}
