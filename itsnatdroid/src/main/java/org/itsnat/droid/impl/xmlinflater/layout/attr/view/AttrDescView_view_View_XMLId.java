package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutImpl;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_XMLId extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{

    public AttrDescView_view_View_XMLId(ClassDescViewBased parent)
    {
        super(parent, "id");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        XMLInflaterLayout xmlInflaterLayout = attrCtx.getXMLInflaterLayout();
        InflatedXMLLayoutImpl inflated = xmlInflaterLayout.getInflatedXMLLayoutImpl();
        inflated.setXMLId(attr.getValue(), view);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        XMLInflaterLayout xmlInflaterLayout = attrCtx.getXMLInflaterLayout();
        InflatedXMLLayoutImpl inflated = xmlInflaterLayout.getInflatedXMLLayoutImpl();
        inflated.unsetXMLId(view);
    }

}
