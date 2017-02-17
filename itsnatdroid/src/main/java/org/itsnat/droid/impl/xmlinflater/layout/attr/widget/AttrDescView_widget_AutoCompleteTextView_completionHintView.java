package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueCompiled;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AutoCompleteTextView_completionHintView extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<Integer> fieldHintResource;
    protected FieldContainer<View> fieldHintView;

    public AttrDescView_widget_AutoCompleteTextView_completionHintView(ClassDescViewBased parent)
    {
        super(parent,"completionHintView"); // Android tiene un recurso por defecto

        this.fieldHintResource = new FieldContainer<Integer>(parent.getDeclaredClass(),"mHintResource");
        this.fieldHintView = new FieldContainer<View>(parent.getDeclaredClass(),"mHintView");
    }

    @Override
    public void setAttribute(final View view, final DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        LayoutValue layoutValue = getLayoutValue(attr.getResourceDesc(),attrCtx.getXMLInflaterContext(), attrCtx.getXMLInflaterLayout(), null, -1, null);
        if (layoutValue instanceof LayoutValueDynamic)
        {
            View hintView = ((LayoutValueDynamic) layoutValue).getView();
            TextView textViewChildHint = (TextView)hintView.findViewById(android.R.id.text1);
            fieldHintView.set(view,textViewChildHint);
        }
        else if (layoutValue instanceof LayoutValueCompiled)
        {
            int id = ((LayoutValueCompiled) layoutValue).getLayoutId();
            fieldHintResource.set(view, id);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No hacemos nada, Android tiene un layout por defecto
    }

}
