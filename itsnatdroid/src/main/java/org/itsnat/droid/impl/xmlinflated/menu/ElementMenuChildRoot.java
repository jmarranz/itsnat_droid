package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

/**
 * Created by jmarranz on 27/11/14.
 */
public class ElementMenuChildRoot extends ElementMenuChildBased
{
    //http://grepcode.com/file/repo1.maven.org/maven2/org.robolectric/android-all/4.4_r1-robolectric-0/android/view/MenuInflater.java#MenuInflater.MenuState.addSubMenuItem%28%29
    private final int defaultGroupId = Menu.NONE;
    private final int defaultItemId = Menu.NONE;
    private final int defaultItemCategory = 0;
    private final int defaultItemOrder = 0;
    private final int defaultItemCheckable = 0;
    private final boolean defaultItemChecked = false;
    private final boolean defaultItemVisible = true;
    private final boolean defaultItemEnabled = true;

    protected int groupId = defaultGroupId;

    protected Menu menu;

 /*   public ElementMenuChildRoot(ElementMenuChildBased parentElementMenu)
    {
        super(parentElementMenuChild);
    } */

    public ElementMenuChildRoot(ElementMenuChildBased parentElementMenuChild, DOMElement domElement,AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        super(parentElementMenuChild,domElement,attrCtx);
        this.menu = androidRootMenu;
    }

    public int startGroup()
    {
        groupId++; // Para evitar el 0 (defaultGroupId)
        return groupId;
    }

    public int getCurrentGroupId()
    {
        return groupId;
    }

    public void endGroup()
    {
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
