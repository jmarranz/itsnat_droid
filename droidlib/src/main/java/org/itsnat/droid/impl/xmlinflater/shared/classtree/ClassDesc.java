package org.itsnat.droid.impl.xmlinflater.shared.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
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
    protected HashMap<String, AttrDesc> attrDescAndroidNSMap;
    protected MapLight<String, AttrDesc> attrDescNoNSMap;

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
        this.initiated = true;
    }

    protected <T extends AttrDesc> void addAttrDescAN(T attrDesc) // AN = Android Namespace
    {
        if (attrDescAndroidNSMap == null) this.attrDescAndroidNSMap = new HashMap<String, AttrDesc>();

        AttrDesc old = attrDescAndroidNSMap.put(attrDesc.getName(),attrDesc);
        if (old != null) throw new ItsNatDroidException("Internal Error, duplicated attribute in this class or element: " + getClassOrDOMElemName() + " " + NamespaceUtil.XMLNS_ANDROID + " " + attrDesc.getName());
    }

    protected <T extends AttrDesc> void addAttrDescNoNS(T attrDesc) // NoNS no namespace
    {
        if (attrDescNoNSMap == null) this.attrDescNoNSMap = new MapLight<String, AttrDesc>(3);

        AttrDesc old = attrDescNoNSMap.put(attrDesc.getName(),attrDesc);
        if (old != null) throw new ItsNatDroidException("Internal Error, duplicated attribute in this class or element: " + getClassOrDOMElemName() + " " + attrDesc.getName());
    }


    @SuppressWarnings("unchecked")
    protected <TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext> AttrDesc<TclassDesc,TattrTarget,TattrContext> getAttrDescAN(String name)
    {
        if (attrDescAndroidNSMap == null) return null;
        return attrDescAndroidNSMap.get(name);
    }

    @SuppressWarnings("unchecked")
    protected <TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext> AttrDesc<TclassDesc,TattrTarget,TattrContext> getAttrDescNoNS(String name)
    {
        if (attrDescNoNSMap == null) return null;
        return attrDescNoNSMap.get(name);
    }

    @SuppressWarnings("unchecked")
    protected <TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext> AttrDesc<TclassDesc,TattrTarget,TattrContext> getAttrDesc(String namespaceURI, String name)
    {
        if (NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI))
            return getAttrDescAN(name);
        else if (MiscUtil.isEmpty(namespaceURI))
            return getAttrDescNoNS(name);
        else
            return null; // Namespace no gestionado aquí
    }


    public abstract Class<Tnative> getDeclaredClass();

}
