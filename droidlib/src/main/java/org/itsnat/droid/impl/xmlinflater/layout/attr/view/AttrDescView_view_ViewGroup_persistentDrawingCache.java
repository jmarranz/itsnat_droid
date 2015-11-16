package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.ViewGroup;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodMultipleName;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_ViewGroup_persistentDrawingCache extends AttrDescViewReflecMethodMultipleName
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create(4);
    static
    {
        valueMap.put("none",ViewGroup.PERSISTENT_NO_CACHE);
        valueMap.put("animation",ViewGroup.PERSISTENT_ANIMATION_CACHE);
        valueMap.put("scrolling",ViewGroup.PERSISTENT_SCROLLING_CACHE);
        valueMap.put("all",ViewGroup.PERSISTENT_ALL_CACHES);
    }

    public AttrDescView_view_ViewGroup_persistentDrawingCache(ClassDescViewBased parent)
    {
        super(parent,"persistentDrawingCache",valueMap,"scrolling");
    }

}
