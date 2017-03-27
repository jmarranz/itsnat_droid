package org.itsnat.droid;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

/**
 * Created by jmarranz on 01/04/2016.
 */
public interface ItsNatResources
{
    public boolean getBoolean(String resourceDesc);
    public int getColor(String resourceDesc);
    public int getIdentifier(String resourceDesc);
    public int getInteger(String resourceDesc);
    public float getFloat(String resourceDesc);
    public String getString(String resourceDesc);
    public CharSequence getText(String resourceDesc);
    public CharSequence[] getTextArray(String resourceDesc);
    public int getDimensionIntFloor(String resourceDesc);
    public int getDimensionIntRound(String resourceDesc);
    public float getDimensionFloat(String resourceDesc);
    public float getDimensionFloatFloor(String resourceDesc);
    public float getDimensionFloatRound(String resourceDesc);
    public int getDimensionWithNameIntRound(String resourceDesc);
    public String getDimensionOrString(String resourceDesc);
    public PercFloat getDimensionPercFloat(String resourceDesc);
    public PercFloat getPercFloat(String resourceDesc);
    public Animation getAnimation(String resourceDesc);
    public Animator getAnimator(String resourceDesc);
    public Drawable getDrawable(String resourceDesc);
    public Interpolator getInterpolator(String resourceDesc);
    public LayoutAnimationController getLayoutAnimation(String resourceDesc);
    public Menu getMenu(String resourceDescValue,Menu rootMenuParent);
    public View getLayout(String resourceDescValue, ViewGroup viewParent, int indexChild);


}
