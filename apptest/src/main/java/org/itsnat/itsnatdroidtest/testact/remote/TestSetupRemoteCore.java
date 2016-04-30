package org.itsnat.itsnatdroidtest.testact.remote;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.EventMonitor;
import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatView;
import org.itsnat.droid.Page;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.event.Event;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupRemoteCore extends TestSetupRemotePageBase
{
    public TestSetupRemoteCore(final TestActivityTabFragment fragment, final ItsNatDroidBrowser droidBrowser)
    {
        super(fragment,droidBrowser);
    }

    public void test(String url)
    {
        TestActivity act = getTestActivity();

        boolean testSSL = false;
        boolean testSSLSelfSignedAllowed = true;
        if (testSSL)
        {
            if (testSSLSelfSignedAllowed)
            {
                droidBrowser.setSSLSelfSignedAllowed(true);
                url = "https://mms.nw.ru";
                // url = "https://www.pcwebshop.co.uk";  ya no funciona?
            }
            else
            {
                droidBrowser.setSSLSelfSignedAllowed(false); // Idem valor por defecto
                url = "https://www.google.com";
            }
        }

        PageRequest pageRequest = droidBrowser.createPageRequest();
        pageRequest.setContext(act)
        .setSynchronous(TEST_SYNC_REQUESTS)
        .setBitmapDensityReference(DisplayMetrics.DENSITY_XHIGH)
        .setOnPageLoadListener(this)
        .setOnPageLoadErrorListener(this)  // Comentar para ver el modo de error built-in
        .setOnScriptErrorListener(this)
        .setAttrLayoutInflaterListener(this)
        .setAttrDrawableInflaterListener(this)
        .setAttrAnimationInflaterListener(this)
        .setAttrAnimatorInflaterListener(this)
        .setAttrInterpolatorInflaterListener(this)
        .setConnectTimeout(getConnectionTimeout())
        .setReadTimeout(getReadTimeout())
        .setURL(url)
        .execute();
    }

    @Override
    public void onPageLoad(final Page page)
    {
        super.onPageLoad(page);

        if (page.getId() == null)
            return;

        final TestActivity act = getTestActivity();

        ItsNatDoc itsNatDoc = page.getItsNatDoc();

        View testNativeListenersButton = itsNatDoc.findViewByXMLId("testNativeListenersId");
        ItsNatView testNativeListenersButtonItsNat = itsNatDoc.getItsNatView(testNativeListenersButton);
        final TextView testNativeListenersLog = (TextView)itsNatDoc.findViewByXMLId("testNativeListenersLogId");

        testNativeListenersButtonItsNat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testNativeListenersLog.setText(testNativeListenersLog.getText() + "OK 3/3 Click Native ");
            }
        });

        testNativeListenersButtonItsNat.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                int action = motionEvent.getAction();
                int count = action + 1;
                testNativeListenersLog.setText(testNativeListenersLog.getText() + "OK " + count + "/3 Touch Native, action:" + action + "\n");
                return false;
            }
        });


        itsNatDoc.addEventMonitor(new EventMonitor()
        {
            @Override
            public void before(Event event)
            {
                Log.v("TestActivity", "Evt Monitor: before");
            }

            @Override
            public void after(Event event, boolean timeout)
            {
                Log.v("TestActivity", "Evt Monitor: after, timeout: " + timeout);
            }
        });

    }

}
