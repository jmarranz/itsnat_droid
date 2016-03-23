package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrCompiledResource;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribs;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class AttrDesc<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
{
    private static Class class_R_styleable;

    protected String name;
    protected TclassDesc classDesc;

    public AttrDesc(TclassDesc classDesc, String name)
    {
        this.classDesc = classDesc;
        this.name = name;
    }

    protected static Class getClass_R_styleable()
    {
        if (class_R_styleable == null)
            class_R_styleable = MiscUtil.resolveClass("com.android.internal.R$styleable");
        return class_R_styleable;
    }

    public TclassDesc getClassDesc()
    {
        return classDesc;
    }

    public String getName()
    {
        return name;
    }

    protected XMLInflateRegistry getXMLInflateRegistry()
    {
        return classDesc.getXMLInflateRegistry();
    }

    public static void processDownloadTask(DOMAttrRemote attr, Runnable task, XMLInflater xmlInflater)
    {
        PageImpl page = PageImpl.getPageImpl(xmlInflater); // NO puede ser nulo

        page.getItsNatDocImpl().downloadResources(attr, task);
    }

    public int getIdentifier(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getIdentifier(attr, xmlInflater);
    }

    public ViewStyleAttribs getViewStyle(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getViewStyle(attr, xmlInflater);
    }

    public int getViewStyle(ViewStyleAttribs style,List<DOMAttr> styleItemsDynamicAttribs,Context ctx)
    {
        return getXMLInflateRegistry().getViewStyle(style,styleItemsDynamicAttribs,ctx);
    }

    public int getInteger(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getInteger(attr, xmlInflater);
    }

    public float getFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getFloat(attr, xmlInflater);
    }

    public String getString(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getString(attr, xmlInflater);
    }

    public CharSequence getText(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getText(attr, xmlInflater);
    }

    public CharSequence[] getTextArray(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getTextArray(attr, xmlInflater);
    }

    public boolean getBoolean(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getBoolean(attr, xmlInflater);
    }

    public int getDimensionIntFloor(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionIntFloor(attr, xmlInflater);
    }

    public int getDimensionIntRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionIntRound(attr, xmlInflater);
    }

    public float getDimensionFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionFloat(attr, xmlInflater);
    }

    public float getDimensionFloatFloor(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionFloatFloor(attr, xmlInflater);
    }

    public float getDimensionFloatRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionFloatRound(attr, xmlInflater);
    }


    public PercFloat getDimensionPercFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionPercFloat(attr, xmlInflater);
    }

    public int getDimensionWithNameIntRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDimensionWithNameIntRound(attr, xmlInflater);
    }

    public Drawable getDrawable(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDrawable(attr, xmlInflater);
    }

    public LayoutValue getLayout(DOMAttr attr,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild)
    {
        return getXMLInflateRegistry().getLayout(attr, xmlInflaterParent, viewParent, indexChild);
    }

    public View getViewLayout(DOMAttr attr, XMLInflaterLayout xmlInflater, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        return getXMLInflateRegistry().getViewLayout(attr, xmlInflater, viewParent, indexChild, includeAttribs);
    }

    public int getColor(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getColor(attr, xmlInflater);
    }

    public float getPercent(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getPercent(attr, xmlInflater);
    }

    public Animation getAnimation(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getAnimation(attr, xmlInflater);
    }

    public Animator getAnimator(DOMAttr attr, XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getAnimator(attr, xmlInflater);
    }


    public static <T> T parseSingleName(String value, MapSmart<String, T> nameValueMap)
    {
        // Se llama directamente sin Context porque es para atributos que no pueden ser un recurso
        T valueRes = nameValueMap.get(value);
        if (valueRes == null)
            throw new ItsNatDroidException("Unrecognized value name " + value + " for attribute");
        return valueRes;
    }

    public static int parseMultipleName(String value, MapSmart<String, Integer> nameValueMap)
    {
        // Se llama directamente sin Context porque es para atributos que no pueden ser un recurso
        String[] names = value.split("\\|");
        int res = 0;
        for(int i = 0; i < names.length; i++)
        {
            // No hace falta hacer trim, los espacios dan error
            String name = names[i];
            Integer valueInt = nameValueMap.get(name);
            if (valueInt == null)
                throw new ItsNatDroidException("Unrecognized value name " + name + " for attribute");

            res |= valueInt;
        }

        return res;
    }

    protected void setToRemoveAttribute(TattrTarget target, String value, TattrContext attrCtx)
    {
        // Este método es llamado desde removeAttributeFromRemote, cuyo valor será o @null o un recurso de Android, no esperamos
        // nada dinámico (Remote o Asset), por eso hacemos cast sin complejos a DOMAttrCompiledResource
        DOMAttrCompiledResource attr = (DOMAttrCompiledResource) DOMAttr.create(NamespaceUtil.XMLNS_ANDROID, getName(), value);

        setAttribute(target, attr,attrCtx);
    }

    public abstract void setAttribute(TattrTarget target,DOMAttr attr,TattrContext attrCtx);

    public abstract void removeAttribute(TattrTarget target, TattrContext attrCtx);
}
