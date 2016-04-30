package org.itsnat.itsnatdroidtest.testact.local;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.Assert;

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
        final TextView textView = (TextView) rootView.findViewById(R.id.alphaAnimationTestId1);

        final AlphaAnimation alphaAnimation;
        if (layout == null)
            alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(act,R.anim.test_animation_alpha_compiled);
        else
            alphaAnimation = null; // (AnimatorSet)layout.getItsNatResources().getAnimator("@assets:animator/res/animator/test_animator_set_asset.xml");

        textView.startAnimation(alphaAnimation);


    }
}
