package org.itsnat.itsnatdroidtest.testact.local;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestSetupAssetLayoutDrawables extends TestSetupAssetLayoutBase
{
    public TestSetupAssetLayoutDrawables(TestActivityTabFragment fragment) {
        super(fragment);
    }

    public void test() {
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_drawables_compiled);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                InflatedLayout layout = loadDynamicAndBindBackReloadButtons("res/layout/test_local_layout_drawables_asset.xml");
                View dynamicRootView = layout.getRootView();

                initialConfiguration(act, dynamicRootView);

                TestAssetLayoutDrawables.test((ScrollView) compiledRootView, (ScrollView) dynamicRootView);
            }
        });

        initialConfiguration(act, compiledRootView);
    }

    private static void initialConfiguration(TestActivity act, View rootView)
    {

        TextView testClipDrawable = (TextView) rootView.findViewById(R.id.testClipDrawableId);
        ((ClipDrawable) testClipDrawable.getBackground()).setLevel(5000); // La mitad se verá

        TextView testClipDrawable2 = (TextView) rootView.findViewById(R.id.testClipDrawableId2);
        ((ClipDrawable) testClipDrawable2.getBackground()).setLevel(5000); // La mitad se verá


        TextView testLevelListDrawable = (TextView) rootView.findViewById(R.id.testLevelListDrawableId);
        final LevelListDrawable levelListDrawable = (LevelListDrawable) testLevelListDrawable.getBackground();
        levelListDrawable.setLevel(1);
        testLevelListDrawable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                levelListDrawable.setLevel(4);
            }
        });

        TextView testTransitionDrawable = (TextView) rootView.findViewById(R.id.testTransitionDrawableId);
        final TransitionDrawable transitionDrawable = (TransitionDrawable) testTransitionDrawable.getBackground();
        testTransitionDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawable.startTransition(1000); // Begin the second layer on top of the first layer.
            }
        });

        TextView testScaleDrawable = (TextView) rootView.findViewById(R.id.testScaleDrawableId);
        ScaleDrawable scaleDrawable = (ScaleDrawable) testScaleDrawable.getBackground();
        scaleDrawable.setLevel(1);

        TextView testAnimationDrawable = (TextView) rootView.findViewById(R.id.testAnimationDrawableId);
        AnimationDrawable animationDrawable = (AnimationDrawable) testAnimationDrawable.getBackground();
        animationDrawable.start();

        TextView testRotateDrawable = (TextView) rootView.findViewById(R.id.testRotateDrawableId);
        RotateDrawable rotateDrawable = (RotateDrawable) testRotateDrawable.getBackground();
        rotateDrawable.setLevel(10000); // 0 (minimum) to 10000 (maximum) para que rote los 45%
    }
}
