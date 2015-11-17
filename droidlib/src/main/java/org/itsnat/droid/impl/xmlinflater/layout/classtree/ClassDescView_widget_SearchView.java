package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.ImeOptionsUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.InputTypeUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodMultipleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_SearchView extends ClassDescViewBased
{
    public ClassDescView_widget_SearchView(ClassDescViewMgr classMgr, ClassDescView_widget_LinearLayout parentClass)
    {
        super(classMgr,"android.widget.SearchView",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodBoolean(this,"iconifiedByDefault",true));
        addAttrDesc(new AttrDescReflecMethodMultipleName(this,"imeOptions", ImeOptionsUtil.valueMap,"actionUnspecified"));
        addAttrDesc(new AttrDescReflecMethodMultipleName(this,"inputType", InputTypeUtil.valueMap,"text")); // No estoy seguro que el valor por defecto sea "text" pero parece el más razonable
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this,"maxWidth",0f));
        addAttrDesc(new AttrDescReflecMethodCharSequence(this,"queryHint",""));
    }
}

