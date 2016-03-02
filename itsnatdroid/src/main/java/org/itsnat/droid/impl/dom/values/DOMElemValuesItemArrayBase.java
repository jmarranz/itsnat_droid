package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemArrayBase extends DOMElemValuesNoChildElem
{
    public DOMElemValuesItemArrayBase(DOMElemValuesArrayBase parentElement)
    {
        super("item", parentElement);
    }

    protected String getValueAsDOMAttrName()
    {
        return parentElement.getTagName() + "-item";
    }

    @Override
    public DOMAttr setTextNode(String text,XMLDOMParserContext xmlDOMParserContext)
    {
        this.valueAsDOMAttr = DOMAttr.create(null, getValueAsDOMAttrName(), text,xmlDOMParserContext);

        return valueAsDOMAttr;
    }
}
