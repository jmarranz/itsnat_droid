package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarStyle extends AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 4 );
    static
    {
        nameValueMap.put("insideOverlay", View.SCROLLBARS_INSIDE_OVERLAY);
        nameValueMap.put("insideInset", View.SCROLLBARS_INSIDE_INSET);
        nameValueMap.put("outsideOverlay", View.SCROLLBARS_OUTSIDE_OVERLAY);
        nameValueMap.put("outsideInset", View.SCROLLBARS_OUTSIDE_INSET);
    }

    public AttrDescView_view_View_scrollbarStyle(ClassDescViewBased parent)
    {
        super(parent,"scrollbarStyle","setScrollBarStyle",int.class, nameValueMap,"insideOverlay");
    }

}
