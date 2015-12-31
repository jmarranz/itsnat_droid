package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_drawingCacheQuality extends AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 3 );
    static
    {
        nameValueMap.put("auto", View.DRAWING_CACHE_QUALITY_AUTO);
        nameValueMap.put("low", View.DRAWING_CACHE_QUALITY_LOW);
        nameValueMap.put("high", View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public AttrDescView_view_View_drawingCacheQuality(ClassDescViewBased parent)
    {
        super(parent,"drawingCacheQuality",int.class, nameValueMap,"auto");
    }

}
