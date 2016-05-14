package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestLocalLayoutAnimations2
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


        // layoutAnimation / gridLayoutAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "layoutAnimation / gridLayoutAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // layoutAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "layoutAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);

            assertEquals(compLayout.getChildCount(), 2);
            assertEquals(compLayout.getChildCount(), parsedLayout.getChildCount());

            LayoutAnimationController compLayoutAnim = compLayout.getLayoutAnimation();
            LayoutAnimationController parsedLayoutAnim = parsedLayout.getLayoutAnimation();

            AnimationSet compAnim = (AnimationSet)compLayoutAnim.getAnimation();
            AnimationSet parsedAnim = (AnimationSet)parsedLayoutAnim.getAnimation();

                assertEquals(compAnim.getAnimations().size(), 1);
                assertEquals(compAnim.getAnimations().size(), parsedAnim.getAnimations().size());

                AlphaAnimation compAnimChild = (AlphaAnimation)compAnim.getAnimations().get(0);
                AlphaAnimation parsedAnimChild = (AlphaAnimation)parsedAnim.getAnimations().get(0);
                assertEquals(compAnimChild.getDuration(), 1000);
                assertEquals(compAnimChild.getDuration(), parsedAnimChild.getDuration());

            assertEquals(compLayoutAnim.getOrder(), 1); // "reverse"
            assertEquals(compLayoutAnim.getOrder(),parsedLayoutAnim.getOrder());

            assertEquals(compLayoutAnim.getDelay(),0.5f);
            assertEquals(compLayoutAnim.getDelay(),parsedLayoutAnim.getDelay());

            AccelerateDecelerateInterpolator compInterp = (AccelerateDecelerateInterpolator)compLayoutAnim.getInterpolator();
            assertNotNull(compInterp);
            AccelerateDecelerateInterpolator parsedInterp = (AccelerateDecelerateInterpolator)parsedLayoutAnim.getInterpolator();
            assertNotNull(parsedInterp);
        }

        // gridLayoutAnimation Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "gridLayoutAnimation Tests");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final GridView compLayout = (GridView) comp.getChildAt(childCount);
            final GridView parsedLayout = (GridView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getChildCount(), 7);
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
                {
                    assertEquals(compLayout.getChildCount(), parsedLayout.getChildCount());
                }
            });

            GridLayoutAnimationController compLayoutAnim = (GridLayoutAnimationController)compLayout.getLayoutAnimation();
            GridLayoutAnimationController parsedLayoutAnim = (GridLayoutAnimationController)parsedLayout.getLayoutAnimation();

            AnimationSet compAnim = (AnimationSet)compLayoutAnim.getAnimation();
            AnimationSet parsedAnim = (AnimationSet)parsedLayoutAnim.getAnimation();

                assertEquals(compAnim.getAnimations().size(), 1);
                assertEquals(compAnim.getAnimations().size(), parsedAnim.getAnimations().size());

                AlphaAnimation compAnimChild = (AlphaAnimation)compAnim.getAnimations().get(0);
                AlphaAnimation parsedAnimChild = (AlphaAnimation)parsedAnim.getAnimations().get(0);
                assertEquals(compAnimChild.getDuration(), 1000);
                assertEquals(compAnimChild.getDuration(), parsedAnimChild.getDuration());

            assertEquals(compLayoutAnim.getColumnDelay(), 0.15f);
            assertEquals(compLayoutAnim.getColumnDelay(),parsedLayoutAnim.getColumnDelay());

            assertEquals(compLayoutAnim.getRowDelay(), 0.60f);
            assertEquals(compLayoutAnim.getRowDelay(),parsedLayoutAnim.getRowDelay());

            assertEquals(compLayoutAnim.getOrder(),0);
            assertEquals(compLayoutAnim.getOrder(),parsedLayoutAnim.getOrder());

            assertEquals(compLayoutAnim.getDirection(),GridLayoutAnimationController.DIRECTION_TOP_TO_BOTTOM | GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT);
            assertEquals(compLayoutAnim.getDirection(),parsedLayoutAnim.getDirection());

            LinearInterpolator compInterp = (LinearInterpolator)compLayoutAnim.getInterpolator();
            assertNotNull(compInterp);
            LinearInterpolator parsedInterp = (LinearInterpolator)parsedLayoutAnim.getInterpolator();
            assertNotNull(parsedInterp);
        }

SEGUIR;

        //System.out.println("\n\n\n");

    }

}