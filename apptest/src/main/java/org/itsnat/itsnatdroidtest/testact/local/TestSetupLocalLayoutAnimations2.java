package org.itsnat.itsnatdroidtest.testact.local;

import android.view.View;
import android.widget.ScrollView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupLocalLayoutAnimations2 extends TestSetupLocalLayoutBase
{
    public TestSetupLocalLayoutAnimations2(TestActivityTabFragment fragment) {
        super(fragment);
    }

    public void test() {
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_animations_2_compiled);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                InflatedLayout layout = loadDynamicAndBindBackReloadButtons("res/layout/test_local_layout_animations_2_asset.xml");
                View dynamicRootView = layout.getRootView();

                initialConfiguration(act, dynamicRootView,layout);

                TestLocalLayoutAnimations2.test((ScrollView) compiledRootView, (ScrollView) dynamicRootView);

            }
        });

        initialConfiguration(act, compiledRootView,null);
    }

    private static void initialConfiguration(TestActivity act, View rootView,InflatedLayout layout)
    {

    }


}
