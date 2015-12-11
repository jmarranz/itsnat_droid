package org.itsnat.droid.impl.xmlinflater.layout.page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventGroupInfo;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatViewImpl;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatViewNotNullImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterPage;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterLayoutPage extends XMLInflaterLayout implements XMLInflaterPage
{
    public XMLInflaterLayoutPage(InflatedLayoutPageImpl inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener inflateLayoutListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,inflateLayoutListener,attrDrawableInflaterListener);
    }

    public PageImpl getPageImpl()
    {
        return getInflatedLayoutPageImpl().getPageImpl(); // No puede ser nulo
    }

    public InflatedLayoutPageImpl getInflatedLayoutPageImpl()
    {
        return (InflatedLayoutPageImpl) inflatedXML;
    }

    public void setAttributeFromRemote(View view, DOMAttr attr,ClassDescViewBased viewClassDesc,AttrLayoutContext attrCtx)
    {
        if (viewClassDesc == null)
        {
            ClassDescViewMgr classDescViewMgr = getInflatedLayoutPageImpl().getXMLInflateRegistry().getClassDescViewMgr();
            viewClassDesc = classDescViewMgr.get(view);
        }

        boolean singleSetAttr = (attrCtx == null);
        if (singleSetAttr)
        {
            // Consideramos que el setAttributeRemote en una operación "single" y por tanto si define alguna tarea pendiente tenemos que ejecutarla como si ya no hubiera más atributos pendientes
            PendingViewPostCreateProcess pendingViewPostCreateProcess = viewClassDesc.createPendingViewPostCreateProcess(view, (ViewGroup) view.getParent());
            attrCtx = new AttrLayoutContext(getContext(), this, pendingViewPostCreateProcess, null);
        }

        setAttribute(viewClassDesc, view, attr, attrCtx);

        if (singleSetAttr)
        {
            PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
            pendingViewPostCreateProcess.executePendingSetAttribsTasks();
            pendingViewPostCreateProcess.executePendingLayoutParamsTasks();
        }
    }

    public boolean removeAttributeFromRemote(View view, String namespaceURI, String name)
    {
        ClassDescViewMgr viewMgr = getInflatedLayoutPageImpl().getXMLInflateRegistry().getClassDescViewMgr();
        ClassDescViewBased viewClassDesc = viewMgr.get(view);

        AttrLayoutContext attrCtx = new AttrLayoutContext(getContext(),this,null,null);
        return removeAttribute(viewClassDesc,view, namespaceURI, name,attrCtx);
    }

    private ItsNatViewImpl getItsNatViewOfInlineHandler(String type,View view)
    {
        if (type.equals("load") || type.equals("unload"))
        {
            // El handler inline de load o unload sólo se puede poner una vez por layout por lo que obligamos
            // a que sea el View root de forma similar al <body> en HTML
            if (view != getInflatedLayoutPageImpl().getRootView())
                throw new ItsNatDroidException("onload/onunload handlers only can be defined in the view root of the layout");
        }
        return getPageImpl().getItsNatDocImpl().getItsNatViewImpl(view);
    }

    private String getTypeInlineEventHandler(String name)
    {
        if (!name.startsWith("on")) return null;
        String type = name.substring(2);
        DroidEventGroupInfo eventGroup = DroidEventGroupInfo.getEventGroupInfo(type);
        if (eventGroup.getEventGroupCode() == DroidEventGroupInfo.UNKNOWN_EVENT)
            return null;
        return type;
    }

    @Override
    public boolean setAttribute(ClassDescViewBased classDesc, View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        if (MiscUtil.isEmpty(namespaceURI))
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

        return super.setAttribute(classDesc,view, attr, attrCtx);
    }

    @Override
    public boolean removeAttribute(ClassDescViewBased classDesc,View view, String namespaceURI, String name, AttrLayoutContext attrCtx)
    {
        if (MiscUtil.isEmpty(namespaceURI))
        {
            String type = getTypeInlineEventHandler(name);
            if (type != null)
            {
                ItsNatViewImpl viewData = getItsNatViewOfInlineHandler(type, view);
                viewData.removeOnTypeInlineCode(name);

                return true;
            }
        }

        return super.removeAttribute(classDesc, view, namespaceURI, name, attrCtx);
    }

    @Override
    public View inflateLayout(ViewGroup viewParent)
    {
        InflatedLayoutPageImpl inflatedLayoutPage = getInflatedLayoutPageImpl();
        XMLDOMLayout domLayout = inflatedLayoutPage.getXMLDOMLayout();

        inflatedLayoutPage.setLoadScript(domLayout.getLoadScript());

        fillScriptList(domLayout);

        return super.inflateLayout(viewParent);
    }

    private void fillScriptList(XMLDOMLayout domLayout)
    {
        List<DOMScript> scriptListFromTree = domLayout.getDOMScriptList();
        if (scriptListFromTree != null)
        {
            InflatedLayoutPageImpl inflatedLayoutPage = getInflatedLayoutPageImpl();
            for (DOMScript script : scriptListFromTree)
                inflatedLayoutPage.addScript(script.getCode());
        }
    }
}
