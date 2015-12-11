package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayoutParserItsNatPageOrFragment extends XMLDOMLayoutParser
{
    public XMLDOMLayoutParserItsNatPageOrFragment(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        super(xmlDOMRegistry, assetManager);
    }


}
