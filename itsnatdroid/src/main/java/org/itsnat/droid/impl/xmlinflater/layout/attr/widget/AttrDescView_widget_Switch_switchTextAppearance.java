package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;
import android.widget.Switch;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribs;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribsDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_Switch_switchTextAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_Switch_switchTextAppearance(ClassDescViewBased parent)
    {
        super(parent,"switchTextAppearance");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Context ctx = attrCtx.getContext();
        ViewStyleAttribs style = getViewStyle(attr.getResourceDesc(), attrCtx.getXMLInflaterLayout());
        List<DOMAttr> styleItemsDynamicAttribs = (style instanceof ViewStyleAttribsDynamic) ? new ArrayList<DOMAttr>() : null;
        int switchTextAppearanceResId = getViewStyle(style,styleItemsDynamicAttribs,ctx);

        if (switchTextAppearanceResId > 0)
            ((Switch)view).setSwitchTextAppearance(attrCtx.getContext(), switchTextAppearanceResId);

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
        // Android tiene un estilo por defecto
    }
}
