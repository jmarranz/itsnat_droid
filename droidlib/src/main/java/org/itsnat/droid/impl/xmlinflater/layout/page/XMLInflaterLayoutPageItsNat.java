package org.itsnat.droid.impl.xmlinflater.layout.page;

import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageItsNatImpl;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 20/01/2016.
 */
public class XMLInflaterLayoutPageItsNat extends XMLInflaterLayoutPage
{
    public XMLInflaterLayoutPageItsNat(InflatedLayoutPageItsNatImpl inflatedXML, int bitmapDensityReference, AttrLayoutInflaterListener inflateLayoutListener, AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, inflateLayoutListener, attrDrawableInflaterListener);
    }

    public InflatedLayoutPageItsNatImpl getInflatedLayoutPageItsNatImpl()
    {
        return (InflatedLayoutPageItsNatImpl) inflatedXML;
    }

    public PageItsNatImpl getPageItsNatImpl()
    {
        return getInflatedLayoutPageItsNatImpl().getPageItsNatImpl(); // No puede ser nulo
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
}
