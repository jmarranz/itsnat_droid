package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_drawable_relative_NOFUNCIONA extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    /* EL LEVEL 16 DA MUCHOS PROBLEMAS PARA IMPLEMENTAR drawableStart y drawableEnd (visibilidad de métodos etc), to_do apunta a que to_do está en orden level 17 */

    private static Map<String,Integer> drawableMap = new HashMap<String,Integer>( 2  );
    static
    {
        drawableMap.put("drawableStart",0);
        drawableMap.put("drawableEnd",1);
    }

    protected FieldContainer<Object> fieldDrawables;  // mDrawables
    @SuppressWarnings("unchecked")
    protected FieldContainer<Drawable>[] fieldMemberDrawables = new FieldContainer[2];

    protected int fieldMemberDrawablesIndex;

    protected MethodContainer<Void> methodRelativeDrawablesIfNeeded;

    @SuppressWarnings("unchecked")
    public AttrDescView_widget_TextView_drawable_relative_NOFUNCIONA(ClassDescViewBased parent, String name)
    {
        super(parent,name);
        this.fieldDrawables = new FieldContainer<Object>(parent.getDeclaredClass(),"mDrawables");

        Class classDrawables = fieldDrawables.getField().getType();

        String[] fieldMemberNames = new String[]{"mDrawableStart", "mDrawableEnd"};
        this.fieldMemberDrawables = (FieldContainer<Drawable>[])new FieldContainer[fieldMemberNames.length];
        for (int i = 0; i < fieldMemberNames.length; i++) {
            fieldMemberDrawables[i] = new FieldContainer(classDrawables, fieldMemberNames[i]);
        }

        methodRelativeDrawablesIfNeeded = new MethodContainer<Void>(classDrawables,"setRelativeDrawablesIfNeeded",new Class[]{Drawable.class,Drawable.class});
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        Drawable convValue = getDrawable(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        Drawable drawableStart = fieldMemberDrawablesIndex == 0 ? convValue : getDrawable(view,0);
        Drawable drawableEnd   = fieldMemberDrawablesIndex == 1 ? convValue : getDrawable(view,1);

        methodRelativeDrawablesIfNeeded.invoke((TextView)view,drawableStart,drawableEnd);
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

        return fieldMemberDrawables[index].get(fieldValue);
    }
}
