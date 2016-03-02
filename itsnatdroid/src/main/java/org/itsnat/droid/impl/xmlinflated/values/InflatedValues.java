package org.itsnat.droid.impl.xmlinflated.values;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedValues extends InflatedXML
{
    protected ElementValuesResources rootElement;

    public InflatedValues(ItsNatDroidImpl itsNatDroid, XMLDOMValues xmlDOMValues, Context ctx)
    {
        super(itsNatDroid, xmlDOMValues,ctx);
    }

    public static InflatedValues createInflatedValues(ItsNatDroidImpl itsNatDroid, XMLDOMValues xmlDOMValues, Context ctx,PageImpl page)
    {
        return page != null ? new InflatedValuesPage(itsNatDroid, xmlDOMValues, ctx, page) : new InflatedValuesStandalone(itsNatDroid, xmlDOMValues, ctx);
    }

    public XMLDOMValues getXMLDOMValues()
    {
        return (XMLDOMValues) xmlDOM;
    }

    public ElementValuesResources getRootElementResources()
    {
        return rootElement;
    }

    public void setRootElementResources(ElementValuesResources rootElement)
    {
        this.rootElement = rootElement;
    }

}
