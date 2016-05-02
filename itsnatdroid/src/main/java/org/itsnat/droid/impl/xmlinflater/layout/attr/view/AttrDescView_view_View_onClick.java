package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.StringUtil;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_View_onClick extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_view_View_onClick(ClassDescViewBased parent)
    {
        super(parent,"onClick");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, final AttrLayoutContext attrCtx)
    {
        final String handlerName = attr.getValue();

        if (!StringUtil.isEmpty(handlerName))
        {
            View.OnClickListener listener = new View.OnClickListener()
            {
                private Method mHandler;

                public void onClick(View v)
                {
                    Context ctx = attrCtx.getContext();
                    if (mHandler == null)
                    {
                        try
                        {
                            mHandler = ctx.getClass().getMethod(handlerName, View.class);
                        }
                        catch (NoSuchMethodException e)
                        {
                            int id = v.getId();
                            String idText = id == View.NO_ID ? "" : " with id '" + ctx.getResources().getResourceEntryName(id) + "'";
                            throw new IllegalStateException("Could not find a method " +
                                    handlerName + "(View) in the activity " + ctx.getClass() + " for onClick handler" + " on view " + v.getClass() + idText, e);
                        }
                    }

                    try
                    {
                        mHandler.invoke(ctx, v);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new IllegalStateException("Could not execute non " + "public method of the activity", e);
                    }
                    catch (InvocationTargetException e)
                    {
                        throw new IllegalStateException("Could not execute " + "method of the activity", e);
                    }
                }

            };

            view.setOnClickListener(listener); // Obviamente esto interviene en la capacidad de procesar eventos click ItsNat en esta View
        }
        else
        {
            view.setOnClickListener(null);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "",attrCtx);
    }
}
