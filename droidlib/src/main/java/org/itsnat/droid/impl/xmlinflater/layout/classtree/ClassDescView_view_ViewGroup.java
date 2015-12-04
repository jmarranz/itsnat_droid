package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_animateLayoutChanges;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_descendantFocusability;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_layoutAnimation;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_persistentDrawingCache;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_view_ViewGroup extends ClassDescViewBased
{
    public ClassDescView_view_ViewGroup(ClassDescViewMgr classMgr, ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.view.ViewGroup",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "addStatesFromChildren", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "alwaysDrawnWithCache", "setAlwaysDrawnWithCacheEnabled", true));
        addAttrDescAN(new AttrDescView_view_ViewGroup_animateLayoutChanges(this)); // animateLayoutChanges
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "animationCache", "setAnimationCacheEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "clipChildren", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "clipToPadding", true));
        addAttrDescAN(new AttrDescView_view_ViewGroup_descendantFocusability(this)); // descendantFocusability
        addAttrDescAN(new AttrDescView_view_ViewGroup_layoutAnimation(this)); // layoutAnimation
        // android:layoutMode es Level 18
        addAttrDescAN(new AttrDescView_view_ViewGroup_persistentDrawingCache(this)); // persistentDrawingCache
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "splitMotionEvents", "setMotionEventSplittingEnabled", false));

    }
}

