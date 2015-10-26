package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_compoundDrawables extends AttrDescView
{
    // Constantes en la subclase Drawables
    private static final int LEFT   = 0;
    private static final int TOP    = 1;
    private static final int RIGHT  = 2;
    private static final int BOTTOM = 3;

    private static Map<String,Integer> drawableMap = new HashMap<String,Integer>( 4  );
    static
    {
        drawableMap.put("drawableLeft",LEFT);
        drawableMap.put("drawableTop",TOP);
        drawableMap.put("drawableRight",RIGHT);
        drawableMap.put("drawableBottom",BOTTOM);
    }

    protected FieldContainer fieldDrawables;  // mDrawables
    @SuppressWarnings("unchecked")
    protected FieldContainer<Drawable>[] fieldMemberDrawables = new FieldContainer[4];

    protected FieldContainer<Drawable[]> fieldShowing; // Drawable[] mShowing


    @SuppressWarnings("unchecked")
    public AttrDescView_widget_TextView_compoundDrawables(ClassDescViewBased parent, String name)
    {
        super(parent,name);
        this.fieldDrawables = new FieldContainer(parent.getDeclaredClass(),"mDrawables");

        Class clasz = fieldDrawables.getField().getType();

        if (Build.VERSION.SDK_INT < MiscUtil.MARSHMALLOW) // < 23
        {
            String[] fieldMemberNames = new String[]{"mDrawableLeft", "mDrawableTop", "mDrawableRight", "mDrawableBottom"};
            this.fieldMemberDrawables = new FieldContainer[fieldMemberNames.length];
            for (int i = 0; i < fieldMemberNames.length; i++) {
                fieldMemberDrawables[i] = new FieldContainer(clasz, fieldMemberNames[i]);
            }
        }
        else
        {
            this.fieldShowing = new FieldContainer(clasz, "mShowing");
        }
    }

    public void setAttribute(final View view, final DOMAttr attr, final XMLInflaterLayout xmlInflaterLayout, final Context ctx, OneTimeAttrProcess oneTimeAttrProcess, PendingPostInsertChildrenTasks pending)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                Drawable convValue = getDrawable(attr,ctx,xmlInflaterLayout);

                int index = drawableMap.get(name);

                Drawable drawableLeft   = index == LEFT ? convValue : getDrawable(view,LEFT);
                Drawable drawableTop    = index == TOP ? convValue : getDrawable(view,TOP);
                Drawable drawableRight  = index == RIGHT ? convValue : getDrawable(view,RIGHT);
                Drawable drawableBottom = index == BOTTOM ? convValue : getDrawable(view,BOTTOM);

                ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            }
        };
        if (DOMAttrRemote.isPendingToDownload(attr))
            processDownloadTask((DOMAttrRemote)attr,task,xmlInflaterLayout);
        else
            task.run();
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "@null", xmlInflaterLayout, ctx);
    }

    protected Drawable getDrawable(View view,int index)
    {
        Object fieldValue = fieldDrawables.get(view);
        if (fieldValue == null)
            return null; // Esto es normal, y es cuando todavía no se ha definido ningún Drawable, setCompoundDrawablesWithIntrinsicBounds lo creará en la primera llamada

        if (Build.VERSION.SDK_INT < MiscUtil.MARSHMALLOW) // < 23
        {
            return fieldMemberDrawables[index].get(fieldValue);
        }
        else
        {
            Object array = fieldShowing.get(fieldValue);
            Object drawable = Array.get(array, index);
            return (Drawable)drawable;
        }
    }
}
