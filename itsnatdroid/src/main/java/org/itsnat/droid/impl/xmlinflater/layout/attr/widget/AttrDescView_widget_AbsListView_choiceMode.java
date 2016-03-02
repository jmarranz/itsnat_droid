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
public class AttrDescView_widget_AbsListView_choiceMode extends AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(4);
    static
    {
        nameValueMap.put("none", AbsListView.CHOICE_MODE_NONE);
        nameValueMap.put("singleChoice", AbsListView.CHOICE_MODE_SINGLE);
        nameValueMap.put("multipleChoice", AbsListView.CHOICE_MODE_MULTIPLE);
        nameValueMap.put("multipleChoiceModal", AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
    }

    public AttrDescView_widget_AbsListView_choiceMode(ClassDescViewBased parent)
    {
        super(parent,"choiceMode",int.class, nameValueMap,"none");
    }


}
