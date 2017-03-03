package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

/**
 * Created by jmarranz on 12/04/2016.
 */
public class ItsNatResourcesRemoteImpl extends ItsNatResourcesImpl
{
    protected ItsNatDocPageImpl itsNatDoc;

    public ItsNatResourcesRemoteImpl(ItsNatDocPageImpl itsNatDoc)
    {
        super(itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry(),
              itsNatDoc.getPageImpl().getXMLInflaterLayoutPage().getXMLInflaterContext(),
              itsNatDoc.getPageImpl().getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLInflaterRegistry());

        this.itsNatDoc = itsNatDoc;
    }

    public XMLInflaterLayout getXMLInflaterLayout()
    {
        return itsNatDoc.getPageImpl().getXMLInflaterLayoutPage();

    }

    private ItsNatDroidException newException(String resourceDescValue)
    {
        return NamespaceUtil.resourceStillNotLoadedException(resourceDescValue);
    }

    @Override
    protected ResourceDesc prepare(String resourceDescValue,ResourceDesc resourceDesc)
    {
        if (resourceDesc == null) throw newException(resourceDescValue);
        return resourceDesc;
    }

}
