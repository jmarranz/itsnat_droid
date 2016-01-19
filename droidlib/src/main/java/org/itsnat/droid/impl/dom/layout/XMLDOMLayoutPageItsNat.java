package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 19/01/2016.
 */
public class XMLDOMLayoutPageItsNat extends XMLDOMLayoutPage
{
    protected String loadScript;

    @Override
    protected XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageItsNat();
    }

    public String getLoadScript()
    {
        return loadScript;
    }

    public void setLoadScript(String loadScript)
    {
        this.loadScript = loadScript;
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayoutPageItsNat)cloned).loadScript = null; // queda claro que NO se clona
    }
}
