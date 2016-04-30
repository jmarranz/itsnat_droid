package org.itsnat.itsnatdroidtest.testact.remote;

import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.OnPageLoadListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupSetupRemotePage extends TestSetupRemotePageBase
{
    public TestSetupSetupRemotePage(final TestActivityTabFragment fragment, final ItsNatDroidBrowser droidBrowser)
    {
        super(fragment,droidBrowser);
    }

    public void test(String url)
    {
        executePageRequest(url);
    }
}
