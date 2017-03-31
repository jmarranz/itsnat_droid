package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

/**
 * Created by jmarranz on 27/11/14.
 */
public class ElementMenuChildRoot extends ElementMenuChildBased
{
    protected Menu menu;

    public ElementMenuChildRoot()
    {
        super(null);
    }

    public ElementMenuChildRoot(Menu menu)
    {
        super(null);
        this.menu = menu;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

}
