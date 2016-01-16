package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.util.LinkedList;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class XMLDOM
{
    protected TimestampExtended timestampExt;
    protected MapLight<String,String> namespacesByPrefix = new MapLight<String,String>();
    protected String androidNSPrefix;
    protected DOMElement rootElement;
    protected LinkedList<DOMAttrRemote> remoteAttribList;

    public XMLDOM()
    {
        this.timestampExt = new TimestampExtended();
    }

    public TimestampExtended getTimestampExtended()
    {
        return timestampExt;
    }

    public String getAndroidNSPrefix()
    {
        return androidNSPrefix;
    }

    public void addNamespace(String prefix,String ns)
    {
        namespacesByPrefix.put(prefix,ns);
        if (NamespaceUtil.XMLNS_ANDROID.equals(ns))
            this.androidNSPrefix = prefix;
    }

    public MapLight<String,String> getNamespacesByPrefix()
    {
        return namespacesByPrefix;
    }

    public String getNamespace(String prefix)
    {
        return namespacesByPrefix.get(prefix);
    }

    public DOMElement getRootDOMElement()
    {
        return rootElement;
    }

    public void setRootElement(DOMElement rootElement)
    {
        this.rootElement = rootElement;
    }

    public LinkedList<DOMAttrRemote> getDOMAttrRemoteList()
    {
        return remoteAttribList;
    }

    public void addDOMAttrRemote(DOMAttrRemote attr)
    {
        if (remoteAttribList == null) this.remoteAttribList = new LinkedList<DOMAttrRemote>();
        remoteAttribList.add(attr);
    }

    public void partialClone(XMLDOM cloned)
    {
        cloned.timestampExt = new TimestampExtended(this.timestampExt); // No es necesario clonar pero así aseguramos que no el original no cambia a través del clone accidentalmente y viceversa
        cloned.namespacesByPrefix = this.namespacesByPrefix;
        cloned.androidNSPrefix = this.androidNSPrefix;
        cloned.rootElement = this.rootElement;
        cloned.remoteAttribList = this.remoteAttribList;
    }
}
