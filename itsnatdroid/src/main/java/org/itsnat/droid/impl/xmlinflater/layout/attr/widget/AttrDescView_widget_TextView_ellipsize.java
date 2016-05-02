package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_ellipsize extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,TextUtils.TruncateAt> valueMap = MapSmart.<String,TextUtils.TruncateAt>create( 5 );
    static
    {
        valueMap.put("none", null);
        valueMap.put("start", TextUtils.TruncateAt.START);
        valueMap.put("middle", TextUtils.TruncateAt.MIDDLE);
        valueMap.put("end", TextUtils.TruncateAt.END);
        valueMap.put("marquee", TextUtils.TruncateAt.MARQUEE);
    }

    public AttrDescView_widget_TextView_ellipsize(ClassDescViewBased parent)
    {
        super(parent,"ellipsize");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextUtils.TruncateAt convValue = AttrDesc.<TextUtils.TruncateAt>parseSingleName(attr.getValue(), valueMap);

        ((TextView)view).setEllipsize(convValue);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "@null",attrCtx);
    }
}
