package org.itsnat.droid.impl.dommini;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MiscUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DOMMiniParser
{
    public static DMNode[] parse(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        // Se espera que parser esté inicialmente en START_TAG
        if (parser.getEventType() != XmlPullParser.START_TAG) throw MiscUtil.internalError();

        ArrayList<DMNode> nodeList = new ArrayList<DMNode>();
        while(parser.nextToken() != XmlPullParser.END_TAG)
        {
            if (isIgnored(parser.getEventType()))
                continue;

            DMNode nextSibling = processNextSiblingToken(parser);
            nodeList.add(nextSibling);
        }

        DMNode[] nodeArray = nodeList.toArray(new DMNode[nodeList.size()]);
        return nodeArray;
    }

    private static DMNode processNextSiblingToken(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        int tokenType = parser.getEventType();
        if (tokenType == XmlPullParser.START_TAG)
        {
            return processElement(parser);
        }
        else if (tokenType == XmlPullParser.TEXT || tokenType == XmlPullParser.CDSECT)
        {
            return processTextNode(parser);
        }
        else if (tokenType == XmlPullParser.ENTITY_REF)
        {
            return processEntity(parser);
        }

        throw new ItsNatDroidException("Unexpected token " + tokenType);
    }

    private static DMEntityRefNode processEntity(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        int tokenType = parser.getEventType();
        if (tokenType != XmlPullParser.ENTITY_REF) throw MiscUtil.internalError();
        // parser.getText() devuelve el entity resuelto, ej "&lt;" => "<"  PERO no nos interesa, pues luego Html.fromHtml lo hará
        // En teoría parser.getTextCharacters(new int[2]) debería devolvernos el entity sin resolver pero NO es así
        // Afortunadamente he descubierto que getName() devuelve el entity sin & ni ; es decir por ejemplo "lt"

        String entityName = parser.getName();
        return new DMEntityRefNode(entityName);
    }

    private static DMTextNode processTextNode(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        int tokenType = parser.getEventType();
        if (tokenType != XmlPullParser.TEXT && tokenType != XmlPullParser.CDSECT) throw MiscUtil.internalError();
        return new DMTextNode(parser.getText());
    }

    private static DMElem processElement(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        if (parser.getEventType() != XmlPullParser.START_TAG) throw MiscUtil.internalError();

        DMElem elem = new DMElem(parser.getName());
        int attrLen = parser.getAttributeCount();
        for(int i = 0; i < attrLen; i++)
        {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            DMAttr attr = new DMAttr(name,value);
            elem.addDMAttr(attr);
        }

        processChildNodes(parser,elem);

        return elem;
    }

    private static void processChildNodes(XmlPullParser parser,DMElem elem) throws IOException, XmlPullParserException
    {
        while(parser.nextToken() != XmlPullParser.END_TAG)
        {
            if (isIgnored(parser.getEventType()))
                continue;

            DMNode nextChild = processNextSiblingToken(parser);
            elem.addChildDMNode(nextChild);
        }
    }


    private static boolean isIgnored(int tokenType)
    {
        return tokenType == XmlPullParser.COMMENT;
    }
}
