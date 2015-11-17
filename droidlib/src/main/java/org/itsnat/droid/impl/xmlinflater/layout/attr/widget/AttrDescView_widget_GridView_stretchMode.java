package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.GridView;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_GridView_stretchMode extends AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create(4);
    static
    {
        valueMap.put("none", GridView.NO_STRETCH);
        valueMap.put("spacingWidth",GridView.STRETCH_SPACING);
        valueMap.put("columnWidth",GridView.STRETCH_COLUMN_WIDTH);
        valueMap.put("spacingWidthUniform",GridView.STRETCH_SPACING_UNIFORM);
    }

    public AttrDescView_widget_GridView_stretchMode(ClassDescViewBased parent)
    {
        super(parent,"stretchMode",int.class,valueMap,"columnWidth");
    }

}
