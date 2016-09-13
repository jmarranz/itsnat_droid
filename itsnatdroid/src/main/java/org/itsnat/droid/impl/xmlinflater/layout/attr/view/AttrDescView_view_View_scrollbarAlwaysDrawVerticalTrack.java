package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbarAlwaysDrawVerticalTrack extends AttrDescView_view_View_scrollbarAlwaysDrawHVTrack_Base
{
    public AttrDescView_view_View_scrollbarAlwaysDrawVerticalTrack(ClassDescViewBased parent)
    {
        super(parent,"scrollbarAlwaysDrawVerticalTrack","mScrollCache","scrollBar","setAlwaysDrawVerticalTrack",
                MiscUtil.resolveClass("android.view.View$ScrollabilityCache"),MiscUtil.resolveClass("android.widget.ScrollBarDrawable"),
                boolean.class);
    }

}
