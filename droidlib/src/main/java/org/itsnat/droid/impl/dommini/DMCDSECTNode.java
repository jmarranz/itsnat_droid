package org.itsnat.droid.impl.dommini;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DMCDSECTNode extends DMNode
{
    protected String text;

    public DMCDSECTNode(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
