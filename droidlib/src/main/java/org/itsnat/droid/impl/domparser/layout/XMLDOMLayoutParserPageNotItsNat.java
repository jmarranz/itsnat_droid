package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 27/10/14.
 */
public class XMLDOMLayoutParserPageNotItsNat extends XMLDOMLayoutParserPage
{
    public XMLDOMLayoutParserPageNotItsNat(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager,Configuration configuration)
    {
        super(xmlDOMRegistry, assetManager,configuration);
    }

    @Override
    public XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageNotItsNat();
    }
}
