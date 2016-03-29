package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestLocalLayoutAnimations
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

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());
        }

        //System.out.println("\n\n\n");

    }
}