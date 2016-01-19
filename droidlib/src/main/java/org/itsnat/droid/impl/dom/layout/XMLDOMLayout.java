package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayout extends XMLDOM
{
    public XMLDOMLayout()
    {
    }

    /*
    public DOMView getRootView()
    {
        return (DOMView)getRootDOMElement();
    }
*/

    public static String extractLoadScriptMarkup(String markup,String[] markupNoLoadScript)
    {
        markupNoLoadScript[0] = markup;

        // Este es el patrón esperado: <script id="itsnat_load_script"><![CDATA[ code ]]></script></lastElement>
        String patternStart = "<script id=\"itsnat_load_script\"><![CDATA[";
        int start = markup.lastIndexOf(patternStart);
        if (start == -1) return null; // No hay script de carga
        start += patternStart.length();

        String patternEnd = "]]></script>";
        int end = markup.lastIndexOf(patternEnd);
        if (end == -1) return null; // No hay script de carga

        String loadScript = markup.substring(start,end);
        markupNoLoadScript[0] = markup.substring(0,start) + markup.substring(end);
        return loadScript;
    }

    public XMLDOMLayout partialClone()
    {
        // Reutilizamos t_odo excepto el loadScript pues es la única parte que cambia
        XMLDOMLayout cloned = createXMLDOMLayout();
        partialClone(cloned);
        return cloned;
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
    }

    protected abstract XMLDOMLayout createXMLDOMLayout();
}
