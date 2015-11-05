package org.itsnat.itsnatdroidtest.testact.util;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.text.SpannableStringBuilder;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by jmarranz on 19/06/14.
 */
public class Assert
{
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
        float res = 100 * Math.abs(a - b)/Math.max(a,b);
        if (a != b && (100 * Math.abs(a - b)/Math.max(a, b) > 1E-5)) // Admitimos un pequeño error porcentual por el redondeo
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
        // El CharSequence puede ser por ejemplo Spannable
        assertEqualsInternal(a.getClass(),b.getClass());
        assertEqualsInternal(a.toString(),b.toString());
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
        if (a != null && !a.equals(b) || b != null && !b.equals(a)) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }

    public final static void assertEquals(Boolean a,Boolean b)
    {
        assertEquals((boolean)a,(boolean)b);
    }

    public final static void assertEquals(Integer a,int b)
    {
        assertEquals((int)a,b);
    }

    public final static void assertEquals(Integer a,Integer b)
    {
        assertEquals((int)a,(int)b);
    }

    public final static void assertEquals(Long a,Long b)
    {
        assertEquals((long)a,(long)b);
    }

    public final static void assertEquals(Float a,float b)
    {
        assertEquals((float)a,b);
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
        if (a.length != b.length) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        for(int i = 0; i < a.length; i++)
            assertEquals(a[i],b[i]);
    }

    public static void assertEquals(Bitmap a,Bitmap b)
    {
        //if (executeAllTests)
        {
            assertEquals(a.getByteCount(), b.getByteCount());
            assertEquals(a.getWidth(), b.getWidth());
            assertEquals(a.getHeight(), b.getHeight());
        }
    }

    private static void assertEqualsDrawable(Drawable a,Drawable b)
    {
        assertEqualsDrawable(a, b, true);
    }

    private static void assertEqualsDrawable(Drawable a,Drawable b,boolean testOpacity)
    {
        // La propia clase Drawable, no derivadas
        if (testOpacity) assertEquals(a.getOpacity(), b.getOpacity());
        assertEquals(a.getIntrinsicWidth(), b.getIntrinsicWidth());
        assertEquals(a.getIntrinsicHeight(), b.getIntrinsicHeight());

        assertEquals(a.isStateful(), b.isStateful());
        //assertEquals(a.isVisible(),b.isVisible());  no coincide nunca pues sólo se está viendo uno de los dos (supongo que esa es la razon)
    }

    public static void assertEquals(Drawable a,Drawable b)
    {
        if (!a.getClass().equals(b.getClass())) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");

        //assertEquals(a.getBounds(),b.getBounds());

        if (a instanceof BitmapDrawable)
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

    public static void assertEquals(ClipDrawable a,ClipDrawable b)
    {
        assertEqualsDrawable(a, b);

        Rect ar = new Rect(); Rect br = new Rect();
        a.getPadding(ar); b.getPadding(br);
        assertEquals(ar, br);

        if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // < 23
        {
            Drawable.ConstantState sa = a.getConstantState();
            Drawable.ConstantState sb = b.getConstantState();

            Class classClipState = TestUtil.resolveClass(ClipDrawable.class.getName() + "$ClipState");
            assertEquals((Drawable) TestUtil.getField(sa, classClipState, "mDrawable"), (Drawable) TestUtil.getField(sb, classClipState, "mDrawable"));
            assertEquals((Integer) TestUtil.getField(sa, classClipState, "mOrientation"), (Integer) TestUtil.getField(sb, classClipState, "mOrientation"));
            assertEquals((Integer) TestUtil.getField(sa, classClipState, "mGravity"), (Integer) TestUtil.getField(sb, classClipState, "mGravity"));

        }
        else
        {
            Class clasz = TestUtil.resolveClass("android.graphics.drawable.DrawableWrapper"); // DrawableWrapper es la clase base de ClipDrawable
            assertEquals((Drawable) TestUtil.getField(a, clasz, "mDrawable"), (Drawable) TestUtil.getField(b, clasz, "mDrawable"));
            assertEquals((Integer) TestUtil.getField(a, clasz, "mOrientation"), (Integer) TestUtil.getField(b, clasz, "mOrientation"));
            assertEquals((Integer) TestUtil.getField(a, clasz, "mGravity"), (Integer) TestUtil.getField(b, clasz, "mGravity"));
        }
    }

    public static void assertEquals(ColorDrawable a,ColorDrawable b)
    {
        assertEqualsDrawable(a, b);
        assertEquals(a.getColor(),b.getColor());
    }

    public static void assertEquals(GradientDrawable a,GradientDrawable b)
    {
        assertEqualsDrawable(a,b,false); // No comparar getOpacity() no se porqué

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        // <gradient>
        assertEquals((Integer) TestUtil.getField(a_state, "mGradient"), (Integer) TestUtil.getField(b_state, "mGradient")); // tests android:type
        assertEquals((Float) TestUtil.getField(a_state, "mGradientRadius"), (Float) TestUtil.getField(b_state, "mGradientRadius"));
        assertEquals((GradientDrawable.Orientation) TestUtil.getField(a_state, "mOrientation"), (GradientDrawable.Orientation) TestUtil.getField(b_state, "mOrientation"));
        assertEquals((int[]) TestUtil.getField(a_state, "mColors"), (int[]) TestUtil.getField(b_state, "mColors"));
        assertEquals((Float) TestUtil.getField(a_state, "mCenterX"), (Float) TestUtil.getField(b_state, "mCenterX"));
        assertEquals((Float) TestUtil.getField(a_state, "mCenterY"), (Float) TestUtil.getField(b_state, "mCenterY"));
        assertEquals((Boolean) TestUtil.getField(a_state, "mUseLevel"), (Boolean) TestUtil.getField(b_state, "mUseLevel"));
        if (Build.VERSION.SDK_INT >= TestUtil.LOLLIPOP)
        {
            assertEquals((Integer) TestUtil.getField(a_state, "mGradientRadiusType"), (Integer) TestUtil.getField(b_state, "mGradientRadiusType"));
        }



        assertEquals((Integer) TestUtil.getField(a_state, "mStrokeWidth"), (Integer) TestUtil.getField(b_state, "mStrokeWidth"));
        // mSolidColor ya no existe en level 21: assertEquals((Integer)TestUtil.getField(sa,"mSolidColor"),(Integer)TestUtil.getField(sb,"mSolidColor"));
    }

    public static void assertEqualsStrokeWidth(GradientDrawable a,int b)
    {
        Drawable.ConstantState sa = ((GradientDrawable) a).getConstantState();
        assertEquals((Integer)TestUtil.getField(sa,"mStrokeWidth"),b);
    }

    public static void assertEquals(LayerDrawable a,LayerDrawable b)
    {
        assertEqualsDrawable(a, b);

        // También pasa por aquí TransitionDrawable (que deriva de LayerDrawable)

        assertEquals(a.getNumberOfLayers(), b.getNumberOfLayers());
        Rect ar = new Rect(); Rect br = new Rect();
        a.getPadding(ar); b.getPadding(br);
        assertEquals(ar, br);

        Object a_ls = TestUtil.getField(a,LayerDrawable.class, "mLayerState"); // LayerState
        Object b_ls = TestUtil.getField(b,LayerDrawable.class, "mLayerState"); // "

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

        assertEquals(a.isCrossFadeEnabled(),b.isCrossFadeEnabled());
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

/*
        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classState = TestUtil.resolveClass(NinePatchDrawable.class.getName() + "$NinePatchState");
        assertEquals((Boolean) TestUtil.getField(a_state, classState, "mDither"), (Boolean) TestUtil.getField(b_state, classState, "mDither"));
*/
    }

    public static void assertEquals(RotateDrawable a,RotateDrawable b)
    {
        assertEqualsDrawable(a, b);

        assertEquals(a.getDrawable(), b.getDrawable());
    }

    private static void assertEquals(DrawableContainer a,DrawableContainer b)
    {
        assertEqualsDrawable(a, b);

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classState = TestUtil.resolveClass(DrawableContainer.class.getName() + "$DrawableContainerState");

        Drawable[] a_drawables = (Drawable[])TestUtil.getField(a_state, classState, "mDrawables");
        Drawable[] b_drawables = (Drawable[])TestUtil.getField(b_state, classState, "mDrawables");
        assertEquals(a_drawables.length,b_drawables.length);
        for(int i = 0; i < a_drawables.length; i++)
        {
            if (a_drawables[i] != null) // Android reserva más items en el array que los usados
                assertEquals(a_drawables[i], b_drawables[i]);
            else
                break;
        }
    }

    public static void assertEquals(StateListDrawable a,StateListDrawable b)
    {
        assertEquals((DrawableContainer) a, (DrawableContainer) b);

        Method methodStateListState = TestUtil.getMethod(StateListDrawable.class, "getStateListState", new Class[0]);

        Method methodGetStateListStateIsConstantSize = TestUtil.getMethod(MiscUtil.resolveClass("android.graphics.drawable.DrawableContainer$DrawableContainerState"), "isConstantSize", new Class[0]);

        Object a_stateListState = TestUtil.callMethod(a, null, methodStateListState);
        Object b_stateListState = TestUtil.callMethod(b, null, methodStateListState);

        boolean a_isConstantSize = (Boolean)TestUtil.callMethod(a_stateListState,null,methodGetStateListStateIsConstantSize);
        boolean b_isConstantSize = (Boolean)TestUtil.callMethod(b_stateListState,null,methodGetStateListStateIsConstantSize);

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
    }

    public static void assertEquals(LevelListDrawable a,LevelListDrawable b)
    {
        assertEquals((DrawableContainer)a, (DrawableContainer)b);

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
        assertEqualsDrawable(a, b);

        assertEquals(a.isStateful(), b.isStateful());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classInsetState = TestUtil.resolveClass(InsetDrawable.class.getName() + "$InsetState");
        assertEquals((Drawable) TestUtil.getField(a_state, classInsetState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classInsetState, "mDrawable"));

        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetLeft"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetLeft"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetTop"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetTop"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetRight"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetRight"));
        assertEquals((Integer) TestUtil.getField(a_state, classInsetState, "mInsetBottom"), (Integer) TestUtil.getField(b_state, classInsetState, "mInsetBottom"));
    }

    public static void assertEquals(ScaleDrawable a,ScaleDrawable b)
    {
        assertEqualsDrawable(a, b);

        assertEquals(a.isStateful(), b.isStateful());

        Drawable.ConstantState a_state = a.getConstantState();
        Drawable.ConstantState b_state = b.getConstantState();

        Class classScaleState = TestUtil.resolveClass(ScaleDrawable.class.getName() + "$ScaleState");
        assertEquals((Drawable) TestUtil.getField(a_state, classScaleState, "mDrawable"), (Drawable) TestUtil.getField(b_state, classScaleState, "mDrawable"));

        assertEquals((Float) TestUtil.getField(a_state, classScaleState, "mScaleWidth"), (Float) TestUtil.getField(b_state, classScaleState, "mScaleWidth"));
        assertEquals((Float) TestUtil.getField(a_state, classScaleState, "mScaleHeight"), (Float) TestUtil.getField(b_state, classScaleState, "mScaleHeight"));
        assertEquals((Integer) TestUtil.getField(a_state, classScaleState, "mGravity"), (Integer) TestUtil.getField(b_state, classScaleState, "mGravity"));
    }




    public static void assertEquals(ColorStateList a,ColorStateList b)
    {
        assertEquals((int[][])TestUtil.getField(a,"mStateSpecs"),(int[][])TestUtil.getField(b,"mStateSpecs"));
        assertEquals((int[])TestUtil.getField(a,"mColors"),(int[])TestUtil.getField(b,"mColors"));
        assertEquals((Integer)TestUtil.getField(a,"mDefaultColor"),(Integer)TestUtil.getField(b,"mDefaultColor"));

//        if (!a.equals(b)) throw new ItsNatDroidException("Not equal: \"" + a + "\" - \"" + b + "\"");
    }



    public static void assertEquals(ObjectAnimator a,ObjectAnimator b)
    {
        // Comparamos unas cuantas propiedades
        assertEquals(a.getPropertyName(),b.getPropertyName());
//        assertEquals(a.getTarget().getClass().getName(),a.getTarget().getClass().getName());
        assertTrue(a.getInterpolator().equals(b.getInterpolator()));

        assertEquals(a.getDuration(), b.getDuration());
        assertEquals(a.getRepeatMode(),b.getRepeatMode());
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
