package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_drawable_rect extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
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

    protected FieldContainer<Object> fieldDrawables;  // mDrawables
    @SuppressWarnings("unchecked")
    protected FieldContainer<Drawable>[] fieldMemberDrawables = new FieldContainer[4];

    protected int fieldMemberDrawablesIndex;

    protected FieldContainer<Drawable[]> fieldShowing; // Drawable[] mShowing


    @SuppressWarnings("unchecked")
    public AttrDescView_widget_TextView_drawable_rect(ClassDescViewBased parent, String name)
    {
        super(parent,name);
        this.fieldDrawables = new FieldContainer<Object>(parent.getDeclaredClass(),"mDrawables");

        Class classDrawables = fieldDrawables.getField().getType();

        if (Build.VERSION.SDK_INT < MiscUtil.MARSHMALLOW) // < 23
        {
            String[] fieldMemberNames = new String[]{"mDrawableLeft", "mDrawableTop", "mDrawableRight", "mDrawableBottom"};
            this.fieldMemberDrawables = (FieldContainer<Drawable>[])new FieldContainer[fieldMemberNames.length];
            for (int i = 0; i < fieldMemberNames.length; i++)
            {
                fieldMemberDrawables[i] = new FieldContainer(classDrawables, fieldMemberNames[i]);
            }
        }
        else
        {
            this.fieldShowing = new FieldContainer(classDrawables, "mShowing"); // Drawables[]
        }

        this.fieldMemberDrawablesIndex = drawableMap.get(name);
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        Drawable convValue = getDrawable(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        Drawable drawableLeft   = fieldMemberDrawablesIndex == LEFT ? convValue : getDrawable(view,LEFT);
        Drawable drawableTop    = fieldMemberDrawablesIndex == TOP ? convValue : getDrawable(view,TOP);
        Drawable drawableRight  = fieldMemberDrawablesIndex == RIGHT ? convValue : getDrawable(view,RIGHT);
        Drawable drawableBottom = fieldMemberDrawablesIndex == BOTTOM ? convValue : getDrawable(view,BOTTOM);

        ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "@null", attrCtx);
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
            // fieldValue -> mShowing
            Drawable[] array = fieldShowing.get(fieldValue);
            Drawable drawable = (Drawable)Array.get(array, index);
            return drawable;
        }
    }
}
