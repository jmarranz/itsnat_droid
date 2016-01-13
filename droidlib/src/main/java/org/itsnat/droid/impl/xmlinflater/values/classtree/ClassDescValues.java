package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChild;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;
import org.itsnat.droid.impl.xmlinflater.values.XMLInflaterValues;
import org.itsnat.droid.impl.xmlinflater.values.XMLInflaterValuesPage;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescValues<T extends ElementValuesChild> extends ClassDesc<ElementValuesChild>
{
    public ClassDescValues(ClassDescValuesMgr classMgr, String elemName, ClassDescValues<? super ElementValuesChild> parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    public ClassDescValuesMgr getClassDescValuesMgr()
    {
        return (ClassDescValuesMgr) classMgr;
    }

    public ClassDescValues getParentClassDescValues()
    {
        return (ClassDescValues) getParentClassDesc();
    }

    @Override
    public Class<ElementValuesChild> getDeclaredClass()
    {
        return null; // No se necesita, no se implementa
    }

    public static PageImpl getPageImpl(XMLInflaterValues xmlInflaterValues)
    {
        return (xmlInflaterValues instanceof XMLInflaterValuesPage) ? ((XMLInflaterValuesPage) xmlInflaterValues).getPageImpl() : null;
    }

    @Override
    protected void init()
    {
        // initClass();

        super.init();
    }

}
