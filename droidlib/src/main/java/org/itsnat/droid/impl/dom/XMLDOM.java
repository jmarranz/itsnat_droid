package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;

import java.util.LinkedList;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class XMLDOM
{
    protected TimestampExtended timestampExt;
    protected MapLight<String,String> rootNamespacesByPrefix = new MapLight<String,String>(); // Se lee en multihilo pero se crea en monohilo por lo que no lo sincronizamos
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
        rootNamespacesByPrefix.put(prefix, ns);
        if (NamespaceUtil.XMLNS_ANDROID.equals(ns))
            this.androidNSPrefix = prefix;
    }

    public MapLight<String,String> getRootNamespacesByPrefix()
    {
        return rootNamespacesByPrefix;
    }

    public String getRootNamespaceByPrefix(String prefix)
    {
        return rootNamespacesByPrefix.get(prefix);
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
        cloned.rootNamespacesByPrefix = this.rootNamespacesByPrefix;
        cloned.androidNSPrefix = this.androidNSPrefix;
        cloned.rootElement = this.rootElement;
        cloned.remoteAttribList = this.remoteAttribList;
    }

}
