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
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
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

    protected XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return classDesc.getXMLInflaterRegistry();
    }

    public static void processDownloadTask(DOMAttrRemote attr, Runnable task, XMLInflater xmlInflater)
    {
        PageImpl page = PageImpl.getPageImpl(xmlInflater); // NO puede ser nulo

        page.getItsNatDocImpl().downloadResources(attr.getResourceDescRemote(), task);
    }

    public int getIdentifier(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getIdentifier(resourceDesc, xmlInflater);
    }

    public ViewStyleAttribs getViewStyle(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getViewStyle(resourceDesc, xmlInflater);
    }

    public int getViewStyle(ViewStyleAttribs style,List<DOMAttr> styleItemsDynamicAttribs,Context ctx)
    {
        return getXMLInflaterRegistry().getViewStyle(style, styleItemsDynamicAttribs, ctx);
    }

    public int getInteger(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getInteger(resourceDesc, xmlInflater);
    }

    public float getFloat(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getFloat(resourceDesc, xmlInflater);
    }

    public String getString(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getString(resourceDesc, xmlInflater);
    }

    public CharSequence getText(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getText(resourceDesc, xmlInflater);
    }

    public CharSequence[] getTextArray(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getTextArray(resourceDesc, xmlInflater);
    }

    public boolean getBoolean(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getBoolean(resourceDesc, xmlInflater);
    }

    public int getDimensionIntFloor(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionIntFloor(resourceDesc, xmlInflater);
    }

    public int getDimensionIntRound(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionIntRound(resourceDesc, xmlInflater);
    }

    public float getDimensionFloat(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionFloat(resourceDesc, xmlInflater);
    }

    public float getDimensionFloatFloor(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionFloatFloor(resourceDesc, xmlInflater);
    }

    public float getDimensionFloatRound(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionFloatRound(resourceDesc, xmlInflater);
    }


    public PercFloat getDimensionPercFloat(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionPercFloat(resourceDesc, xmlInflater);
    }

    public int getDimensionWithNameIntRound(ResourceDesc resourceDesc,XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDimensionWithNameIntRound(resourceDesc, xmlInflater);
    }

    public Drawable getDrawable(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getDrawable(resourceDesc, xmlInflater);
    }

    public LayoutValue getLayout(ResourceDesc resourceDesc,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild)
    {
        return getXMLInflaterRegistry().getLayout(resourceDesc, xmlInflaterParent, viewParent, indexChild);
    }

    public View getViewLayout(ResourceDesc resourceDesc, XMLInflaterLayout xmlInflater, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        return getXMLInflaterRegistry().getViewLayout(resourceDesc, xmlInflater, viewParent, indexChild, includeAttribs);
    }

    public int getColor(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getColor(resourceDesc, xmlInflater);
    }

    public float getPercent(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getPercent(resourceDesc, xmlInflater);
    }

    public Animation getAnimation(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getAnimation(resourceDesc, xmlInflater);
    }

    public Animator getAnimator(ResourceDesc resourceDesc, XMLInflater xmlInflater)
    {
        return getXMLInflaterRegistry().getAnimator(resourceDesc, xmlInflater);
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
        // nada dinámico (Remote o Asset), por eso hacemos cast sin complejos a DOMAttrCompiled
        DOMAttr attr = DOMAttr.createDOMAttr(NamespaceUtil.XMLNS_ANDROID, getName(), value);

        setAttribute(target, attr,attrCtx);
    }

    public abstract void setAttribute(TattrTarget target,DOMAttr attr,TattrContext attrCtx);

    public abstract void removeAttribute(TattrTarget target, TattrContext attrCtx);
}
