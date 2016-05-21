package org.itsnat.itsnatdroidtest.testact.local;

import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

import java.net.URL;
import java.util.Properties;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupInternLayoutLoadDrawables extends TestSetupAssetLayoutBase
{
    public TestSetupInternLayoutLoadDrawables(TestActivityTabFragment fragment) {
        super(fragment);
    }

    public void test()
    {
        final TestActivity act = fragment.getTestActivity();


        AsyncTaskLoader task = new AsyncTaskLoader(act);
        task.execute();
    }

}
