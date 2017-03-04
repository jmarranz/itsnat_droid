package org.itsnat.itsnatdroidtest.testact.remote;

import android.view.View;

import org.itsnat.droid.ItsNatDocPage;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatView;
import org.itsnat.droid.Page;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupRemoteControl extends TestSetupRemotePageBase
{
    public TestSetupRemoteControl(final TestActivityTabFragment fragment, final ItsNatDroidBrowser droidBrowser)
    {
        super(fragment,droidBrowser);
    }

    public void test(String url)
    {
        executePageRequest(url);
    }

    @Override
    public boolean setAttribute(final Page page,Object resource, String namespace, String name, final String value)
    {
        if (resource instanceof View && name.equals("url"))
        {
            View view = (View)resource;
            ItsNatDocPage itsNatDocPage = (ItsNatDocPage)page.getItsNatDoc();
            ItsNatView itsNatView = itsNatDocPage.getItsNatView(view);
            itsNatView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    page.dispose();
                    page.reusePageRequest().setURL(value).execute();
                }
            });
            return true;
        }
        else return super.setAttribute(page,resource, namespace, name, value);
    }

}
