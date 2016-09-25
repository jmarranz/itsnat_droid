package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsSeekBar;
import android.widget.AdapterViewAnimator;
import android.widget.AdapterViewFlipper;
import android.widget.AnalogClock;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidRoot;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.util.CustomTextView;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;
import org.itsnat.itsnatdroidtest.testact.util.ValueUtil;

import java.util.Locale;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEqualsRelativeLayoutLayoutParamsBellow;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEqualsViewGroupLayoutParams;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertFalse;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotZero;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertPositive;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertTrue;


// import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEqualsPixels;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestAssetLayout1
{

    public static void test(ScrollView compRoot, ScrollView parsedRoot, InflatedLayout layout)
    {
        Context ctx = compRoot.getContext();
        final Resources res = ctx.getResources();

        // comp = "Layout compiled"
        // parsed = "Layout dynamically parsed"

        LinearLayout comp = (LinearLayout)compRoot.getChildAt(0);
        LinearLayout parsed = (LinearLayout)parsedRoot.getChildAt(0);

        assertEquals(comp.getOrientation(),parsed.getOrientation());

        int childCount;

        // buttonBack
        {
            childCount = 0;

            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }

        // buttonReload
        {
            childCount++;

            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }

        // test <include>
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Included 1 w:match_parent by include");
            assertEquals(compTextView.getText(), parsedTextView.getText());

            assertEquals(((TextView)comp.findViewById(R.id.testIncludeId1)),compTextView);
            assertEquals(((TextView)parsed.findViewById(parsedTextView.getId())),parsedTextView);
            assertEquals(compTextView.getId(),parsedTextView.getId()); // Porque existe el id compilado y tiene prioridad en el caso dinámico

            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // test <include> dynamic (assets) 2
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Included 2 w:match_parent by include");
            assertEquals(compTextView.getText(), parsedTextView.getText());

            assertEquals(((TextView)comp.findViewById(R.id.testIncludeId2)),compTextView);
            assertEquals(((TextView) parsed.findViewById(parsedTextView.getId())), parsedTextView);
            assertEquals(compTextView.getId(),parsedTextView.getId()); // Porque existe el id compilado y tiene prioridad en el caso dinámico

            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // test <include> dynamic (assets) 3
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Included 3 w:wrap_content");
            assertEquals(compTextView.getText(), parsedTextView.getText());

            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Included 4 w:match_parent");
            assertEquals(compTextView.getText(), parsedTextView.getText());

            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // Testing misc attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test misc attribs");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            {
                RelativeLayout compLayout = (RelativeLayout) comp.getChildAt(childCount);
                RelativeLayout parsedLayout = (RelativeLayout) parsed.getChildAt(childCount);

                int childCountL2;


                {
                    childCountL2 = 0;

                    final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                    assertEquals(compTextView.getText(), "Test style including LayoutParams attribs");
                    assertEquals(compTextView.getText(), parsedTextView.getText());

                    assertEquals(compTextView.getTextSize(), ValueUtil.dpToPixelFloatRound(15.3f, res));
                    assertEquals(compTextView.getTextSize(), parsedTextView.getTextSize());

                    assertEquals(((ColorDrawable) compTextView.getBackground()).getColor(), 0xffdddddd);

                    ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                    ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();

                    assertEquals(a_params.height, ValueUtil.dpToPixelFloatRound(40.3f, res));
                    assertEquals(a_params.height, b_params.height);
                    assertEquals(a_params.width, ViewGroup.LayoutParams.WRAP_CONTENT);
                    assertEquals(a_params.width, b_params.width);

                    RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                    RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                    int[] compTextRules = compTextParams.getRules();
                    int[] parsedTextRules = parsedTextParams.getRules();
                    assertEquals(compTextRules.length, parsedTextRules.length); // Por si acaso pero son todas las posibles rules
                    assertNotZero(compTextRules[RelativeLayout.ALIGN_PARENT_TOP]);
                    assertEquals(compTextRules[RelativeLayout.ALIGN_PARENT_TOP], parsedTextRules[RelativeLayout.ALIGN_PARENT_TOP]);
                }


                {
                    childCountL2++;

                    final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                    // Test id ya definido como recurso compilado
                    assertEquals(compTextView.getId(), R.id.textViewTest1);
                    assertEquals(((TextView) compLayout.findViewById(R.id.textViewTest1)), compTextView);
                    assertEquals(((TextView) parsedLayout.findViewById(parsedTextView.getId())), parsedTextView);
                    assertEquals(compTextView.getId(), parsedTextView.getId()); // Porque existe el id compilado y tiene prioridad en el caso dinámico

                    // Test findViewByXMLId
                    assertEquals(parsedTextView, layout.findViewByXMLId("textViewTest1"));


                    assertEquals(compTextView.getText(), "Hello world 1!");
                    assertEquals(compTextView.getText(), parsedTextView.getText());

                    assertEquals(compTextView.getTextSize(), ValueUtil.dpToPixelFloatRound(15.3f, res));
                    assertEquals(compTextView.getTextSize(), parsedTextView.getTextSize());

                    // Test style
                    assertEquals(compTextView.getPaddingLeft(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingLeft(), parsedTextView.getPaddingLeft());

                    assertEquals(compTextView.getPaddingRight(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingRight(), parsedTextView.getPaddingRight());

                    assertEquals(compTextView.getPaddingTop(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compTextView.getPaddingTop(), parsedTextView.getPaddingTop());

                    assertEquals(compTextView.getPaddingBottom(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compTextView.getPaddingBottom(), parsedTextView.getPaddingBottom());

                    assertEquals(compTextView.getTextColors().getDefaultColor(), 0xff0000ff);
                    assertEquals(compTextView.getTextColors(), parsedTextView.getTextColors());

                    assertEquals(((ColorDrawable) compTextView.getBackground()).getColor(), res.getColor(res.getIdentifier("@android:color/holo_green_light", null, null)));
                    assertEquals(compTextView.getBackground(), parsedTextView.getBackground());
                }

                childCountL2++;


                TextView compTextViewUpper;
                TextView parsedTextViewUpper;

                {
                    final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                    // Test id añadido dinámicamente "@+id/..." los ids compilado y asset son nombres diferentes en este caso
                    // En este caso el valor del id compilado (que existe) no es igual al añadido dinámicamente
                    assertEquals(((TextView) compLayout.findViewById(R.id.textViewTest2_compiled)), compTextView);
                    assertEquals(((TextView) parsedLayout.findViewById(parsedTextView.getId())), parsedTextView);

                    assertEquals("org.itsnat.itsnatdroidtest:id/textViewTest2_compiled",ctx.getResources().getResourceName(compTextView.getId()));

                    assertEquals(compTextView.getText(), "Hello world 2!");
                    assertEquals(compTextView.getText(), parsedTextView.getText());
                    assertEquals(compTextView.getBackground(), parsedTextView.getBackground());

                    // Test atributo style
                    // No tenemos una forma de testear "textAppearanceMedium" de forma directa, una forma es testear una de las propiedades que impone, ej el tamaño del texto
                    assertEquals(compTextView.getTextSize(), parsedTextView.getTextSize());

                    compTextViewUpper = compTextView;
                    parsedTextViewUpper = parsedTextView;
                }

                childCountL2++;

                {
                    final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                    assertEquals(compTextView.getId(), R.id.test_id_textviewtest3);
                    //assertEquals(compTextView.getId(), parsedTextView.getId());
                    ItsNatDroidImpl itsNatDroid = (ItsNatDroidImpl)ItsNatDroidRoot.get(); // Internal access, not valid for end users
                    assertEquals(parsedTextView.getId(),itsNatDroid.getXMLInflaterRegistry().findViewIdDynamicallyAdded("textViewTest3"));

                    assertEquals(compTextView.getText(), "Text size=15.3dp, color=red,padding");
                    assertEquals(compTextView.getText(), parsedTextView.getText());

                    ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                    ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                    assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    assertEquals(compTextView.getTextSize(), ValueUtil.dpToPixelIntRound(15.3f, res)); // A pesar de usar un estilo parent de Android con textSize, lo imponemos
                    assertEquals(compTextView.getTextSize(), parsedTextView.getTextSize());

                    assertEquals(compTextView.getTextColors().getDefaultColor(), 0xffff0000);
                    assertEquals(compTextView.getTextColors(), parsedTextView.getTextColors());

                    // Test style
                    assertEquals(compTextView.getPaddingLeft(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingLeft(), parsedTextView.getPaddingLeft());

                    assertEquals(compTextView.getPaddingRight(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingRight(), parsedTextView.getPaddingRight());


                    RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                    RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                    assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                    compTextViewUpper = compTextView;
                    parsedTextViewUpper = parsedTextView;
                }

                childCountL2++;

                {
                    final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                    assertEquals(compTextView.getId(), R.id.textViewTest4);
                    assertEquals(compTextView.getId(), parsedTextView.getId());

                    assertEquals(compTextView.getText(), "Text size=smaller,color=red,padding");
                    assertEquals(compTextView.getText(), parsedTextView.getText());

                    ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                    ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                    assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    // assertEquals(compTextView.getTextSize(), ValueUtil.dpToPixelIntRound(15.3f, res)); // Se utiliza un style parent de Android, no sabemos el valor exacto
                    assertEquals(compTextView.getTextSize(), parsedTextView.getTextSize());

                    assertEquals(compTextView.getTextColors().getDefaultColor(), 0xffff0000);
                    assertEquals(compTextView.getTextColors(), parsedTextView.getTextColors());

                    // Test style
                    assertEquals(compTextView.getPaddingLeft(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingLeft(), parsedTextView.getPaddingLeft());

                    assertEquals(compTextView.getPaddingRight(), ValueUtil.dpToPixelIntRound(21.3f, res));
                    assertEquals(compTextView.getPaddingRight(), parsedTextView.getPaddingRight());


                    RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                    RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                    assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                    compTextViewUpper = compTextView;
                    parsedTextViewUpper = parsedTextView;
                }
            }

            // Test resource folder filters
            {
                childCount++;

                final TextView compTextView = (TextView) comp.getChildAt(childCount);
                final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
                assertEquals(compTextView.getText(), "Test resource folder filters");
                assertEquals(compTextView.getText(), parsedTextView.getText());
            }

            {
                childCount++;

                {
                    RelativeLayout compLayout = (RelativeLayout) comp.getChildAt(childCount);
                    RelativeLayout parsedLayout = (RelativeLayout) parsed.getChildAt(childCount);

                    int childCountL2;

                    TextView compTextViewUpper;
                    TextView parsedTextViewUpper;

                    {
                        childCountL2 = 0;


                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest1);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            assertTrue(compTextView.getText().toString().startsWith("Device Data to Test Filters:\n"));
                            assertTrue(parsedTextView.getText().toString().startsWith("Device Data to Test Filters:\n"));
                            // Indirectamente testeamos la conversión manual que hacemos de \n, \t (hay más casos de caracteres especiales pero no los testeamos):
                            assertEquals(compTextView.getText(), parsedTextView.getText());


                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            //assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest2);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            Locale locale = ctx.getResources().getConfiguration().locale;
                            String lang = locale.getLanguage();
                            String region = locale.getCountry();
                            if (lang.equals("es") && region.equals("ES"))
                                assertEquals(compTextView.getText(), "Test filter lang and region: -es-rES");
                            else
                                assertEquals(compTextView.getText(), "Test filter lang and region: (not -es-rES)");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest3);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int smallestScreenWidthDp = ctx.getResources().getConfiguration().smallestScreenWidthDp;
                            if (smallestScreenWidthDp < 384) assertEquals(compTextView.getText(), "Test filter smallestWidthDp < 384dp");
                            else assertEquals(compTextView.getText(), "Test filter smallestWidthDp >= 384dp");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest4);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int screenWidthDp = ctx.getResources().getConfiguration().screenWidthDp;
                            if (screenWidthDp < 384) assertEquals(compTextView.getText(), "Test filter screenWidthDp < 384dp");
                            else assertEquals(compTextView.getText(), "Test filter screenWidthDp >= 384dp");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest5);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int screenHeightDp = ctx.getResources().getConfiguration().screenHeightDp;
                            if (screenHeightDp < 696) assertEquals(compTextView.getText(), "Test filter screenHeightDp < 696dp");
                            else assertEquals(compTextView.getText(), "Test filter screenHeightDp >= 696dp");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest6);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int screenLayout = ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
                            if (screenLayout < Configuration.SCREENLAYOUT_SIZE_XLARGE) assertEquals(compTextView.getText(), "Test filter screen size < xlarge");
                            else assertEquals(compTextView.getText(), "Test filter screen size >= xlarge");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest7);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int screenLayout = ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LONG_MASK;
                            if (screenLayout == Configuration.SCREENLAYOUT_LONG_NO) assertEquals(compTextView.getText(), "Test filter screen aspect: notlong");
                            else if (screenLayout == Configuration.SCREENLAYOUT_LONG_YES)
                                assertEquals(compTextView.getText(), "Test filter screen aspect: long");
                            else throw new RuntimeException("Unexpected screenLayout " + screenLayout);
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest8);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int orientation = ctx.getResources().getConfiguration().orientation;
                            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                                assertEquals(compTextView.getText(), "Test filter screen orientation: port");
                            else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                                assertEquals(compTextView.getText(), "Test filter screen orientation: land");
                            else throw new RuntimeException("Unexpected orientation " + orientation);
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest9);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int orientation = ctx.getResources().getConfiguration().orientation;
                            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                                assertEquals(compTextView.getText(), "Test filter screen orientation 2: port");
                            else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                                assertEquals(compTextView.getText(), "Test filter screen orientation 2: land");
                            else throw new RuntimeException("Unexpected orientation " + orientation);
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest10);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int uiModeType = ctx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_TYPE_MASK;
                            if (uiModeType != Configuration.UI_MODE_TYPE_TELEVISION) assertEquals(compTextView.getText(), "Test filter ui mode type: (other != television)");
                            else // if (uiModeType == Configuration.UI_MODE_TYPE_TELEVISION)
                                assertEquals(compTextView.getText(), "Test filter ui mode type: television");

                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest11);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            // El modo night mode podríamos testearlo con un método http://developer.android.com/reference/android/app/UiModeManager.html pero es muy obvio que no vale la pena, testeamos sólo el notnight
                            int uiModeNight = ctx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                            if (uiModeNight == Configuration.UI_MODE_NIGHT_NO) assertEquals(compTextView.getText(), "Test filter ui mode night: notnight");
                            else if (uiModeNight == Configuration.UI_MODE_NIGHT_YES) assertEquals(compTextView.getText(), "Test filter ui mode night: night");
                            else throw new RuntimeException("Unexpected ui mode night " + uiModeNight);

                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest12);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            // Testear en el caso compilado es más complicado de lo esperado pues si tenemos un /values sin prefijo y un /values-xxhdpi, coge el /values-xxhdpi aunque sea un xhdpi (menor)
                            // por ello necesitamos al menos dos /values-algo para que no seleccione siempre el /values-algo único, se elige el más cercano
                            // Nuestro sistema es mucho más simple, sólo un selector
                            int densityDpi = ctx.getResources().getDisplayMetrics().densityDpi;
                            if (densityDpi < 320) // < xhdpi
                            {
                                assertEquals(compTextView.getText(), "Test filter screen pixel density < xhdpi");
                            }
                            else if (densityDpi >= 320 && densityDpi < (480 - 320) / 2 + 320) // >= xhdpi && < xxhdpi
                            {
                                assertEquals(compTextView.getText(), "Test filter screen pixel density >= xhdpi & < xxhdpi");
                            }
                            else // xxhdpi
                            {
                                assertEquals(compTextView.getText(), "Test filter screen pixel density >= xxhdpi");
                            }

                            if (densityDpi < 480) // < xhdpi
                            {
                                assertEquals(parsedTextView.getText(), "Test filter screen pixel density < xxhdpi");
                            }
                            else // >= 480 (xxhdpi)
                            {
                                assertEquals(parsedTextView.getText(), "Test filter screen pixel density >= xxhdpi");
                            }

                            // assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest13);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int touchscreen = ctx.getResources().getConfiguration().touchscreen;
                            if (touchscreen == Configuration.TOUCHSCREEN_NOTOUCH) assertEquals(compTextView.getText(), "Test filter touchscreen type: notouch");
                            else if (touchscreen == Configuration.TOUCHSCREEN_FINGER) assertEquals(compTextView.getText(), "Test filter touchscreen type: finger");
                            else throw new RuntimeException("Unexpected filter touchscreen type " + touchscreen);
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest14);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int keyboard = ctx.getResources().getConfiguration().keyboard;
                            if (keyboard == Configuration.KEYBOARD_QWERTY) assertEquals(compTextView.getText(), "Test filter primary text input: qwerty");
                            else if (keyboard == Configuration.KEYBOARD_NOKEYS) assertEquals(compTextView.getText(), "Test filter primary text input: nokeys");
                            else throw new RuntimeException("Unexpected filter primary text input " + keyboard);
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                        childCountL2++;

                        {
                            final TextView compTextView = (TextView) compLayout.getChildAt(childCountL2);
                            final TextView parsedTextView = (TextView) parsedLayout.getChildAt(childCountL2);

                            assertEquals(compTextView.getId(), R.id.textViewFilterTest15);
                            assertEquals(compTextView.getId(), parsedTextView.getId());

                            int navigation = ctx.getResources().getConfiguration().navigation;
                            if (navigation != Configuration.NAVIGATION_NONAV) assertEquals(compTextView.getText(), "Test filter prim nontouch nav: (other)");
                            else /* if (navigation == Configuration.NAVIGATION_NONAV) */ assertEquals(compTextView.getText(), "Test filter prim nontouch nav: nonav");
                            assertEquals(compTextView.getText(), parsedTextView.getText());

                            ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                            ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();
                            assertEqualsViewGroupLayoutParams(a_params, b_params, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams) compTextView.getLayoutParams();
                            RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams) parsedTextView.getLayoutParams();
                            assertEqualsRelativeLayoutLayoutParamsBellow(compTextParams, parsedTextParams, compTextViewUpper.getId(), parsedTextViewUpper.getId());

                            compTextViewUpper = compTextView;
                            parsedTextViewUpper = parsedTextView;
                        }

                    }
                }
            }
        }


        // Testing custom View
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test Custom View");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final CustomTextView compCustomTextView = (CustomTextView) comp.getChildAt(childCount);
            final CustomTextView parsedCustomTextView = (CustomTextView) parsed.getChildAt(childCount);

            assertEquals(compCustomTextView.getText(), "Custom View 1");
            assertEquals(compCustomTextView.getText(), parsedCustomTextView.getText());
            assertEquals(compCustomTextView.getBackground(), parsedCustomTextView.getBackground());
        }

        {
            childCount++;

            final CustomTextView compCustomTextView = (CustomTextView) comp.getChildAt(childCount);
            final CustomTextView parsedCustomTextView = (CustomTextView) parsed.getChildAt(childCount);

            assertEquals(compCustomTextView.getText(), "Custom View 2");
            assertEquals(compCustomTextView.getText(), parsedCustomTextView.getText());
            assertEquals(compCustomTextView.getBackground(), parsedCustomTextView.getBackground());
        }


        // Test View Attribs
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test View Attribs");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            LinearLayout compLinLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLinLayout = (LinearLayout) parsed.getChildAt(childCount);
            {
                {
                    final TextView compTextView = (TextView) compLinLayout.getChildAt(0);
                    final TextView parsedTextView = (TextView) parsedLinLayout.getChildAt(0);
                    assertEquals(compTextView.getAlpha(), 0.7f);
                    assertEquals(compTextView.getAlpha(), parsedTextView.getAlpha());
                    assertEquals(((ColorDrawable) compTextView.getBackground()).getColor(), 0xffdddddd);
                    assertEquals(compTextView.getBackground(), parsedTextView.getBackground());

                    assertFalse(compTextView.isClickable());
                    assertEquals(compTextView.isClickable(), parsedTextView.isClickable());
                    assertEquals(compTextView.getContentDescription(), "For Testing View Attribs");
                    assertEquals(compTextView.getContentDescription(), parsedTextView.getContentDescription());
                    assertEquals(compTextView.getDrawingCacheQuality(), View.DRAWING_CACHE_QUALITY_HIGH);
                    assertEquals(compTextView.getDrawingCacheQuality(), parsedTextView.getDrawingCacheQuality());
                    assertTrue(compTextView.isDuplicateParentStateEnabled());
                    assertEquals(compTextView.isDuplicateParentStateEnabled(), parsedTextView.isDuplicateParentStateEnabled());
                    assertTrue(compTextView.getFitsSystemWindows());
                    assertEquals(compTextView.getFitsSystemWindows(), parsedTextView.getFitsSystemWindows());
                }

                {
                    final ScrollView compScrollView = (ScrollView) compLinLayout.getChildAt(1);
                    final ScrollView parsedScrollView = (ScrollView) parsedLinLayout.getChildAt(1);

                    // Test android:fadingEdgeLength
                    assertEquals(compScrollView.getVerticalFadingEdgeLength(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compScrollView.getVerticalFadingEdgeLength(), parsedScrollView.getVerticalFadingEdgeLength());
                    assertEquals(compScrollView.getHorizontalFadingEdgeLength(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compScrollView.getHorizontalFadingEdgeLength(), parsedScrollView.getHorizontalFadingEdgeLength());

                    assertTrue(compScrollView.isScrollbarFadingEnabled()); // Correspondiente a requiresFadingEdge
                    assertEquals(compScrollView.isScrollbarFadingEnabled(), parsedScrollView.isScrollbarFadingEnabled());


                    // Test android:scrollbarAlwaysDrawHorizontalTrack

                    final Class[] scrollCacheClasses = new Class[]{View.class, TestUtil.resolveClass("android.view.View$ScrollabilityCache"), TestUtil.resolveClass("android.widget.ScrollBarDrawable")};

                    assertTrue((Boolean) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawHorizontalTrack"}));
                    assertEquals((Boolean) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawHorizontalTrack"}), (Boolean) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawHorizontalTrack"}));

                    // Test android:scrollbarAlwaysDrawVerticalTrack
                    assertTrue((Boolean) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawVerticalTrack"}));
                    assertEquals((Boolean) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawVerticalTrack"}), (Boolean) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mAlwaysDrawVerticalTrack"}));

                    // Test android:scrollbarThumbHorizontal
                    assertEquals((GradientDrawable) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mHorizontalThumb"}), (GradientDrawable) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mHorizontalThumb"}));

                    // Test android:scrollbarThumbVertical
                    assertEquals((GradientDrawable) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mVerticalThumb"}), (GradientDrawable) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mVerticalThumb"}));

                    // Test android:scrollbarTrackHorizontal
                    assertEquals((GradientDrawable) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mHorizontalTrack"}), (GradientDrawable) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mHorizontalTrack"}));

                    // Test android:scrollbarTrackVertical
                    assertEquals((GradientDrawable) TestUtil.getField(compScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mVerticalTrack"}), (GradientDrawable) TestUtil.getField(parsedScrollView, scrollCacheClasses, new String[]{"mScrollCache", "scrollBar", "mVerticalTrack"}));

                    // Test android:scrollbars
                    int scrollbars = (Integer)TestUtil.getField(compScrollView,View.class,"mViewFlags");
                    int SCROLLBARS_MASK = 0x00000300;
                    scrollbars = scrollbars & SCROLLBARS_MASK;
                    assertEquals(scrollbars & 0x00000100,0x00000100); // Horizontal
                    assertEquals(scrollbars & 0x00000200,0x00000200); // Vertical

                    // Test android:scrollbarDefaultDelayBeforeFade
                    // lo testeamos en un ScrollView porque de otra manera el atributo es ignorado si el componente no tiene scrollbars
                    assertEquals(compScrollView.getScrollBarDefaultDelayBeforeFade(), 500);
                    assertEquals(compScrollView.getScrollBarDefaultDelayBeforeFade(), parsedScrollView.getScrollBarDefaultDelayBeforeFade());

                    // No testeamos android:scrollX y android:scrollY (con getScrollX() y getScrollY()) porque después de definirse correctamente
                    // algo hace poner a cero los valores, quizás al insertar la View

                    assertEquals(compScrollView.getScrollBarFadeDuration(), 600);
                    assertEquals(compScrollView.getScrollBarFadeDuration(), parsedScrollView.getScrollBarFadeDuration());

                    assertEquals(compScrollView.getScrollBarSize(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compScrollView.getScrollBarSize(), parsedScrollView.getScrollBarSize());

                    assertPositive(compScrollView.getScrollBarStyle());
                    assertEquals(compScrollView.getScrollBarStyle(), parsedScrollView.getScrollBarStyle());

                }

                {
                    // No usamos aquí TextView porque minHeight/minWidth se definen también en TextView y no podríamos testear para View (testearíamos los de TextView)
                    final View compTextView2 = compLinLayout.getChildAt(2);
                    final View parsedTextView2 = parsedLinLayout.getChildAt(2);

                    //assertEquals(compTextView2.getText(), parsedTextView2.getText());
                    // Test android:filterTouchesWhenObscured
                    assertTrue(compTextView2.getFilterTouchesWhenObscured());
                    // En el emulador 4.0.4 el setFilterTouchesWhenObscured() parece como si hiciera un NOT al parámetro, sin embargo en el Nexus 4 perfecto
                    // por ello mostramos un alertDialog no lanzamos una excepción
                    if (compTextView2.getFilterTouchesWhenObscured() != parsedTextView2.getFilterTouchesWhenObscured())
                        TestUtil.alertDialog(compTextView2.getContext(), "Test failed in filterTouchesWhenObscured, don't worry it seems an Android emulator bug (running on 4.1 API 16 emulator?)");
                    assertTrue(compTextView2.isFocusable());
                    assertEquals(compTextView2.isFocusable(), parsedTextView2.isFocusable());
                    assertTrue(compTextView2.isFocusableInTouchMode());
                    assertEquals(compTextView2.isFocusableInTouchMode(), parsedTextView2.isFocusableInTouchMode());
                    assertFalse(compTextView2.isHapticFeedbackEnabled());
                    assertEquals(compTextView2.isHapticFeedbackEnabled(), parsedTextView2.isHapticFeedbackEnabled());
                    assertPositive(compTextView2.getId());
                    assertEquals(compTextView2.getId(), parsedTextView2.getId());
                    assertEquals(compTextView2.getImportantForAccessibility(),View.IMPORTANT_FOR_ACCESSIBILITY_YES);
                    assertEquals(compTextView2.getImportantForAccessibility(), parsedTextView2.getImportantForAccessibility());

                    assertFalse(compTextView2.isScrollContainer());
                    assertEquals(compTextView2.isScrollContainer(), parsedTextView2.isScrollContainer());

                    assertTrue(compTextView2.getKeepScreenOn());
                    assertEquals(compTextView2.getKeepScreenOn(), parsedTextView2.getKeepScreenOn());
                    assertEquals(compTextView2.getLayerType(), View.LAYER_TYPE_HARDWARE);
                    assertEquals(compTextView2.getLayerType(), parsedTextView2.getLayerType());
                    assertTrue(compTextView2.isLongClickable());
                    assertEquals(compTextView2.isLongClickable(), parsedTextView2.isLongClickable());
                    // Test android:minHeight
                    assertEquals((Integer) TestUtil.getField(compTextView2, View.class, "mMinHeight"), ValueUtil.dpToPixelIntRound(30.3f, res));
                    assertEquals((Integer) TestUtil.getField(compTextView2, View.class, "mMinHeight"), (Integer) TestUtil.getField(parsedTextView2, View.class, "mMinHeight"));
                    assertEquals((Integer) TestUtil.getField(compTextView2, View.class, "mMinWidth"), ValueUtil.dpToPixelIntRound(30.3f, res));
                    assertEquals((Integer) TestUtil.getField(compTextView2, View.class, "mMinWidth"), (Integer) TestUtil.getField(parsedTextView2, View.class, "mMinWidth"));
                    assertPositive(compTextView2.getNextFocusDownId());
                    assertEquals(compTextView2.getNextFocusDownId(), parsedTextView2.getNextFocusDownId());
                    assertPositive(compTextView2.getNextFocusForwardId());
                    assertEquals(compTextView2.getNextFocusForwardId(), parsedTextView2.getNextFocusForwardId());
                    assertPositive(compTextView2.getNextFocusLeftId());
                    assertEquals(compTextView2.getNextFocusLeftId(), parsedTextView2.getNextFocusLeftId());
                    assertPositive(compTextView2.getNextFocusRightId());
                    assertEquals(compTextView2.getNextFocusRightId(), parsedTextView2.getNextFocusRightId());
                    assertPositive(compTextView2.getNextFocusUpId());
                    assertEquals(compTextView2.getNextFocusUpId(), parsedTextView2.getNextFocusUpId());
                    // No puedo testear android:onClick porque no hay get nativo asociado
                    assertEquals(compTextView2.getPaddingLeft(), ValueUtil.dpToPixelIntRound(10.3f, res));
                    assertEquals(compTextView2.getPaddingLeft(), parsedTextView2.getPaddingLeft());
                    assertEquals(compTextView2.getPaddingRight(), ValueUtil.dpToPixelIntRound(11.3f, res));
                    assertEquals(compTextView2.getPaddingRight(), parsedTextView2.getPaddingRight());
                    assertEquals(compTextView2.getPaddingTop(), ValueUtil.dpToPixelIntRound(12.3f, res));
                    assertEquals(compTextView2.getPaddingTop(), parsedTextView2.getPaddingTop());
                    assertEquals(compTextView2.getPaddingBottom(), ValueUtil.dpToPixelIntRound(13.3f, res));
                    assertEquals(compTextView2.getPaddingBottom(), parsedTextView2.getPaddingBottom());
                    assertEquals(compTextView2.getRotation(), 10.5f);
                    assertEquals(compTextView2.getRotation(), parsedTextView2.getRotation());
                    assertEquals(compTextView2.getRotationX(), 45.5f);
                    assertEquals(compTextView2.getRotationX(), parsedTextView2.getRotationX());
                    assertEquals(compTextView2.getRotationY(), 10.5f);
                    assertEquals(compTextView2.getRotationY(), parsedTextView2.getRotationY());
                    assertFalse(compTextView2.isSaveEnabled());
                    assertEquals(compTextView2.isSaveEnabled(), parsedTextView2.isSaveEnabled());
                    assertEquals(compTextView2.getScaleX(), 1.2f);
                    assertEquals(compTextView2.getScaleX(), parsedTextView2.getScaleX());
                    assertEquals(compTextView2.getScaleY(), 1.2f);
                    assertEquals(compTextView2.getScaleY(), parsedTextView2.getScaleY());


                    assertFalse(compTextView2.isSoundEffectsEnabled());
                    assertEquals(compTextView2.isSoundEffectsEnabled(), parsedTextView2.isSoundEffectsEnabled());
                    assertEquals((String) compTextView2.getTag(), "theTag");
                    assertEquals((String) compTextView2.getTag(), (String) parsedTextView2.getTag());

                    /*
                    final int TEXT_ALIGNMENT_MASK_SHIFT = 13;
                    final int TEXT_ALIGNMENT_MASK = 0x00000007 << TEXT_ALIGNMENT_MASK_SHIFT;

                    int compPrivateFlags2 = (Integer)TestUtil.getField(compTextView2,View.class,"mPrivateFlags2");
                    int compTextAlignment = (compPrivateFlags2 & TEXT_ALIGNMENT_MASK) >> TEXT_ALIGNMENT_MASK_SHIFT;
                    assertEquals(compTextAlignment, 4 ); // center
                    */

                    assertEquals(compTextView2.getPivotX(), ValueUtil.dpToPixelFloatFloor(70.3f, res));
                    assertEquals(compTextView2.getPivotX(), parsedTextView2.getPivotX());
                    assertEquals(compTextView2.getPivotY(), ValueUtil.dpToPixelFloatFloor(10.3f, res));
                    assertEquals(compTextView2.getPivotY(), parsedTextView2.getPivotY());
                    assertEquals(compTextView2.getTranslationX(), ValueUtil.dpToPixelFloatFloor(10.3f, res));
                    assertEquals(compTextView2.getTranslationX(), parsedTextView2.getTranslationX());
                    assertEquals(compTextView2.getTranslationY(), ValueUtil.dpToPixelFloatFloor(10.3f, res));
                    assertEquals(compTextView2.getTranslationY(), parsedTextView2.getTranslationY());
                }
            }

        }

        // Test AnalogClock
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test AnalogClock");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final AnalogClock compLayout = (AnalogClock) comp.getChildAt(childCount);
            final AnalogClock parsedLayout = (AnalogClock) parsed.getChildAt(childCount);

            // android:dial
            assertNotNull((Drawable) TestUtil.getField(compLayout, "mDial"));
            assertEquals((Drawable)TestUtil.getField(compLayout,"mDial"),(Drawable)TestUtil.getField(parsedLayout,"mDial"));

            // android:hand_hour
            assertNotNull((Drawable) TestUtil.getField(compLayout, "mHourHand"));
            assertEquals((Drawable)TestUtil.getField(compLayout, "mHourHand"),(Drawable)TestUtil.getField(parsedLayout, "mHourHand"));

            // android:hand_minute
            assertNotNull((Drawable) TestUtil.getField(compLayout, "mMinuteHand"));
            assertEquals((Drawable)TestUtil.getField(compLayout,"mMinuteHand"),(Drawable)TestUtil.getField(parsedLayout,"mMinuteHand"));
        }

        // Test ImageView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ImageView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final ImageView compLayout = (ImageView) comp.getChildAt(childCount);
            final ImageView parsedLayout = (ImageView) parsed.getChildAt(childCount);

            // android:adjustViewBounds (método get es Level 16)
            assertTrue((Boolean) TestUtil.getField(compLayout, "mAdjustViewBounds"));
            assertEquals((Boolean) TestUtil.getField(compLayout, "mAdjustViewBounds"), (Boolean) TestUtil.getField(parsedLayout, "mAdjustViewBounds"));

            assertEquals(compLayout.getBaseline(), ValueUtil.dpToPixelIntFloor(40.3f, res));
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    assertEquals(compLayout.getBaseline(), parsedLayout.getBaseline());
                }
            });

            assertTrue(compLayout.getBaselineAlignBottom());
            assertEquals(compLayout.getBaselineAlignBottom(), parsedLayout.getBaselineAlignBottom());

            assertTrue((Boolean) TestUtil.getField(compLayout, "mCropToPadding"));
            assertEquals((Boolean) TestUtil.getField(compLayout, "mCropToPadding"), (Boolean) TestUtil.getField(parsedLayout, "mCropToPadding"));

            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxHeight"), ValueUtil.dpToPixelIntRound(1000.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxHeight"), (Integer) TestUtil.getField(parsedLayout, "mMaxHeight"));

            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"),ValueUtil.dpToPixelIntRound(1000.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"), (Integer) TestUtil.getField(parsedLayout, "mMaxWidth"));

            assertEquals(compLayout.getScaleType().ordinal(), ImageView.ScaleType.CENTER_INSIDE.ordinal());
            assertEquals(compLayout.getScaleType().ordinal(), parsedLayout.getScaleType().ordinal());

            // android:src (no tiene método get)
            assertNotNull((Drawable) TestUtil.getField(compLayout, "mDrawable"));
            assertEquals((Drawable) TestUtil.getField(compLayout, "mDrawable"), (Drawable) TestUtil.getField(parsedLayout, "mDrawable"));

            // android:tint (no tiene método get)
            if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP) // LOLLIPOP = 21
            {
                // No hay manera de comparar dos PorterDuffColorFilter, si no define el hint devuelve null por lo que algo es algo
                assertNotNull(((PorterDuffColorFilter) TestUtil.getField(compLayout, "mColorFilter"))); // 0x55eeee55
                assertNotNull(((PorterDuffColorFilter) TestUtil.getField(parsedLayout, "mColorFilter")));
            }
            else
            {
                // A partir de Lollipop via XML no se define el tint con setColorFilter() sino de otra forma
                assertEquals((PorterDuff.Mode)TestUtil.callGetMethod(compLayout, "getImageTintMode"),PorterDuff.Mode.SRC_ATOP);
                assertEquals((PorterDuff.Mode)TestUtil.callGetMethod(compLayout, "getImageTintMode"), (PorterDuff.Mode) TestUtil.callGetMethod(parsedLayout, "getImageTintMode"));

                assertEquals((ColorStateList) TestUtil.callGetMethod(compLayout, "getImageTintList"),ColorStateList.valueOf(0x55eeee55));
                assertEquals((ColorStateList) TestUtil.callGetMethod(compLayout, "getImageTintList"), (ColorStateList) TestUtil.callGetMethod(parsedLayout, "getImageTintList"));
            }
        }

        // Test ProgressBar
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ProgressBar");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // Test ProgressBar (indeterminate)
        {
            childCount++;

            final ProgressBar compLayout = (ProgressBar) comp.getChildAt(childCount);
            final ProgressBar parsedLayout = (ProgressBar) parsed.getChildAt(childCount);

            assertTrue(compLayout.isIndeterminate());
            assertEquals(compLayout.isIndeterminate(), parsedLayout.isIndeterminate());

            // android:indeterminateBehavior
            assertEquals((Integer) TestUtil.getField(compLayout, "mBehavior"), 2);
            assertEquals((Integer) TestUtil.getField(compLayout, "mBehavior"), (Integer) TestUtil.getField(parsedLayout, "mBehavior"));

            assertNotNull((LayerDrawable) compLayout.getIndeterminateDrawable());
            assertEquals((LayerDrawable)compLayout.getIndeterminateDrawable(),(LayerDrawable)parsedLayout.getIndeterminateDrawable());

            // android:indeterminateDuration
            assertEquals((Integer) TestUtil.getField(compLayout, "mDuration"), 6000);
            assertEquals((Integer) TestUtil.getField(compLayout, "mDuration"), (Integer) TestUtil.getField(parsedLayout, "mDuration"));

            // android:indeterminateOnly
            assertTrue((Boolean) TestUtil.getField(compLayout, "mOnlyIndeterminate"));
            assertEquals((Boolean) TestUtil.getField(compLayout,"mOnlyIndeterminate"), (Boolean) TestUtil.getField(parsedLayout, "mOnlyIndeterminate"));

            // android:interpolator
            // LinearInterpolator no tiene atributos, simplemente el valor suministrado es devuelto como tal por lo que
            // todos los objetos LinearInterpolator son iguales funcionalmente aunque no sean iguales como instancia
            // testear la no nulidad y el tipo es suficiente
            // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/view/animation/LinearInterpolator.java?av=f
            assertNotNull((LinearInterpolator)compLayout.getInterpolator());
            assertNotNull((LinearInterpolator)parsedLayout.getInterpolator());
        }

        // Test ProgressBar (determinate)
        {
            childCount++;

            final ProgressBar compLayout = (ProgressBar) comp.getChildAt(childCount);
            final ProgressBar parsedLayout = (ProgressBar) parsed.getChildAt(childCount);

            assertEquals(compLayout.getMax(), 90);
            assertEquals(compLayout.getMax(),parsedLayout.getMax());

            assertEquals((Integer) TestUtil.getField(compLayout,"mMaxHeight"),ValueUtil.dpToPixelIntRound(30.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout,"mMaxHeight"), (Integer) TestUtil.getField(parsedLayout, "mMaxHeight"));

            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"), ValueUtil.dpToPixelIntRound(30.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"), (Integer) TestUtil.getField(parsedLayout, "mMaxWidth"));

            assertEquals((Integer) TestUtil.getField(compLayout,"mMinHeight"),ValueUtil.dpToPixelIntRound(20.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout,"mMinHeight"), (Integer) TestUtil.getField(parsedLayout, "mMinHeight"));

            assertEquals((Integer) TestUtil.getField(compLayout, "mMinWidth"), ValueUtil.dpToPixelIntRound(20.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout,"mMinWidth"), (Integer) TestUtil.getField(parsedLayout, "mMinWidth"));

            assertEquals(compLayout.getProgress(),30);
            assertEquals(compLayout.getProgress(),parsedLayout.getProgress());

            assertNotNull((LayerDrawable)compLayout.getProgressDrawable());
            assertEquals((LayerDrawable)compLayout.getProgressDrawable(),(LayerDrawable)parsedLayout.getProgressDrawable());

            assertEquals(compLayout.getSecondaryProgress(), 50);
            assertEquals(compLayout.getSecondaryProgress(), parsedLayout.getSecondaryProgress());
        }

        // Test RatingBar
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test RatingBar");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final RatingBar compLayout = (RatingBar) comp.getChildAt(childCount);
            final RatingBar parsedLayout = (RatingBar) parsed.getChildAt(childCount);

            assertFalse(compLayout.isIndicator());
            assertEquals(compLayout.isIndicator(),parsedLayout.isIndicator());

            assertEquals(compLayout.getNumStars(),6);
            assertEquals(compLayout.getNumStars(),parsedLayout.getNumStars());

            assertEquals(compLayout.getRating(),5.25f);
            assertEquals(compLayout.getRating(),parsedLayout.getRating());

            assertEquals(compLayout.getStepSize(),0.75f);
            assertEquals(compLayout.getStepSize(),parsedLayout.getStepSize());
        }

         // Test SeekBar
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test SeekBar");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final SeekBar compLayout = (SeekBar) comp.getChildAt(childCount);
            final SeekBar parsedLayout = (SeekBar) parsed.getChildAt(childCount);

            // Test android:thumb
            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, AbsSeekBar.class, "mThumb"));
            assertEquals((StateListDrawable) TestUtil.getField(parsedLayout, AbsSeekBar.class, "mThumb"),(StateListDrawable) TestUtil.getField(parsedLayout, AbsSeekBar.class, "mThumb"));
        }

        // Test TextView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test TextView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // Test TextView 1
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            // Test android:autoLink
            assertEquals(compLayout.getAutoLinkMask() & 0x01,0x01); // web
            assertEquals(compLayout.getAutoLinkMask() & 0x02,0x02); // email
            assertEquals(compLayout.getAutoLinkMask(),0x03); // web|email
            assertEquals(compLayout.getAutoLinkMask(),parsedLayout.getAutoLinkMask());

            // Test android:bufferType
            assertEquals((TextView.BufferType)TestUtil.getField(compLayout,"mBufferType"),TextView.BufferType.EDITABLE);
            assertEquals((TextView.BufferType)TestUtil.getField(compLayout,"mBufferType"),(TextView.BufferType)TestUtil.getField(parsedLayout,"mBufferType"));

            // Test android:cursorVisible
            assertTrue((Boolean) TestUtil.callGetMethod(compLayout,"isCursorVisible"));
            assertEquals((Boolean)TestUtil.callGetMethod(compLayout,"isCursorVisible"),(Boolean)TestUtil.callGetMethod(parsedLayout,"isCursorVisible"));


            // Test android:drawableBottom,android:drawableLeft,android:drawableRight,android:drawableTop
            assertEquals(compLayout.getCompoundDrawables().length, 4);
            Drawable[] compDrawArr = compLayout.getCompoundDrawables();
            Drawable[] parsedDrawArr = parsedLayout.getCompoundDrawables();
            for(int i = 0; i < 4; i++)
            {
                assertEquals(compDrawArr[i],parsedDrawArr[i]);
            }

            // Test android:drawableStart,android:drawableEnd
            // Visualmente no se pueden probar si no es en modo RTL y API 17
            // LO dejamos para cuando el level minimo sea API 17
            /*
            FieldContainer<Object> fieldDrawables = new FieldContainer<Object>(TextView.class,"mDrawables");
            Class classDrawables = fieldDrawables.getField().getType();
            String[] fieldMemberNames = new String[]{"mDrawableStart", "mDrawableEnd"};
            FieldContainer<Drawable>[] fieldMemberDrawables = (FieldContainer<Drawable>[])new FieldContainer[fieldMemberNames.length];
            for (int i = 0; i < fieldMemberNames.length; i++) {
                fieldMemberDrawables[i] = new FieldContainer(classDrawables, fieldMemberNames[i]);
            }
            assertEquals(fieldMemberDrawables[0].get(compLayout),fieldMemberDrawables[0].get(parsedLayout));
            assertEquals(fieldMemberDrawables[1].get(compLayout),fieldMemberDrawables[1].get(parsedLayout));
            */

            // Test android:drawablePadding
            assertEquals(compLayout.getCompoundDrawablePadding(),ValueUtil.dpToPixelIntRound(10.3f, res));
            assertEquals(compLayout.getCompoundDrawablePadding(),parsedLayout.getCompoundDrawablePadding());

            assertEquals(compLayout.getEllipsize(), TextUtils.TruncateAt.MARQUEE);
            assertEquals(compLayout.getEllipsize(),parsedLayout.getEllipsize());

            // Test android:ems  Cuando se define llamando setEms(int) se definen también con el mismo valor minEms y maxEms
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"), 50);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"),(Integer)TestUtil.getField(parsedLayout, "mMinWidth"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"), 50);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"),(Integer)TestUtil.getField(parsedLayout, "mMaxWidth"));

            // Test android:fontFamily
            assertEquals(compLayout.getTypeface().getStyle(),parsedLayout.getTypeface().getStyle());

            assertTrue(compLayout.getFreezesText());
            assertEquals(compLayout.getFreezesText(),parsedLayout.getFreezesText());

            // Tests android:gravity
            assertEquals(compLayout.getGravity(),Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            assertEquals(compLayout.getGravity(),parsedLayout.getGravity());

            /* No testeamos android:height porque se pisa con android:ems
            assertEquals(compLayout.getHeight(),ValueUtil.dpToPixelIntRound(45,res));
            assertEquals(compLayout.getHeight(),parsedLayout.getHeight());
            */

            assertEquals(compLayout.getHint(),"Hint Text Test");
            assertEquals(compLayout.getHint(),parsedLayout.getHint());

            assertEquals(compLayout.getImeActionId(),0x00000002);
            assertEquals(compLayout.getImeActionId(),parsedLayout.getImeActionId());

            assertEquals(compLayout.getImeActionLabel(),"Gojm");
            assertEquals(compLayout.getImeActionLabel(),parsedLayout.getImeActionLabel());

            assertEquals(compLayout.getImeOptions(),EditorInfo.IME_ACTION_GO|EditorInfo.IME_ACTION_SEARCH);
            assertEquals(compLayout.getImeOptions(),parsedLayout.getImeOptions());

            // Test android:includeFontPadding
            assertFalse((Boolean) TestUtil.getField(compLayout, "mIncludePad"));
            assertEquals((Boolean)TestUtil.getField(compLayout, "mIncludePad"),(Boolean)TestUtil.getField(parsedLayout, "mIncludePad"));

            assertEquals(compLayout.getInputType(), InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            assertEquals(compLayout.getInputType(), parsedLayout.getInputType());

            // Test android:lineSpacingExtra
            assertEquals((Float) TestUtil.getField(compLayout, "mSpacingAdd"), ValueUtil.dpToPixelFloatRound(5.3f, res));
            assertEquals((Float) TestUtil.getField(compLayout, "mSpacingAdd"), (Float) TestUtil.getField(parsedLayout, "mSpacingAdd"));

            // Test android:lineSpacingMultiplier
            assertEquals((Float)TestUtil.getField(compLayout, "mSpacingMult"),1.2f);
            assertEquals((Float)TestUtil.getField(compLayout, "mSpacingMult"),(Float)TestUtil.getField(parsedLayout, "mSpacingMult"));

            // Test android:lines
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),(Integer)TestUtil.getField(parsedLayout, "mMaximum"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinimum"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinimum"),(Integer)TestUtil.getField(parsedLayout, "mMinimum"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),1); // modo LINES = 1
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),(Integer)TestUtil.getField(parsedLayout, "mMaxMode"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinMode"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinMode"),(Integer)TestUtil.getField(parsedLayout, "mMinMode"));

            assertFalse(compLayout.getLinksClickable());
            assertEquals(compLayout.getLinksClickable(), parsedLayout.getLinksClickable());

            assertEquals((Integer)TestUtil.getField(compLayout, "mMarqueeRepeatLimit"),-1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMarqueeRepeatLimit"),(Integer)TestUtil.getField(parsedLayout, "mMarqueeRepeatLimit"));

            // Test android:maxEms
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"), 50);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"),(Integer)TestUtil.getField(parsedLayout, "mMaxWidth"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidthMode"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidthMode"),(Integer)TestUtil.getField(parsedLayout, "mMaxWidthMode"));

            // Test android:maxLength
            assertEquals((InputFilter.LengthFilter)compLayout.getFilters()[0],new InputFilter.LengthFilter(1000));
            assertEquals((InputFilter.LengthFilter)compLayout.getFilters()[0],(InputFilter.LengthFilter)parsedLayout.getFilters()[0]);

            // Test android:maxLines
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),(Integer)TestUtil.getField(parsedLayout, "mMaximum"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),1); // modo LINES = 1
            assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),(Integer)TestUtil.getField(parsedLayout, "mMaxMode"));

            // Test android:minEms
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"), 50);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"),(Integer)TestUtil.getField(parsedLayout, "mMinWidth"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidthMode"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidthMode"),(Integer)TestUtil.getField(parsedLayout, "mMinWidthMode"));

            // Test android:minLines
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinimum"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinimum"),(Integer)TestUtil.getField(parsedLayout, "mMinimum"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinMode"),1);
            assertEquals((Integer)TestUtil.getField(compLayout, "mMinMode"),(Integer)TestUtil.getField(parsedLayout, "mMinMode"));

            assertEquals(compLayout.getPrivateImeOptions(), "com.example.myapp.JustToWriteSomething=3");
            assertEquals(compLayout.getPrivateImeOptions(), parsedLayout.getPrivateImeOptions());

            // android:scrollHorizontally
            assertTrue((Boolean) TestUtil.getField(compLayout, "mHorizontallyScrolling"));
            assertEquals((Boolean)TestUtil.getField(compLayout,"mHorizontallyScrolling"),(Boolean)TestUtil.getField(parsedLayout, "mHorizontallyScrolling"));

            // Test android:selectAllOnFocus
            // mSelectAllOnFocus está dentro del atributo mEditor (android.widget.Editor) desde 4.1 (API 16)
            Object compEditor = TestUtil.getField(compLayout,"mEditor");
            Object parsedEditor = TestUtil.getField(parsedLayout,"mEditor");

            assertTrue((Boolean) TestUtil.getField(compEditor, "mSelectAllOnFocus"));
            assertEquals((Boolean) TestUtil.getField(compEditor, "mSelectAllOnFocus"), (Boolean) TestUtil.getField(parsedEditor, "mSelectAllOnFocus"));


            // Test android:shadowColor
            // A partir de la versión 16 hay un método getShadowColor(), en teoría se podría seguir usando el atributo interno shadowColor de Paint pero en Level 21 (Lollipop) desaparece, usar el método desde level 16 es la mejor opción

            assertEquals((Integer)TestUtil.callGetMethod(compLayout,"getShadowColor"),0xffff0000);
            assertEquals((Integer)TestUtil.callGetMethod(compLayout,"getShadowColor"),(Integer) TestUtil.callGetMethod(parsedLayout,"getShadowColor"));


            // Test android:shadowDx
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowDx"),1.1f);
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowDx"),(Float)TestUtil.getField(parsedLayout,"mShadowDx"));

            // Test android:shadowDy
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowDy"),1.2f);
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowDy"),(Float)TestUtil.getField(parsedLayout,"mShadowDy"));

            // Test android:shadowRadius
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowRadius"),1.3f);
            assertEquals((Float)TestUtil.getField(compLayout,"mShadowRadius"),(Float)TestUtil.getField(parsedLayout,"mShadowRadius"));

            // Test android:singleLine
            assertTrue((Boolean)TestUtil.getField(compLayout,"mSingleLine"));
            assertEquals((Boolean)TestUtil.getField(compLayout,"mSingleLine"),(Boolean)TestUtil.getField(parsedLayout,"mSingleLine"));


            // Test android:text
            // El inputType influye en el tipo de objeto de texto
            assertEquals((SpannableStringBuilder)compLayout.getText(),new SpannableStringBuilder("TextView Tests 1 (this text is cut on the right)"));
            assertEquals(compLayout.getText(),parsedLayout.getText());

            // Test: android:textAllCaps no he conseguido que funcione ni en modo compilado en este test
            // pero los TransformationMethod parecen correctos
            TransformationMethod comp_trans = compLayout.getTransformationMethod();
            TransformationMethod parsed_trans = parsedLayout.getTransformationMethod();
            assertEquals(comp_trans.getClass().getName(),"android.text.method.AllCapsTransformationMethod");
            assertEquals(comp_trans.getClass().getName(),parsed_trans.getClass().getName());


            // Test: android:textColor
            assertEquals(compLayout.getTextColors().getDefaultColor(),0xff00ff00);
            assertEquals(compLayout.getTextColors(), parsedLayout.getTextColors());

            // Test android:textColorHighlight
            assertEquals((Integer)TestUtil.getField(compLayout,"mHighlightColor"),0xff0000ff);
            assertEquals((Integer)TestUtil.getField(compLayout,"mHighlightColor"),(Integer)TestUtil.getField(parsedLayout,"mHighlightColor"));

            // Test android:textColorHint
            assertEquals(compLayout.getHintTextColors().getDefaultColor(),0xff00ff00);
            assertEquals(compLayout.getHintTextColors(), parsedLayout.getHintTextColors());

            assertFalse(compLayout.isTextSelectable());
            assertEquals(compLayout.isTextSelectable(), parsedLayout.isTextSelectable());

            assertEquals(compLayout.getTextScaleX(),1.2f);
            assertEquals(compLayout.getTextScaleX(), parsedLayout.getTextScaleX());

            assertEquals(compLayout.getTextSize(),ValueUtil.dpToPixelFloatRound(15.3f, res));
            assertEquals(compLayout.getTextSize(), parsedLayout.getTextSize());

            // Test android:textStyle y typeface
            int NORMAL = 0, BOLD = 1, ITALIC = 2;

            Typeface compTf = compLayout.getTypeface();
            Typeface parsedTf = parsedLayout.getTypeface();
            assertEquals(compTf.getStyle(),BOLD | ITALIC);
            assertEquals(compTf.getStyle(),parsedTf.getStyle());
            if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP)
                assertEquals((Integer)TestUtil.getField(compTf,"native_instance"),(Integer)TestUtil.getField(parsedTf,"native_instance"));
            else // A partir de Lollipop (level 21) es un long
                assertEquals((Long)TestUtil.getField(compTf,"native_instance"),(Long)TestUtil.getField(parsedTf,"native_instance"));
        }

        // Test TextView 2
        // Se testean de nuevo algunos atributos y otros que no podían testearse antes
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            // Test android:textStyle y fontFamily
            int NORMAL = 0, BOLD = 1, ITALIC = 2;

            Typeface compTf = compLayout.getTypeface(); // sans-serif
            Typeface parsedTf = parsedLayout.getTypeface();

            assertEquals(compTf.getStyle(),NORMAL | BOLD | ITALIC);
            assertEquals(compTf.getStyle(),parsedTf.getStyle());
            if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP)
                assertEquals((Integer)TestUtil.getField(compTf,"native_instance"),(Integer)TestUtil.getField(parsedTf,"native_instance"));
            else // A partir de Lollipop (level 21) es un long
                assertEquals((Long)TestUtil.getField(compTf,"native_instance"),(Long)TestUtil.getField(parsedTf,"native_instance"));


            assertEquals((SpannableString)compLayout.getText(),new SpannableString("TextView Tests 2 (this text is much more longer and is cut on the right with ellipsis points)"));
            assertEquals(compLayout.getText(),parsedLayout.getText());

            // Test android:bufferType
            // Repetimos este test con SPANNABLE porque en el anterior por alguna razón se cambiaba a EDITABLE
            assertEquals((TextView.BufferType)TestUtil.getField(compLayout,"mBufferType"),TextView.BufferType.SPANNABLE);
            assertEquals((TextView.BufferType)TestUtil.getField(compLayout,"mBufferType"),(TextView.BufferType)TestUtil.getField(parsedLayout,"mBufferType"));

            assertEquals(compLayout.getEllipsize(), TextUtils.TruncateAt.END);
            assertEquals(compLayout.getEllipsize(),parsedLayout.getEllipsize());

            // Test android:height, android:maxHeight
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    // getHeight() no se corresponde exactamente con setHeight(int) pero el resultado es coherente (NO CON PRECISION)
                    //assertEquals(compLayout.getHeight(), ValueUtil.dpToPixelIntRound(30.3f, res));
                    //assertEquals(compLayout.getHeight(), parsedLayout.getHeight());

                    // Via mMinWidth y mMaximum testeamos indirectamente el android:height, la discordancia es por el setHeight() que no usa internamente Android (mMinHeight es 0 en parsedLayout)
                    assertEquals((Integer)TestUtil.getField(compLayout,View.class,"mMinHeight"), ValueUtil.dpToPixelIntRound(30.3f, res));
                    assertEquals((Integer)TestUtil.getField(compLayout,View.class,"mMinHeight"), (Integer)TestUtil.getField(parsedLayout,"mMaximum"));

                    // Estos tests no funcionan porque mi impresión es que layout_height="30dp" define la altura por su cuenta pero
                    // no como PIXELS sino como LINES en el layout compilado
                    //assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),2); // modo PIXELS = 2
                    //assertEquals((Integer)TestUtil.getField(compLayout, "mMaxMode"),(Integer)TestUtil.getField(parsedLayout, "mMaxMode"));
                    //assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),ValueUtil.dpToPixelIntRound(30,res));
                    //assertEquals((Integer)TestUtil.getField(compLayout, "mMaximum"),(Integer)TestUtil.getField(parsedLayout, "mMaximum"));
                }
            });

            // Test android:width, android:maxWidth
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    // Ver notas de android:height, android:maxHeight
                    //assertEquals(compLayout.getWidth(), ValueUtil.dpToPixelIntRound(200.3f, res));
                    //assertEquals(compLayout.getWidth(), parsedLayout.getWidth());

                    // Via mMinWidth testeamos indirectamente el android:width
                    assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"), ValueUtil.dpToPixelIntRound(200.3f, res));
                    assertEquals((Integer)TestUtil.getField(compLayout, "mMinWidth"), (Integer)TestUtil.getField(parsedLayout, "mMinWidth"));

                    assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"), ValueUtil.dpToPixelIntRound(200.3f, res));
                    assertEquals((Integer)TestUtil.getField(compLayout, "mMaxWidth"), (Integer)TestUtil.getField(parsedLayout, "mMaxWidth"));

                }
            });

            // Test: android:textAllCaps
            TransformationMethod comp_trans = compLayout.getTransformationMethod();
            TransformationMethod parsed_trans = parsedLayout.getTransformationMethod();
            assertEquals(comp_trans.getClass().getName(), "android.text.method.AllCapsTransformationMethod");
            assertEquals(comp_trans.getClass().getName(),parsed_trans.getClass().getName());

            assertTrue(compLayout.isTextSelectable());
            assertEquals(compLayout.isTextSelectable(),parsedLayout.isTextSelectable());


            // Test android:singleLine
            assertTrue((Boolean) TestUtil.getField(compLayout, "mSingleLine"));
            assertEquals((Boolean)TestUtil.getField(compLayout,"mSingleLine"),(Boolean)TestUtil.getField(parsedLayout,"mSingleLine"));
        }

        // Test TextView 3-1 (textAppearance y hint)
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getHint(),"Hint Text (TextView Tests 3-1)");
            assertEquals(compLayout.getHint(),parsedLayout.getHint());

            // Test del textAppearance style incluyendo el parent="...", el size explícito substituye el definido en el parent

            assertEquals(compLayout.getTextSize(),ValueUtil.dpToPixelFloatRound(21.3f, res));
            assertEquals(compLayout.getTextSize(), parsedLayout.getTextSize());
        }

        // Test TextView 3-2 (textAppearance)
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(),"TextView Tests 3-2 (text color=red,size=small)");
            assertEquals(compLayout.getText(), parsedLayout.getText());

            // Test del <style> incluyendo el parent="..."
            int[] attrs = {android.R.attr.textSize};
            TypedArray ta = ctx.obtainStyledAttributes(ctx.getResources().getIdentifier("@android:style/TextAppearance.DeviceDefault.Small", null, null), attrs);
            float textSize = ta.getDimension(0, 0);
            ta.recycle();
            // El textSize se define haciendo un float-round, eso mismo tenemos que hacer con el default de TextAppearance:
            textSize = (int)(textSize + 0.5f);
            assertEquals(compLayout.getTextSize(), textSize);
            assertEquals(compLayout.getTextSize(), parsedLayout.getTextSize());

            assertEquals(compLayout.getCurrentTextColor(), 0xFFFF0000);
            assertEquals(compLayout.getCurrentTextColor(),parsedLayout.getCurrentTextColor());
        }

        // CompoundButton Tests (a través de CheckBox)
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test CompoundButton via CheckBox");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final CheckBox compLayout = (CheckBox) comp.getChildAt(childCount);
            final CheckBox parsedLayout = (CheckBox) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(),"CompoundButton Tests");
            assertEquals(compLayout.getText(),parsedLayout.getText());

            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, CompoundButton.class, "mButtonDrawable"));
            assertEquals((StateListDrawable)TestUtil.getField(compLayout,CompoundButton.class,"mButtonDrawable"),(StateListDrawable)TestUtil.getField(parsedLayout,CompoundButton.class,"mButtonDrawable"));

            assertTrue(compLayout.isChecked());
            assertEquals(compLayout.isChecked(),parsedLayout.isChecked());

        }

        // Switch Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test Switch");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final Switch compLayout = (Switch) comp.getChildAt(childCount);
            final Switch parsedLayout = (Switch) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(),"Switch Tests");
            assertEquals(compLayout.getText(),parsedLayout.getText());

            // android:switchMinWidth
            assertEquals((Integer) TestUtil.getField(compLayout, "mSwitchMinWidth"), ValueUtil.dpToPixelIntRound(150.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout,"mSwitchMinWidth"),(Integer)TestUtil.getField(parsedLayout,"mSwitchMinWidth"));

            // android:switchPadding
            assertEquals((Integer)TestUtil.getField(compLayout,"mSwitchPadding"),ValueUtil.dpToPixelIntRound(30.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout,"mSwitchPadding"),(Integer)TestUtil.getField(parsedLayout,"mSwitchPadding"));

            // android:switchTextAppearance
            // No tenemos una forma de testear "switchTextAppearanceLarge" de forma directa, una forma es testear una de las propiedades que impone, ej el tamaño del texto
            Paint compTextPaint = (Paint)TestUtil.getField(compLayout,"mTextPaint");
            Paint parsedTextPaint = (Paint)TestUtil.getField(parsedLayout,"mTextPaint");
            assertEquals((Float)TestUtil.callMethod(compTextPaint,null,Paint.class,"getTextSize",null),(Float)TestUtil.callMethod(parsedTextPaint,null,Paint.class,"getTextSize",null));

            assertEquals(compLayout.getTextOff(),"NORL");
            assertEquals(compLayout.getTextOff(),parsedLayout.getTextOff());

            assertEquals(compLayout.getTextOn(),"YESRL");
            assertEquals(compLayout.getTextOn(),parsedLayout.getTextOn());

            // Test android:textStyle y android:typeface

            int NORMAL = 0, BOLD = 1, ITALIC = 2;

            Typeface compTf = compLayout.getTypeface();
            Typeface parsedTf = parsedLayout.getTypeface();
            assertEquals(compTf.getStyle(),BOLD | ITALIC);
            assertEquals(compTf.getStyle(),parsedTf.getStyle());
            if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP)
                assertEquals((Integer)TestUtil.getField(compTf,"native_instance"),(Integer)TestUtil.getField(parsedTf,"native_instance"));
            else // A partir de Lollipop (level 21) es un long
                assertEquals((Long)TestUtil.getField(compTf,"native_instance"),(Long)TestUtil.getField(parsedTf,"native_instance"));

            // Test android:thumb
            assertNotNull((StateListDrawable) TestUtil.getField(compLayout,"mThumbDrawable"));
            assertEquals((StateListDrawable) TestUtil.getField(parsedLayout,"mThumbDrawable"),(StateListDrawable) TestUtil.getField(parsedLayout,"mThumbDrawable"));

            // Test android:thumbTextPadding
            assertEquals((Integer) TestUtil.getField(compLayout, "mThumbTextPadding"), ValueUtil.dpToPixelIntRound(20.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout,"mThumbTextPadding"),(Integer)TestUtil.getField(parsedLayout,"mThumbTextPadding"));

            // Test android:track
            assertNotNull((StateListDrawable) TestUtil.getField(compLayout,"mTrackDrawable"));
            assertEquals((StateListDrawable) TestUtil.getField(parsedLayout,"mTrackDrawable"),(StateListDrawable) TestUtil.getField(parsedLayout,"mTrackDrawable"));

        }

        // ToggleButton Tests
        // Nota: ToggleButton ha sido reemplazado totalmente por Switch, lo implementamos para los despistados
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ToggleButton");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final ToggleButton compLayout = (ToggleButton) comp.getChildAt(childCount);
            final ToggleButton parsedLayout = (ToggleButton) parsed.getChildAt(childCount);

            assertEquals((Float)TestUtil.getField(compLayout,"mDisabledAlpha"),0.6f);
            assertEquals((Float)TestUtil.getField(compLayout,"mDisabledAlpha"),(Float)TestUtil.getField(parsedLayout,"mDisabledAlpha"));

            assertEquals(compLayout.getTextOff(), "NORL");
            assertEquals(compLayout.getTextOff(),parsedLayout.getTextOff());

            assertEquals(compLayout.getTextOn(), "YESRL");
            assertEquals(compLayout.getTextOn(),parsedLayout.getTextOn());
        }


        // CheckedTextView Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test CheckedTextView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }


        {
            childCount++;

            final CheckedTextView compLayout = (CheckedTextView) comp.getChildAt(childCount);
            final CheckedTextView parsedLayout = (CheckedTextView) parsed.getChildAt(childCount);

            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, "mCheckMarkDrawable"));
            assertEquals((StateListDrawable) TestUtil.getField(parsedLayout,"mCheckMarkDrawable"),(StateListDrawable) TestUtil.getField(parsedLayout,"mCheckMarkDrawable"));

            assertTrue(compLayout.isChecked());
            assertEquals(compLayout.isChecked(),parsedLayout.isChecked());
        }

        // Chronometer Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test Chronometer");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final Chronometer compLayout = (Chronometer) comp.getChildAt(childCount);
            final Chronometer parsedLayout = (Chronometer) parsed.getChildAt(childCount);

            assertEquals(compLayout.getFormat(), "Time: %s");
            assertEquals(compLayout.getFormat(),parsedLayout.getFormat());
        }

        // EditText Tests
        // No tiene atributos propios pero nos interesa probar si funciona visualmente inputType
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test EditText");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final EditText compLayout = (EditText) comp.getChildAt(childCount);
            final EditText parsedLayout = (EditText) parsed.getChildAt(childCount);

            assertEquals(compLayout.getImeActionId(),0x00000002);
            assertEquals(compLayout.getImeActionId(),parsedLayout.getImeActionId());

            assertEquals(compLayout.getImeActionLabel(),"Go Next");
            assertEquals(compLayout.getImeActionLabel(),parsedLayout.getImeActionLabel());

            assertEquals(compLayout.getImeOptions(),EditorInfo.IME_ACTION_NEXT);
            assertEquals(compLayout.getImeOptions(),parsedLayout.getImeOptions());

            assertEquals(compLayout.getInputType(), InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            assertEquals(compLayout.getInputType(), parsedLayout.getInputType());

            assertFalse(compLayout.isTextSelectable());
            assertEquals(compLayout.isTextSelectable(), parsedLayout.isTextSelectable());
        }


        // AutoCompleteTextView Tests
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test AutoCompleteTextView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final AutoCompleteTextView compLayout = (AutoCompleteTextView) comp.getChildAt(childCount);
            final AutoCompleteTextView parsedLayout = (AutoCompleteTextView) parsed.getChildAt(childCount);

            // android:completionHint
            assertEquals((CharSequence) TestUtil.getField(compLayout, "mHintText"), "Sports suggested");
            assertEquals((CharSequence) TestUtil.getField(compLayout, "mHintText"), (CharSequence) TestUtil.getField(parsedLayout, "mHintText"));

            // android:completionHintView
            assertPositive((Integer) TestUtil.getField(compLayout, "mHintResource"));
            assertEquals((Integer) TestUtil.getField(compLayout, "mHintResource"), (Integer) TestUtil.getField(parsedLayout, "mHintResource"));

            assertEquals((Integer) TestUtil.getField(compLayout, "mHintResource"),R.layout.auto_complete_text_view_hint_view);
            assertEquals((Integer) TestUtil.getField(compLayout, "mHintResource"),(Integer) TestUtil.getField(parsedLayout, "mHintResource")); // Porque existe el id compilado y tiene prioridad en el caso dinámico


            // android:completionThreshold
            assertEquals((Integer) TestUtil.getField(compLayout, "mThreshold"), 3);
            assertEquals((Integer) TestUtil.getField(compLayout, "mThreshold"), (Integer) TestUtil.getField(parsedLayout, "mThreshold"));


            assertEquals(compLayout.getDropDownAnchor(), res.getIdentifier("id/anchorOfAutoCompleteTextViewDropDownId", null, ctx.getPackageName()));
            assertEquals(compLayout.getDropDownAnchor(),parsedLayout.getDropDownAnchor());

            assertEquals(compLayout.getDropDownHeight(),ValueUtil.dpToPixelIntRound(150.3f, res));
            assertEquals(compLayout.getDropDownHeight(),parsedLayout.getDropDownHeight());

            assertEquals(compLayout.getDropDownHorizontalOffset(),ValueUtil.dpToPixelIntFloor(10.3f, res));
            assertEquals(compLayout.getDropDownHorizontalOffset(), parsedLayout.getDropDownHorizontalOffset());

            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}));
            assertEquals((StateListDrawable) TestUtil.getField(compLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}), (StateListDrawable) TestUtil.getField(parsedLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}));

            assertEquals(compLayout.getDropDownVerticalOffset(), ValueUtil.dpToPixelIntFloor(5.3f, res));
            assertEquals(compLayout.getDropDownVerticalOffset(),parsedLayout.getDropDownVerticalOffset());


            assertEquals(compLayout.getDropDownWidth(),ValueUtil.dpToPixelIntRound(300.3f, res));
            assertEquals(compLayout.getDropDownWidth(),parsedLayout.getDropDownWidth());
        }


        // TextView used as anchor of AutoCompleteTextView Suggest Drop Down (upper View)
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(),"Anchor of AutoCompleteTextView Suggest Drop Down");
            assertEquals(compLayout.getText(),parsedLayout.getText());
        }

        {
            childCount++;

            final AutoCompleteTextView compLayout = (AutoCompleteTextView) comp.getChildAt(childCount);
            final AutoCompleteTextView parsedLayout = (AutoCompleteTextView) parsed.getChildAt(childCount);

            // android:completionHint
            assertEquals((CharSequence) TestUtil.getField(compLayout, "mHintText"), "Sports suggested");
            assertEquals((CharSequence) TestUtil.getField(compLayout, "mHintText"), (CharSequence) TestUtil.getField(parsedLayout, "mHintText"));

            // android:completionHintView

            assertEquals((Integer) TestUtil.getField(compLayout, "mHintResource"), R.layout.auto_complete_text_view_hint_view_compiled);
            assertPositive((Integer) TestUtil.getField(compLayout, "mHintResource")); // El layout por defecto, en este test no se toca mHintResource, se define directamente mHintView

            assertPositive((Integer) TestUtil.getField(parsedLayout, "mHintResource"));

            TextView compHintView = (TextView) TestUtil.getField(compLayout, "mHintView");
            assertEquals(compHintView.getCurrentTextColor(),0xFFFF0000);
            TextView parsedHintView = (TextView) TestUtil.getField(parsedLayout, "mHintView");
            assertEquals(parsedHintView.getCurrentTextColor(),0xFFFF0000);

            // android:completionThreshold
            assertEquals((Integer) TestUtil.getField(compLayout, "mThreshold"), 3);
            assertEquals((Integer) TestUtil.getField(compLayout, "mThreshold"), (Integer) TestUtil.getField(parsedLayout, "mThreshold"));


            assertEquals(compLayout.getDropDownAnchor(), res.getIdentifier("id/anchorOfAutoCompleteTextViewDropDownId2", null, ctx.getPackageName()));
            assertEquals(compLayout.getDropDownAnchor(),parsedLayout.getDropDownAnchor());

            assertEquals(compLayout.getDropDownHeight(),ValueUtil.dpToPixelIntRound(150.3f, res));
            assertEquals(compLayout.getDropDownHeight(),parsedLayout.getDropDownHeight());

            assertEquals(compLayout.getDropDownHorizontalOffset(),ValueUtil.dpToPixelIntFloor(10.3f, res));
            assertEquals(compLayout.getDropDownHorizontalOffset(), parsedLayout.getDropDownHorizontalOffset());

            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}));
            assertEquals((StateListDrawable) TestUtil.getField(compLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}), (StateListDrawable) TestUtil.getField(parsedLayout, new Class[]{AutoCompleteTextView.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownListHighlight"}));

            assertEquals(compLayout.getDropDownVerticalOffset(), ValueUtil.dpToPixelIntFloor(5.3f, res));
            assertEquals(compLayout.getDropDownVerticalOffset(),parsedLayout.getDropDownVerticalOffset());

            assertEquals(compLayout.getDropDownWidth(),ValueUtil.dpToPixelIntRound(300.3f, res));
            assertEquals(compLayout.getDropDownWidth(),parsedLayout.getDropDownWidth());
        }

        // TextView used as anchor of AutoCompleteTextView Suggest Drop Down (upper View)
        {
            childCount++;

            final TextView compLayout = (TextView) comp.getChildAt(childCount);
            final TextView parsedLayout = (TextView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getText(),"Anchor of AutoCompleteTextView Suggest Drop Down 2");
            assertEquals(compLayout.getText(),parsedLayout.getText());
        }

        // Test AdapterViewFlipper y AdapterViewAnimator
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test AdapterViewFlipper (and AdapterViewAnimator)");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final AdapterViewFlipper compLayout = (AdapterViewFlipper) comp.getChildAt(childCount);
            final AdapterViewFlipper parsedLayout = (AdapterViewFlipper) parsed.getChildAt(childCount);

            // AdapterViewAnimator

            assertTrue((Boolean)TestUtil.getField(compLayout,AdapterViewAnimator.class,"mAnimateFirstTime"));
            assertEquals((Boolean) TestUtil.getField(compLayout,AdapterViewAnimator.class,"mAnimateFirstTime"), (Boolean) TestUtil.getField(parsedLayout,AdapterViewAnimator.class,"mAnimateFirstTime"));

            assertNotNull(compLayout.getInAnimation());
            assertEquals(compLayout.getInAnimation(),parsedLayout.getInAnimation());

            assertTrue((Boolean)TestUtil.getField(compLayout,AdapterViewAnimator.class,"mLoopViews"));
            assertEquals((Boolean)TestUtil.getField(compLayout,AdapterViewAnimator.class,"mLoopViews"),(Boolean)TestUtil.getField(parsedLayout,AdapterViewAnimator.class,"mLoopViews"));

            assertNotNull(compLayout.getOutAnimation());
            assertEquals(compLayout.getOutAnimation(),parsedLayout.getOutAnimation());

            // AdapterViewFlipper
            assertTrue(compLayout.isAutoStart());
            assertEquals(compLayout.isAutoStart(),parsedLayout.isAutoStart());

            // android:flipInterval  (getFlipInterval es Level 16)
            assertEquals((Integer)TestUtil.getField(compLayout,"mFlipInterval"),3000);
            assertEquals((Integer)TestUtil.getField(compLayout,"mFlipInterval"),(Integer)TestUtil.getField(parsedLayout,"mFlipInterval"));
        }

        // Test ViewGroup Attribs and ViewGroup.LayoutParams
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ViewGroup attrs and ViewGroup.LayoutParams");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            final LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);

            assertTrue(compLayout.addStatesFromChildren());
            assertEquals(compLayout.addStatesFromChildren(), parsedLayout.addStatesFromChildren());
            assertFalse(compLayout.isAlwaysDrawnWithCacheEnabled());
            assertEquals(compLayout.isAlwaysDrawnWithCacheEnabled(), parsedLayout.isAlwaysDrawnWithCacheEnabled());
            // Test de android:animateLayoutChanges
            // Si animateLayoutChanges="false" getLayoutTransition() devuelve null por lo que el chequear a null es suficiente test
            assertNotNull(compLayout.getLayoutTransition());
            assertNotNull(parsedLayout.getLayoutTransition());

            assertFalse(compLayout.isAnimationCacheEnabled());
            assertEquals(compLayout.isAnimationCacheEnabled(), parsedLayout.isAnimationCacheEnabled());
            // Tests de android:clipChildren (el método get es Level 18)
            assertFalse(((Integer) TestUtil.getField(compLayout, ViewGroup.class, "mGroupFlags") & 0x1) == 0x1); // FLAG_CLIP_CHILDREN = 0x1
            assertEquals( ((int)(Integer)TestUtil.getField(compLayout, ViewGroup.class, "mGroupFlags") & 0x1) == 0x1, ((int)(Integer)TestUtil.getField(parsedLayout, ViewGroup.class, "mGroupFlags") & 0x1) == 0x1 );
            // Tests de android:clipToPadding
            assertFalse(((Integer)TestUtil.getField(compLayout, ViewGroup.class, "mGroupFlags") & 0x2) == 0x2); // FLAG_CLIP_TO_PADDING = 0x2
            assertEquals(((int) (Integer)TestUtil.getField(compLayout, ViewGroup.class, "mGroupFlags") & 0x2) == 0x2, ((int) (Integer)TestUtil.getField(parsedLayout, ViewGroup.class, "mGroupFlags") & 0x2) == 0x2);
            assertEquals(compLayout.getDescendantFocusability(), ViewGroup.FOCUS_AFTER_DESCENDANTS);
            assertEquals(compLayout.getDescendantFocusability(), parsedLayout.getDescendantFocusability());
            assertTrue((compLayout.getLayoutAnimation().getDelay() - 1.0f * 10 / 100) < 0.00001); // Testeamos el delay porque testear la igualdad del LayoutAnimationController es un rollo
            assertEquals(compLayout.getLayoutAnimation().getDelay(), parsedLayout.getLayoutAnimation().getDelay());
            assertEquals(compLayout.getPersistentDrawingCache(), parsedLayout.getPersistentDrawingCache());
            assertTrue(compLayout.isMotionEventSplittingEnabled());
            assertEquals(compLayout.isMotionEventSplittingEnabled(), parsedLayout.isMotionEventSplittingEnabled());

            // Testing ViewGroup.LayoutParams
            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);

                assertEquals(compTextView.getText(),"Test ViewGroup Attribs");
                assertEquals(compTextView.getText(),parsedTextView.getText());

                ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();

                assertEquals(a_params.height, ViewGroup.LayoutParams.WRAP_CONTENT);
                assertEquals(a_params.height, b_params.height);
                assertEquals(a_params.width, ViewGroup.LayoutParams.MATCH_PARENT);
                assertEquals(a_params.width, b_params.width);

                parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {

                        if (oldLeft == 0 && oldTop == 0 && oldRight == 0 && oldBottom == 0)
                        {
                            assertEquals(compTextView.getWidth(), parsedTextView.getWidth());
                            assertEquals(compTextView.getHeight(), parsedTextView.getHeight());
                        }
                        else TestUtil.alertDialog(parsedTextView.getContext(),"Test ignored when rotating the device watching the dynamic view"); // El compilado está en la otra orientación
                    }
                });
            }

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(1);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(1);

                assertEquals(compTextView.getText(),"Test ViewGroup Attribs 2");
                assertEquals(compTextView.getText(),parsedTextView.getText());

                ViewGroup.LayoutParams a_params = compTextView.getLayoutParams();
                ViewGroup.LayoutParams b_params = parsedTextView.getLayoutParams();

                assertEquals(a_params.height, ValueUtil.dpToPixelFloatRound(30.3f,res));
                assertEquals(a_params.height, b_params.height);
                assertEquals(a_params.width, ValueUtil.dpToPixelFloatRound(200.3f,res));
                assertEquals(a_params.width, b_params.width);

                parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        assertEquals(compTextView.getWidth(), parsedTextView.getWidth());
                        assertEquals(compTextView.getHeight(), parsedTextView.getHeight());
                    }
                });
            }


        }

        // Test ViewGroup.MarginLayoutParams
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ViewGroup.MarginLayoutParams");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            LinearLayout compLinLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLinLayout = (LinearLayout) parsed.getChildAt(childCount);

            {
                final TextView compTextView = (TextView) compLinLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLinLayout.getChildAt(0);

                assertEquals(compTextView.getText(), "Test Margins 1");
                assertEquals(compTextView.getText(), parsedTextView.getText());

                ViewGroup.MarginLayoutParams a_params = (ViewGroup.MarginLayoutParams) compTextView.getLayoutParams();
                ViewGroup.MarginLayoutParams b_params = (ViewGroup.MarginLayoutParams) parsedTextView.getLayoutParams();

                assertEquals(a_params.topMargin, ValueUtil.dpToPixelIntRound(15.3f, res));
                assertEquals(a_params.topMargin, b_params.topMargin);
                assertEquals(a_params.leftMargin, ValueUtil.dpToPixelIntRound(10.3f, res));
                assertEquals(a_params.leftMargin, b_params.leftMargin);
                assertEquals(a_params.bottomMargin, ValueUtil.dpToPixelIntRound(5.3f, res));
                assertEquals(a_params.bottomMargin, b_params.bottomMargin);
                assertEquals(a_params.rightMargin, ValueUtil.dpToPixelIntRound(1.3f, res));
                assertEquals(a_params.rightMargin, b_params.rightMargin);
            }

            {
                final TextView compTextView = (TextView) compLinLayout.getChildAt(1);
                final TextView parsedTextView = (TextView) parsedLinLayout.getChildAt(1);

                assertEquals(compTextView.getText(), "Test Margins 2");
                assertEquals(compTextView.getText(), parsedTextView.getText());

                ViewGroup.MarginLayoutParams a_params = (ViewGroup.MarginLayoutParams) compTextView.getLayoutParams();
                ViewGroup.MarginLayoutParams b_params = (ViewGroup.MarginLayoutParams) parsedTextView.getLayoutParams();

                int margin = ValueUtil.dpToPixelIntRound(10.3f, res);
                assertEquals(a_params.topMargin, margin);
                assertEquals(a_params.topMargin, b_params.topMargin);
                assertEquals(a_params.leftMargin, margin);
                assertEquals(a_params.leftMargin, b_params.leftMargin);
                assertEquals(a_params.bottomMargin, margin);
                assertEquals(a_params.bottomMargin, b_params.bottomMargin);
                assertEquals(a_params.rightMargin, margin);
                assertEquals(a_params.rightMargin, b_params.rightMargin);
            }
        }

        // Test AbsListView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test AbsListView (and AdapterView) via ListView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            ListView compLayout = (ListView) comp.getChildAt(childCount);
            ListView parsedLayout = (ListView) parsed.getChildAt(childCount);

            assertEquals(compLayout.getCacheColorHint(),0xffff0000);
            assertEquals(compLayout.getCacheColorHint(), parsedLayout.getCacheColorHint());
            assertEquals(compLayout.getChoiceMode(), AbsListView.CHOICE_MODE_MULTIPLE);
            assertEquals(compLayout.getChoiceMode(), parsedLayout.getChoiceMode());
            // No podemos testear android:drawSelectorOnTop porque no hay un isDrawSelectorOnTop
            assertFalse(compLayout.isFastScrollEnabled()); // Preferiría testear el true pero no se porqué razón se ignora el true
            assertEquals(compLayout.isFastScrollEnabled(), parsedLayout.isFastScrollEnabled());
            // android:listSelector
            assertEquals(((ColorDrawable)compLayout.getSelector()).getColor(), 0x6600ff00);
            assertEquals(compLayout.getSelector(), parsedLayout.getSelector());
            assertFalse(compLayout.isScrollingCacheEnabled());
            assertEquals(compLayout.isScrollingCacheEnabled(), parsedLayout.isScrollingCacheEnabled());
            assertFalse(compLayout.isSmoothScrollbarEnabled());
            assertEquals(compLayout.isSmoothScrollbarEnabled(), parsedLayout.isSmoothScrollbarEnabled());
            assertTrue(compLayout.isStackFromBottom());
            assertEquals(compLayout.isStackFromBottom(), parsedLayout.isStackFromBottom());
            assertTrue(compLayout.isTextFilterEnabled());
            assertEquals(compLayout.isTextFilterEnabled(), parsedLayout.isTextFilterEnabled());
            assertEquals(compLayout.getTranscriptMode(), AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            assertEquals(compLayout.getTranscriptMode(), parsedLayout.getTranscriptMode());

        }

        // Test GridView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test GridView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final GridView compLayout = (GridView) comp.getChildAt(childCount);
            final GridView parsedLayout = (GridView) parsed.getChildAt(childCount);

            // Tests android:columnWidth (getColumnWidth es Level 16):
            // En teoría existe el atributo mColumnWidth pero el valor final puede no coincidir con el columnWidth especificado
            // porque es corregido dinámicamente
            assertEquals((Integer)TestUtil.getField(compLayout, "mRequestedColumnWidth"),ValueUtil.dpToPixelIntFloor(30.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout, "mRequestedColumnWidth"),(Integer)TestUtil.getField(parsedLayout, "mRequestedColumnWidth"));

            // Tests android:gravity (getGravity es Level 16)
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"), Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"),(Integer)TestUtil.getField(parsedLayout, "mGravity"));

            // Tests android:horizontalSpacing (getHorizontalSpacing es Level 16):
            assertEquals((Integer)TestUtil.getField(compLayout, "mHorizontalSpacing"),ValueUtil.dpToPixelIntFloor(5.3f, res));
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    // No se consolida hasta que se hace el Layout
                    assertEquals((Integer) TestUtil.getField(compLayout, "mHorizontalSpacing"), (Integer) TestUtil.getField(parsedLayout, "mHorizontalSpacing"));
                }
            });
            assertEquals(compLayout.getNumColumns(), 3);
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    // Usamos un OnLayoutChangeListener porque el setNumColumns define un atributo "request" que no es el atributo
                    // donde va el numColumns definitivo el cual se calcula al hacer el layout
                    assertEquals(compLayout.getNumColumns(), parsedLayout.getNumColumns());
                }
            });
            assertEquals(compLayout.getStretchMode(), GridView.STRETCH_COLUMN_WIDTH); // Es el modo por defecto pero los demás modos en nuestro test se ven muy mal
            assertEquals(compLayout.getStretchMode(), parsedLayout.getStretchMode());

            // Tests android:verticalSpacing (getVerticalSpacing es Level 16):
            assertEquals((Integer) TestUtil.getField(compLayout, "mVerticalSpacing"), ValueUtil.dpToPixelIntFloor(5.3f, res));
            parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    // No se consolida hasta que se hace el Layout
                    assertEquals((Integer) TestUtil.getField(compLayout, "mVerticalSpacing"), (Integer) TestUtil.getField(parsedLayout, "mVerticalSpacing"));
                }
            });
        }


        // Test ListView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ListView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final ListView compLayout = (ListView) comp.getChildAt(childCount);
            final ListView parsedLayout = (ListView) parsed.getChildAt(childCount);

            // Test android:divider
            // Test visual: líneas rojas separadoras de items
            assertEquals((GradientDrawable) compLayout.getDivider(), (GradientDrawable) parsedLayout.getDivider());

            // Test android:entries
            ListAdapter compAdapter = compLayout.getAdapter();
            ListAdapter parsedAdapter = parsedLayout.getAdapter();
            assertEquals(compAdapter.getCount(),6);
            assertEquals(compAdapter.getCount(),parsedAdapter.getCount());
            for (int i = 0; i < compAdapter.getCount(); i++)
            {
                Object compItem = compAdapter.getItem(i);
                Object parsedItem = parsedAdapter.getItem(i);
                if (i >= 0 && i <= 1)
                {
                    assertEquals(compItem.getClass(), SpannedString.class);
                    assertEquals(compItem.getClass(), parsedItem.getClass());
                    String compUnstyledText = ((SpannedString)compItem).toString();
                    String parsedUnstyledText = ((SpannedString)parsedItem).toString();
                    if (i == 0)      { assertEquals(compUnstyledText,"Barcelona"); }
                    else if (i == 1) { assertEquals(compUnstyledText,"Madrid"); }
                    //else if (i == 2) { assertEquals(compUnstyledText,"Bilbao"); }
                    assertEquals(compUnstyledText,parsedUnstyledText);
                }
                else
                {
                    assertEquals(compItem.getClass(), String.class);
                    assertEquals(compItem.getClass(), parsedItem.getClass());
                    String compUnstyledText = compItem.toString();
                    String parsedUnstyledText = parsedItem.toString();
                    assertEquals(compUnstyledText,parsedUnstyledText);
                }
            }

            assertEquals(compLayout.getDividerHeight(), ValueUtil.dpToPixelIntRound(2.3f, res));
            assertEquals(compLayout.getDividerHeight(),parsedLayout.getDividerHeight());
            // Test android:footerDividersEnabled (areFooterDividersEnabled es Level 19)
            assertFalse((Boolean) TestUtil.getField(compLayout, "mFooterDividersEnabled"));
            assertEquals((Boolean)TestUtil.getField(compLayout, "mFooterDividersEnabled"), (Boolean)TestUtil.getField(parsedLayout, "mFooterDividersEnabled"));
            // Test android:headerDividersEnabled (areHeaderDividersEnabled es Level 19)
            assertFalse((Boolean)TestUtil.getField(compLayout, "mHeaderDividersEnabled"));
            assertEquals((Boolean)TestUtil.getField(compLayout, "mHeaderDividersEnabled"),(Boolean)TestUtil.getField(parsedLayout, "mHeaderDividersEnabled"));

        }

        // Test ExpandableListView
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ExpandableListView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final ExpandableListView compLayout = (ExpandableListView) comp.getChildAt(childCount);
            final ExpandableListView parsedLayout = (ExpandableListView) parsed.getChildAt(childCount);

            // Test android:childDivider, no hay método get
            // Test visual: líneas rojas separadoras de items
            assertEquals((GradientDrawable) TestUtil.getField(compLayout, "mChildDivider"), (GradientDrawable) TestUtil.getField(parsedLayout, "mChildDivider"));

            // Test android:childIndicator, no hay método get, si no se define devuelve null
            assertEquals((GradientDrawable) TestUtil.getField(compLayout, "mChildIndicator"), (GradientDrawable) TestUtil.getField(parsedLayout, "mChildIndicator"));

            // Test android:childIndicatorLeft, no hay método get
            // No podemos testearlo porque por ej en 4.4.1 tras definir mChildIndicatorLeft y mChildIndicatorright
            // se llama a un método que los cambia de nuevo de una forma inexplicable, en 4.0.3 no hay tal llamada
            //assertPositive((Integer)TestUtil.getField(compLayout,"mChildIndicatorLeft"));
            //assertEquals((Integer)getField(compLayout,"mChildIndicatorLeft"),(Integer)getField(parsedLayout,"mChildIndicatorLeft"));
            // No testeamos android:childIndicatorRight pues tenemos idéntico problema que childIndicatorLeft

            // Test android:groupIndicator, no hay método get
            assertNotNull((StateListDrawable) TestUtil.getField(compLayout, "mGroupIndicator"));
            assertEquals((StateListDrawable) TestUtil.getField(parsedLayout, "mGroupIndicator"), (StateListDrawable) TestUtil.getField(parsedLayout, "mGroupIndicator"));

            // No testeamos android:indicatorLeft ni indicatorRight porque les pasa igual que a childIndicatorLeft
            //assertPositive((Integer)getField(compLayout,"mIndicatorLeft"));
        }

        // Test AbsSpinner (entries sólo) y Gallery
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test Gallery");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            @SuppressWarnings("deprecation")
            final Gallery compLayout = (Gallery) comp.getChildAt(childCount);
            @SuppressWarnings("deprecation")
            final Gallery parsedLayout = (Gallery) parsed.getChildAt(childCount);
            assertEquals((Integer)TestUtil.getField(compLayout, "mAnimationDuration"), 100);
            assertEquals((Integer)TestUtil.getField(compLayout, "mAnimationDuration"),(Integer)TestUtil.getField(parsedLayout, "mAnimationDuration"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"), Gravity.CENTER_VERTICAL);
            assertEquals((Integer) TestUtil.getField(compLayout, "mGravity"), (Integer) TestUtil.getField(parsedLayout, "mGravity"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mSpacing"),ValueUtil.dpToPixelIntFloor(50.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout, "mSpacing"),(Integer)TestUtil.getField(parsedLayout, "mSpacing"));
            assertEquals((Float)TestUtil.getField(compLayout, "mUnselectedAlpha"), 0.6f);
            assertEquals((Float)TestUtil.getField(compLayout, "mUnselectedAlpha"),(Float)TestUtil.getField(parsedLayout, "mUnselectedAlpha"));
        }

        // Test Spinner
        {
            childCount++;

            TextView compTextView = (TextView) comp.getChildAt(childCount);
            TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test Spinner");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // Test Spinner (dropdown)
        {
            childCount++;

            final Spinner compLayout = (Spinner) comp.getChildAt(childCount);
            final Spinner parsedLayout = (Spinner) parsed.getChildAt(childCount);

            // Tests android:dropDownHorizontalOffset
            // Este atributo es difícil de testear pues se solapa con paddingLeft (definido en el style en este ejemplo) el cual suele imponer su valor
            // http://stackoverflow.com/questions/21503142/android-spinner-dropdownhorizontaloffset-not-functioning-but-dropdownverticleoff
            assertEquals((Integer)TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownHorizontalOffset"}), ValueUtil.dpToPixelIntFloor(21.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownHorizontalOffset"}), (Integer)TestUtil.getField(parsedLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownHorizontalOffset"}));

            // Tests android:dropDownVerticalOffset
            assertEquals((Integer) TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownVerticalOffset"}), ValueUtil.dpToPixelIntFloor(10.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownVerticalOffset"}), (Integer) TestUtil.getField(parsedLayout, new Class[]{Spinner.class, ListPopupWindow.class}, new String[]{"mPopup", "mDropDownVerticalOffset"}));

            // Tests android:dropDownWidth ( getDropDownWidth() es Level 16)
            assertEquals((Integer)TestUtil.getField(compLayout, "mDropDownWidth"),ValueUtil.dpToPixelIntRound(200.3f, res));
            assertEquals((Integer)TestUtil.getField(compLayout, "mDropDownWidth"),(Integer)TestUtil.getField(parsedLayout, "mDropDownWidth"));

            // Tests android:gravity (no get en Level 15)
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"), Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            assertEquals((Integer) TestUtil.getField(compLayout, "mGravity"), (Integer) TestUtil.getField(parsedLayout, "mGravity"));

            // Tests android:popupBackground
            assertEquals(((ColorDrawable)TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class, PopupWindow.class}, new String[]{"mPopup", "mPopup", "mBackground"})).getColor(), 0xffeeee55);
            assertEquals((ColorDrawable)TestUtil.getField(compLayout, new Class[]{Spinner.class, ListPopupWindow.class, PopupWindow.class}, new String[]{"mPopup", "mPopup", "mBackground"}),(ColorDrawable)TestUtil.getField(parsedLayout, new Class[]{Spinner.class, ListPopupWindow.class, PopupWindow.class}, new String[]{"mPopup", "mPopup", "mBackground"}));

            // Test style (necesario testear porque se construye de forma especial)

            assertEquals(compLayout.getPaddingLeft(),ValueUtil.dpToPixelIntRound(21.3f, res));
            assertEquals(compLayout.getPaddingLeft(),parsedLayout.getPaddingLeft());
            assertEquals(compLayout.getPaddingRight(),ValueUtil.dpToPixelIntRound(21.3f, res));
            assertEquals(compLayout.getPaddingRight(),parsedLayout.getPaddingRight());

        }

        // Test Spinner (dialog)
        {
            childCount++;

            final Spinner compLayout = (Spinner) comp.getChildAt(childCount);
            final Spinner parsedLayout = (Spinner) parsed.getChildAt(childCount);

            assertEquals(compLayout.getPrompt(), "Sport List");
            assertEquals(compLayout.getPrompt(), parsedLayout.getPrompt());
        }




//         System.out.println("\n\n\nDEFAULT VALUE: " + compLayout.getColumnCount() + " " + parsedLayout.getColumnCount());
        //System.out.println("\n\n\n");
    }


}
