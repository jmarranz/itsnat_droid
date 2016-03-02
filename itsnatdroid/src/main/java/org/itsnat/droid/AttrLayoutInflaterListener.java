package org.itsnat.droid;

import android.view.View;

/**
 * Created by jmarranz on 19/05/14.
 */
public interface AttrLayoutInflaterListener
{
    public boolean setAttribute(Page page, View view, String namespace, String name, String value);
    public boolean removeAttribute(Page page, View view, String namespace, String name);
}
