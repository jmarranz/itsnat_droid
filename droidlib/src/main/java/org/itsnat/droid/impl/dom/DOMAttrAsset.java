package org.itsnat.droid.impl.dom;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrAsset extends DOMAttrDynamic
{
    public DOMAttrAsset(String namespaceURI, String name, String value,XMLDOMParserContext xmlDOMParserContext)
    {
        super(namespaceURI, name, value, xmlDOMParserContext);
    }

    public static boolean isAsset(String value)
    {
        return value.startsWith("@assets:");
    }
}
