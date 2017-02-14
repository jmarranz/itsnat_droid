package org.itsnat.droid.impl.xmlinflater.shared.classtree;

import android.content.Context;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;
import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescResourceBased<TnativeResource,TattrCtx extends AttrResourceContext> extends ClassDesc<TnativeResource>
{
    public ClassDescResourceBased(ClassDescMgr<? extends ClassDescResourceBased> classMgr, String tagName, ClassDescResourceBased<? super TnativeResource,TattrCtx> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    @SuppressWarnings("unchecked")
    public ClassDescMgr<ClassDescResourceBased> getClassDescMgr()
    {
        return (ClassDescMgr<ClassDescResourceBased>)classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescResourceBased<TnativeResource,TattrCtx> getParentClassDescResourceBased()
    {
        return (ClassDescResourceBased<TnativeResource,TattrCtx>) getParentClassDesc(); // Puede ser null
    }

    //public abstract Class<TnativeResource> getDeclaredClass();


    protected abstract TnativeResource createResourceNative(Context ctx);

    public TnativeResource createRootResourceAndFillAttributes(DOMElement rootDOMElem, TattrCtx attrCtx)
    {
        @SuppressWarnings("unchecked")
        XMLInflaterResource<TnativeResource> xmlInflater = attrCtx.getXMLInflaterResource();

        TnativeResource rootResource = createResourceNative(xmlInflater.getContext());
        xmlInflater.getInflatedXMLResource().setRootResource(rootResource); // Lo antes posible

        fillResourceAttributes(rootResource, rootDOMElem, attrCtx);

        return rootResource;
    }


    public TnativeResource createResourceAndFillAttributes(DOMElement domElement, TattrCtx attrCtx)
    {
        @SuppressWarnings("unchecked")
        XMLInflaterResource<TnativeResource> xmlInflater = attrCtx.getXMLInflaterResource();

        TnativeResource resource = createResourceNative(xmlInflater.getContext());

        fillResourceAttributes(resource, domElement, attrCtx);

        return resource;
    }

    public void fillResourceAttributes(TnativeResource resource, DOMElement domElement, TattrCtx attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(resource, attr, attrCtx);
            }
        }
    }

    public boolean setAttribute(final TnativeResource resource, final DOMAttr attr, final TattrCtx attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();
        try
        {
            if (setAttributeThisClass(resource,attr,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processSetAttrCustom(resource, namespaceURI, name, value, xmlInflaterContext);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + value + " in object " + resource, ex);
        }
    }

    private boolean setAttributeThisClass(final TnativeResource resource, final DOMAttr attr, final TattrCtx attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        //String value = attr.getValue();

        if (isAttributeIgnored(resource,namespaceURI, name))
            return true; // Se trata de forma especial en otro lugar

        final AttrDesc<ClassDescResourceBased, TnativeResource, TattrCtx> attrDesc = this.<ClassDescResourceBased, TnativeResource, TattrCtx>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run()
                {
                    attrDesc.setAttribute(resource, attr, attrCtx);
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
            ClassDescResourceBased<TnativeResource,TattrCtx> parentClass = getParentClassDescResourceBased();
            if (parentClass != null)
            {
                return parentClass.setAttributeThisClass(resource, attr, attrCtx);
            }

            return false;
        }

    }

    public boolean removeAttribute(TnativeResource resource, String namespaceURI, String name, TattrCtx attrCtx)
    {
        try
        {
            if (removeAttributeThisClass(resource,namespaceURI,name,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processRemoveAttrCustom(resource, namespaceURI, name, xmlInflaterContext);
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error removing attribute: " + namespaceURI + " " + name + " in object " + resource, ex);
        }
    }

    private boolean removeAttributeThisClass(TnativeResource resource, String namespaceURI, String name, TattrCtx attrCtx)
    {
        if (!isInit()) init();


        if (isAttributeIgnored(resource,namespaceURI,name))
            return true; // Se trata de forma especial en otro lugar

        AttrDesc<ClassDescResourceBased,TnativeResource, TattrCtx> attrDesc = this.<ClassDescResourceBased,TnativeResource, TattrCtx>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            attrDesc.removeAttribute(resource,attrCtx);
            // No tiene mucho sentido añadir isPendingToDownload etc aquí, no encuentro un caso de que al eliminar el atributo el valor por defecto a definir sea remoto aunque sea un drawable lo normal será un "@null" o un drawable por defecto nativo de Android
            return true;
        }
        else
        {
            ClassDescResourceBased<TnativeResource,TattrCtx> parentClass = getParentClassDescResourceBased();
            if (parentClass != null)
            {
                return parentClass.removeAttributeThisClass(resource, namespaceURI, name, attrCtx);
            }

            return false;
        }

    }

    private boolean processSetAttrCustom(TnativeResource resource, String namespaceURI, String name, String value, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrResourceInflaterListener listener = xmlInflaterContext.getAttrResourceInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.setAttribute(page, resource, namespaceURI, name, value);
        }
        return false;
    }

    private boolean processRemoveAttrCustom(TnativeResource resource, String namespaceURI, String name, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrResourceInflaterListener listener = xmlInflaterContext.getAttrResourceInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.removeAttribute(page, resource, namespaceURI, name);
        }
        return false;
    }

    public boolean isAttributeIgnored(TnativeResource resource,String namespaceURI, String name)
    {
        return false;
    }

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

}
