package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.AbsSpinner;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ListViewAndAbsSpinner_entries extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ListViewAndAbsSpinner_entries(ClassDescViewBased parent)
    {
        super(parent,"entries");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        CharSequence[] entries = getTextArray(attr.getValue(),attrCtx.getContext());

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(attrCtx.getContext(), android.R.layout.simple_list_item_1, entries);
        if (view instanceof ListView)
            ((ListView)view).setAdapter(adapter);
        else if (view instanceof AbsSpinner)
            ((AbsSpinner)view).setAdapter(adapter);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0",attrCtx);
    }

}
