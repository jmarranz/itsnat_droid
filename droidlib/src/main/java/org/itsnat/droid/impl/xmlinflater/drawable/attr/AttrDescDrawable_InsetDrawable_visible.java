package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.InsetDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_InsetDrawable_visible extends AttrDescDrawable<InsetDrawable>
{
    public AttrDescDrawable_InsetDrawable_visible(ClassDescDrawable parent)
    {
        super(parent,"visible");
    }

    @Override
    public void setAttribute(InsetDrawable draw, DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable,Context ctx)
    {
        boolean restart = true; // Ni idea
        draw.setVisible(getBoolean(attr.getValue(),ctx),restart);
    }
}
