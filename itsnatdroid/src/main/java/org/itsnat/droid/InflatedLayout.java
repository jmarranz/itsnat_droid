package org.itsnat.droid;

import android.content.Context;
import android.view.View;

/**
 * Created by jmarranz on 16/06/14.
 */
public interface InflatedLayout
{
    public Context getContext();
    public ItsNatDroid getItsNatDroid();
    public ItsNatDoc getItsNatDoc();

    public View getRootView();
    public View findViewByXMLId(String id);

    public ItsNatResources getItsNatResources();
}
