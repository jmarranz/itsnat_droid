package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.content.Intent;
import android.view.View;

import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupAssetLayoutMenu extends TestSetupAssetLayoutBase
{
    public TestSetupAssetLayoutMenu(TestActivityTabFragment fragment)
    {
        super(fragment);

        startTestActivity();
    }

    public void startTestActivity()
    {
        final TestActivity act = fragment.getTestActivity();
        Intent intent = new Intent(fragment.getTestActivity(), TestActivityMenu.class);
        //intent.putExtra("urlTestBase",urlTestBase);
        act.startActivity(intent);
    }

    public void test()
    {
        /*
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_menu_container_compiled);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                InflatedLayout layout = loadAssetAndBindBackReloadButtons("res/menu/test_local_layout_menu_container_asset.xml");
                View dynamicRootView = layout.getItsNatDoc().getRootView();

                initialConfiguration(act, dynamicRootView);

                // TestAssetLayoutDrawables.test((ScrollView) compiledRootView, (ScrollView) dynamicRootView);
            }
        });

        initialConfiguration(act, compiledRootView);
        */
    }

    private static void initialConfiguration(TestActivity act, View rootView)
    {
    }
}
