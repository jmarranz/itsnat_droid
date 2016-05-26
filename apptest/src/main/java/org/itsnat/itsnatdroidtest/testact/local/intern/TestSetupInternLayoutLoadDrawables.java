package org.itsnat.itsnatdroidtest.testact.local.intern;

import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;


/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupInternLayoutLoadDrawables // extends TestSetupAssetLayoutBase
{
    protected final TestActivityTabFragment fragment;

    public TestSetupInternLayoutLoadDrawables(TestActivityTabFragment fragment)
    {
        this.fragment = fragment;
    }

    public void test()
    {
        final TestActivity act = fragment.getTestActivity();

        AsyncTaskInternResourcesLoader task = new AsyncTaskInternResourcesLoader(act);
        task.execute();
    }

}
