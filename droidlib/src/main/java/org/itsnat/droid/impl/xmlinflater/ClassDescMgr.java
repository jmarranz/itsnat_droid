package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.HashMap;

/**
 * Created by jmarranz on 6/11/14.
 */
public abstract class ClassDescMgr<TclassDesc extends ClassDesc,TnativeClassBase>
{
    protected XMLInflateRegistry parent;
    protected final HashMap<String, TclassDesc> classes = new HashMap<String, TclassDesc>();

    public ClassDescMgr(XMLInflateRegistry parent)
    {
        this.parent = parent;
    }

    public XMLInflateRegistry getXMLInflateRegistry()
    {
        return parent;
    }


    public void addClassDesc(TclassDesc classDesc)
    {
        ClassDesc old = classes.put(classDesc.getClassOrDOMElemName(), classDesc);
        if (old != null) throw new ItsNatDroidException("Internal Error, duplicated: " + classDesc.getClassOrDOMElemName());
    }

    public abstract TclassDesc get(String classOrDOMElemName);

/*
    public abstract TclassDesc get(Class<? extends TnativeClassBase> nativeClass);

    public abstract TclassDesc get(TnativeClassBase nativeObj);

    public abstract TclassDesc registerUnknown(Class<? extends TnativeClassBase> nativeClass);

    public abstract Class<? extends TnativeClassBase> resolveClass(String className) throws ClassNotFoundException;

    protected abstract TclassDesc createClassDescUnknown(String className,TclassDesc parentClass);
*/


    protected abstract void initClassDesc();
}
