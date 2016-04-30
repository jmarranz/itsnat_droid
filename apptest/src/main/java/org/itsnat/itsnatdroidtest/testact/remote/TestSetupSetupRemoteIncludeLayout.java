package org.itsnat.itsnatdroidtest.testact.remote;

import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupSetupRemoteIncludeLayout extends TestSetupSetupRemotePage
{
    public TestSetupSetupRemoteIncludeLayout(final TestActivityTabFragment fragment, final ItsNatDroidBrowser droidBrowser)
    {
        super(fragment,droidBrowser);
    }

    @Override
    public boolean isScriptingDisabled() { return true; }
}
