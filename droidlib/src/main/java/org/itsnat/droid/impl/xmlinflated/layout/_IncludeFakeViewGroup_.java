package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;
import android.widget.FrameLayout;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.ArrayList;

/**
 * Created by Jose on 02/12/2015.
 */
public class _IncludeFakeViewGroup_ extends FrameLayout
{
    protected ArrayList<DOMAttr> attribs;

    public _IncludeFakeViewGroup_(Context context)
    {
        super(context);
    }

    public void saveAttrib(DOMAttr attr)
    {
        if (attribs == null) attribs = new ArrayList<DOMAttr>(4);
        attribs.add(attr);
    }

    public ArrayList<DOMAttr> getAttribs()
    {
        return attribs;
    }
}
