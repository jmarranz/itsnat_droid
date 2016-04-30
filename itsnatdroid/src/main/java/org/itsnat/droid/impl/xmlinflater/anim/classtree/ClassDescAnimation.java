package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_Animation_repeatCount;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodLong;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimation extends ClassDescAnimationBased<Animation>
{
    public static final MapSmart<String,Integer> repeatModeMap = MapSmart.<String,Integer>create(2);
    static
    {
        repeatModeMap.put("restart", Animation.RESTART);
        repeatModeMap.put("reverse", Animation.REVERSE);
    }

    public ClassDescAnimation(ClassDescAnimationMgr classMgr)
    {
        super(classMgr, "NONE", null);
    }

    @Override
    public Class<Animation> getDeclaredClass()
    {
        return Animation.class;
    }


    @Override
    protected Animation createAnimationNative(Context ctx)
    {
        return null; // No se necesita, Animation es abstract
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodColor(this, "background",0));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "detachWallpaper", false));
        addAttrDescAN(new AttrDescReflecMethodLong(this, "duration", 0L));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fillEnabled", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fillBefore", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fillAfter", false));
        addAttrDescAN(new AttrDescReflecMethodInterpolator(this, "interpolator","@android:anim/accelerateDecelerateInterpolator")); // Cuando no se define uno externamente se asegura un AccelerateDecelerateInterpolator por lo que consideramos que es el de por defecto si se eliminara el atributo
        addAttrDescAN(new AttrDescAnimation_view_animation_Animation_repeatCount(this));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "repeatMode", int.class, repeatModeMap, "restart"));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "zAdjustment", 0));
        addAttrDescAN(new AttrDescReflecMethodLong(this, "startOffset", 0L));
    }
}

