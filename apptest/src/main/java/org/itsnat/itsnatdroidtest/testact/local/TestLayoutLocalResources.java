package org.itsnat.itsnatdroidtest.testact.local;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.TestActivityTabFragment;
import org.itsnat.itsnatdroidtest.testact.util.CustomScrollView;

/**
 * Created by jmarranz on 16/07/14.
 */
public class TestLayoutLocalResources extends TestLayoutLocalBase {
    public TestLayoutLocalResources(TestActivityTabFragment fragment) {
        super(fragment);
    }

    public void test() {
        final TestActivity act = fragment.getTestActivity();
        final View compiledRootView = loadCompiledAndBindBackReloadButtons(R.layout.test_local_layout_compiled_resources);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                InflatedLayout layout = loadDynamicAndBindBackReloadButtons("res/layout/test_local_layout_dynamic_resources.xml");
                View dynamicRootView = layout.getRootView();

                initialConfiguration(act, dynamicRootView);

                TestLocalXMLInflateResources.test((CustomScrollView) compiledRootView, (CustomScrollView) dynamicRootView);
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
        testLevelListDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        final ScaleDrawable scaleDrawable = (ScaleDrawable) testScaleDrawable.getBackground();
        scaleDrawable.setLevel(1);

    }
}
