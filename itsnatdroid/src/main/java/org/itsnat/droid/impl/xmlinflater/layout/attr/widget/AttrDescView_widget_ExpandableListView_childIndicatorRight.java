package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ExpandableListView;

import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldGetIntRound;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ExpandableListView_childIndicatorRight extends AttrDescReflecFieldGetIntRound<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ExpandableListView_childIndicatorRight(ClassDescViewBased parent)
    {
        super(parent,"childIndicatorRight","mChildIndicatorLeft", -1);
    }

    protected void callMethod(View view,int value)
    {
        ((ExpandableListView) view).setChildIndicatorBounds(getField(view),value);
    }

}
