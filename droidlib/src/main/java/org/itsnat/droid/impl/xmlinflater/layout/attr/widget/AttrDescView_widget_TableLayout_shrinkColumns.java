package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TableLayout;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TableLayout_shrinkColumns extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TableLayout_shrinkColumns(ClassDescViewBased parent)
    {
        super(parent,"shrinkColumns");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final TableLayout tableView = (TableLayout)view;

        OneTimeAttrProcess oneTimeAttrProcess = attrCtx.getOneTimeAttrProcess();
        if (oneTimeAttrProcess == null) // Si es no nulo es que estamos creando el TableLayout y no hace falta ésto
        {
            tableView.setShrinkAllColumns(false);
        }

        String value = attr.getValue();
        if ("".equals(value))
            tableView.setShrinkAllColumns(false);
        else if ("*".equals(value))
            tableView.setShrinkAllColumns(true);
        else
        {
            String[] columns = value.split(",");
            for (int i = 0; i < columns.length; i++)
            {
                String columnStr = columns[i];
                int column = Integer.parseInt(columnStr);
                tableView.setColumnShrinkable(column, true);
            }
        }

    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "", attrCtx);
    }

}
