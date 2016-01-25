package org.itsnat.droid.impl.dom.layout;

/**
 * Created by jmarranz on 29/10/14.
 */
public abstract class DOMScript
{
    protected volatile String code;

    public DOMScript()
    {
    }

    public DOMScript(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
