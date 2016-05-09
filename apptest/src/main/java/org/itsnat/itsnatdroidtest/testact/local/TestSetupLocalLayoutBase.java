package org.itsnat.itsnatdroidtest.testact.local;

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

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jmarranz on 16/07/14.
 */
public abstract class TestSetupLocalLayoutBase extends TestSetupBase
{

    protected final TestActivityTabFragment fragment;

    public TestSetupLocalLayoutBase(final TestActivityTabFragment fragment)
    {
        this.fragment = fragment;
    }

    protected TestActivity getTestActivity()
    {
        return fragment.getTestActivity();
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

    protected InflatedLayout loadDynamicAndBindBackReloadButtons(String layoutAssetPath)
    {
        // Sólo para testear carga local
        TestActivity act = getTestActivity();
        AssetManager am = act.getResources().getAssets();
        InputStream input;
        try
        {

            input = am.open(layoutAssetPath);
        }
        catch (IOException e)
        {
            throw new ItsNatDroidException(e);
        }

        // Alternativa es poner los layout root en raw: InputStream input = act.getResources().openRawResource(rawResId);
        // el problema es que raw no deja formar árboles de recursos con directorios, es mejor assets

        InflateLayoutRequest inflateRequest = ItsNatDroidRoot.get().createInflateLayoutRequest();
        InflatedLayout layout = inflateRequest
                .setEncoding("UTF-8")
                .setBitmapDensityReference(DisplayMetrics.DENSITY_XHIGH) // 320
                .setAttrResourceInflaterListener(this)
                .setContext(act)
                .inflate(input,null);


        View dynamicRootView = layout.getRootView();
        changeLayout(fragment, dynamicRootView);

        Toast.makeText(act, "OK XML DYNAMIC", Toast.LENGTH_SHORT).show();

        bindBackButton(dynamicRootView);
        bindReloadButtonDynamic(dynamicRootView);

        return layout;
    }

    protected void bindBackButton(View rootView)
    {
        View backButton = rootView.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fragment.gotoLayoutIndex();
            }
        });
    }

    protected void bindReloadButtonDynamic(View dynamicRootView)
    {
        View buttonReload = dynamicRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                test();
            }
        });
    }

    protected static void changeLayout(TestActivityTabFragment fragment,View rootView)
    {
        fragment.setRootView(rootView);
        fragment.updateFragmentLayout();
    }

    public abstract void test();
}
