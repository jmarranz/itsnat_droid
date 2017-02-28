package org.itsnat.droid.impl.stdalone;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.UserData;
import org.itsnat.droid.impl.browser.ItsNatDroidBrowserImpl;
import org.itsnat.droid.impl.util.UINotification;
import org.itsnat.droid.impl.util.UserDataImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

import java.io.StringReader;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;

/**
 * Created by jmarranz on 28/02/2017.
 */

public class InflatedLayoutImpl implements InflatedLayout
{
    protected ItsNatDroidBrowserImpl browser;
    protected InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone;
    protected final Interpreter interp;
    protected ItsNatResourcesStandaloneImpl itsNatResourcesStandalone;
    protected UserDataImpl userData;

    public InflatedLayoutImpl(InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone)
    {
        this.inflatedXMLLayoutStandalone = inflatedXMLLayoutStandalone;
        this.browser = (ItsNatDroidBrowserImpl)inflatedXMLLayoutStandalone.getItsNatDroidImpl().createItsNatDroidBrowser();

        String uniqueIdForInterpreter = browser.getUniqueIdGenerator().generateId("i"); // i = interpreter
        this.interp = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(browser.getInterpreter().getNameSpace(), uniqueIdForInterpreter)); // El StringReader está copiado del código fuente de beanshell2 https://code.google.com/p/beanshell2/source/browse/branches/v2.1/src/bsh/Interpreter.java


        set("inflatedLayout", this);

        StringBuilder methods = new StringBuilder();
        methods.append("alert(data){inflatedLayout.alert(data);}");
        methods.append("toast(value,duration){inflatedLayout.toast(value,duration);}");
        methods.append("toast(value){inflatedLayout.toast(value);}");
        methods.append("eval(code){inflatedLayout.eval(code);}");

        eval(methods.toString()); // Rarísimo que de error
    }

    @Override
    public Context getContext()
    {
        return inflatedXMLLayoutStandalone.getContext();
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
        try
        {
            return interp.eval(code);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }

    @Override
    public void set(String name, Object value )
    {
        try
        {
            interp.set(name, value);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }
    @Override
    public void set(String name, long value) {
        set(name, new Primitive(value));
    }
    @Override
    public void set(String name, int value) {
        set(name, new Primitive(value));
    }
    @Override
    public void set(String name, double value) {
        set(name, new Primitive(value));
    }
    @Override
    public void set(String name, float value) {
        set(name, new Primitive(value));
    }
    @Override
    public void set(String name, boolean value) {
        set(name, value ? Primitive.TRUE : Primitive.FALSE);
    }
    @Override
    public void unset(String name)  {
        try
        {
            interp.unset(name);
        }
        catch (EvalError ex)
        {
            throw new ItsNatDroidException(ex);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }

    @Override
    public ItsNatDroidBrowser getItsNatDroidBrowser()
    {
        return browser;
    }

    @Override
    public ItsNatResources getItsNatResources()
    {
        return getItsNatResourcesStandaloneImpl();
    }

    @Override
    public ItsNatDroid getItsNatDroid()
    {
        return browser.getItsNatDroid();
    }

    @Override
    public View getRootView()
    {
        return inflatedXMLLayoutStandalone.getRootView();
    }

    @Override
    public View findViewByXMLId(String id)
    {
        return inflatedXMLLayoutStandalone.findViewByXMLId(id);
    }

    @Override
    public UserData getUserData()
    {
        return userData;
    }

    public ItsNatResourcesStandaloneImpl getItsNatResourcesStandaloneImpl()
    {
        if (itsNatResourcesStandalone == null)
            this.itsNatResourcesStandalone = new ItsNatResourcesStandaloneImpl(this);
        return itsNatResourcesStandalone;
    }

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedXMLLayoutStandalone;
    }


}
