package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ClassDescElementDrawableChild<TelementDrawable extends ElementDrawableChild> extends ClassDescDrawableOrElementDrawableChild
{
    public ClassDescElementDrawableChild(ClassDescDrawableOrElementDrawableChildMgr classMgr, String elemName)
    {
        super(classMgr, elemName, null);
    }

    public abstract ElementDrawableChild createChildElementDrawable(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx);
}
