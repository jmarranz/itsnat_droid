package org.itsnat.droid.impl.xmlinflater.menu;


import android.view.Menu;

import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;


/**
 * Created by Jose on 09/11/2015.
 */
public class AttrMenuContext extends AttrResourceContext<Menu>
{
    public AttrMenuContext(XMLInflaterMenu xmlInflaterMenu)
    {
        super(xmlInflaterMenu);
    }

    public XMLInflaterMenu getXMLInflaterMenu()
    {
        return (XMLInflaterMenu)xmlInflater;
    }
}
