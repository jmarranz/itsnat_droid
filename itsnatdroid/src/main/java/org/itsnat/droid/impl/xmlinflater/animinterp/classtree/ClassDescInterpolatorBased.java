package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.Interpolator;

import org.itsnat.droid.AttrInterpolatorInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.AttrInterpolatorContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.XMLInflaterInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescInterpolatorBased<T extends Interpolator> extends ClassDesc<T>
{
    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased(ClassDescInterpolatorMgr classMgr, String tagName, ClassDescInterpolatorBased parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescInterpolatorMgr getClassDescInterpolatorMgr()
    {
        return (ClassDescInterpolatorMgr) classMgr;
    }

    public ClassDescInterpolatorBased getParentClassDescInterpolatorBased()
    {
        return (ClassDescInterpolatorBased) getParentClassDesc(); // Puede ser null
    }

    public abstract Class<T> getDeclaredClass();

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

    public Interpolator createRootInterpolatorNativeAndFillAttributes(DOMElemInterpolator rootDOMElemInterpolator, AttrInterpolatorContext attrCtx)
    {
        XMLInflaterInterpolator xmlInflaterInterpolator = attrCtx.getXMLInflaterInterpolator();

        Interpolator rootInterpolator = createInterpolatorNative(xmlInflaterInterpolator.getContext());
        xmlInflaterInterpolator.getInflatedInterpolator().setRootInterpolator(rootInterpolator); // Lo antes posible

        fillInterpolatorAttributes(rootInterpolator, rootDOMElemInterpolator, attrCtx);

        return rootInterpolator;
    }

    public Interpolator createInterpolatorNativeAndFillAttributes(DOMElemInterpolator domElement, AttrInterpolatorContext attrCtx)
    {
        XMLInflaterInterpolator xmlInflaterInterpolator = attrCtx.getXMLInflaterInterpolator();

        Interpolator interpolator = createInterpolatorNative(xmlInflaterInterpolator.getContext());

        fillInterpolatorAttributes(interpolator, domElement, attrCtx);

        return interpolator;
    }

    protected abstract T createInterpolatorNative(Context ctx);

    protected void fillInterpolatorAttributes(Interpolator interpolator,DOMElemInterpolator domElement,AttrInterpolatorContext attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(interpolator, attr, attrCtx);
            }
        }
    }

    protected boolean setAttribute(final Interpolator interpolator, final DOMAttr attr, final AttrInterpolatorContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();

        try
        {
            if (setAttributeThisClass(interpolator,attr,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processSetAttrCustom(interpolator, namespaceURI, name, value, xmlInflaterContext);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + value + " in object " + interpolator, ex);
        }
    }

    private boolean setAttributeThisClass(final Interpolator interpolator, final DOMAttr attr, final AttrInterpolatorContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        //String value = attr.getValue();

        if (isAttributeIgnored(namespaceURI, name))
            return true; // Se trata de forma especial en otro lugar

        final AttrDesc<ClassDescInterpolatorBased, Interpolator, AttrInterpolatorContext> attrDesc = this.<ClassDescInterpolatorBased, Interpolator, AttrInterpolatorContext>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run()
                {
                    attrDesc.setAttribute(interpolator, attr, attrCtx);
                }
            };
            if (DOMAttrRemote.isPendingToDownload(attr)) // Ver comentarios en la clase equivalente de drawables, layouts etc
                AttrDesc.processDownloadTask((DOMAttrRemote) attr, task, attrCtx.getXMLInflaterContext());
            else
                task.run();

            return true;
        }
        else
        {
            // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
            // y tiene prioridad la clase más derivada
            ClassDescInterpolatorBased parentClass = getParentClassDescInterpolatorBased();
            if (parentClass != null)
            {
                return parentClass.setAttributeThisClass(interpolator, attr, attrCtx);
            }

            return false;
        }
    }


    public boolean removeAttribute(Interpolator interpolator, String namespaceURI, String name, AttrInterpolatorContext attrCtx)
    {
        try
        {
            if (removeAttributeThisClass(interpolator,namespaceURI,name,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processRemoveAttrCustom(interpolator, namespaceURI, name, xmlInflaterContext);
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error removing attribute: " + namespaceURI + " " + name + " in object " + interpolator, ex);
        }
    }

    //@SuppressWarnings("unchecked")
    private boolean removeAttributeThisClass(Interpolator interpolator, String namespaceURI, String name, AttrInterpolatorContext attrCtx)
    {
        if (!isInit()) init();


        if (isAttributeIgnored(namespaceURI,name))
            return true; // Se trata de forma especial en otro lugar

        AttrDesc<ClassDescInterpolatorBased,Interpolator,AttrInterpolatorContext> attrDesc = this.<ClassDescInterpolatorBased,Interpolator,AttrInterpolatorContext>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            attrDesc.removeAttribute(interpolator,attrCtx);
            // No tiene mucho sentido añadir isPendingToDownload etc aquí, no encuentro un caso de que al eliminar el atributo el valor por defecto a definir sea remoto aunque sea un drawable lo normal será un "@null" o un drawable por defecto nativo de Android
            return true;
        }
        else
        {
            ClassDescInterpolatorBased parentClass = getParentClassDescInterpolatorBased();
            if (parentClass != null)
            {
                return parentClass.removeAttributeThisClass(interpolator, namespaceURI, name, attrCtx);
            }

            return false;
        }
    }

    private boolean processSetAttrCustom(Interpolator interpolator, String namespaceURI, String name, String value, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrInterpolatorInflaterListener listener = xmlInflaterContext.getAttrInflaterListeners().getAttrInterpolatorInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.setAttribute(page, interpolator, namespaceURI, name, value);
        }
        return false;
    }

    private boolean processRemoveAttrCustom(Interpolator interpolator, String namespaceURI, String name, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrInterpolatorInflaterListener listener = xmlInflaterContext.getAttrInflaterListeners().getAttrInterpolatorInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.removeAttribute(page, interpolator, namespaceURI, name);
        }
        return false;
    }

    protected boolean isAttributeIgnored(String namespaceURI, String name)
    {
        return false;
    }

}
