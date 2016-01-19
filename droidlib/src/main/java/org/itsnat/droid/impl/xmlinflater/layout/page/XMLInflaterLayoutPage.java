package org.itsnat.droid.impl.xmlinflater.layout.page;

import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.ItsNatDocImpl;
import org.itsnat.droid.impl.browser.ItsNatViewImpl;
import org.itsnat.droid.impl.browser.ItsNatViewNotNullImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventGroupInfo;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
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

    public void setAttributeSingleFromRemote(View view, DOMAttr attr)
    {
        ClassDescViewMgr classDescViewMgr = getInflatedLayoutPageImpl().getXMLInflateRegistry().getClassDescViewMgr();
        ClassDescViewBased viewClassDesc = classDescViewMgr.get(view);

        // Es single y por tanto  si define alguna tarea pendiente tenemos que ejecutarla como si ya no hubiera más atributos pendientes
        PendingViewPostCreateProcess pendingViewPostCreateProcess = viewClassDesc.createPendingViewPostCreateProcess(view, (ViewGroup) view.getParent());
        AttrLayoutContext attrCtx = new AttrLayoutContext(this, pendingViewPostCreateProcess, null);

        viewClassDesc.setAttributeOrInlineEventHandler(view, attr, attrCtx);

        pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        pendingViewPostCreateProcess.executePendingSetAttribsTasks();
        pendingViewPostCreateProcess.executePendingLayoutParamsTasks();
    }

    public void setAttrBatchFromRemote(View view,DOMAttr[] attrArray)
    {
        // Este método por ahora no se llama nunca, pero lo dejamos programado por si ItsNat Server amplía su uso (ver código y notas del llamador de este método)

        PageImpl page = getPageImpl();
        ClassDescViewMgr classDescViewMgr = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLInflateRegistry().getClassDescViewMgr();
        ClassDescViewBased viewClassDesc = classDescViewMgr.get(view);

        PendingViewPostCreateProcess pendingViewPostCreateProcess = viewClassDesc.createPendingViewPostCreateProcess(view, (ViewGroup) view.getParent());
        AttrLayoutContext attrCtx = new AttrLayoutContext(this, pendingViewPostCreateProcess, null);

        int len = attrArray.length;
        for(int i = 0; i < len; i++)
        {
            DOMAttr attr = attrArray[i];
            viewClassDesc.setAttributeOrInlineEventHandler(view, attr, attrCtx);
        }

        pendingViewPostCreateProcess.executePendingSetAttribsTasks();
        pendingViewPostCreateProcess.executePendingLayoutParamsTasks();
    }

    public boolean removeAttributeFromRemote(View view, String namespaceURI, String name)
    {
        ClassDescViewMgr viewMgr = getInflatedLayoutPageImpl().getXMLInflateRegistry().getClassDescViewMgr();
        ClassDescViewBased viewClassDesc = viewMgr.get(view);

        AttrLayoutContext attrCtx = new AttrLayoutContext(this,null,null);
        return viewClassDesc.removeAttributeOrInlineEventHandler(view, namespaceURI, name, attrCtx);
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

        ItsNatDocImpl itsNatDoc = getPageImpl().getItsNatDocImpl();
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

        return false;
    }

    @Override
    public boolean removeAttributeInlineEventHandler(View view, String namespaceURI, String name)
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

        return false;
    }

    @Override
    public View inflateLayout(ViewGroup viewParent,int index)
    {
        InflatedLayoutPageImpl inflatedLayoutPage = getInflatedLayoutPageImpl();
        XMLDOMLayoutPage xmldomLayoutPage = inflatedLayoutPage.getXMLDOMLayoutPage();

        if (xmldomLayoutPage instanceof XMLDOMLayoutPageItsNat)
            inflatedLayoutPage.setLoadScript(((XMLDOMLayoutPageItsNat)xmldomLayoutPage).getLoadScript());

        fillScriptList(xmldomLayoutPage);

        return super.inflateLayout(viewParent,index);
    }

    private void fillScriptList(XMLDOMLayoutPage domLayout)
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
