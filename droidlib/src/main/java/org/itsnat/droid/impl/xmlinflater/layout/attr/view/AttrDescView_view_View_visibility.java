package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodSingleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_visibility extends AttrDescReflecMethodSingleName<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create( 3 );
    static
    {
        valueMap.put("visible", View.VISIBLE);
        valueMap.put("invisible",View.INVISIBLE);
        valueMap.put("gone",View.GONE);
    }

    public AttrDescView_view_View_visibility(ClassDescViewBased parent)
    {
        super(parent,"visibility",int.class,valueMap,"visible");
    }
}
