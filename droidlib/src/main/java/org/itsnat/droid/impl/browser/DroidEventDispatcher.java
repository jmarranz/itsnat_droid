package org.itsnat.droid.impl.browser;

import android.view.View;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.ItsNatDroidScriptException;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventDispatcherItsNat;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.eventfake.DroidEventFakeImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.DroidEventDispatcherNotItsNat;
import org.itsnat.droid.impl.browser.servernotitsnat.ItsNatDocNotItsNatImpl;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Created by jmarranz on 22/08/14.
 */
public abstract class DroidEventDispatcher
{
    protected ItsNatDocImpl itsNatDoc;

    public DroidEventDispatcher(ItsNatDocImpl itsNatDoc)
    {
        this.itsNatDoc = itsNatDoc;
    }

    public static DroidEventDispatcher createDroidEventDispatcher(ItsNatDocImpl itsNatDoc)
    {
        if (itsNatDoc instanceof ItsNatDocItsNatImpl)
            return new DroidEventDispatcherItsNat((ItsNatDocItsNatImpl)itsNatDoc);
        else if (itsNatDoc instanceof ItsNatDocNotItsNatImpl)
            return new DroidEventDispatcherNotItsNat((ItsNatDocNotItsNatImpl)itsNatDoc);
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

        Interpreter interp = itsNatDoc.getPageImpl().getInterpreter();
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
                errorListener.onError(inlineCode,ex, event);
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
                errorListener.onError(inlineCode, ex, event);
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
