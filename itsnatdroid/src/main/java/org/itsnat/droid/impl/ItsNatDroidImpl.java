package org.itsnat.droid.impl;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.R;
import org.itsnat.droid.impl.browser.ItsNatDroidBrowserImpl;
import org.itsnat.droid.impl.browser.serveritsnat.CustomFunction;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.util.UniqueIdGenerator;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.InflateLayoutRequestStandaloneImpl;

import bsh.EvalError;
import bsh.Interpreter;


/**
 * Created by jmarranz on 3/05/14.
 */
public class ItsNatDroidImpl implements ItsNatDroid
{
    public static ItsNatDroidImpl DEFAULT;

    protected Application app;
    protected XMLInflaterRegistry xmlInflaterRegistry = new XMLInflaterRegistry(this); // Sólo creamos una instancia pues cuesta mucho instanciar los objetos procesadores de clases y atributos
    protected XMLDOMRegistry xmlDOMRegistry = new XMLDOMRegistry(this);
    protected Interpreter interp = new Interpreter(); // Global
    protected UniqueIdGenerator idGenerator = new UniqueIdGenerator();

    public ItsNatDroidImpl(Application app)
    {
        this.app = app;
        try
        {
            interp.set(NamespaceUtil.XMLNS_ANDROID_ALIAS, NamespaceUtil.XMLNS_ANDROID);

            // interp.setStrictJava(true);

            // No definimos aquí set("itsNatDoc",null) o similar para poder definir métodos alert y toast
            // porque queda "itsNatDoc" como global y cualquier set() cambia valor global y por tanto ya no es local por Page


            // Funciones de utilidad que se reflejarán en los Interpreter hijos, pero así se interpretan una sola vez
            StringBuilder code = new StringBuilder();

            code.append("import org.itsnat.droid.*;");
            code.append("import org.itsnat.droid.event.*;");
            code.append("import android.view.*;");
            code.append("import android.widget.*;");
            code.append("import " + CustomFunction.class.getName() + ";");


            code.append("arr(a){return new Object[]{a};}");
            code.append("arr(a){return new Object[]{a};}");
            code.append("arr(a,b){return new Object[]{a,b};}");
            code.append("arr(a,b,c){return new Object[]{a,b,c};}");
            code.append("arr(a,b,c,d){return new Object[]{a,b,c,d};}");

            interp.eval(code.toString());
        }
        catch (EvalError ex) { throw new ItsNatDroidException(ex); } // No debería ocurrir
    }

    public static void init(Application app)
    {
        if (DEFAULT != null) throw new ItsNatDroidException("ItsNat Droid is already initialized");
        DEFAULT = new ItsNatDroidImpl(app);
    }

    public Application getApplication()
    {
        return app;
    }

    public UniqueIdGenerator getUniqueIdGenerator()
    {
        return idGenerator;
    }

    public Interpreter getInterpreter()
    {
        return interp;
    }

    @Override
    public ItsNatDroidBrowser createItsNatDroidBrowser()
    {
        return new ItsNatDroidBrowserImpl(this);
    }

    @Override
    public InflateLayoutRequest createInflateLayoutRequest()
    {
        // El modelo ItsNat está muy bien pero ofrecemos como alternativa que el propio programador se descargue sus layouts
        // y los gestione a su manera
        return new InflateLayoutRequestStandaloneImpl(this);
    }

    @Override
    public String getVersionName()
    {
        Resources res = app.getApplicationContext().getResources();
        return res.getString(R.string.itsNatDroidLibVersionName);
    }

    @Override
    public int getVersionCode()
    {
        Resources res = app.getApplicationContext().getResources();
        return res.getInteger(R.integer.itsNatDroidLibVersionCode);
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return xmlInflaterRegistry;
    }

    public XMLDOMRegistry getXMLDOMRegistry()
    {
        return xmlDOMRegistry;
    }

    public void onConfigurationChanged(Activity activity, Configuration newConfig)
    {
        // Este método conviene ser llamado en onConfigurationChanged(Configuration newConfig) en la actividad de la app que usa la librería
        // Llamando a cleanCaches de esta manera se recrean los DOMAttrDynamic en los cuales se aplican los filtros de nuevo de acuerdo con los cambios de configuración
        getXMLDOMRegistry().cleanCaches();
    }
}
