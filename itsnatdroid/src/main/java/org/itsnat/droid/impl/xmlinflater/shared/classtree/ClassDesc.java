package org.itsnat.droid.impl.xmlinflater.shared.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttributeMap;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.util.StringUtil;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.HashMap;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class ClassDesc<Tnative>
{
    protected ClassDescMgr<? extends ClassDesc> classMgr;
    protected String classOrDOMElemName;
    protected ClassDesc<? super Tnative> parentClass;
    protected boolean initiated;
    protected HashMap<String, AttrDesc> attrDescAndroidNSMap;
    protected MapLight<String, AttrDesc> attrDescNoNSMap;

    public ClassDesc(ClassDescMgr<? extends ClassDesc> classMgr,String classOrDOMElemName,ClassDesc<? super Tnative> parentClass)
    {
        this.classMgr = classMgr;
        this.classOrDOMElemName = classOrDOMElemName;
        this.parentClass = parentClass;
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return classMgr.getXMLInflaterRegistry();
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
        else if (StringUtil.isEmpty(namespaceURI))
            return getAttrDescNoNS(name);
        else
            return null; // Namespace no gestionado aquí
    }

    protected static DOMAttr findAttribute(String namespaceURI, String attrName, DOMAttributeMap attribMap)
    {
        return attribMap.getDOMAttribute(namespaceURI, attrName);
    }

    public abstract Class<Tnative> getDeclaredClass();

}
