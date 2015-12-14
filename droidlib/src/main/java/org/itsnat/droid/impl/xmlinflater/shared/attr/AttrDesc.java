package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.ArrayList;

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
        // Es el caso de inserción dinámica post page load via ItsNat de nuevos View con atributos que especifican recursos remotos
        // Hay que cargar primero los recursos y luego ejecutar la task que definirá el drawable remoto por ejemplo
        PageImpl page = ClassDesc.getPageImpl(xmlInflater); // NO puede ser nulo

        page.getItsNatDocImpl().downloadResources(attr, task);
    }

    public int getIdentifierAddIfNecessary(String value, Context ctx)
    {
        return getXMLInflateRegistry().getIdentifierAddIfNecessary(value, ctx);
    }

    public int getIdentifier(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getIdentifier(attrValue, ctx);
    }

    public int getInteger(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getInteger(attrValue, ctx);
    }

    public float getFloat(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getFloat(attrValue, ctx);
    }

    public String getString(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getString(attrValue, ctx);
    }

    public CharSequence getText(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getText(attrValue, ctx);
    }

    public CharSequence[] getTextArray(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getTextArray(attrValue, ctx);
    }

    public boolean getBoolean(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getBoolean(attrValue, ctx);
    }

    public int getDimensionIntFloor(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionIntFloor(attrValue, ctx);
    }

    public int getDimensionIntRound(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionIntRound(attrValue, ctx);
    }

    public float getDimensionFloat(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionFloat(attrValue, ctx);
    }

    public float getDimensionFloatFloor(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionFloatFloor(attrValue, ctx);
    }

    public float getDimensionFloatRound(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionFloatRound(attrValue, ctx);
    }


    public PercFloat getDimensionPercFloat(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionPercFloat(attrValue, ctx);
    }

    public int getDimensionWithNameIntRound(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getDimensionWithNameIntRound(attrValue, ctx);
    }

    public Drawable getDrawable(DOMAttr attr, Context ctx,XMLInflater xmlInflater)
    {
        return getXMLInflateRegistry().getDrawable(attr, ctx, xmlInflater);
    }

    public View getLayout(DOMAttr attr, Context ctx,XMLInflater xmlInflater,ViewGroup viewParent,int indexChild,ArrayList<DOMAttr> includeAttribs)
    {
        return getXMLInflateRegistry().getLayout(attr, ctx, xmlInflater,viewParent,indexChild,includeAttribs);
    }

    public int getColor(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getColor(attrValue, ctx);
    }

    public float getPercent(String attrValue, Context ctx)
    {
        return getXMLInflateRegistry().getPercent(attrValue, ctx);
    }

    public static <T> T parseSingleName(String value, MapSmart<String, T> valueMap)
    {
        // Se llama directamente sin Context porque es para atributos que no pueden ser un recurso
        T valueRes = valueMap.get(value);
        if (valueRes == null)
            throw new ItsNatDroidException("Unrecognized value name " + value + " for attribute");
        return valueRes;
    }

    public static int parseMultipleName(String value, MapSmart<String, Integer> valueMap)
    {
        // Se llama directamente sin Context porque es para atributos que no pueden ser un recurso
        String[] names = value.split("\\|");
        int res = 0;
        for(int i = 0; i < names.length; i++)
        {
            // No hace falta hacer trim, los espacios dan error
            String name = names[i];
            Integer valueInt = valueMap.get(name);
            if (valueInt == null)
                throw new ItsNatDroidException("Unrecognized value name " + name + " for attribute");

            res |= valueInt;
        }

        return res;
    }

    protected void setToRemoveAttribute(TattrTarget target, String value, TattrContext attrCtx)
    {
        // Este método es llamado desde removeAttributeFromRemote, cuyo valor será o @null o un recurso de Android, no esperamos
        // nada dinámico (Remote o Asset), por eso hacemos cast sin complejos a DOMAttrLocalResource
        DOMAttrLocalResource attr = (DOMAttrLocalResource) DOMAttr.create(InflatedXML.XMLNS_ANDROID, getName(), value);

        setAttribute(target, attr,attrCtx);
    }

    public abstract void setAttribute(TattrTarget target,DOMAttr attr,TattrContext attrCtx);

    public abstract void removeAttribute(TattrTarget target, TattrContext attrCtx);
}
