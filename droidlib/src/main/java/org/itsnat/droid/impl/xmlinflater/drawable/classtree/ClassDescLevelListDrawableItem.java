package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawableItem extends ClassDescElementDrawableChildWithDrawable<LevelListDrawableItem>
{
    public ClassDescLevelListDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"level-list:item");
    }

    @Override
    public Class<LevelListDrawableItem> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new LevelListDrawableItem(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDrawable(this, "drawable","@null"));

        addAttrDesc(new AttrDescReflecMethodInt(this, "maxLevel",0));
        addAttrDesc(new AttrDescReflecMethodInt(this, "minLevel",0));
    }

}
