package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.util.Assert;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;

import java.util.List;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestLocalLayoutAnimations1
{
    public static void test(ScrollView compRoot, ScrollView parsedRoot)
    {
        Context ctx = compRoot.getContext();
        final Resources res = ctx.getResources();

        LinearLayout comp = (LinearLayout) compRoot.getChildAt(0);
        LinearLayout parsed = (LinearLayout) parsedRoot.getChildAt(0);

        assertEquals(comp.getOrientation(), parsed.getOrientation());

        int childCount = 0;

        // buttonBack
        {
            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }

        childCount++;

        // buttonReload
        {
            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }


        // Animator Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Animator Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // ObjectAnimator Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "ObjectAnimator Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.objectAnimatorTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.objectAnimatorTestId2);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.objectAnimatorTestId3);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.objectAnimatorTestId4);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());

        }

        // ValueAnimator Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "ValueAnimator Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.valueAnimatorTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineValueAnimatorTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // AnimatorSet Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "AnimatorSet Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.animatorSetTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineAnimatorSetTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // Animation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Animation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // AlphaAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "AlphaAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.alphaAnimationTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineAlphaAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // RotateAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "RotateAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.rotateAnimationTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineRotateAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // ScaleAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "ScaleAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.scaleAnimationTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineScaleAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // TranslateAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "TranslateAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.translateAnimationTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineTranslateAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.translateAnimationTestId2);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineTranslateAnimationTests(TestActivity act, View rootView,InflatedLayout layout)
        }

        // AnimationSet Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "AnimationSet Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getId(), R.id.animationSetTestId1);
            assertEquals(compLayout.getId(), parsedLayout.getId());

            // El test de la animación se hace en otro lado:
            // TestSetupLocalLayoutAnimations.defineAnimationSetTests(TestActivity act, View rootView,InflatedLayout layout)
        }


        //System.out.println("\n\n\n");

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

    public static void testAlphaAnimation(AlphaAnimation alphaAnimation)
    {
        testAnimation(alphaAnimation);

        // android:fromAlpha
        Assert.assertEquals(TestUtil.getField(alphaAnimation,AlphaAnimation.class,"mFromAlpha"), 0.0f);
        // android:toAlpha
        Assert.assertEquals(TestUtil.getField(alphaAnimation,AlphaAnimation.class,"mToAlpha"), 1.0f);
    }

    public static void testRotateAnimation(RotateAnimation rotateAnimation)
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

    public static void testScaleAnimation(ScaleAnimation scaleAnimation)
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

    public static void testTranslateAnimation_1(TranslateAnimation translateAnimation)
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

    public static void testTranslateAnimation_2(TranslateAnimation translateAnimation)
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

    public static void testAnimationSet(AnimationSet animation)
    {
        int mFlags = (Integer)TestUtil.getField(animation,AnimationSet.class,"mFlags");
        boolean shareInterpolator = (mFlags & 0x10) == 0x10;// PROPERTY_SHARE_INTERPOLATOR_MASK = 0x10
        Assert.assertFalse(shareInterpolator);
        Assert.assertEquals(animation.getDuration(), 3000);
        Assert.assertEquals(animation.getFillAfter(), true);
        Assert.assertEquals(animation.getFillBefore(), true);
        Assert.assertEquals(animation.getRepeatMode(), 1); // restart
        Assert.assertEquals(animation.getStartOffset(), 10);

        List<Animation> children = animation.getAnimations();
        Assert.assertEquals(children.size(),2);

        Assert.assertTrue(children.get(0) instanceof AlphaAnimation);
        Assert.assertTrue(children.get(1) instanceof AnimationSet);

        AnimationSet animSetChild = (AnimationSet)children.get(1);
        Assert.assertTrue(animSetChild.getAnimations().get(0) instanceof TranslateAnimation);
    }

}