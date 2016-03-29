package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.HashMap;

/**
 * Created by jmarranz on 6/11/14.
 */
public abstract class ClassDescMgr<TclassDesc extends ClassDesc>
{
    protected XMLInflaterRegistry parent;
    protected final HashMap<String, TclassDesc> classes = new HashMap<String, TclassDesc>();

    public ClassDescMgr(XMLInflaterRegistry parent)
    {
        this.parent = parent;
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return parent;
    }


    public void addClassDesc(TclassDesc classDesc)
    {
        ClassDesc old = classes.put(classDesc.getClassOrDOMElemName(), classDesc);
        if (old != null) throw new ItsNatDroidException("Internal Error, duplicated: " + classDesc.getClassOrDOMElemName());
    }

    public abstract TclassDesc get(String classOrDOMElemName);

    protected abstract void initClassDesc();
}
