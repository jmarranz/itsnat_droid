package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.AnimationSet;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationSet extends ClassDescAnimationBased<AnimationSet>
{
    public ClassDescAnimationSet(ClassDescAnimationMgr classMgr, ClassDescAnimation parentClass)
    {
        super(classMgr, "set", parentClass);
    }

    @Override
    public Class<AnimationSet> getDeclaredClass()
    {
        return AnimationSet.class;
    }

    @Override
    protected AnimationSet createAnimationNative(Context ctx)
    {
        return new AnimationSet(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
        /*
        if (super.isAttributeIgnored(namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && name.equals("ordering");
        */
    }

    /*
    public static String getOrderingAttribute(DOMElemAnimatorSet domElemParent)
    {
        // Por defecto es together
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/animation/AnimatorInflater.java#AnimatorInflater.createAnimatorFromXml
        DOMAttr attr = findAttribute(NamespaceUtil.XMLNS_ANDROID, "ordering", domElemParent.getDOMAttributeMap());
        if (attr == null)
            return "together";
        return attr.getValue();
    }
    */
}
