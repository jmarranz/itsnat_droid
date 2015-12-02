package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;

import java.util.LinkedList;

/**
 * Created by jmarranz on 6/05/14.
 */
public abstract class PendingViewCreateProcess
{
    protected View view;
    protected LinkedList<Runnable> pendingSetAttribsTaskList;
    protected LinkedList<Runnable> pendingLayoutParamsTasks;
    protected boolean destroy = false;

    public PendingViewCreateProcess(View view)
    {
        this.view = view;
    }

    public void addPendingSetAttribsTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingSetAttribsTaskList == null) this.pendingSetAttribsTaskList = new LinkedList<Runnable>();
        pendingSetAttribsTaskList.add(task);
    }

    public void executePendingSetAttribsTasks()
    {
        if (pendingSetAttribsTaskList != null)
        {
            for (Runnable task : pendingSetAttribsTaskList) task.run();

            this.pendingSetAttribsTaskList = null;
        }
    }

    public void addPendingLayoutParamsTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingLayoutParamsTasks == null) this.pendingLayoutParamsTasks = new LinkedList<Runnable>();
        pendingLayoutParamsTasks.add(task);
    }

    public void executePendingLayoutParamsTasks()
    {
        if (pendingLayoutParamsTasks != null)
        {
            for (Runnable task : pendingLayoutParamsTasks) task.run();

            view.setLayoutParams(view.getLayoutParams()); // Para que los cambios que se han hecho en los objetos "stand-alone" *.LayoutParams se entere el View asociado (esa llamada hace requestLayout creo recordar), al hacerlo al final evitamos múltiples llamadas por cada cambio en LayoutParams

            this.pendingLayoutParamsTasks = null;
        }
    }

    public void destroy()
    {
        this.destroy = true;
    }
}
