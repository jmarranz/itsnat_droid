package org.itsnat.droid.impl.xmlinflater.shared.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterPage;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.HashMap;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class ClassDesc<Tnative>
{
    protected ClassDescMgr classMgr;
    protected String classOrDOMElemName;
    protected ClassDesc<? super Tnative> parentClass;
    protected boolean initiated;
    protected HashMap<String, AttrDesc> attrDescMap;

    public ClassDesc(ClassDescMgr classMgr,String classOrDOMElemName,ClassDesc<? super Tnative> parentClass)
    {
        this.classMgr = classMgr;
        this.classOrDOMElemName = classOrDOMElemName;
        this.parentClass = parentClass;
    }

    public XMLInflateRegistry getXMLInflateRegistry()
    {
        return classMgr.getXMLInflateRegistry();
    }

    public ClassDescMgr getClassDescMgr()
    {
        return classMgr;
    }

    public ClassDesc<? super Tnative> getParentClassDesc()
    {
        return parentClass;
    }


    public String getClassOrDOMElemName()
    {
        return classOrDOMElemName;
    }

    public static PageImpl getPageImpl(XMLInflater xmlInflater)
    {
        // PUEDE SER NULL
        return (xmlInflater instanceof XMLInflaterPage) ? ((XMLInflaterPage) xmlInflater).getPageImpl() : null;
    }

    protected boolean isInit()
    {
        return initiated;
    }

    protected void init()
    {
        this.attrDescMap = new HashMap<String, AttrDesc>();
    }

    protected <T extends AttrDesc> void addAttrDesc(T attrDesc)
    {
        AttrDesc old = attrDescMap.put(attrDesc.getName(),attrDesc);
        if (old != null) throw new ItsNatDroidException("Internal Error, duplicated attribute in this class: " + attrDesc.getName());
    }

    @SuppressWarnings("unchecked")
    protected <TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext> AttrDesc<TclassDesc,TattrTarget,TattrContext> getAttrDesc(String name)
    {
        return attrDescMap.get(name);
    }

    public abstract Class<Tnative> getDeclaredClass();

}
