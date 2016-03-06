package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttr;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttrDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_textAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_textAppearance(ClassDescViewBased parent)
    {
        super(parent,"textAppearance");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Context ctx = attrCtx.getContext();
        ViewStyleAttr style = getViewStyle(attr, attrCtx.getXMLInflaterLayout());
        List<DOMAttr> styleItemsDynamicAttribs = (style instanceof ViewStyleAttrDynamic) ? new ArrayList<DOMAttr>() : null;
        int idStyleCompiledOrParent = getViewStyle(style,styleItemsDynamicAttribs,ctx);

        if (idStyleCompiledOrParent > 0)
            ((TextView)view).setTextAppearance(ctx,idStyleCompiledOrParent);

        if (styleItemsDynamicAttribs != null)
        {
            ClassDescViewBased classDesc = getClassDesc();
            for(DOMAttr styleAttr : styleItemsDynamicAttribs)
            {
                classDesc.setAttributeOrInlineEventHandler(view,styleAttr,attrCtx);
            }
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No se que hacer
    }
}
