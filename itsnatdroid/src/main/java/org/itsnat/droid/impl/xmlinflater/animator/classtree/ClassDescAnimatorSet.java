package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorSet;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorSet extends ClassDescAnimatorBased<AnimatorSet>
{
    public ClassDescAnimatorSet(ClassDescAnimatorMgr classMgr, ClassDescAnimator parentClass)
    {
        super(classMgr, "set", parentClass);
    }

    @Override
    public Class<AnimatorSet> getDeclaredClass()
    {
        return AnimatorSet.class;
    }

    @Override
    protected AnimatorSet createResourceNative(Context ctx)
    {
        return new AnimatorSet();
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && name.equals("ordering");
    }

    public static String getOrderingAttribute(DOMElemAnimatorSet domElemParent)
    {
        // Por defecto es together
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/animation/AnimatorInflater.java#AnimatorInflater.createAnimatorFromXml
        DOMAttr attr = findAttribute(NamespaceUtil.XMLNS_ANDROID, "ordering", domElemParent.getDOMAttributeMap());
        if (attr == null)
            return "together";
        return attr.getValue();
    }
}
