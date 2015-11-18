package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_scrollbars extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create( 3 );

    static
    {
        valueMap.put("none",0); // SCROLLBARS_NONE
        valueMap.put("horizontal", 0x00000100); // SCROLLBARS_HORIZONTAL
        valueMap.put("vertical", 0x00000200);  // SCROLLBARS_VERTICAL
    }

    protected static final int SCROLLBARS_MASK = 0x00000300;

    protected MethodContainer methodSetFlags;

    @SuppressWarnings("unchecked")
    public AttrDescView_view_View_scrollbars(ClassDescViewBased parent)
    {
        super(parent,"scrollbars");
        this.methodSetFlags = new MethodContainer(parent.getDeclaredClass(),"setFlags",new Class[]{int.class, int.class});
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int scrollbars = parseMultipleName(attr.getValue(), valueMap);

        setFlags(view, scrollbars, SCROLLBARS_MASK);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "none",attrCtx);
    }


    protected void setFlags(View view,int scrollbars,int scrollbarsMask)
    {
        methodSetFlags.invoke(view, scrollbars, scrollbarsMask);
    }
}
