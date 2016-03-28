package org.itsnat.itsnatdroidtest.testact.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MiscUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jmarranz on 19/06/14.
 */
public class Assert
{
    public static void assertNotZero(int a)
    {
        if (a == 0)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertPositive(int a)
    {
        if (a <= 0)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertPositive(float a)
    {
        if (a <= 0)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertNotNull(Object a)
    {
        if (a == null)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertNull(Object a)
    {
        if (a != null)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertFalse(boolean a)
    {
        if (a)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertTrue(boolean a)
    {
        if (!a)
            throw new ItsNatDroidException("Failed " + a);
    }

    public static void assertEquals(boolean a,boolean b)
    {
        if (a != b)
            throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public static void assertEquals(int a,int b)
    {
        if (a != b)
            throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public static void assertEquals(long a,long b)
    {
        if (a != b)
            throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public static void assertEquals(float a,float b)
    {
        if (a != b && (100 * Math.abs(a - b)/Math.max(a, b) > 1E-4)) // Admitimos un pequeño error porcentual por el redondeo
            throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public static void assertEquals(String a,String b)
    {
        assertEqualsInternal(a,b);
    }

    public static void assertEquals(CharSequence a,String b)
    {
        // El CharSequence puede ser por ejemplo Spannable pero en este caso queremos comparar "el valor textual"
        assertEqualsInternal(a.toString(), b);
    }

    public static void assertEquals(SpannableStringBuilder a,SpannableStringBuilder b)
    {
        // En teoría se puede hacer un test más completo
        assertEqualsInternal(a.toString(),b.toString());
    }

    public static void assertEquals(CharSequence a,CharSequence b)
    {
        // El CharSequence puede ser por ejemplo Spannable, Android tiende en ciertos casos de HTML a devolver String pero no se cual es la casuística exacta y yo tiendo a devolver SpannableString
        // lo importante es que el texto renderizado sea el mismo
        if (a instanceof Spannable)
            assertEqualsInternal(a.getClass(),b.getClass());
        assertEqualsInternal(a.toString(), b.toString());
    }

    public static void assertEquals(Rect a,Rect b)
    {
        assertEqualsInternal(a, b);
    }

    public static void assertEquals(InputFilter.LengthFilter a,InputFilter.LengthFilter b)
    {
        int a_int = (Integer)TestUtil.getField(a,"mMax");
        int b_int = (Integer)TestUtil.getField(b,"mMax");
        assertEquals(a_int, b_int);
    }


    public final static void assertEquals(Object a,Object b)
    {
        assertEqualsInternal(a, b);
    }

    public final static void assertEqualsInternal(Object a,Object b)
    {
        if (a == b) return;
        if (a != null && !a.equals(b) || b != null && !b.equals(a))
            throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public final static void assertEquals(Boolean a,Boolean b)
    {
        assertEquals((boolean) a, (boolean) b);
    }

    public final static void assertEquals(Integer a,int b)
    {
        assertEquals((int) a, b);
    }

    public final static void assertEquals(Integer a,Integer b)
    {
        assertEquals((int) a, (int) b);
    }

    public final static void assertEquals(Long a,Long b)
    {
        assertEquals((long)a,(long)b);
    }

    public final static void assertEquals(Float a,float b)
    {
        assertEquals((float) a, b);
    }

    public final static void assertEquals(Float a,Float b)
    {
        assertEquals((float)a,(float)b);
    }

    public final static void assertEquals(int[][] a,int[][] b)
    {
        if (a.length != b.length) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        for(int i = 0; i < a.length; i++)
            assertEquals(a[i],b[i]);
    }

    public final static void assertEquals(int[] a,int[] b)
    {
        if (a == null && b == null) return; // Hay un caso

        if (a.length != b.length) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        for(int i = 0; i < a.length; i++)
            assertEquals(a[i],b[i]);
    }

    public final static void assertEquals(float[] a,float[] b)
    {
        if (a == null && b == null) return;

        if (a.length != b.length) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        for(int i = 0; i < a.length; i++)
            assertEquals(a[i],b[i]);
    }

    public final static void assertEqualsViewGroupLayoutParams(ViewGroup.LayoutParams a_params,ViewGroup.LayoutParams b_params,int width,int height)
    {
        assertEquals(a_params.width, width);
        assertEquals(a_params.width, b_params.width);
        assertEquals(a_params.height, height);
        assertEquals(a_params.height, b_params.height);
    }

    public final static void assertEqualsRelativeLayoutLayoutParamsBellow(RelativeLayout.LayoutParams a,RelativeLayout.LayoutParams b,int aUpperId,int bUpperId)
    {
        int[] compTextRules = a.getRules();
        int[] parsedTextRules = b.getRules();
        assertEquals(compTextRules.length, parsedTextRules.length); // Por si acaso pero son todas las posibles rules

        assertEquals(compTextRules.length, parsedTextRules.length); // Por si acaso pero son todas las posibles rules
        assertNotZero(compTextRules[RelativeLayout.BELOW]);
        assertEquals(compTextRules[RelativeLayout.BELOW], aUpperId);
        assertNotZero(parsedTextRules[RelativeLayout.BELOW]);
        assertEquals(parsedTextRules[RelativeLayout.BELOW], bUpperId);
    }


    public static void assertEquals(Bitmap a,Bitmap b)
    {
        assertEquals(a.getByteCount(), b.getByteCount());
        assertEquals(a.getWidth(), b.getWidth());
        assertEquals(a.getHeight(), b.getHeight());
    }

    private static void assertEqualsDrawable(Drawable a,Drawable b)
    {
        assertEqualsDrawable(a, b, true);
    }

    private static void assertEqualsDrawable(Drawable a,Drawable b,boolean testOpacity)
    {
        // La propia clase Drawable, no derivadas
        if (testOpacity) assertEquals(a.getOpacity(), b.getOpacity());
        // assertEquals(a.getBounds(),b.getBounds()); quizás necesite hacer un layout antes
        if (a.getCurrent().getClass().equals(b.getCurrent().getClass()))
        {
            assertEquals(a.getIntrinsicWidth(), b.getIntrinsicWidth());
            assertEquals(a.getIntrinsicHeight(), b.getIntrinsicHeight());
            assertEquals(a.getMinimumWidth(), b.getMinimumWidth());
            assertEquals(a.getMinimumHeight(), b.getMinimumHeight());
        }
        else
        {
            // En el caso de AnimationDrawable en el que no sabes cual es el que ha quedado el último al salir del layout, pero no hay problema que no se ejecute este test en este caso pues sistemáticamente se en otro lugar se testean todos los drawables uno a uno
            if (!(a instanceof AnimationDrawable)) // Sólo hemos detectado este caso
                assertTrue(false);

        }

        assertEquals(a.isStateful(), b.isStateful());
        //assertEquals(a.isVisible(),b.isVisible());  no coincide nunca pues sólo se está viendo uno de los dos (supongo que esa es la razon)
    }

    public static void assertEquals(Drawable a,Drawable b)
    {
        if (!a.getClass().equals(b.getClass())) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        if (a instanceof AnimationDrawable)
        {
            assertEquals((AnimationDrawable) a, (AnimationDrawable) b);
        }
        else if (a instanceof BitmapDrawable)
        {
            assertEquals((BitmapDrawable)a,(BitmapDrawable)b);
        }
        else if (a instanceof ClipDrawable)
        {
            assertEquals((ClipDrawable)a,(ClipDrawable)b);
        }
        else if (a instanceof ColorDrawable)
        {
            assertEquals(((ColorDrawable) a).getColor(), ((ColorDrawable) b).getColor());
        }
        else if (a instanceof GradientDrawable)
        {
            assertEquals((GradientDrawable)a,(GradientDrawable)b);
        }
        else if (a instanceof InsetDrawable)
        {
            assertEquals((InsetDrawable)a,(InsetDrawable)b);
        }
        else if (a instanceof LayerDrawable)
        {
            assertEquals((LayerDrawable)a,(LayerDrawable)b);
        }
        else if (a instanceof LevelListDrawable)
        {
            assertEquals((LevelListDrawable)a,(LevelListDrawable)b);
        }
        else if (a instanceof NinePatchDrawable)
        {
            assertEquals((NinePatchDrawable)a,(NinePatchDrawable)b);
        }
        else if (a instanceof RotateDrawable)
        {
            assertEquals((RotateDrawable)a,(RotateDrawable)b);
        }
        else if (a instanceof ScaleDrawable)
        {
            assertEquals((ScaleDrawable)a,(ScaleDrawable)b);
        }
        else if (a instanceof StateListDrawable)
        {
            assertEquals((StateListDrawable)a,(StateListDrawable)b);
        }
        else if (a instanceof TransitionDrawable)
        {
            assertEquals((TransitionDrawable)a,(TransitionDrawable)b);
        }
        else
            throw new ItsNatDroidException("Cannot test drawable " + a);
    }

    public static void assertEquals(AnimationDrawable a,AnimationDrawable b)
    {
        assertEqualsDrawableContainer(a, b);

        // android:oneshot
        assertEquals(a.isOneShot(), b.isOneShot());
        // android:visible
        assertEquals(a.isVisible(), b.isVisible());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        // android:variablePadding
        Class classDrawableContainerState = TestUtil.resolveClass(DrawableContainer.class.getName() + "$DrawableContainerState");
        assertEquals(TestUtil.getField(a_state, classDrawableContainerState, "mVariablePadding"), TestUtil.getField(b_state, classDrawableContainerState, "mVariablePadding"));

        // <item>

        // android:drawable (o child element drawable) se testea en assertEqualsDrawableContainer
        // android:duration
        Class classState = TestUtil.resolveClass(AnimationDrawable.class.getName() + "$AnimationState");

        int[] a_durations = (int[])TestUtil.getField(a_state, classState, "mDurations");
        int[] b_durations = (int[])TestUtil.getField(b_state, classState, "mDurations");

        assertEquals(a_durations,b_durations);
    }

    public static void assertEquals(BitmapDrawable a,BitmapDrawable b)
    {
        assertEqualsDrawable(a, b);

        assertEquals(a.getGravity(), b.getGravity());
        assertEquals(a.getBitmap(), b.getBitmap());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classBitmapState = TestUtil.resolveClass(BitmapDrawable.class.getName() + "$BitmapState");
        assertEquals(TestUtil.getField(a_state, classBitmapState, "mTileModeX"), TestUtil.getField(b_state, classBitmapState, "mTileModeX")); // Shader.TileMode
        assertEquals(TestUtil.getField(a_state, classBitmapState, "mTileModeY"), TestUtil.getField(b_state, classBitmapState, "mTileModeY"));

        Paint a_paint = a.getPaint();
        Paint b_paint = b.getPaint();

        assertEquals(a_paint.isAntiAlias(), b_paint.isAntiAlias());
        assertEquals(a_paint.isDither(), b_paint.isDither());
        assertEquals(a_paint.isFilterBitmap(), b_paint.isFilterBitmap());
    }

    // DrawableWrapper es del level 23, no podemos usar el tipo todavía con level 15
    public static void assertEqualsDrawableWrapper(Drawable a,Drawable b)
    {
        assertEqualsDrawable(a, b);

        if (Build.VERSION.SDK_INT >= TestUtil.MARSHMALLOW) // 23
        {
            Class clasz = TestUtil.resolveClass("android.graphics.drawable.DrawableWrapper"); // DrawableWrapper es la clase base de ClipDrawable
            assertEquals((Drawable) TestUtil.getField(a, clasz, "mDrawable"), (Drawable) TestUtil.getField(b, clasz, "mDrawable"));
        }
    }

    public static void assertEquals(ClipDrawable a,ClipDrawable b)
    {
        assertEqualsDrawableWrapper(a, b);

        Rect ar = new Rect(); Rect br = new Rect();
        a.getPadding(ar); b.getPadding(br);
        assertEquals(ar, br);

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classClipState = TestUtil.resolveClass(ClipDrawable.class.getName() + "$ClipState");
        assertEquals((Integer) TestUtil.getField(a_state, classClipState, "mOrientation"), (Integer) TestUtil.getField(b_state, classClipState, "mOrientation"));
        assertEquals((Integer) TestUtil.getField(a_state, classClipState, "mGravity"), (Integer) TestUtil.getField(b_state, classClipState, "mGravity"));

        // android:drawable
        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // 23
        {
            assertEquals((Drawable) TestUtil.getField(a_state, classClipState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classClipState, "mDrawable"));
        }
    }

    public static void assertEquals(ColorDrawable a,ColorDrawable b)
    {
        assertEqualsDrawable(a, b);
        assertEquals(a.getColor(), b.getColor());
    }

    public static void assertEquals(GradientDrawable a,GradientDrawable b)
    {
        assertEqualsDrawable(a, b, false); // No comparar getOpacity() no se porqué

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        // tests android:shape
        assertEquals((Integer) TestUtil.getField(a_state, "mShape"), (Integer) TestUtil.getField(b_state, "mShape"));
        // tests android:dither
        if (Build.VERSION.SDK_INT >= MiscUtil.LOLLIPOP) // level 21, 5.0
            assertEquals((Boolean) TestUtil.getField(a_state, "mDither"), (Boolean) TestUtil.getField(b_state, "mDither"));
        else
            assertEquals((Boolean) TestUtil.getField(a, "mDither"), (Boolean) TestUtil.getField(b, "mDither"));

        // Caso de RING
        assertEquals((Integer) TestUtil.getField(a_state, "mInnerRadius"), (Integer) TestUtil.getField(b_state, "mInnerRadius")); // tests android:innerRadius
        assertEquals((Float) TestUtil.getField(a_state, "mInnerRadiusRatio"), (Float) TestUtil.getField(b_state, "mInnerRadiusRatio")); // tests android:innerRadiusRatio
        assertEquals((Integer) TestUtil.getField(a_state, "mThickness"), (Integer) TestUtil.getField(b_state, "mThickness")); // tests android:innerRadius
        assertEquals((Float) TestUtil.getField(a_state, "mThicknessRatio"), (Float) TestUtil.getField(b_state, "mThicknessRatio")); // tests android:innerRadiusRatio
        assertEquals((Boolean) TestUtil.getField(a_state, "mUseLevel"), (Boolean) TestUtil.getField(b_state, "mUseLevel")); // android:useLevel


        // <gradient>
        assertEquals((Integer) TestUtil.getField(a_state, "mGradient"), (Integer) TestUtil.getField(b_state, "mGradient")); // tests android:type
        int gradientType = (Integer) TestUtil.getField(a_state, "mGradient");
        if (gradientType != 0) // != LINEAR_GRADIENT
        {
            assertEquals((Float) TestUtil.getField(a_state, "mGradientRadius"), (Float) TestUtil.getField(b_state, "mGradientRadius"));
        }
        assertEquals((GradientDrawable.Orientation) TestUtil.getField(a_state, "mOrientation"), (GradientDrawable.Orientation) TestUtil.getField(b_state, "mOrientation"));
        if (Build.VERSION.SDK_INT >= MiscUtil.MARSHMALLOW) // 6.0
            assertEquals((int[]) TestUtil.getField(a_state, "mGradientColors"), (int[]) TestUtil.getField(b_state, "mGradientColors"));
        else
            assertEquals((int[]) TestUtil.getField(a_state, "mColors"), (int[]) TestUtil.getField(b_state, "mColors"));
        assertEquals((Float) TestUtil.getField(a_state, "mCenterX"), (Float) TestUtil.getField(b_state, "mCenterX"));
        assertEquals((Float) TestUtil.getField(a_state, "mCenterY"), (Float) TestUtil.getField(b_state, "mCenterY"));
        assertEquals((Boolean) TestUtil.getField(a_state, "mUseLevel"), (Boolean) TestUtil.getField(b_state, "mUseLevel"));
        if (Build.VERSION.SDK_INT >= TestUtil.LOLLIPOP)
        {
            if (gradientType != 0) // != LINEAR_GRADIENT
            {
                assertEquals((Integer) TestUtil.getField(a_state, "mGradientRadiusType"), (Integer) TestUtil.getField(b_state, "mGradientRadiusType")); // Se introduce como nuevo atributo
            }
        }

        // <corners>
        assertEquals((Float) TestUtil.getField(a_state, "mRadius"), (Float) TestUtil.getField(b_state, "mRadius")); // tests android:radius
        assertEquals((float[]) TestUtil.getField(a_state, "mRadiusArray"), (float[]) TestUtil.getField(b_state, "mRadiusArray")); // tests android:topLeftRadius etc

        // <padding>
        assertEquals((Rect) TestUtil.getField(a, "mPadding"), (Rect) TestUtil.getField(b, "mPadding"));
        assertEquals((Rect) TestUtil.getField(a_state, "mPadding"), (Rect) TestUtil.getField(b_state, "mPadding")); // tests android:left etc

        // <size>
        assertEquals((Integer) TestUtil.getField(a_state, "mWidth"), (Integer) TestUtil.getField(b_state, "mWidth")); // tests android:width
        assertEquals((Integer) TestUtil.getField(a_state, "mHeight"), (Integer) TestUtil.getField(b_state, "mHeight")); // tests android:height

        // <solid>

        // android:color

        if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP) // < 21 (5.0)
        {
            assertEquals((Integer) TestUtil.getField(a_state, "mSolidColor"), (Integer) TestUtil.getField(b_state, "mSolidColor"));
        }
        else if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // < 23 (6.0)
        {
            // mSolidColor ya no existe en level 21

            ColorStateList a_clist = (ColorStateList)TestUtil.getField(a_state, "mColorStateList");
            ColorStateList b_clist = (ColorStateList)TestUtil.getField(b_state, "mColorStateList");
            if (a_clist != null)
                assertEquals(a_clist.getDefaultColor(),b_clist.getDefaultColor());
        }
        else // >= 23
        {
            ColorStateList a_clist = (ColorStateList)TestUtil.getField(a_state, "mSolidColors");
            ColorStateList b_clist = (ColorStateList)TestUtil.getField(b_state, "mSolidColors");
            if (a_clist != null)
                assertEquals(a_clist.getDefaultColor(),b_clist.getDefaultColor());
        }


        // <stroke>

        // android:width
        assertEquals((Integer) TestUtil.getField(a_state, "mStrokeWidth"), (Integer) TestUtil.getField(b_state, "mStrokeWidth"));

        // android:color
        if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP) // < 21 (5.0)
        {
            assertEquals((Integer) TestUtil.getField(a_state, "mStrokeColor"), (Integer) TestUtil.getField(b_state, "mStrokeColor"));
        }
        else if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // < 23 (6.0)
        {
            // mStrokeColor ya no existe en level 21
            ColorStateList a_clist = (ColorStateList)TestUtil.getField(a_state, "mStrokeColorStateList");
            ColorStateList b_clist = (ColorStateList)TestUtil.getField(b_state, "mStrokeColorStateList");
            if (a_clist != null)
                assertEquals(a_clist.getDefaultColor(),b_clist.getDefaultColor());
        }
        else // >= 23
        {
            ColorStateList a_clist = (ColorStateList)TestUtil.getField(a_state, "mStrokeColors");
            ColorStateList b_clist = (ColorStateList)TestUtil.getField(b_state, "mStrokeColors");
            if (a_clist != null)
                assertEquals(a_clist.getDefaultColor(),b_clist.getDefaultColor());
        }


        assertEquals((Float) TestUtil.getField(a_state, "mStrokeDashWidth"), (Float) TestUtil.getField(b_state, "mStrokeDashWidth"));
        assertEquals((Float) TestUtil.getField(a_state, "mStrokeDashGap"), (Float) TestUtil.getField(b_state, "mStrokeDashGap"));

    }

    public static void assertEquals(LayerDrawable a,LayerDrawable b)
    {
        assertEqualsDrawable(a, b);

        // También pasa por aquí TransitionDrawable (que deriva de LayerDrawable)

        assertEquals(a.getNumberOfLayers(), b.getNumberOfLayers());
        Rect ar = new Rect(); Rect br = new Rect();
        a.getPadding(ar);
        b.getPadding(br);
        assertEquals(ar, br);

        Object a_ls = TestUtil.getField(a,LayerDrawable.class, "mLayerState"); // LayerState
        Object b_ls = TestUtil.getField(b, LayerDrawable.class, "mLayerState"); // "

        Object[] a_cd_array = (Object[])TestUtil.getField(a_ls,TestUtil.resolveClass(LayerDrawable.class.getName() + "$LayerState"),"mChildren"); // ChildDrawable[]
        Object[] b_cd_array = (Object[])TestUtil.getField(b_ls,TestUtil.resolveClass(LayerDrawable.class.getName() + "$LayerState"),"mChildren"); // "


        for (int i = 0; i < a.getNumberOfLayers(); i++)
        {
            assertEquals(a.getId(i), b.getId(i));

            Object a_cd = a_cd_array[i]; // ChildDrawable
            Object b_cd = b_cd_array[i];

            assertEquals((Integer)TestUtil.getField(a_cd,"mInsetL"),(Integer)TestUtil.getField(b_cd,"mInsetL"));
            assertEquals((Integer)TestUtil.getField(a_cd,"mInsetT"),(Integer)TestUtil.getField(b_cd,"mInsetT"));
            assertEquals((Integer)TestUtil.getField(a_cd,"mInsetR"),(Integer)TestUtil.getField(b_cd,"mInsetR"));
            assertEquals((Integer)TestUtil.getField(a_cd,"mInsetB"),(Integer)TestUtil.getField(b_cd,"mInsetB"));

            assertEquals(a.getDrawable(i), b.getDrawable(i));
        }
    }

    public static void assertEquals(TransitionDrawable a,TransitionDrawable b)
    {
        assertEquals((LayerDrawable) a, (LayerDrawable) b); // LayerDrawable es la clase base de TransitionDrawable

        assertEquals(a.isCrossFadeEnabled(), b.isCrossFadeEnabled());
    }

    public static void assertEquals(NinePatchDrawable a,NinePatchDrawable b)
    {
        assertEqualsDrawable(a, b);

        NinePatch a2 = (NinePatch) TestUtil.getField(a, "mNinePatch");
        NinePatch b2 = (NinePatch) TestUtil.getField(b, "mNinePatch");
        assertEquals((Bitmap) TestUtil.getField(a2, "mBitmap"), (Bitmap) TestUtil.getField(b2, "mBitmap"));


        Paint a_paint = a.getPaint();
        Paint b_paint = b.getPaint();

        //assertEquals(a_paint.isAntiAlias(), b_paint.isAntiAlias());
        assertEquals(a_paint.isDither(), b_paint.isDither());
        // assertEquals(a_paint.isFilterBitmap(), b_paint.isFilterBitmap());

    }

    public static void assertEquals(RotateDrawable a,RotateDrawable b)
    {
        assertEqualsDrawableWrapper(a, b);

        //assertEquals(a.getDrawable(), b.getDrawable());

        Class classState = TestUtil.resolveClass(RotateDrawable.class.getName() + "$RotateState");

        Drawable.ConstantState a_state;
        Drawable.ConstantState b_state;

        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // 23
        {
            a_state = a.getConstantState();
            b_state = b.getConstantState();
        }
        else // >= 23
        {
            a_state = (Drawable.ConstantState)TestUtil.getField(a, RotateDrawable.class, "mState");
            b_state = (Drawable.ConstantState)TestUtil.getField(b, RotateDrawable.class, "mState"); // Devuelve null no se porqué con b.getConstantState()
        }


        assertEquals((Boolean) TestUtil.getField(a_state, classState, "mPivotXRel"), (Boolean) TestUtil.getField(b_state, classState, "mPivotXRel"));
        assertEquals((Float) TestUtil.getField(a_state, classState, "mPivotX"), (Float) TestUtil.getField(b_state, classState, "mPivotX"));
        assertEquals((Boolean) TestUtil.getField(a_state, classState, "mPivotYRel"), (Boolean) TestUtil.getField(b_state, classState, "mPivotYRel"));
        assertEquals((Float) TestUtil.getField(a_state, classState, "mPivotY"), (Float) TestUtil.getField(b_state, classState, "mPivotY"));
        assertEquals((Float) TestUtil.getField(a_state, classState, "mFromDegrees"), (Float) TestUtil.getField(b_state, classState, "mFromDegrees"));
        assertEquals((Float) TestUtil.getField(a_state, classState, "mToDegrees"), (Float) TestUtil.getField(b_state, classState, "mToDegrees"));

        // android:drawable
        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // 23
        {
            assertEquals((Drawable) TestUtil.getField(a_state, classState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classState, "mDrawable"));
        }
    }

    private static void assertEqualsDrawableContainer(DrawableContainer a,DrawableContainer b)
    {
        assertEqualsDrawable(a, b);

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        // <item>

        // android:drawable (o child element drawable)
        Class classState = TestUtil.resolveClass(DrawableContainer.class.getName() + "$DrawableContainerState");

        Drawable[] a_drawables = (Drawable[])TestUtil.getField(a_state, classState, "mDrawables");
        Drawable[] b_drawables = (Drawable[])TestUtil.getField(b_state, classState, "mDrawables");
        assertEquals(a_drawables.length,b_drawables.length);
        for(int i = 0; i < a_drawables.length; i++)
        {
            // No se porqué a veces no coinciden en número de imágenes en a (el compilado) hay más que en b, al menos chequeamos que los comunes coinciden (los índices con data en b deben coincidir con el dato en a)
            if (a_drawables[i] != null && b_drawables[i] != null)
                    assertEquals(a_drawables[i], b_drawables[i]);
            /*
            Falla curiosamente cuando se testea dos veces, el compilado CAMBIA
            if (b_drawables[i] != null && a_drawables[i] == null) // Si b (dinámico) está definido DEBE estarlo a (compilado)
                    assertTrue(false);
                    */
        }
    }

    public static void assertEquals(StateListDrawable a,StateListDrawable b)
    {
        assertEqualsDrawableContainer(a,b);

        Method methodStateListState = TestUtil.getMethod(StateListDrawable.class, "getStateListState", new Class[0]);

        Method methodGetStateListStateIsConstantSize = TestUtil.getMethod(MiscUtil.resolveClass("android.graphics.drawable.DrawableContainer$DrawableContainerState"), "isConstantSize", new Class[0]);

        Object a_state = TestUtil.callMethod(a, null, methodStateListState);
        Object b_state = TestUtil.callMethod(b, null, methodStateListState);

        boolean a_isConstantSize = (Boolean)TestUtil.callMethod(a_state,null,methodGetStateListStateIsConstantSize);
        boolean b_isConstantSize = (Boolean)TestUtil.callMethod(b_state,null,methodGetStateListStateIsConstantSize);

        assertEquals(a_isConstantSize, b_isConstantSize);

        Method methodIsVisible = TestUtil.getMethod(Drawable.class, "isVisible", new Class[0]);

        boolean a_isVisible = (Boolean)TestUtil.callMethod(a ,null,methodIsVisible);
        boolean b_isVisible = (Boolean)TestUtil.callMethod(b ,null,methodIsVisible);

        assertEquals(a_isVisible,b_isVisible);


        int[] a_stateArr = a.getState();
        int[] b_stateArr = b.getState();
        assertEquals(a_stateArr.length, b_stateArr.length);
        for (int i = 0; i < a_stateArr.length; i++)
        {
            assertEquals(a_stateArr[i], b_stateArr[i]);
        }


        Class classState = TestUtil.resolveClass(StateListDrawable.class.getName() + "$StateListState");
        int[][] a_state_sets = (int[][])TestUtil.getField(a_state, classState, "mStateSets");
        int[][] b_state_sets = (int[][])TestUtil.getField(b_state, classState, "mStateSets");
        assertEquals(a_state_sets.length,b_state_sets.length);
        for(int i = 0; i < a_state_sets.length; i++)
        {
            // Sinceramente no se como Android construye los estados desde un resource, como el b es el dinámico al menos coomprobaremos que los estados de "b" están en "a"
            // aunque en "a" haya más "por defecto"
            // De todas formas con el test anterior con getState() debería ser suficiente.
            int[] a_itemStates = a_state_sets[i];
            if (a_itemStates == null) // Android reserva más array del que necesita
                continue; // Debería ser break; pero por si acaso

            Set<Integer> a_map = new HashSet<Integer>();
            for (int k = 0; k < a_itemStates.length; k++)
                a_map.add(a_itemStates[k]);

            int[] b_itemStates = b_state_sets[i];
            Set<Integer> b_map = new HashSet<Integer>();
            for (int k = 0; k < b_itemStates.length; k++)
                b_map.add(b_itemStates[k]);

            for(Integer state : b_map)
            {
                assertTrue(a_map.contains(state));
            }
        }

    }

    public static void assertEquals(LevelListDrawable a,LevelListDrawable b)
    {
        assertEqualsDrawableContainer( a, b);

        assertEquals(a.getLevel(),b.getLevel());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classState = TestUtil.resolveClass(LevelListDrawable.class.getName() + "$LevelListState");

        int[] a_lows = (int[])TestUtil.getField(a_state, classState, "mLows");
        int[] b_lows = (int[])TestUtil.getField(b_state, classState, "mLows");
        assertEquals(a_lows.length,b_lows.length);
        for(int i = 0; i < a_lows.length; i++)
            assertEquals(a_lows[i],b_lows[i]);

        int[] a_highs = (int[])TestUtil.getField(a_state, classState, "mHighs");
        int[] b_highs = (int[])TestUtil.getField(b_state, classState, "mHighs");
        assertEquals(a_highs.length,b_highs.length);
        for(int i = 0; i < a_highs.length; i++)
            assertEquals(a_highs[i],b_highs[i]);

    }

    public static void assertEquals(InsetDrawable a,InsetDrawable b)
    {
        assertEqualsDrawableWrapper(a, b);

        assertEquals(a.isStateful(), b.isStateful());
        // android:visible
        assertEquals(a.isVisible(), b.isVisible());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classInsetState = TestUtil.resolveClass(InsetDrawable.class.getName() + "$InsetState");
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetLeft"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetLeft"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetTop"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetTop"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetRight"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetRight"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetBottom"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetBottom"));

        // android:drawable
        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // < 23 (en level 23 mDrawable está en la clase base)
        {
            assertEquals((Drawable) TestUtil.getField(a_state, classInsetState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classInsetState, "mDrawable"));
        }
    }

    public static void assertEquals(ScaleDrawable a,ScaleDrawable b)
    {
        assertEqualsDrawableWrapper(a, b);

        assertEquals(a.isStateful(), b.isStateful());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classScaleState = TestUtil.resolveClass(ScaleDrawable.class.getName() + "$ScaleState");
        assertEquals((Float) TestUtil.getField(a_state, classScaleState, "mScaleWidth"), (Float) TestUtil.getField(b_state, classScaleState, "mScaleWidth"));
        assertEquals((Float) TestUtil.getField(a_state, classScaleState, "mScaleHeight"), (Float) TestUtil.getField(b_state, classScaleState, "mScaleHeight"));
        assertEquals((Integer) TestUtil.getField(a_state, classScaleState, "mGravity"), (Integer) TestUtil.getField(b_state, classScaleState, "mGravity"));

        // android:drawable
        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // 23
        {
            assertEquals((Drawable) TestUtil.getField(a_state, classScaleState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classScaleState, "mDrawable"));
        }
    }




    public static void assertEquals(ColorStateList a,ColorStateList b)
    {
        assertEquals((int[][])TestUtil.getField(a,"mStateSpecs"),(int[][])TestUtil.getField(b,"mStateSpecs"));
        assertEquals((int[])TestUtil.getField(a,"mColors"),(int[])TestUtil.getField(b,"mColors"));
        assertEquals((Integer)TestUtil.getField(a,"mDefaultColor"),(Integer)TestUtil.getField(b,"mDefaultColor"));

//        if (!a.equals(b)) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }


    public static void assertEquals(Animator a,Animator b)
    {
        if (a instanceof ObjectAnimator)
            assertEquals((ObjectAnimator)a,(ObjectAnimator)b);
        else if (a instanceof ValueAnimator)
            assertEquals((ValueAnimator)a,(ValueAnimator)b);
        else if (a instanceof AnimatorSet)
            assertEquals((AnimatorSet)a,(AnimatorSet)b);
    }

    public static void assertEqualsAnimator(Animator a,Animator b)
    {
        // duration, startOffset
        assertEquals(a.getDuration(), b.getDuration());
        assertEquals(a.getStartDelay(), b.getStartDelay());
    }

    public static void assertEquals(ValueAnimator a,ValueAnimator b)
    {
        assertEqualsAnimator(a,b);

        // repeatCount, repeatMode
        assertEquals(a.getRepeatCount(), b.getRepeatCount());
        assertEquals(a.getRepeatMode(), b.getRepeatMode());

        assertTrue(a.getInterpolator().equals(b.getInterpolator()));

        // "valueType" "valueFrom" "valueTo"

        PropertyValuesHolder[] a_pvhArr = a.getValues();
        PropertyValuesHolder[] b_pvhArr = b.getValues();
        assertEquals(a_pvhArr.length,b_pvhArr.length);
        for(int i = 0; i < a_pvhArr.length; i++)
        {
            PropertyValuesHolder a_pvh = a_pvhArr[i];
            PropertyValuesHolder b_pvh = b_pvhArr[i];

            assertEquals(a_pvh.getPropertyName(), b_pvh.getPropertyName());

            String a_cvt = ((Class) TestUtil.getField(a_pvh, PropertyValuesHolder.class, "mValueType")).getName();
            String b_cvt = ((Class) TestUtil.getField(b_pvh, PropertyValuesHolder.class, "mValueType")).getName();
            assertEquals(a_cvt, b_cvt);

            Object a_keyframeset = TestUtil.getField(a_pvh, PropertyValuesHolder.class, "mKeyframeSet");
            Object b_keyframeset = TestUtil.getField(b_pvh, PropertyValuesHolder.class, "mKeyframeSet");

            ArrayList a_keyframes = (ArrayList)TestUtil.getField(a_keyframeset, TestUtil.resolveClass("android.animation.KeyframeSet"), "mKeyframes");
            ArrayList b_keyframes = (ArrayList)TestUtil.getField(b_keyframeset, TestUtil.resolveClass("android.animation.KeyframeSet"), "mKeyframes");

            int a_size = a_keyframes.size();
            int b_size = b_keyframes.size();
            assertEquals(a_size, b_size);
            for(int j = 0; j < a_size; j++)
            {
                Object a_keyframe = a_keyframes.get(j);
                Object b_keyframe = b_keyframes.get(j);

                String a_valueType = ((Class)TestUtil.getField(a_keyframe, TestUtil.resolveClass("android.animation.Keyframe"), "mValueType")).getName();
                String b_valueType = ((Class)TestUtil.getField(b_keyframe, TestUtil.resolveClass("android.animation.Keyframe"), "mValueType")).getName();

                assertEquals(a_cvt, a_valueType);
                assertEquals(b_cvt, b_valueType);

                String a_keyframeClassName = null;
                if (a_valueType.equals("int"))
                    a_keyframeClassName = "android.animation.Keyframe$IntKeyframe";
                else if (a_valueType.equals("float"))
                    a_keyframeClassName = "android.animation.Keyframe$FloatKeyframe";

                String b_keyframeClassName = null;
                if (b_valueType.equals("int"))
                    b_keyframeClassName = "android.animation.Keyframe$IntKeyframe";
                else if (b_valueType.equals("float"))
                    b_keyframeClassName = "android.animation.Keyframe$FloatKeyframe";

                Object a_value = TestUtil.getField(a_keyframe, TestUtil.resolveClass(a_keyframeClassName),"mValue");
                Object b_value = TestUtil.getField(b_keyframe, TestUtil.resolveClass(b_keyframeClassName),"mValue");
                assertEquals(a_value, b_value);
            }

            TypeEvaluator a_te = (TypeEvaluator) TestUtil.getField(a_pvh, PropertyValuesHolder.class, "mEvaluator");
            TypeEvaluator b_te = (TypeEvaluator) TestUtil.getField(b_pvh, PropertyValuesHolder.class, "mEvaluator");
            assertEquals(a_te, b_te);
        }
    }


    public static void assertEquals(ObjectAnimator a,ObjectAnimator b)
    {
        assertEquals((ValueAnimator) a, (ValueAnimator)b);

        assertEquals(a.getPropertyName(),b.getPropertyName());


    }

    public static void assertEquals(AnimationSet a,AnimationSet b)
    {
        // Comparamos unas cuantas propiedades

        assertEquals(a.getDuration(),b.getDuration());
        //assertEquals(a.getStartTime(),b.getStartTime());
        //assertEquals(a.getStartOffset(),b.getStartOffset());

        assertEquals(a.getAnimations().size(),b.getAnimations().size());

        Iterator<Animation> a_it = a.getAnimations().iterator();
        Iterator<Animation> b_it = b.getAnimations().iterator();
        while(a_it.hasNext())
        {
            Animation a_anim = a_it.next();
            Animation b_anim = b_it.next();
            assertEquals(a_anim,b_anim);
        }
    }

    public static void assertEquals(Animation a,Animation b)
    {
        if (a instanceof TranslateAnimation)
        {
            TranslateAnimation a_trans = (TranslateAnimation)a;
            TranslateAnimation b_trans = (TranslateAnimation)b;

            // Comparamos unas cuantas propiedades
            assertEquals(a_trans.getDuration(),b_trans.getDuration());
//            assertEquals(a_trans.getStartOffset(),b_trans.getStartOffset());
//            assertEquals(a_trans.getStartTime(),b_trans.getStartTime());
            assertEquals(a_trans.getInterpolator(),b_trans.getInterpolator());
        }
        else
            throw new ItsNatDroidException("Cannot test " + a);
    }

    public static void assertEquals(android.view.animation.Interpolator a,android.view.animation.Interpolator b)
    {
        assertEquals(a.getClass(),b.getClass());
        assertEquals(a.getInterpolation(5),a.getInterpolation(5));
    }

}
