package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.XMLDOM;

import java.util.ArrayList;

/**
 * Created by jmarranz on 19/01/2016.
 */
public abstract class XMLDOMLayoutPage extends XMLDOMLayout
{
    protected ArrayList<DOMScript> scriptList;

    public ArrayList<DOMScript> getDOMScriptList()
    {
        return scriptList;
    }

    public void addDOMScript(DOMScript script)
    {
        if (scriptList == null) this.scriptList = new ArrayList<DOMScript>();
        scriptList.add(script);
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayoutPage)cloned).scriptList = this.scriptList;
    }
}
