package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.graphics.Typeface;
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
public abstract class AttrDescViewTypeface extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(4);
    static
    {
        nameValueMap.put("normal", 0);
        nameValueMap.put("sans", 1);
        nameValueMap.put("serif", 2);
        nameValueMap.put("monospace", 3);
    }

    public AttrDescViewTypeface(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Typeface tf = null; // El caso null
        int convValue = AttrDesc.<Integer>parseSingleName(attr.getValue(), nameValueMap);
        switch(convValue)
        {
            case 0: tf = null;
                    break; // Es el caso "normal"
            case 1: tf = Typeface.SANS_SERIF;
                    break;
            case 2: tf = Typeface.SERIF;
                    break;
            case 3: tf = Typeface.MONOSPACE;
                    break;
        }

        TextView textView = (TextView)view;
        Typeface currTf = textView.getTypeface();
        int style = currTf != null? currTf.getStyle() : 0;

        textView.setTypeface(tf,style);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "normal",attrCtx);
    }

}
