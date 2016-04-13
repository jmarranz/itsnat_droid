package org.itsnat.itsnatdroidtest.testact.local;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.Assert;
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

                    initialConfiguration(act, dynamicRootView,layout);

                    TestLocalLayoutAnimations.test((CustomScrollView) compiledRootView, (CustomScrollView) dynamicRootView);
                //}
                //catch(ItsNatDroidException ex)
                //{
                  //  throw ex;
                //}
            }
        });

        initialConfiguration(act, compiledRootView,null);
    }

    private static void initialConfiguration(TestActivity act, View rootView,InflatedLayout layout)
    {
        defineObjectAnimatorTests(act, rootView);
        defineValueAnimatorTests(act, rootView,layout);
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

    private static void defineValueAnimatorTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        final TextView textView = (TextView) rootView.findViewById(R.id.valueAnimatorTestId1);

        ValueAnimator valueAnimator;
        if (layout == null)
            valueAnimator = (ValueAnimator)AnimatorInflater.loadAnimator(act,R.animator.test_value_animator_compiled);
        else
            valueAnimator = (ValueAnimator)layout.getItsNatResources().getAnimator("@assets:animator/res/animator/test_value_animator_asset.xml");

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Integer value = (Integer) animation.getAnimatedValue();
                textView.setBackgroundColor(value);
            }
        });

        valueAnimator.start();

        Assert.assertEquals(valueAnimator.getDuration(), 2000);
        Assert.assertEquals(valueAnimator.getRepeatCount(), -1);
        Assert.assertEquals(valueAnimator.getRepeatMode(), 2);
        Assert.assertEquals(valueAnimator.getStartDelay(), 10);
    }


}
