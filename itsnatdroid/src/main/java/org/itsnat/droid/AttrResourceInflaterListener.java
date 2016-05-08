package org.itsnat.droid;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrResourceInflaterListener
{
    public boolean setAttribute(Page page, Object resource, String namespace, String name, String value);
    public boolean removeAttribute(Page page, Object resource, String namespace, String name);
}
