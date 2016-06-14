package org.itsnat.itsnatdroidtest.testact.local.intern;

import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;


/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupInternLayoutCleanReloadDrawables
{
    protected final TestActivityTabFragment fragment;

    public TestSetupInternLayoutCleanReloadDrawables(TestActivityTabFragment fragment)
    {
        this.fragment = fragment;
    }

    public void test()
    {
        final TestActivity act = fragment.getTestActivity();

        AsyncTaskInternResourcesLoader task = new AsyncTaskInternResourcesLoader(act)
        {
            protected void onFinishError(final Exception ex)
            {
                ex.printStackTrace();
                TestUtil.alertDialog(act,ex.getMessage());
            }
        };

        task.execute();
    }

}
