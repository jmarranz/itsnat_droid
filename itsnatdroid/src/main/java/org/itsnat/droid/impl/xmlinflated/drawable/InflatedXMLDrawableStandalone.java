package org.itsnat.droid.impl.xmlinflated.drawable;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedXMLDrawableStandalone extends InflatedXMLDrawable
{
    public InflatedXMLDrawableStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMDrawable xmlDOMDrawable, Context ctx)
    {
        super(itsNatDroid, xmlDOMDrawable,ctx);
    }
}
