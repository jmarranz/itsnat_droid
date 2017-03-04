package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.XMLDOM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayout extends XMLDOM
{
    protected ArrayList<DOMScript> scriptList;

    public XMLDOMLayout()
    {
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayout)cloned).scriptList = this.scriptList;
    }

    public XMLDOMLayout partialClone()
    {
        // Reutilizamos t_odo excepto el loadInitScript pues es la única parte que cambia
        XMLDOMLayout cloned = createXMLDOMLayout();
        partialClone(cloned);
        return cloned;
    }


    public List<DOMScript> getDOMScriptList()
    {
        return scriptList;
    }

    public void addDOMScript(DOMScript script)
    {
        if (scriptList == null) this.scriptList = new ArrayList<DOMScript>();
        scriptList.add(script);
    }

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



    protected abstract XMLDOMLayout createXMLDOMLayout();
}
