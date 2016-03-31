package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrRemote extends DOMAttrDynamic
{
    public DOMAttrRemote(String namespaceURI, String name, ResourceDescRemote resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public static boolean isPendingToDownload(DOMAttr attr)
    {
        return (attr instanceof DOMAttrRemote && !(((DOMAttrRemote)attr).getResourceDescRemote()).isDownloaded());
    }

    public ResourceDescRemote getResourceDescRemote()
    {
        return (ResourceDescRemote)resourceDesc;
    }
}
