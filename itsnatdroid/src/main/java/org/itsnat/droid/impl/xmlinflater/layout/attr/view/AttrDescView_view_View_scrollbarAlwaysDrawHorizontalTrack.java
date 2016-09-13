package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarAlwaysDrawHorizontalTrack extends AttrDescView_view_View_scrollbarAlwaysDrawHVTrack_Base
{
    public AttrDescView_view_View_scrollbarAlwaysDrawHorizontalTrack(ClassDescViewBased parent)
    {
        super(parent,"scrollbarAlwaysDrawHorizontalTrack","mScrollCache","scrollBar","setAlwaysDrawHorizontalTrack",
                MiscUtil.resolveClass("android.view.View$ScrollabilityCache"), MiscUtil.resolveClass("android.widget.ScrollBarDrawable"),
                boolean.class);
    }
}
