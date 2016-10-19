package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawableChildItem extends ClassDescElementDrawableChildWithDrawable<LevelListDrawableChildItem>
{
    public ClassDescLevelListDrawableChildItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"level-list:item",null);
    }

    @Override
    public Class<LevelListDrawableChildItem> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawableChildItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new LevelListDrawableChildItem(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "drawable", "@null"));

        addAttrDescAN(new AttrDescReflecMethodInt(this, "maxLevel", 0));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "minLevel", 0));
    }

}
