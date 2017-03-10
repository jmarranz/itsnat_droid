package org.itsnat.droid.impl.stdalone;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDocImpl;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

import bsh.EvalError;


/**
 * Created by jmarranz on 03/03/2017.
 */

public class ItsNatDocStandaloneImpl extends ItsNatDocImpl
{
    protected InflatedLayoutImpl inflatedLayout;

    public ItsNatDocStandaloneImpl(InflatedLayoutImpl inflatedLayout)
    {
        super(inflatedLayout.getItsNatDroidImpl());
        this.inflatedLayout = inflatedLayout;
    }

    public InflatedLayoutImpl getInflatedLayoutImpl()
    {
        return inflatedLayout;
    }

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedLayout.getInflatedXMLLayoutStandaloneImpl();
    }

    @Override
    public View getRootView()
    {
        return getInflatedXMLLayoutStandaloneImpl().getRootView();
    }

    @Override
    public View findViewByXMLId(String id)
    {
        return getInflatedXMLLayoutStandaloneImpl().findViewByXMLId(id);
    }

    @Override
    public Object eval(String code,Object context)
    {
        try
        {
            return getInterpreter().eval(code);
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
            getInterpreter().set(name, value);
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
    public void unset(String name)  {
        try
        {
            getInterpreter().unset(name);
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
