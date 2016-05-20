package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 6/05/14.
 */
public abstract class PendingViewPostCreateProcess
{
    protected View view;
    protected List<Runnable> pendingSetAttribsTaskList;
    protected List<Runnable> pendingLayoutParamsTasks;
    protected List<Runnable> pendingPostAddViewTasks;
    protected boolean destroy = false;

    public PendingViewPostCreateProcess(View view)
    {
        this.view = view;
    }

    public void addPendingSetAttribsTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingSetAttribsTaskList == null) this.pendingSetAttribsTaskList = new ArrayList<Runnable>();
        pendingSetAttribsTaskList.add(task);
    }

    public void executePendingSetAttribsTasks()
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingSetAttribsTaskList != null)
        {
            for (Runnable task : pendingSetAttribsTaskList) task.run();

            this.pendingSetAttribsTaskList = null;
        }
    }

    public void addPendingLayoutParamsTask(Runnable task)
    {
        if (destroy)
            throw new ItsNatDroidException("Is already destroyed");
        if (pendingLayoutParamsTasks == null) this.pendingLayoutParamsTasks = new ArrayList<Runnable>();
        pendingLayoutParamsTasks.add(task);
    }

    public void executePendingLayoutParamsTasks() // Se debe ejecutar antes de que se añada el View al parent
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingLayoutParamsTasks != null)
        {
            for (Runnable task : pendingLayoutParamsTasks) task.run();

            onChangedLayoutParams(view);

            this.pendingLayoutParamsTasks = null;
        }
    }

    public void addPendingPostAddViewTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingPostAddViewTasks == null) this.pendingPostAddViewTasks = new ArrayList<Runnable>();
        pendingPostAddViewTasks.add(task);
    }

    public void executePendingPostAddViewTasks()
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (pendingPostAddViewTasks != null)
        {
            for (Runnable task : pendingPostAddViewTasks)
                task.run();

            this.pendingPostAddViewTasks = null;
        }
    }

    public static void onChangedLayoutParams(View view)
    {
        // Para que los cambios que se han hecho en los objetos "stand-alone" (ya renderizados) *.LayoutParams se entere el View asociado (esa llamada hace poco más que un requestLayout()), al hacerlo al final evitamos múltiples llamadas por cada cambio en LayoutParams
        view.setLayoutParams(view.getLayoutParams());
    }

    public void destroy()
    {
        this.destroy = true;
    }
}
