package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TableLayout_collapseColumns extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TableLayout_collapseColumns(ClassDescViewBased parent)
    {
        super(parent,"collapseColumns");
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final TableLayout tableView = (TableLayout)view;

        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess == null) // Si es no nulo es que estamos creando el TableLayout y no hace falta ésto
        {
            int maxColumns = getMaxColumns((TableLayout) view);
            for (int i = 0; i < maxColumns; i++)
            {
                if (tableView.isColumnCollapsed(i))
                    tableView.setColumnCollapsed(i, false);
            }
        }

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                String value = attr.getValue();
                if ("".equals(value))
                {
                    int maxColumns = getMaxColumns((TableLayout) view);
                    for (int i = 0; i < maxColumns; i++)
                    {
                        if (tableView.isColumnCollapsed(i))
                            tableView.setColumnCollapsed(i, false);
                    }
                }
                else
                {
                    String[] columns = value.split(",");
                    for (int i = 0; i < columns.length; i++)
                    {
                        String columnStr = columns[i];
                        int column = Integer.parseInt(columnStr);
                        tableView.setColumnCollapsed(column, true);
                    }
                }
            }
        };
        PendingPostInsertChildrenTasks pendingPostInsertChildrenTasks = attrCtx.getPendingPostInsertChildrenTasks();
        if (pendingPostInsertChildrenTasks != null)
            pendingPostInsertChildrenTasks.addTask(task);
        else
            task.run();

    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "", attrCtx);
    }

    private static int getMaxColumns(TableLayout view)
    {
        int maxColumns = 0;
        int childCount = view.getChildCount();
        for(int i = 0; i < childCount; i++)
        {
            View child = view.getChildAt(i);
            if (child instanceof TableRow)
            {
                int columns = ((TableRow)child).getChildCount();
                if (columns > maxColumns) maxColumns = columns;
            }
        }
        return maxColumns;
    }

}
