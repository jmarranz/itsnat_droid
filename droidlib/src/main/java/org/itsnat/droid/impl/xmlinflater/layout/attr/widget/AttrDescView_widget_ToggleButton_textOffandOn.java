package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ToggleButton_textOffandOn extends AttrDescReflecMethodCharSequence<ClassDescViewBased,View,AttrLayoutContext>
{
    protected MethodContainer methodContainer;

    @SuppressWarnings("unchecked")
    public AttrDescView_widget_ToggleButton_textOffandOn(ClassDescViewBased parent, String name)
    {
        super(parent,name,null); // Android tiene un texto por defecto

        this.methodContainer = new MethodContainer(parent.getDeclaredClass(),"syncTextState");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        super.setAttribute(view, attr, attrCtx);

        methodContainer.invoke(view); // Hay que llamar a este método syncTextState sino no se entera del cambio, ni siquiera en creación via parse dinámico
    }
}
