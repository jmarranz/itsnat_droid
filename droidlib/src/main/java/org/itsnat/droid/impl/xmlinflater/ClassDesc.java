package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.HashMap;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class ClassDesc<Tnative>
{
    protected ClassDescMgr classMgr;
    protected String className;
    protected ClassDesc<? super Tnative> parentClass;
    protected boolean initiated;
    protected HashMap<String, AttrDesc> attrDescMap;

    public ClassDesc(ClassDescMgr classMgr,String className,ClassDesc<? super Tnative> parentClass)
    {
        this.classMgr = classMgr;
        this.className = className;
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

    public String getClassName()
    {
        return className;
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

    public abstract Class<?> getDeclaredClass();

}
