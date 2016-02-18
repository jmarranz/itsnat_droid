package org.itsnat.droid.impl.dom.values;

import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.util.Locale;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemStyle extends DOMElemValuesItemNamed
{
    public DOMElemValuesItemStyle(DOMElemValuesStyle parentElement)
    {
        super("item", parentElement);
    }

    @Override
    public DOMAttr setTextNode(String text,Configuration configuration)
    {
        String itemName = getNameAttr();

        String prefix = NamespaceUtil.getPrefix(itemName);
        String namespaceURI = prefix != null && prefix.equals("android") ? NamespaceUtil.XMLNS_ANDROID : null;
        if (namespaceURI != null)
            itemName = NamespaceUtil.getLocalName(itemName);

        this.valueAsDOMAttr = DOMAttr.create(namespaceURI, itemName, text, configuration);

        return valueAsDOMAttr;
    }
}
