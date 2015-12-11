package org.itsnat.droid.impl.xmlinflater.layout.stdalone;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutStandaloneImpl;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterLayoutStandalone extends XMLInflaterLayout
{
    public XMLInflaterLayoutStandalone(InflatedLayoutStandaloneImpl inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener);
    }
}
