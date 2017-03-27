package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
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

    public static void processDownloadTask(DOMAttrRemote attr, Runnable task, XMLInflaterContext xmlInflaterContext)
    {
        PageImpl page = xmlInflaterContext.getPageImpl(); // NO puede ser nulo

        page.getItsNatDocImpl().downloadResources(attr.getResourceDescRemote(), task);
    }

    public boolean getBoolean(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getBoolean(resourceDesc, xmlInflaterContext);
    }

    public int getColor(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getColor(resourceDesc, xmlInflaterContext);
    }

    public int getIdentifier(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getIdentifier(resourceDesc, xmlInflaterContext);
    }

    public ViewStyleAttribs getViewStyle(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getViewStyle(resourceDesc, xmlInflaterContext);
    }

    public int getViewStyle(ViewStyleAttribs style,List<DOMAttr> styleItemsDynamicAttribs,Context ctx)
    {
        return getXMLInflaterRegistry().getViewStyle(style, styleItemsDynamicAttribs, ctx);
    }

    public int getInteger(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getInteger(resourceDesc, xmlInflaterContext);
    }

    public float getFloat(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getFloat(resourceDesc, xmlInflaterContext);
    }

    public String getString(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getString(resourceDesc, xmlInflaterContext);
    }

    public CharSequence getText(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getText(resourceDesc, xmlInflaterContext);
    }

    public CharSequence[] getTextArray(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getTextArray(resourceDesc, xmlInflaterContext);
    }

    public int getDimensionIntFloor(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionIntFloor(resourceDesc, xmlInflaterContext);
    }

    public int getDimensionIntRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionIntRound(resourceDesc, xmlInflaterContext);
    }

    public float getDimensionFloat(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionFloat(resourceDesc, xmlInflaterContext);
    }

    public float getDimensionFloatFloor(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionFloatFloor(resourceDesc, xmlInflaterContext);
    }

    public float getDimensionFloatRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionFloatRound(resourceDesc, xmlInflaterContext);
    }

    public int getDimensionWithNameIntRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionWithNameIntRound(resourceDesc, xmlInflaterContext);
    }

    public String getDimensionOrString(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionOrString(resourceDesc, xmlInflaterContext);
    }

    public PercFloatImpl getDimensionPercFloat(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDimensionPercFloat(resourceDesc, xmlInflaterContext);
    }

    public PercFloatImpl getPercFloat(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getPercFloat(resourceDesc, xmlInflaterContext);
    }

    public Animation getAnimation(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getAnimation(resourceDesc, xmlInflaterContext);
    }

    public Animator getAnimator(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getAnimator(resourceDesc, xmlInflaterContext);
    }

    public Drawable getDrawable(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getDrawable(resourceDesc,xmlInflaterContext);
    }

    public Interpolator getInterpolator(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getInterpolator(resourceDesc, xmlInflaterContext);
    }

    public View getLayout(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild,ArrayList<DOMAttr> includeAttribs)
    {
        return getXMLInflaterRegistry().getLayout(resourceDesc, xmlInflaterContext, xmlInflaterParent, viewParent, indexChild,includeAttribs);
    }

    public LayoutValue getLayoutValue(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild,ArrayList<DOMAttr> includeAttribs)
    {
        return getXMLInflaterRegistry().getLayoutValue(resourceDesc, xmlInflaterContext, xmlInflaterParent, viewParent, indexChild,includeAttribs);
    }


    public LayoutAnimationController getLayoutAnimation(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        return getXMLInflaterRegistry().getLayoutAnimation(resourceDesc, xmlInflaterContext);
    }

    public Menu getMenu(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext,Menu rootMenuParent)
    {
        return getXMLInflaterRegistry().getMenu(resourceDesc, xmlInflaterContext, rootMenuParent);
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

    protected void setAttributeToRemove(TattrTarget target, String value, TattrContext attrCtx)
    {
        // Este método es llamado desde removeAttributeFromRemote, cuyo valor será o @null o un recurso de Android, no esperamos
        // nada dinámico (Remote o Asset), por eso hacemos cast sin complejos a DOMAttrCompiled
        DOMAttr attr = DOMAttr.createDOMAttr(NamespaceUtil.XMLNS_ANDROID, getName(), value);

        setAttribute(target, attr,attrCtx);
    }

    public abstract void setAttribute(TattrTarget target,DOMAttr attr,TattrContext attrCtx);

    public abstract void removeAttribute(TattrTarget target, TattrContext attrCtx);
}
