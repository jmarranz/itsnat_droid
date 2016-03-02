package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 19/01/2016.
 */
public class XMLDOMLayoutPageItsNat extends XMLDOMLayoutPage
{
    protected String loadInitScript;

    public XMLDOMLayoutPageItsNat()
    {
    }

    @Override
    protected XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageItsNat();
    }

    public String getLoadInitScript()
    {
        return loadInitScript;
    }

    public void setLoadInitScript(String loadInitScript)
    {
        this.loadInitScript = loadInitScript;
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayoutPageItsNat)cloned).loadInitScript = null; // queda claro que NO se clona
    }
}
