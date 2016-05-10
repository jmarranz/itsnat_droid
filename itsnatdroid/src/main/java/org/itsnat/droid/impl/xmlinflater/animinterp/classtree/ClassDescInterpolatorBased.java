package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.view.animation.Interpolator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.xmlinflater.animinterp.AttrInterpolatorContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.XMLInflaterInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescInterpolatorBased<T extends Interpolator> extends ClassDescResourceBased<T,AttrInterpolatorContext>
{
    public ClassDescInterpolatorBased(ClassDescInterpolatorMgr classMgr, String tagName, ClassDescInterpolatorBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescInterpolatorMgr getClassDescInterpolatorMgr()
    {
        return (ClassDescInterpolatorMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased<Interpolator> getParentClassDescInterpolatorBased()
    {
        return (ClassDescInterpolatorBased<Interpolator>) getParentClassDescResourceBased(); // Puede ser null
    }

    public abstract Class<T> getDeclaredClass();

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }


}
