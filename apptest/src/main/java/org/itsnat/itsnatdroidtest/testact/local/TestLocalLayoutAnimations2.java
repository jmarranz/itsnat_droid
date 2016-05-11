package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
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



        //System.out.println("\n\n\n");

    }

}