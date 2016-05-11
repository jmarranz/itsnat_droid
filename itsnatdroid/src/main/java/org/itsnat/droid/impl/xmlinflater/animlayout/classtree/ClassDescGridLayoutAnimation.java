package org.itsnat.droid.impl.xmlinflater.animlayout.classtree;

import android.content.Context;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.xmlinflater.animlayout.ClassDescLayoutAnimationMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescGridLayoutAnimation extends ClassDescLayoutAnimationBased<GridLayoutAnimationController>
{
    public ClassDescGridLayoutAnimation(ClassDescLayoutAnimationMgr classMgr, ClassDescLayoutAnimationBased<LayoutAnimationController> parentClass)
    {
        super(classMgr, "gridLayoutAnimation", parentClass);
    }

    @Override
    public Class<GridLayoutAnimationController> getDeclaredClass()
    {
        return GridLayoutAnimationController.class;
    }

    @Override
    protected GridLayoutAnimationController createResourceNative(Context ctx)
    {
        return new GridLayoutAnimationController(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        /*
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "fromAlpha", "mFromAlpha",1.0f));
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "toAlpha", "mToAlpha",1.0f));
        */
    }

}
