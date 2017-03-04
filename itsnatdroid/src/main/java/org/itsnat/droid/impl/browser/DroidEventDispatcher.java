package org.itsnat.droid.impl.browser;

import android.view.View;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.ItsNatDroidScriptException;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventDispatcherItsNat;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.eventfake.DroidEventFakeImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.DroidEventDispatcherNotItsNat;
import org.itsnat.droid.impl.browser.servernotitsnat.ItsNatDocPageNotItsNatImpl;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Created by jmarranz on 22/08/14.
 */
public abstract class DroidEventDispatcher
{
    protected ItsNatDocPageImpl itsNatDoc;

    public DroidEventDispatcher(ItsNatDocPageImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
    }

    public static DroidEventDispatcher createDroidEventDispatcher(ItsNatDocPageImpl itsNatDoc)
    {
        if (itsNatDoc instanceof ItsNatDocPageItsNatImpl)
            return new DroidEventDispatcherItsNat((ItsNatDocPageItsNatImpl)itsNatDoc);
        else if (itsNatDoc instanceof ItsNatDocPageNotItsNatImpl)
            return new DroidEventDispatcherNotItsNat((ItsNatDocPageNotItsNatImpl)itsNatDoc);
        return null; // Never happens
    }

    public void dispatch(ItsNatViewImpl viewDataCurrentTarget,String type,Object nativeEvt)
    {
        String inlineCode = viewDataCurrentTarget.getOnTypeInlineCode("on" + type);
        if (inlineCode != null)
        {
            executeInlineEventHandler(viewDataCurrentTarget, inlineCode, type, nativeEvt);
        }
    }

    private void executeInlineEventHandler(ItsNatViewImpl viewData,String inlineCode, String type, Object nativeEvt)
    {
        View view = viewData.getView();

        /*
        int eventGroupCode = DroidEventGroupInfo.getEventGroupCode(type);
        DroidEventListener listenerFake = new DroidEventListener(itsNatDoc, view, type, null, null, false, -1, -1, eventGroupCode);
        DroidEventImpl event = (DroidEventImpl)listenerFake.createNormalEvent(nativeEvt);
        event.setEventPhase(DroidEventImpl.AT_TARGET);
        event.setTarget(event.getCurrentTarget()); // El inline handler no participa en capture en web
*/

        DroidEventFakeImpl event = new DroidEventFakeImpl(type,view);

        //PageImpl page = itsNatDoc.getPageImpl();
        Interpreter interp = itsNatDoc.getInterpreter();
        try
        {
            interp.set("event", event);
            try
            {
                interp.eval(inlineCode);
            }
            finally
            {
                interp.set("event", null); // Para evitar un memory leak
            }
        }
        catch (EvalError ex)
        {
            OnScriptErrorListener errorListener = itsNatDoc.getPageImpl().getOnScriptErrorListener();
            if (errorListener != null)
            {
                PageImpl page = itsNatDoc.getPageImpl();
                errorListener.onError(page, inlineCode, ex, event);
            }
            else
            {
                int errorMode = itsNatDoc.getClientErrorMode();
                if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
                {
                    itsNatDoc.showErrorMessage(false, ex.getMessage());
                }
                else throw new ItsNatDroidScriptException(ex, inlineCode);
            }
        }
        catch (Exception ex)
        {
            OnScriptErrorListener errorListener = itsNatDoc.getPageImpl().getOnScriptErrorListener();
            if (errorListener != null)
            {
                PageImpl page = itsNatDoc.getPageImpl();
                errorListener.onError(page, inlineCode, ex, event);
            }
            else
            {
                int errorMode = itsNatDoc.getClientErrorMode();
                if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
                {
                    itsNatDoc.showErrorMessage(false, ex.getMessage());
                }
                else throw new ItsNatDroidScriptException(ex, inlineCode);
            }
        }
    }
}
