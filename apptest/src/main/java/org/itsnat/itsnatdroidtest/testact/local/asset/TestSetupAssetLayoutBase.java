package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidRoot;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.TestSetupBase;
import org.itsnat.itsnatdroidtest.testact.local.TestSetupLocalLayoutBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jmarranz on 16/07/14.
 */
public abstract class TestSetupAssetLayoutBase extends TestSetupLocalLayoutBase
{
    public TestSetupAssetLayoutBase(final TestActivityTabFragment fragment)
    {
        super(fragment);
    }

    protected View loadCompiledAndBindBackReloadButtons(int layoutId)
    {
        TestActivity act = getTestActivity();
        View compiledRootView = act.getLayoutInflater().inflate(layoutId, null);
        changeLayout(fragment, compiledRootView);

        Toast.makeText(act, "OK COMPILED", Toast.LENGTH_SHORT).show();

        bindBackButton(compiledRootView);

        return compiledRootView;
    }

}
