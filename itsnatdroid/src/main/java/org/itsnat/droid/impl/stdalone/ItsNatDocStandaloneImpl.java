package org.itsnat.droid.impl.stdalone;

import android.content.Context;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDocImpl;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Created by jmarranz on 03/03/2017.
 */

public class ItsNatDocStandaloneImpl extends ItsNatDocImpl
{
    protected InflatedLayoutImpl inflatedLayout;

    public ItsNatDocStandaloneImpl(InflatedLayoutImpl inflatedLayout)
    {
        this.inflatedLayout = inflatedLayout;
    }

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedLayout.getInflatedXMLLayoutStandaloneImpl();
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
            Interpreter interp = page.getInterpreter();
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

    public ItsNatResourcesImpl createItsNatResourcesImpl()
    {
        return new ItsNatResourcesStandaloneImpl(this);
    }

    @Override
    public Context getContext()
    {
        return inflatedLayout.getContext();
    }
}
