package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedLayoutPageImpl extends InflatedLayoutImpl
{
    protected String loadScript;
    protected List<String> scriptList = new LinkedList<String>();

    public InflatedLayoutPageImpl(ItsNatDroidImpl itsNatDroid,XMLDOMLayout domLayout,Context ctx)
    {
        super(itsNatDroid, domLayout, ctx);
    }

    public String getLoadScript()
    {
        return loadScript;
    }

    public void setLoadScript(String loadScript)
    {
        this.loadScript = loadScript;
    }

    public List<String> getScriptList()
    {
        return scriptList;
    }

    public void addScript(String code)
    {
        scriptList.add(code);
    }
}
