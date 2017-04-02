package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementMenuChildMenu extends ElementMenuChildNormal
{
    public ElementMenuChildMenu(ElementMenuChildMenuItem parentElementMenu)
    {
        super(parentElementMenu);
    }

    protected abstract Menu getMenu();
}
