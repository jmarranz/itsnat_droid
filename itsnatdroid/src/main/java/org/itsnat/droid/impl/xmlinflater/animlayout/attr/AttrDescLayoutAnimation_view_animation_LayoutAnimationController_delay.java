package org.itsnat.droid.impl.xmlinflater.animlayout.attr;

import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.animlayout.AttrLayoutAnimationContext;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimationBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescLayoutAnimation_view_animation_LayoutAnimationController_delay extends AttrDesc<ClassDescLayoutAnimationBased,LayoutAnimationController,AttrLayoutAnimationContext>
{
    public AttrDescLayoutAnimation_view_animation_LayoutAnimationController_delay(ClassDescLayoutAnimation parent)
    {
        super(parent,"delay");
    }

    @Override
    public void setAttribute(LayoutAnimationController animationController, DOMAttr attr, AttrLayoutAnimationContext attrCtx)
    {
        PercFloat convValue = getPercFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        float value = convValue.toFloatBasedOnDataType();
        animationController.setDelay(value);
    }

    @Override
    public void removeAttribute(LayoutAnimationController animationController, AttrLayoutAnimationContext attrCtx)
    {
        setAttributeToRemove(animationController, "0", attrCtx);
    }
}
