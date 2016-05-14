package org.itsnat.droid;

import android.animation.Animator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

/**
 * Created by jmarranz on 01/04/2016.
 */
public interface ItsNatResources
{
    public Animator getAnimator(String resourceDesc);
    public Animation getAnimation(String resourceDesc);
    public LayoutAnimationController getLayoutAnimation(String resourceDesc);
    public Interpolator getInterpolator(String resourceDesc);
    public CharSequence[] getTextArray(String resourceDescValue);
}
