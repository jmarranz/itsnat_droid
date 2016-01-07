package org.itsnat.droid.impl.xmlinflater.drawable;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawablePage;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterPage;

/**
 * Created by jmarranz on 24/11/14.
 */
public class XMLInflaterDrawablePage extends XMLInflaterDrawable implements XMLInflaterPage
{
    public XMLInflaterDrawablePage(InflatedDrawablePage inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
    }

    public InflatedDrawablePage getInflatedDrawablePage()
    {
        return (InflatedDrawablePage)getInflatedDrawable();
    }

    public PageImpl getPageImpl()
    {
        return getInflatedDrawablePage().getPageImpl();
    }
}
