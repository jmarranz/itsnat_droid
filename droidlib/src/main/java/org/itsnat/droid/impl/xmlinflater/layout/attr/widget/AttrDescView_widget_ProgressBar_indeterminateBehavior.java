package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetInt;

/**
 * Created by jmarranz on 17/09/14.
 */
public class AttrDescView_widget_ProgressBar_indeterminateBehavior extends AttrDescReflecFieldSetInt<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ProgressBar_indeterminateBehavior(ClassDescViewBased parent)
    {
        super(parent,"indeterminateBehavior","mBehavior",1 /*repeat*/); // Valor por default: en el código fuente se ve un AlphaAnimation.RESTART que afortunadamente tiene el valor 1 (idem repeat) y probando a no poner el atributo es un repeat
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        // Redefinimos setAttributeFromRemote porque el dato dado es una cadena no un entero
        String value = attr.getValue();
        int convertedValue;
        if ("repeat".equals(value))  // Siempre en el mismo sentido
            convertedValue = 1;
        else if ("cycle".equals(value)) // Da como una vuelta y media y cambia de sentido
            convertedValue = 2;
        else
            convertedValue = 1; // En el código fuente se ve un AlphaAnimation.RESTART que afortunadamente tiene el valor 1 (idem repeat)

        setField(view,convertedValue);
    }

}
