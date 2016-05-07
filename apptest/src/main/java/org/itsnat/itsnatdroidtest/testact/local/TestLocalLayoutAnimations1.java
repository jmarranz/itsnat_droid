package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.itsnatdroidtest.R;

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

}