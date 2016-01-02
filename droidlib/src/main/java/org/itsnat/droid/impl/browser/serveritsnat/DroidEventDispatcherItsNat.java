package org.itsnat.droid.impl.browser.serveritsnat;

import android.view.View;
import android.view.ViewParent;

import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.impl.browser.DroidEventDispatcher;
import org.itsnat.droid.impl.browser.ItsNatViewImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.DroidEventImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.DroidInputEventImpl;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistener.DroidEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 24/12/2015.
 */
public class DroidEventDispatcherItsNat extends DroidEventDispatcher
{
    public DroidEventDispatcherItsNat(ItsNatDocItsNatImpl itsNatDoc)
    {
        super(itsNatDoc);
    }

    public void dispatch(ItsNatViewImpl viewDataCurrentTarget,String type,Object nativeEvt)
    {
        super.dispatch(viewDataCurrentTarget,type,nativeEvt);

        View viewTarget = viewDataCurrentTarget.getView(); // En "unload" y "load" viewDataCurrentTarget es ItsNatViewNullImpl por lo que getView() es nulo
        dispatch(viewDataCurrentTarget,type,nativeEvt,true, DroidEventImpl.AT_TARGET,viewTarget);
    }

    private void dispatch(ItsNatViewImpl viewDataCurrentTarget,String type,Object nativeEvt,boolean checkUseCapture,int eventPhase,View viewTarget)
    {
        List<DroidEventListener> list = viewDataCurrentTarget.getEventListeners(type);
        if (list == null) return;

        View viewCurrentTarget = viewDataCurrentTarget.getView();
        for (DroidEventListener listener : list)
        {
            if (checkUseCapture && listener.isUseCapture())
            {
                dispatchCapture(viewCurrentTarget,type,nativeEvt,viewTarget);
            }

            DroidEventImpl evtWrapper = (DroidEventImpl)listener.createNormalEvent(nativeEvt);
            try
            {
                evtWrapper.setEventPhase(eventPhase);
                evtWrapper.setTarget(viewTarget);
                listener.dispatchEvent(evtWrapper);
            }
            catch(Exception ex)
            {
                // Desde aquí capturamos todos los fallos del proceso de eventos, el código anterior a dispatchEvent(String,InputEvent) nunca debería
                // fallar, o bien porque es muy simple o porque hay llamadas al código del usuario que él mismo puede controlar sus fallos

                PageImpl page = itsNatDoc.getPageImpl();
                OnEventErrorListener errorListener = page.getOnEventErrorListener();
                if (errorListener != null)
                {
                    HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;
                    errorListener.onError(page, evtWrapper, ex, result);
                }
                else
                {
                    if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException) ex;
                    else throw new ItsNatDroidException(ex);
                }
            }
        }
    }

    private void dispatchCapture(View view,String type,Object nativeEvt,View viewTarget)
    {
        List<ViewParent> tree = getViewTreeParent(view);
        for (ViewParent viewParent : tree)
        {
            ItsNatViewImpl viewParentData = itsNatDoc.getItsNatViewImpl((View) viewParent);
            dispatch(viewParentData, type, nativeEvt, false, DroidInputEventImpl.CAPTURING_PHASE, viewTarget);
        }
    }

    private static List<ViewParent> getViewTreeParent(View view)
    {
        List<ViewParent> tree = new LinkedList<ViewParent>();
        ViewParent parent = view.getParent(); // Asegura que en la lista no está el View inicial
        getViewTree(parent,tree);
        return tree;
    }

    private static void getViewTree(ViewParent view,List<ViewParent> tree)
    {
        if (view == null || !(view instanceof View)) return;
        tree.add(0, view);
        getViewTree(view.getParent(),tree);
    }


}
