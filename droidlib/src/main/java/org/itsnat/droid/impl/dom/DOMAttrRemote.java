package org.itsnat.droid.impl.dom;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrRemote extends DOMAttrDynamic
{
    public DOMAttrRemote(String namespaceURI, String name, String value)
    {
        super(namespaceURI, name, value);
    }

    public boolean isDownloaded()
    {
        return resource != null;
    }

    public static boolean isRemote(String value)
    {
        return value.startsWith("@remote:");
    }

    public static boolean isPendingToDownload(DOMAttr attr)
    {
        return (attr instanceof DOMAttrRemote && !((DOMAttrRemote) attr).isDownloaded());
    }

    public void check(String namespaceURI,String name,String value)
    {
        // Este chequeo nos sirve para quedarnos más tranquilos y cuesta muy poco
        if (!MiscUtil.equalsNullAllowed(this.namespaceURI,namespaceURI)) throw new ItsNatDroidException("Internal Error");
        if (!MiscUtil.equalsNullAllowed(this.name,name)) throw new ItsNatDroidException("Internal Error");
        if (!MiscUtil.equalsNullAllowed(this.value,value)) throw new ItsNatDroidException("Internal Error");
    }
}
