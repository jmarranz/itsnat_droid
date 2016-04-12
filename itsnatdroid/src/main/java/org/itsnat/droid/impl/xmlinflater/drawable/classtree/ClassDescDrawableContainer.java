package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescDrawableContainer extends ClassDescElementDrawableRoot<DrawableContainer>
{
    public ClassDescDrawableContainer(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"NONE");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        return null;
    }

    @Override
    public Class<DrawableContainer> getDrawableOrElementDrawableClass()
    {
        return DrawableContainer.class;
    }
}
