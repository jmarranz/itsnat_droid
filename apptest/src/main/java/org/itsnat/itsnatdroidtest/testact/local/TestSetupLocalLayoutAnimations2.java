package org.itsnat.itsnatdroidtest.testact.local;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.Assert;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;

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
        initGridView(act, rootView);
        initInterpolatorTests(act, rootView,layout);
    }

    private static void initGridView(TestActivity act, View rootView)
    {
        Resources res = act.getResources();
        GridView gridView = (GridView) rootView.findViewById(R.id.gridViewAnimationTestId);
        CharSequence[] entries = res.getTextArray(R.array.sports_array);
        gridView.setAdapter(new ArrayAdapter<CharSequence>(act, android.R.layout.simple_list_item_1, entries));
    }

    private static void initInterpolatorTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        initInterpolatorAccelerateTest(act,rootView,layout);
        initInterpolatorAccelerateDecelerateTest(act,rootView,layout);
        initInterpolatorAnticipateTest(act,rootView,layout);
        initInterpolatorAnticipateOvershootTest(act,rootView,layout);
        initInterpolatorBounceTest(act,rootView,layout);
        initInterpolatorCycleTest(act,rootView,layout);
        initInterpolatorDecelerateTest(act,rootView,layout);
        initInterpolatorLinearTest(act,rootView,layout);
        initInterpolatorOvershootTest(act,rootView,layout);
    }

    private static TextView getTextView(View rootView,int id)
    {
        return (TextView) rootView.findViewById(id);
    }

    private static Animation getAnimation(TestActivity act, InflatedLayout layout)
    {
        TranslateAnimation animation;
        if (layout == null) animation = (TranslateAnimation) AnimationUtils.loadAnimation(act, R.anim.test_animation_translate_for_interpolators_compiled);
        else animation = (TranslateAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_translate_for_interpolators_asset.xml");
        return animation;
    }

    private static Interpolator getInterpolator(int compId,String assetRef,TestActivity act,InflatedLayout layout)
    {
        Interpolator interpolator;
        if (layout == null) interpolator = AnimationUtils.loadInterpolator(act, compId);
        else interpolator = layout.getItsNatResources().getInterpolator(assetRef);
        return interpolator;
    }

    private static void initInterpolatorAccelerateTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.acInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        AccelerateInterpolator interpolator = (AccelerateInterpolator)getInterpolator(
                R.anim.test_interpolator_accelerate_compiled,
                "@assets:anim/res/anim/test_interpolator_accelerate_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,AccelerateInterpolator.class,"mFactor"),2.0f);
        Assert.assertEquals((Double)TestUtil.getField(interpolator,AccelerateInterpolator.class,"mDoubleFactor"),(double)(2 * 2.0f));
    }

    private static void initInterpolatorAccelerateDecelerateTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.acDecInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        AccelerateDecelerateInterpolator interpolator = (AccelerateDecelerateInterpolator)getInterpolator(
                R.anim.test_interpolator_acceleratedecelerate_compiled,
                "@assets:anim/res/anim/test_interpolator_acceleratedecelerate_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        // Nothing to test, no fields
    }

    private static void initInterpolatorAnticipateTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.anticipateInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        AnticipateInterpolator interpolator = (AnticipateInterpolator)getInterpolator(
                R.anim.test_interpolator_anticipate_compiled,
                "@assets:anim/res/anim/test_interpolator_anticipate_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,AnticipateInterpolator.class,"mTension"),4.0f);
    }

    private static void initInterpolatorAnticipateOvershootTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.antOverInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        AnticipateOvershootInterpolator interpolator = (AnticipateOvershootInterpolator)getInterpolator(
                R.anim.test_interpolator_anticipateovershoot_compiled,
                "@assets:anim/res/anim/test_interpolator_anticipateovershoot_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,AnticipateOvershootInterpolator.class,"mTension"),3.0f * 2.0f);
    }

    private static void initInterpolatorBounceTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.bounceInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        BounceInterpolator interpolator = (BounceInterpolator)getInterpolator(
                R.anim.test_interpolator_bounce_compiled,
                "@assets:anim/res/anim/test_interpolator_bounce_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        // Nothing to test, no fields
    }

    private static void initInterpolatorCycleTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.cycleInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        CycleInterpolator interpolator = (CycleInterpolator)getInterpolator(
                R.anim.test_interpolator_cycle_compiled,
                "@assets:anim/res/anim/test_interpolator_cycle_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,CycleInterpolator.class,"mCycles"),2.0f);
    }

    private static void initInterpolatorDecelerateTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.decelerateInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        DecelerateInterpolator interpolator = (DecelerateInterpolator)getInterpolator(
                R.anim.test_interpolator_decelerate_compiled,
                "@assets:anim/res/anim/test_interpolator_decelerate_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,DecelerateInterpolator.class,"mFactor"),2.0f);
    }

    private static void initInterpolatorLinearTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.linearInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        LinearInterpolator interpolator = (LinearInterpolator)getInterpolator(
                R.anim.test_interpolator_linear_compiled,
                "@assets:anim/res/anim/test_interpolator_linear_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        // Nothing to test, no fields
    }

    private static void initInterpolatorOvershootTest(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = getTextView(rootView,R.id.overshootInterpolatorTestId1);
        Animation animation = getAnimation(act,layout);

        OvershootInterpolator interpolator = (OvershootInterpolator)getInterpolator(
                R.anim.test_interpolator_overshoot_compiled,
                "@assets:anim/res/anim/test_interpolator_overshoot_asset.xml",act,layout);

        animation.setInterpolator(interpolator);
        textView.startAnimation(animation);

        Assert.assertEquals((Float)TestUtil.getField(interpolator,OvershootInterpolator.class,"mTension"),4.0f);
    }
}
