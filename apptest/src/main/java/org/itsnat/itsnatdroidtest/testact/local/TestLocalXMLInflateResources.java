package org.itsnat.itsnatdroidtest.testact.local;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestLocalXMLInflateResources
{
    public static void test(ScrollView compRoot, ScrollView parsedRoot)
    {
        Context ctx = compRoot.getContext();
        final Resources res = ctx.getResources();

        // comp = "Layout compiled"
        // parsed = "Layout dynamically parsed"
        // No podemos testear layout_width/height en el ScrollView root porque un View está desconectado y al desconectar el width y el height se ponen a 0
        // assertEquals(comp.getWidth(),parsed.getWidth());
        // assertEquals(comp.getHeight(),parsed.getHeight());

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


        childCount++;

        // Test BitmapDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "BitmapDrawable (partial img in center)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((BitmapDrawable) compLayout.getBackground());
            assertEquals((BitmapDrawable)compLayout.getBackground(), (BitmapDrawable)parsedLayout.getBackground());

        }

        childCount++;

        // Test BitmapDrawable 2 attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "BitmapDrawable 2 (partial img repeated)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((BitmapDrawable) compLayout.getBackground());
            assertEquals((BitmapDrawable)compLayout.getBackground(), (BitmapDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test ClipDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "ClipDrawable (half img)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((ClipDrawable) compLayout.getBackground());
            assertEquals((ClipDrawable)compLayout.getBackground(), (ClipDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test ClipDrawable 2 attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "ClipDrawable 2 (half img)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((ClipDrawable) compLayout.getBackground());
            assertEquals((ClipDrawable)compLayout.getBackground(), (ClipDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test ColorDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "ColorDrawable (gray)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((ColorDrawable) compLayout.getBackground());
            assertEquals((ColorDrawable)compLayout.getBackground(), (ColorDrawable)parsedLayout.getBackground());
        }


        childCount++;

        // Test NinePatchDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "NinePatchDrawable (border must be green)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((NinePatchDrawable) compLayout.getBackground());
            assertEquals((NinePatchDrawable)compLayout.getBackground(), (NinePatchDrawable)parsedLayout.getBackground());

        }

        childCount++;

        // Test LayerDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "LayerDrawable (2 green rects and centered img)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((LayerDrawable) compLayout.getBackground());
            assertEquals((LayerDrawable)compLayout.getBackground(), (LayerDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test TransitionDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "TransitionDrawable (green rect padded, press adds a centered bot");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((TransitionDrawable) compLayout.getBackground());
            assertEquals((TransitionDrawable)compLayout.getBackground(), (TransitionDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test StateListDrawable attribs
        {
            final Button compLayout = (Button) comp.getChildAt(childCount);
            final Button parsedLayout = (Button) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "StateListDrawable (green rect, press to change to a bot in center)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((StateListDrawable) compLayout.getBackground());
            assertEquals((StateListDrawable)compLayout.getBackground(), (StateListDrawable)parsedLayout.getBackground());
        }

        childCount++;

        // Test LevelListDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "LevelListDrawable (green rect, press to change to a bot in center)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            ((LevelListDrawable)compLayout.getBackground()).setLevel(1); // Si pulsamos quedó en level 4 y no podemos comparar sin cambiar a 1 pues en carga inicialmente se pone a 1

            assertNotNull((LevelListDrawable) compLayout.getBackground());
            assertEquals((LevelListDrawable) compLayout.getBackground(), (LevelListDrawable) parsedLayout.getBackground());
        }

        childCount++;

        // Test InsetDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "InsetDrawable (green rect with insets, text inside)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((InsetDrawable) compLayout.getBackground());
            assertEquals((InsetDrawable) compLayout.getBackground(), (InsetDrawable) parsedLayout.getBackground());
        }

        childCount++;

        // Test ScaleDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "ScaleDrawable (green, small and centered rect)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((ScaleDrawable) compLayout.getBackground());
            assertEquals((ScaleDrawable) compLayout.getBackground(), (ScaleDrawable) parsedLayout.getBackground());
        }

        childCount++;

        // Test GradientDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "GradientDrawable");
            assertEquals(compLayout.getText(), parsedLayout.getText());


            assertNotNull((GradientDrawable) compLayout.getBackground());
            assertEquals((GradientDrawable) compLayout.getBackground(), (GradientDrawable) parsedLayout.getBackground());

        }

        childCount++;

        // Test GradientDrawable attribs 2
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "GradientDrawable 2");
            assertEquals(compLayout.getText(), parsedLayout.getText());


            assertNotNull((GradientDrawable) compLayout.getBackground());
            assertEquals((GradientDrawable) compLayout.getBackground(), (GradientDrawable) parsedLayout.getBackground());

        }

        childCount++;

        // Test GradientDrawable attribs 3
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "GradientDrawable 3");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((GradientDrawable) compLayout.getBackground());
            assertEquals((GradientDrawable) compLayout.getBackground(), (GradientDrawable) parsedLayout.getBackground());

        }

        childCount++;

        // Test GradientDrawable attribs 4
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "GradientDrawable 4");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((GradientDrawable) compLayout.getBackground());
            assertEquals((GradientDrawable) compLayout.getBackground(), (GradientDrawable) parsedLayout.getBackground());

        }

        childCount++;

        // Test AnimationDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "AnimationDrawable (green rect and bot switching)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((AnimationDrawable) compLayout.getBackground());
            assertEquals((AnimationDrawable) compLayout.getBackground(), (AnimationDrawable) parsedLayout.getBackground());

        }

        childCount++;

        // Test RotateDrawable attribs
        {
            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(), "RotateDrawable (bot rotated 10 degrees, level 10000, pivot (15.3%,40.3%)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            assertNotNull((RotateDrawable) compLayout.getBackground());
            assertEquals((RotateDrawable) compLayout.getBackground(), (RotateDrawable) parsedLayout.getBackground());

        }




        //System.out.println("\n\n\n");

    }
}