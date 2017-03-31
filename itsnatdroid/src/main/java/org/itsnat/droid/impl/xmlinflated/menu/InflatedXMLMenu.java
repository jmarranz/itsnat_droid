package org.itsnat.droid.impl.xmlinflated.menu;

import android.content.Context;
import android.view.Menu;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;


/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLMenu extends InflatedXMLResource<Menu>
{
    protected Menu menu;

    public InflatedXMLMenu(ItsNatDroidImpl itsNatDroid, XMLDOMMenu xmlDOMMenu, Context ctx)
    {
        super(itsNatDroid, xmlDOMMenu,ctx);
    }

    public static InflatedXMLMenu createInflatedXMLMenu(ItsNatDroidImpl itsNatDroid, XMLDOMMenu xmlDOMMenu, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLMenuPage(itsNatDroid, xmlDOMMenu, ctx,page) : new InflatedXMLMenuStandalone(itsNatDroid, xmlDOMMenu, ctx);
    }

    public XMLDOMMenu getXMLDOMMenu()
    {
        return (XMLDOMMenu) xmlDOM;
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
