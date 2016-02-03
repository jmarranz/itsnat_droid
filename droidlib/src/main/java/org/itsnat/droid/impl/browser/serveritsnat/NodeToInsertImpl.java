package org.itsnat.droid.impl.browser.serveritsnat;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttributeMap;

import java.util.Map;

/**
 * Created by jmarranz on 25/06/14.
 */
public class NodeToInsertImpl extends NodeImpl
{
    protected String viewName;
    protected DOMAttributeMap attribs = new DOMAttributeMap();
    protected boolean inserted = false;

    public NodeToInsertImpl(String viewName)
    {
        this.viewName = viewName;
        this.view = null; // para que quede claro que view es null inicialmente
    }

    public void setView(View view)
    {
        this.view = view;
    }

    public String getName()
    {
        return viewName;
    }

    public boolean isInserted()
    {
        return inserted;
    }

    public void setInserted()
    {
        this.inserted = true;
        this.attribs = null;
    }

    public DOMAttributeMap getDOMAttributeMap()
    {
        return attribs; // Puede ser null
    }

    public Map<String,DOMAttr> getDOMAttributes()
    {
        return attribs.getDOMAttributes(); // Puede ser null
    }

    public DOMAttr getDOMAttribute(String namespaceURI, String name)
    {
        return attribs.getDOMAttribute(namespaceURI, name);
    }

    public void setDOMAttribute(DOMAttr attr)
    {
        attribs.setDOMAttribute(attr);
    }

    /*
    public void removeDOMAttribute(String namespaceURI, String name) // No se llama nunca pero lo dejamos por coherencia
    {
        attribs.removeDOMAttribute(namespaceURI, name);
    }
    */
}
