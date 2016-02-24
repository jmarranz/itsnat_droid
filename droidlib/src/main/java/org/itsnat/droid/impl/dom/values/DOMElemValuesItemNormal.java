package org.itsnat.droid.impl.dom.values;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemNormal extends DOMElemValuesItemNamed
{
    public DOMElemValuesItemNormal(String tagName, DOMElemValuesResources parentElement)
    {
        super(tagName, parentElement);
    }

    @Override
    public DOMAttr setTextNode(String text,XMLDOMParserContext xmlDOMParserContext)
    {
        String itemName = getNameAttr();

        this.valueAsDOMAttr = DOMAttr.create(null, itemName, text, xmlDOMParserContext);

        return valueAsDOMAttr;
    }
}
