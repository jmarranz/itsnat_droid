package org.itsnat.droid.impl.xmlinflater.layout.page;

import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.ItsNatDocPageImpl;
import org.itsnat.droid.impl.browser.ItsNatViewImpl;
import org.itsnat.droid.impl.browser.ItsNatViewNotNullImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventGroupInfo;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.util.StringUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterLayoutPage extends XMLInflaterLayout
{
    public XMLInflaterLayoutPage(InflatedXMLLayoutPageImpl inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrResourceInflaterListener);
    }

    public PageImpl getPageImpl()
    {
        return getInflatedXMLLayoutPageImpl().getPageImpl(); // No puede ser nulo
    }

    public InflatedXMLLayoutPageImpl getInflatedXMLLayoutPageImpl()
    {
        return (InflatedXMLLayoutPageImpl) inflatedXML;
    }

    private ItsNatViewImpl getItsNatViewOfInlineHandler(String type,View view)
    {
        if (type.equals("load") || type.equals("unload"))
        {
            // El handler inline de load o unload sólo se puede poner una vez por layout por lo que obligamos
            // a que sea el View root de forma similar al <body> en HTML
            if (view != getInflatedXMLLayoutPageImpl().getRootView())
                throw new ItsNatDroidException("onload/onunload handlers only can be defined in the view root of the layout");
        }

        ItsNatDocPageImpl itsNatDoc = getPageImpl().getItsNatDocImpl();
        return itsNatDoc.getItsNatViewImpl(view);
    }

    private static String getTypeInlineEventHandler(String name)
    {
        if (!name.startsWith("on")) return null;
        String type = name.substring(2);
        DroidEventGroupInfo eventGroup = DroidEventGroupInfo.getEventGroupInfo(type);
        if (eventGroup.getEventGroupCode() == DroidEventGroupInfo.UNKNOWN_EVENT)
            return null;
        return type;
    }

    @Override
    public boolean setAttributeInlineEventHandler(View view, DOMAttr attr)
    {
        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        if (StringUtil.isEmpty(namespaceURI))
        {
            String type = getTypeInlineEventHandler(name);
            if (type != null)
            {
                String value = attr.getValue();
                ItsNatViewImpl viewData = getItsNatViewOfInlineHandler(type,view);
                viewData.setOnTypeInlineCode(name, value);
                if (viewData instanceof ItsNatViewNotNullImpl)
                    ((ItsNatViewNotNullImpl) viewData).registerEventListenerViewAdapter(type);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean removeAttributeInlineEventHandler(View view, String namespaceURI, String name)
    {
        if (StringUtil.isEmpty(namespaceURI))
        {
            String type = getTypeInlineEventHandler(name);
            if (type != null)
            {
                ItsNatViewImpl viewData = getItsNatViewOfInlineHandler(type, view);
                viewData.removeOnTypeInlineCode(name);

                return true;
            }
        }

        return false;
    }

    @Override
    public View inflateLayout(ViewGroup viewParent,int indexChild)
    {
        InflatedXMLLayoutPageImpl inflatedLayoutPage = getInflatedXMLLayoutPageImpl();
        XMLDOMLayoutPage xmldomLayoutPage = inflatedLayoutPage.getXMLDOMLayoutPage();

        // No hace falta definir el loadInitScript en InflatedLayoutPageItsNatImpl pues se obtiene del XMLDOMLayoutPageItsNat y no cambia

        List<DOMScript> scriptListFromTree = xmldomLayoutPage.getDOMScriptList();
        if (scriptListFromTree != null)
        {
            for (DOMScript script : scriptListFromTree)
                inflatedLayoutPage.addScript(script.getCode()); // Podríamos usar los DOMScript directamente pero así es más limpio
        }

        return super.inflateLayout(viewParent,indexChild);
    }

}
