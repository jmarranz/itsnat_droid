package org.itsnat.droid.impl.xmlinflater.layout;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;

import java.util.LinkedList;

/**
 * Created by jmarranz on 6/05/14.
 */
public abstract class OneTimeAttrProcess
{
    protected View view;
    protected LinkedList<Runnable> taskLastList;
    protected LinkedList<Runnable> layoutParamsTasks;
    protected boolean destroy = false;

    public OneTimeAttrProcess(View view)
    {
        this.view = view;
    }

    public void addLastTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (taskLastList == null) this.taskLastList = new LinkedList<Runnable>();
        taskLastList.add(task);
    }

    public void executeLastTasks()
    {
        if (taskLastList != null)
        {
            for (Runnable task : taskLastList) task.run();

            this.taskLastList = null;
        }
    }

    public void addLayoutParamsTask(Runnable task)
    {
        if (destroy) throw new ItsNatDroidException("Is already destroyed");
        if (layoutParamsTasks == null) this.layoutParamsTasks = new LinkedList<Runnable>();
        layoutParamsTasks.add(task);
    }

    public void executeLayoutParamsTasks()
    {
        if (layoutParamsTasks != null)
        {
            for (Runnable task : layoutParamsTasks) task.run();

            view.setLayoutParams(view.getLayoutParams()); // Para que los cambios que se han hecho en los objetos "stand-alone" *.LayoutParams se entere el View asociado (esa llamada hace requestLayout creo recordar), al hacerlo al final evitamos múltiples llamadas por cada cambio en LayoutParams

            this.layoutParamsTasks = null;
        }
    }

    public void destroy()
    {
        this.destroy = true;
    }
}
