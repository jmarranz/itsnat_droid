package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.Locale;

/**
 * Created by jmarranz on 27/10/14.
 */
public class XMLDOMLayoutParserPageNotItsNat extends XMLDOMLayoutParserPage
{
    public XMLDOMLayoutParserPageNotItsNat(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager,Locale locale)
    {
        super(xmlDOMRegistry, assetManager,locale);
    }

    @Override
    public XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageNotItsNat();
    }
}
