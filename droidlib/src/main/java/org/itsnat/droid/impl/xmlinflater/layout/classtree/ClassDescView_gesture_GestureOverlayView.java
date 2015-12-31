package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.gesture.AttrDescView_gesture_GestureOverlayView_gestureColor;
import org.itsnat.droid.impl.xmlinflater.layout.attr.gesture.AttrDescView_gesture_GestureOverlayView_gestureStrokeType;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodLong;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_gesture_GestureOverlayView extends ClassDescViewBased
{
    public ClassDescView_gesture_GestureOverlayView(ClassDescViewMgr classMgr,ClassDescView_widget_FrameLayout parentClass)
    {
        super(classMgr,"android.gesture.GestureOverlayView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "eventsInterceptionEnabled", true));
        addAttrDescAN(new AttrDescReflecFieldSetInt(this, "fadeDuration", "mFadeDuration", 150)); // Curiosamente mFadeDuration es long pero t_odo se procesa como int
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fadeEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodLong(this, "fadeOffset", 420L));
        addAttrDescAN(new AttrDescView_gesture_GestureOverlayView_gestureColor(this));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "gestureStrokeAngleThreshold", 40.0f));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "gestureStrokeLengthThreshold", 50.0f));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "gestureStrokeSquarenessThreshold", "setGestureStrokeSquarenessTreshold", 0.275f));  // Es necesario el nombre del método por un gazapo, falta la h de Threshold
        addAttrDescAN(new AttrDescView_gesture_GestureOverlayView_gestureStrokeType(this));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "gestureStrokeWidth", 12.0f)); // Sorprendetemente NO se admite dimensión (dp etc)
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "orientation", int.class, OrientationUtil.nameValueMap, "vertical"));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "uncertainGestureColor", "#48FFFF00"));


    }
}

