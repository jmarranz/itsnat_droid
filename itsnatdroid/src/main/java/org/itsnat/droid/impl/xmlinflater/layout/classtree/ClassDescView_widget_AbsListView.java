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

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodColor(this, "cacheColorHint", 0));
        addAttrDescAN(new AttrDescView_widget_AbsListView_choiceMode(this));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "drawSelectorOnTop", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fastScrollEnabled", false));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "listSelector", "setSelector", "@null")); // Hay un drawable por defecto de Android
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "scrollingCache", "setScrollingCacheEnabled", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "smoothScrollbar", "setSmoothScrollbarEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "stackFromBottom", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "textFilterEnabled", false));
        addAttrDescAN(new AttrDescView_widget_AbsListView_transcriptMode(this));


    }
}

