package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AbsListView_choiceMode;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_AbsListView_transcriptMode;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_AbsListView extends ClassDescViewBased
{
    public ClassDescView_widget_AbsListView(ClassDescViewMgr classMgr,ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.AbsListView",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodColor(this,"cacheColorHint",0));
        addAttrDesc(new AttrDescView_widget_AbsListView_choiceMode(this));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"drawSelectorOnTop",false));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"fastScrollEnabled",false));
        addAttrDesc(new AttrDescReflecMethodDrawable(this,"listSelector","setSelector",null)); // Hay un drawable por defecto de Android
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"scrollingCache","setScrollingCacheEnabled",false));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"smoothScrollbar","setSmoothScrollbarEnabled",true));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"stackFromBottom",false));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"textFilterEnabled",false));
        addAttrDesc(new AttrDescView_widget_AbsListView_transcriptMode(this));


    }
}

