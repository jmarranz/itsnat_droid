package org.itsnat.itsnatdroidtest.testact.local;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.CustomScrollView;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupLocalLayoutAnimations extends TestSetupLocalLayoutBase
{
    public TestSetupLocalLayoutAnimations(TestActivityTabFragment fragment) {
        super(fragment);
    }

    public void test() {
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_animations_compiled);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                //try
                //{
                    InflatedLayout layout = loadDynamicAndBindBackReloadButtons("res/layout/test_local_layout_animations_asset.xml");
                    View dynamicRootView = layout.getRootView();

                    initialConfiguration(act, dynamicRootView);

                    TestLocalLayoutAnimations.test((CustomScrollView) compiledRootView, (CustomScrollView) dynamicRootView);
                //}
                //catch(ItsNatDroidException ex)
                //{
                  //  throw ex;
                //}
            }
        });

        initialConfiguration(act, compiledRootView);
    }

    private static void initialConfiguration(TestActivity act, View rootView)
    {
        defineObjectAnimatorTests(act, rootView);


    }

    private static void defineObjectAnimatorTests(TestActivity act, View rootView)
    {
        // Resources res = act.getResources();
        AdapterViewFlipper viewFlipper;
        ArrayAdapter<CharSequence> adapter;

        viewFlipper = (AdapterViewFlipper) rootView.findViewById(R.id.objectAnimatorTestId1);
        adapter = ArrayAdapter.createFromResource(act, R.array.sports_array, android.R.layout.simple_list_item_1);
        viewFlipper.setAdapter(adapter);

        viewFlipper = (AdapterViewFlipper) rootView.findViewById(R.id.objectAnimatorTestId2);
        adapter = ArrayAdapter.createFromResource(act, R.array.sports_array, android.R.layout.simple_list_item_1);
        viewFlipper.setAdapter(adapter);

        viewFlipper = (AdapterViewFlipper) rootView.findViewById(R.id.objectAnimatorTestId3);
        adapter = ArrayAdapter.createFromResource(act, R.array.sports_array, android.R.layout.simple_list_item_1);
        viewFlipper.setAdapter(adapter);

        viewFlipper = (AdapterViewFlipper) rootView.findViewById(R.id.objectAnimatorTestId4);
        adapter = ArrayAdapter.createFromResource(act, R.array.sports_array, android.R.layout.simple_list_item_1);
        viewFlipper.setAdapter(adapter);
    }
}
