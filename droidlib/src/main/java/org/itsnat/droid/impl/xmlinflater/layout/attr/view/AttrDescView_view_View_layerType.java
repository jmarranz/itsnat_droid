package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_layerType extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 3 );
    static
    {
        nameValueMap.put("none", View.LAYER_TYPE_NONE);
        nameValueMap.put("software", View.LAYER_TYPE_SOFTWARE);
        nameValueMap.put("hardware", View.LAYER_TYPE_HARDWARE);
    }

    public AttrDescView_view_View_layerType(ClassDescViewBased parent)
    {
        super(parent,"layerType");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int convertedValue = AttrDesc.<Integer>parseSingleName(attr.getValue(), nameValueMap);

        int layerType = convertedValue;
        view.setLayerType(layerType,null);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "none",attrCtx);
    }


}
