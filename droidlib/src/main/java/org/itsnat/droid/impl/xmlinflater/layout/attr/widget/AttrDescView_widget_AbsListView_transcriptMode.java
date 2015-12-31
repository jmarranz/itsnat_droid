package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.AbsListView;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AbsListView_transcriptMode extends AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(3);
    static
    {
        nameValueMap.put("disabled", AbsListView.TRANSCRIPT_MODE_DISABLED);
        nameValueMap.put("normal", AbsListView.TRANSCRIPT_MODE_NORMAL);
        nameValueMap.put("alwaysScroll", AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    public AttrDescView_widget_AbsListView_transcriptMode(ClassDescViewBased parent)
    {
        super(parent,"transcriptMode",int.class, nameValueMap,"disabled");
    }


}
