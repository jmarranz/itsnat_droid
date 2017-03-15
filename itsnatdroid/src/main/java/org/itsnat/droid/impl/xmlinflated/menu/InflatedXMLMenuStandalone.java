package org.itsnat.droid.impl.xmlinflated.menu;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedXMLMenuStandalone extends InflatedXMLMenu
{
    public InflatedXMLMenuStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMMenu xmlDOMMenu, Context ctx)
    {
        super(itsNatDroid, xmlDOMMenu,ctx);
    }
}
