package org.itsnat.droid.impl;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.browser.ItsNatResourcesRemoteImpl;
import org.itsnat.droid.impl.stdalone.ItsNatResourcesStandaloneImpl;
import org.itsnat.droid.impl.util.UINotification;
import org.itsnat.droid.impl.util.UserDataImpl;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.Primitive;

/**
 * Created by jmarranz on 03/03/2017.
 */

public abstract class ItsNatDocImpl implements ItsNatDoc
{
    protected UserDataImpl userData;
    protected ItsNatResourcesImpl itsNatResources;
    protected Handler handler;

    public ItsNatDocImpl()
    {
    }

    @Override
    public ItsNatResources getItsNatResources()
    {
        return getItsNatResourcesImpl();
    }

    @Override
    public void postDelayed(Runnable task,long delay)
    {
        getHandler().postDelayed(task, delay);
    }

    public Handler getHandler()
    {
        if (handler == null) this.handler = new Handler(); // Se asociará (debe) al hilo UI
        return handler;
    }

    @Override
    public void alert(Object value)
    {
        alert("Alert", value);
    }

    @Override
    public void alert(String title,Object value)
    {
        UINotification.alert(title, value, getContext());
    }

    @Override
    public void toast(Object value,int duration)
    {
        UINotification.toast(value, duration, getContext());
    }

    @Override
    public void toast(Object value)
    {
        toast(value, Toast.LENGTH_SHORT);
    }

    @Override
    public Object eval(String code)
    {
        return eval(code,null);
    }

    public abstract Object eval(String code,Object context);

    public abstract void set(String name, Object value);

    public void set(String name, long value) {
        set(name, new Primitive(value));
    }
    public void set(String name, int value) {
        set(name, new Primitive(value));
    }
    public void set(String name, double value) {
        set(name, new Primitive(value));
    }
    public void set(String name, float value) {
        set(name, new Primitive(value));
    }
    public void set(String name, boolean value) {
        set(name, value ? Primitive.TRUE : Primitive.FALSE);
    }

    public ItsNatResourcesImpl getItsNatResourcesImpl()
    {
        if (itsNatResources == null) this.itsNatResources = createItsNatResourcesImpl();
        return itsNatResources;
    }

    public abstract ItsNatResourcesImpl createItsNatResourcesImpl();

    public abstract Context getContext();
}
