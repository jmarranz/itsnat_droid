package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_RelativeLayout extends ClassDescViewBased
{
    public ClassDescView_widget_RelativeLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.RelativeLayout",parentClass);
    }



    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.valueMap, "left|top"));
        addAttrDescAN(new AttrDescReflecMethodId(this, "ignoreGravity", -1));
    }
}

