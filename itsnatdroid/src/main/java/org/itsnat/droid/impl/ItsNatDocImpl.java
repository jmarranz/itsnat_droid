package org.itsnat.droid.impl;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.Toast;

import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.UserData;
import org.itsnat.droid.impl.util.UINotification;
import org.itsnat.droid.impl.util.UserDataImpl;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;

import java.io.StringReader;

import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;

/**
 * Created by jmarranz on 03/03/2017.
 */

public abstract class ItsNatDocImpl implements ItsNatDoc
{
    protected ItsNatDroidImpl itsNatDroid;
    protected UserDataImpl userData;
    protected ItsNatResourcesImpl itsNatResources;
    protected Handler handler;
    protected final Interpreter interp;


    public ItsNatDocImpl(ItsNatDroidImpl itsNatDroid)
    {
        this.itsNatDroid = itsNatDroid;

        String uniqueIdForInterpreter = itsNatDroid.getUniqueIdGenerator().generateId("i"); // i = interpreter
        this.interp = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(itsNatDroid.getInterpreter().getNameSpace(), uniqueIdForInterpreter)); // El StringReader está copiado del código fuente de beanshell2 https://code.google.com/p/beanshell2/source/browse/branches/v2.1/src/bsh/Interpreter.java

        set("itsNatDoc", this);

        StringBuilder methods = new StringBuilder();
        methods.append("alert(data){itsNatDoc.alert(data);}");
        methods.append("toast(value,duration){itsNatDoc.toast(value,duration);}");
        methods.append("toast(value){itsNatDoc.toast(value);}");
        methods.append("eval(code){itsNatDoc.eval(code);}");

        eval(methods.toString()); // Rarísimo que de error

    }

    public ItsNatDroid getItsNatDroid()
    {
        return itsNatDroid;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    public Interpreter getInterpreter()
    {
        return interp;
    }

    @Override
    public ItsNatResources getItsNatResources()
    {
        return getItsNatResourcesImpl();
    }

    public ItsNatResourcesImpl getItsNatResourcesImpl()
    {
        if (itsNatResources == null) this.itsNatResources = createItsNatResourcesImpl();
        return itsNatResources;
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

    public UserData getUserData()
    {
        if (userData == null) this.userData = new UserDataImpl();
        return userData;
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

    @Override
    public int getResourceIdentifier(String name)
    {
        // Formato esperado: package:type/entry  ej my.app:id/someId  o bien simplemente someId

        String packageName;
        int posPkg = name.indexOf(':');
        if (posPkg != -1)
        {
            packageName = null; // Tiene el package en el value, ej "android:" delegamos en Resources.getIdentifier() que lo resuelva
            name = name.substring(posPkg + 1);
        }
        else
        {
            packageName = getContext().getPackageName(); // El package es necesario como parámetro sólo cuando no está en la string (recursos compilados)
        }

        String type;
        int posType = name.indexOf('/');
        if (posType != -1)
        {
            type = null; // Se obtiene del name
            name = name.substring(posType + 1); // Extraemos el name tras el /
        }
        else
        {
            type = "id";
        }

        return getResourceIdentifier(name, type, packageName);
    }

    @Override
    public int getResourceIdentifier(String name, String defType, String defPackage)
    {
        // http://developer.android.com/reference/android/content/res/Resources.html#getIdentifier(java.lang.String, java.lang.String, java.lang.String)
        // Formato esperado: package:type/entry  ej my.app:id/someId  o bien type y package vienen dados como parámetros

        Resources res = getContext().getResources();
        int id = res.getIdentifier(name, defType, defPackage);
        if (id > 0)
            return id;

        XMLInflaterRegistry layoutService = getItsNatDroidImpl().getXMLInflaterRegistry();
        id = layoutService.findViewIdDynamicallyAdded(name);
        return id;
    }

    public abstract ItsNatResourcesImpl createItsNatResourcesImpl();

    public abstract Context getContext();
}
