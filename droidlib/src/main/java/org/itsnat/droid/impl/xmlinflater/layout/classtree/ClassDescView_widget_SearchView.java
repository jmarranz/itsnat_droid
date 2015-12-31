package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.ImeOptionsUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.InputTypeUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_SearchView extends ClassDescViewBased
{
    public ClassDescView_widget_SearchView(ClassDescViewMgr classMgr, ClassDescView_widget_LinearLayout parentClass)
    {
        super(classMgr,"android.widget.SearchView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "iconifiedByDefault", true));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "imeOptions", ImeOptionsUtil.nameValueMap, "actionUnspecified"));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "inputType", InputTypeUtil.nameValueMap, "text")); // No estoy seguro que el valor por defecto sea "text" pero parece el más razonable
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "maxWidth", 0f));
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "queryHint", ""));
    }
}

