package org.itsnat.itsnatdroidtest.testact.local;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.Assert;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;

import java.util.ArrayList;

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

                    TestLocalLayoutAnimations.test((ScrollView) compiledRootView, (ScrollView) dynamicRootView);
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
        // Animator tests
        defineObjectAnimatorTests(act, rootView,layout);
        defineValueAnimatorTests(act, rootView,layout);
        defineSetAnimatorTests(act, rootView,layout);

        // Animation tests
        defineAlphaAnimationTests(act, rootView,layout);
        defineRotateAnimationTests(act, rootView,layout);
        defineScaleAnimationTests(act, rootView,layout);
        defineTranslateAnimationTests(act, rootView,layout);
        defineAnimationSetTests(act,rootView,layout);
    }

    private static void defineObjectAnimatorTests(TestActivity act, View rootView,InflatedLayout layout)
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
        if (layout == null)
        {
            adapter = ArrayAdapter.createFromResource(act, R.array.sports_array, android.R.layout.simple_list_item_1);
        }
        else
        {
            CharSequence[] sportsArray = layout.getItsNatResources().getTextArray("@assets:array/res/values/arrays_asset.xml:sports_array");
            adapter = new ArrayAdapter<CharSequence>(act,android.R.layout.simple_list_item_1,sportsArray);
        }
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

    private static void defineSetAnimatorTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        final TextView textView = (TextView) rootView.findViewById(R.id.animatorSetTestId1);

        final AnimatorSet animatorSet;
        if (layout == null)
            animatorSet = (AnimatorSet)AnimatorInflater.loadAnimator(act,R.animator.test_animator_set_compiled);
        else
            animatorSet = (AnimatorSet)layout.getItsNatResources().getAnimator("@assets:animator/res/animator/test_animator_set_asset.xml");

        animatorSet.setTarget(textView);

        animatorSet.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)  { }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        animatorSet.start();

        testAnimatorSet(animatorSet,-1);
    }

    private static void testAnimatorSet(AnimatorSet animatorSetParent,int indexParent)
    {
        ArrayList<Animator> list = animatorSetParent.getChildAnimations();
        for(int i = 0; i < list.size() ; i++)
        {
            Animator anim = list.get(i);
            if (indexParent == -1)
            {
                if (i == 0)
                {
                    AnimatorSet animSet = (AnimatorSet)anim;
                    testAnimatorSet(animSet,0);
                }
                else if (i == 1)
                {
                    ObjectAnimator objAnim = (ObjectAnimator)anim;
                    Assert.assertEquals(objAnim.getPropertyName(), "alpha");

                    Assert.assertEquals(objAnim.getDuration(), 2000);
                    // Los valores ya se testean más a fondo en otro sitio, esto es un test de AnimatorSet
                    //  android:valueFrom="0"  android:valueTo="1"
                }
            }
            else if (indexParent == 0)
            {
                if (i == 0)
                {
                    ObjectAnimator objAnim = (ObjectAnimator)anim;
                    Assert.assertEquals(objAnim.getPropertyName(), "x");

                    Assert.assertEquals(objAnim.getDuration(), 1000);
                    // Los valores ya se testean más a fondo en otro sitio, esto es un test de AnimatorSet
                    // android:valueType="floatType"  android:valueFrom="0"   android:valueTo="100.3dp"
                }
                else if (i == 1)
                {
                    ObjectAnimator objAnim = (ObjectAnimator)anim;
                    Assert.assertEquals(objAnim.getPropertyName(), "backgroundColor");

                    Assert.assertEquals(objAnim.getDuration(), 1000);
                    // Los valores ya se testean más a fondo en otro sitio, esto es un test de AnimatorSet
                    // android:valueFrom="#FFFF8000"  android:valueTo="#FFFF80FF"
                }
            }
        }
    }

    private static void defineAlphaAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.alphaAnimationTestId1);

        AlphaAnimation alphaAnimation;
        if (layout == null)
            alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(act,R.anim.test_animation_alpha_compiled);
        else
            alphaAnimation = (AlphaAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_alpha_asset.xml");

        textView.startAnimation(alphaAnimation);

        testAlphaAnimation(alphaAnimation);
    }

    private static void defineRotateAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.rotateAnimationTestId1);

        RotateAnimation rotateAnimation;
        if (layout == null)
            rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(act,R.anim.test_animation_rotate_compiled);
        else
            rotateAnimation = (RotateAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_rotate_asset.xml");

        textView.startAnimation(rotateAnimation);

        testRotateAnimation(rotateAnimation);
    }

    private static void defineScaleAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.scaleAnimationTestId1);

        ScaleAnimation animation;
        if (layout == null)
            animation = (ScaleAnimation) AnimationUtils.loadAnimation(act,R.anim.test_animation_scale_compiled);
        else
            animation = (ScaleAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_scale_asset.xml");

        textView.startAnimation(animation);

        testScaleAnimation(animation);
    }

    private static void defineTranslateAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        {
            TextView textView = (TextView) rootView.findViewById(R.id.translateAnimationTestId1);

            TranslateAnimation animation;
            if (layout == null) animation = (TranslateAnimation) AnimationUtils.loadAnimation(act, R.anim.test_animation_translate_compiled);
            else animation = (TranslateAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_translate_asset.xml");

            textView.startAnimation(animation);

            testTranslateAnimation_1(animation);
        }

        {
            TextView textView = (TextView) rootView.findViewById(R.id.translateAnimationTestId2);

            TranslateAnimation animation;
            if (layout == null) animation = (TranslateAnimation) AnimationUtils.loadAnimation(act, R.anim.test_animation_translate_2_compiled);
            else animation = (TranslateAnimation) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_translate_2_asset.xml");

            textView.startAnimation(animation);

            testTranslateAnimation_2(animation);
        }
    }

    private static void defineAnimationSetTests(TestActivity act, View rootView,InflatedLayout layout)
    {
        TextView textView = (TextView) rootView.findViewById(R.id.animationSetTestId1);

        AnimationSet animation;
        if (layout == null) animation = (AnimationSet) AnimationUtils.loadAnimation(act, R.anim.test_animation_set_compiled);
        else animation = (AnimationSet) layout.getItsNatResources().getAnimation("@assets:anim/res/anim/test_animation_set_asset.xml");

        textView.startAnimation(animation);

        testAnimationSet(animation);
    }

    private static void testAnimation(Animation animation)
    {
        // android:detachWallpaper
        Assert.assertEquals(animation.getDetachWallpaper(),true);
        // android:duration
        Assert.assertEquals(animation.getDuration(),1000);
        // android:fillAfter
        Assert.assertEquals(animation.getFillAfter(),true);
        // android:fillBefore
        Assert.assertEquals(animation.getFillBefore(),true);
        // android:fillEnabled
        Assert.assertEquals(animation.isFillEnabled(),true);
        // android:interpolator
        Assert.assertTrue(animation.getInterpolator() instanceof AccelerateInterpolator);
        // android:repeatCount
        Assert.assertEquals(animation.getRepeatCount(),-1); // Infinite
        // android:repeatMode
        Assert.assertEquals(animation.getRepeatMode(),1); // restart
        // android:startOffset
        Assert.assertEquals(animation.getStartOffset(),10);
        // android:zAdjustment
        Assert.assertEquals(animation.getZAdjustment(),-1); // bottom
    }

    private static void testAlphaAnimation(AlphaAnimation alphaAnimation)
    {
        testAnimation(alphaAnimation);

        // android:fromAlpha
        Assert.assertEquals(TestUtil.getField(alphaAnimation,AlphaAnimation.class,"mFromAlpha"), 0.0f);
        // android:toAlpha
        Assert.assertEquals(TestUtil.getField(alphaAnimation,AlphaAnimation.class,"mToAlpha"), 1.0f);
    }

    private static void testRotateAnimation(RotateAnimation rotateAnimation)
    {
        //android:fromDegrees
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mFromDegrees"), 0.3f);
        //android:toDegrees
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mToDegrees"), -25.3f);

        // android:pivotX
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mPivotXType"), 1);
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mPivotXValue"), 0.153f); // 15.3%
        // android:pivotY
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mPivotYType"), 1);
        Assert.assertEquals(TestUtil.getField(rotateAnimation,RotateAnimation.class,"mPivotYValue"), 0.403f); // 40.3%
    }

    private static void testScaleAnimation(ScaleAnimation scaleAnimation)
    {
        // android:fromXScale
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mFromX"), 0.9f);
        // android:fromYScale
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mFromY"), 0.9f);
        // android:toXScale
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mToX"), 2.3f);
        // android:toYScale
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mToY"), 2.3f);


        // android:pivotX
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mPivotXType"), 1);
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mPivotXValue"), 0.153f); // 15.3%
        // android:pivotY
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mPivotYType"), 1);
        Assert.assertEquals(TestUtil.getField(scaleAnimation,ScaleAnimation.class,"mPivotYValue"), 0.403f); // 40.3%
    }

    private static void testTranslateAnimation_1(TranslateAnimation translateAnimation)
    {
        // android:fromXDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromXValue"), 1.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromXType"), 1); // RELATIVE_TO_SELF
        // android:fromYDelta
        Assert.assertEquals((Float)TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromYValue"), 0.1f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromYType"), 2); // RELATIVE_TO_PARENT
        // android:toXDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToXValue"), 0.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToXType"), 1); // RELATIVE_TO_SELF
        // android:toYDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToYValue"), 0.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToYType"), 2); // RELATIVE_TO_PARENT
    }

    private static void testTranslateAnimation_2(TranslateAnimation translateAnimation)
    {
        // android:fromXDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromXValue"), 500f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromXType"), 0); // ABSOLUTE
        // android:fromYDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromYValue"), 0.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mFromYType"), 1); // RELATIVE_TO_SELF
        // android:toXDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToXValue"), 0.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToXType"), 0); // ABSOLUTE
        // android:toYDelta
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToYValue"), 0.0f);
        Assert.assertEquals(TestUtil.getField(translateAnimation,TranslateAnimation.class,"mToYType"), 1); // RELATIVE_TO_SELF
    }

    private static void testAnimationSet(AnimationSet animation)
    {

    }
}
