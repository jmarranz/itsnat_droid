package org.itsnat.droid.impl;

import android.animation.Animator;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatResources;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;

/**
 * Created by jmarranz on 01/04/2016.
 */
public abstract class ItsNatResourcesImpl implements ItsNatResources
{
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final XMLInflaterContext xmlInflaterContext;
    protected final XMLInflaterRegistry xmlInflaterRegistry;

    public ItsNatResourcesImpl(XMLDOMRegistry xmlDOMRegistry,XMLInflaterContext xmlInflaterContext,XMLInflaterRegistry xmlInflaterRegistry)
    {
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.xmlInflaterContext = xmlInflaterContext;
        this.xmlInflaterRegistry = xmlInflaterRegistry;
    }


}
