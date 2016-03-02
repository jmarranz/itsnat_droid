package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.LinearLayout;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_LinearLayout_showDividers extends AttrDescReflecMethodNameMultiple<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 4 );
    static
    {
        nameValueMap.put("none", LinearLayout.SHOW_DIVIDER_NONE);
        nameValueMap.put("beginning", LinearLayout.SHOW_DIVIDER_BEGINNING);
        nameValueMap.put("middle", LinearLayout.SHOW_DIVIDER_MIDDLE);
        nameValueMap.put("end", LinearLayout.SHOW_DIVIDER_END);
    }

    public AttrDescView_widget_LinearLayout_showDividers(ClassDescViewBased parent)
    {
        super(parent,"showDividers", nameValueMap,"none");
    }

}
