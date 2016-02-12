package org.itsnat.droid.impl.dommini;

import org.itsnat.droid.impl.util.MiscUtil;
import org.xmlpull.v1.XmlPullParser;

import java.util.List;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DOMMiniRender
{
    public static String toString(DMNode[] nodeArray)
    {
        StringBuilder res = new StringBuilder();
        for(DMNode node : nodeArray)
            res.append(toString(node));
        return res.toString();
    }

    private static String toString(DMNode node)
    {
        if (node instanceof DMElem)
        {
            return toStringDMElem((DMElem)node);
        }
        else if (node instanceof DMTextNode)
        {
            return toStringDMTextNode((DMTextNode) node);
        }
        else if (node instanceof DMTextNode)
        {
            return toStringDMTextNode((DMTextNode) node);
        }

        throw MiscUtil.internalError();
    }

    private static String toStringDMElem(DMElem elem)
    {
        StringBuilder res = new StringBuilder();
        res.append("<"); res.append(elem.getName());
        List<DMAttr> attrList = elem.getDMAttrList();
        if (attrList != null)
        {
            for(DMAttr attr : attrList)
            {
                res.append(' ');
                res.append(attr.getName());
                res.append('=');
                res.append(attr.getValue());
            }
        }

        List<DMNode> childList = elem.getChildDMNodeList();
        if (childList != null)
        {
            res.append('>');
            for(DMNode node : childList)
            {
                res.append(toString(node));
            }
            res.append("</"); res.append(elem.getName()); res.append('>');
        }
        else res.append("/>");

        return res.toString();
    }

    private static String toStringDMTextNode(DMTextNode text)
    {
        return text.getText();
    }
}
