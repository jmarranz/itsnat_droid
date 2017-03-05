package org.itsnat.droid;

import android.content.Context;

/**
 * Created by jmarranz on 16/06/14.
 */
public interface InflatedLayout
{
    public Context getContext();
    public ItsNatDroid getItsNatDroid();
    public ItsNatDoc getItsNatDoc();

    public UserData getUserData();

}
