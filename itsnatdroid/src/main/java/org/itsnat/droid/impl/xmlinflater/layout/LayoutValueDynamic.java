package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class LayoutValueDynamic extends LayoutValue
{
    protected View view;

    public LayoutValueDynamic(View view)
    {
        this.view = view;
    }

    public View getView()
    {
        return view;
    }
}
