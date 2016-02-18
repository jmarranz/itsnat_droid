package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageFragment;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.Locale;

/**
 * Created by jmarranz on 14/11/14.
 */
public class XMLDOMLayoutParserPageFragment extends XMLDOMLayoutParserPage
{
    public XMLDOMLayoutParserPageFragment(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager,Configuration configuration)
    {
        super(xmlDOMRegistry,assetManager,configuration);
    }

    @Override
    public XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageFragment();
    }
}
