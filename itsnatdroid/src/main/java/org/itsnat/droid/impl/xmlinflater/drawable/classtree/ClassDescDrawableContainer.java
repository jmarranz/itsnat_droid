package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescDrawableContainer extends ClassDescElementDrawableBased<DrawableContainer>
{
    public ClassDescDrawableContainer(ClassDescDrawableMgr classMgr,ClassDescElementDrawableBased<? super DrawableContainer> parent)
    {
        super(classMgr,"NONE",parent);
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        return null;
    }

    @Override
    public Class<DrawableContainer> getDrawableOrElementDrawableClass()
    {
        return DrawableContainer.class;
    }
}
