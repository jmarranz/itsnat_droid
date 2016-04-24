package org.itsnat.droid.impl.domparser.animator;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorObject;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorSet;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorValue;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMAnimatorParser extends XMLDOMParser<XMLDOMAnimator>
{
    protected XMLDOMAnimatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMAnimatorParser createXMLDOMAnimatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMAnimatorParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    public void parse(String markup,XMLDOMAnimator xmlDOMAnimator)
    {
        StringReader input = new StringReader(markup);
        parse(input,xmlDOMAnimator);
    }

    private void parse(Reader input,XMLDOMAnimator xmlDOMAnimator)
    {
        try
        {
            XmlPullParser parser = newPullParser(input);
            parse(parser,xmlDOMAnimator);
        }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        catch (XmlPullParserException ex) { throw new ItsNatDroidException(ex); }
        finally
        {
            try { input.close(); }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
        }
    }

    private void parse(XmlPullParser parser,XMLDOMAnimator xmlDOMAnimator) throws IOException, XmlPullParserException
    {
        // Interesante ver el código fuente de: AnimatorInflater : Animator createAnimatorFromXml(Context c, XmlPullParser parser)

        String rootElemName = getRootElementName(parser);
        parseRootElement(rootElemName,parser, xmlDOMAnimator);
    }

    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        if ("set".equals(tagName))
        {
            return new DOMElemAnimatorSet(parent);
        }
        else if ("objectAnimator".equals(tagName))
        {
            return new DOMElemAnimatorObject(parent);
        }
        else if ("animator".equals(tagName))
        {
            return new DOMElemAnimatorValue(parent);
        }
        else throw new ItsNatDroidException("Unrecognized animator tag name: " + tagName);
    }

}
