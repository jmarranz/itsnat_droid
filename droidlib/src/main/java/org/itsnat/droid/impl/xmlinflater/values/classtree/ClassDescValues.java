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

    public ElementValuesChildNoChildElem createElementValuesChildNoChildren(DOMElemValues domElement, DOMElemValues domElementParent,XMLInflaterValues xmlInflaterValues, ElementValues parentChildValues)
    {
        // Se ha chequeado antes que está t_odo bien en domElement (existe type, name y value de una de las dos formas)

        DOMAttr attrName = domElement.findDOMAttribute(null, "name");
        if (attrName == null) throw new ItsNatDroidException("Unexpected");
        String name = attrName.getValue();

        String value = domElement.getText();

        return createElementValuesChildNoChildren(parentChildValues,name,value);
    }

    @Override
    protected void init()
    {
        // initClass();

        super.init();
    }

    public abstract ElementValuesChildNoChildElem createElementValuesChildNoChildren(ElementValues parentChildValues, String name,String value);
}
