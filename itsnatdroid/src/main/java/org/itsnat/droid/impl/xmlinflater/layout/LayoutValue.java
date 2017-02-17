package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

/**
 * Created by jmarranz on 12/01/2016.
 */
public abstract class LayoutValue
{
    protected View view;

    public LayoutValue(View view)
    {
        this.view = view;
    }

    public View getView()
    {
        return view;
    }
}
