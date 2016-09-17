package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.graphics.Typeface;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescViewTextStyle extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 3 );
    static
    {
        nameValueMap.put("normal", Typeface.NORMAL); // 0
        nameValueMap.put("bold", Typeface.BOLD);   // 1
        nameValueMap.put("italic", Typeface.ITALIC); // 2
    }

    public AttrDescViewTextStyle(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final int style = parseMultipleName(attr.getValue(), nameValueMap);

        // Si se define un atributo typeface el style lo ejecutamos después

        final Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                setTextStyle(view,style);
            }
        };

        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
        {
            // Delegamos al final para que esté totalmente claro si hay o no scrollbars
            pendingViewPostCreateProcess.addPendingSetAttribsTask(task);
        }
        else
            task.run();

    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "normal",attrCtx);
    }

    protected abstract void setTextStyle(View view,int style);
}
