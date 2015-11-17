package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.GridLayout;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodSingleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_GridLayout_alignmentMode extends AttrDescReflecMethodSingleName<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create( 2 );
    static
    {
        valueMap.put("alignBounds", GridLayout.ALIGN_BOUNDS);
        valueMap.put("alignMargins",GridLayout.ALIGN_MARGINS);
    }

    public AttrDescView_widget_GridLayout_alignmentMode(ClassDescViewBased parent)
    {
        super(parent,"alignmentMode",int.class,valueMap,"alignMargins");
    }

}
