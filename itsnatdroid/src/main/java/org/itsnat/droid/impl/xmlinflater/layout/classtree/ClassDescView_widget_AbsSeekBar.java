package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_AbsSeekBar extends ClassDescViewBased
{
    public ClassDescView_widget_AbsSeekBar(ClassDescViewMgr classMgr,ClassDescViewBased parentClass)
    {
        super(classMgr,"android.widget.AbsSeekBar",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        // El atributo android:thumb está documentado en SeekBar pero implementado realmente en AbsSeekBar
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "thumb", "@null")); // Android tiene un drawable por defecto

        // android:android:thumbTint es level 21
        // android:android:thumbTintMode es level 21
        // android:android:tickMarkTint es level 24
        // android:android:tickMarkTintMode es level 24
    }
}

