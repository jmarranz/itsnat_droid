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
import org.itsnat.droid.impl.util.UniqueIdGenerator;
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
    //protected ItsNatDroidBrowserImpl browser;
    protected InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone;
    protected Interpreter interp = new Interpreter(); // Global
    protected UniqueIdGenerator idGenerator = new UniqueIdGenerator();

    public InflatedLayoutImpl(InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone)
    {
        this.inflatedXMLLayoutStandalone = inflatedXMLLayoutStandalone;
        //this.browser = (ItsNatDroidBrowserImpl)inflatedXMLLayoutStandalone.getItsNatDroidImpl().createItsNatDroidBrowser();

        String uniqueIdForInterpreter = idGenerator.generateId("i"); // i = interpreter
        this.interp = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(interp.getNameSpace(), uniqueIdForInterpreter)); // El StringReader está copiado del código fuente de beanshell2 https://code.google.com/p/beanshell2/source/browse/branches/v2.1/src/bsh/Interpreter.java


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

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedXMLLayoutStandalone;
    }


}
