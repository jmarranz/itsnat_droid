package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.content.Context;

import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimatorBased<T extends Animator> extends ClassDesc<Animator>
{
    public ClassDescAnimatorBased(ClassDescAnimatorMgr classMgr, String tagName, ClassDescAnimatorBased<? extends Animator> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimatorMgr getClassDescAnimatorMgr()
    {
        return (ClassDescAnimatorMgr) classMgr;
    }

    public ClassDescAnimatorBased getParentClassDescAnimatorBased()
    {
        return (ClassDescAnimatorBased) getParentClassDesc(); // Puede ser null
    }

    @Override
    public Class<Animator> getDeclaredClass()
    {
        return null; // No se necesita, no se implementa
    }

    @Override
    protected void init()
    {
        // initClass();

        super.init();
    }

    public Animator createRootAnimatorObjectAndFillAttributes(DOMElemAnimator rootDOMElemAnimator,AttrAnimatorContext attrCtx)
    {
        XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();

        Animator rootAnimator = createAnimatorObject(xmlInflaterAnimator.getContext());
        xmlInflaterAnimator.getInflatedAnimator().setRootAnimator(rootAnimator); // Lo antes posible

        fillAnimatorAttributes(rootAnimator, rootDOMElemAnimator.getDOMAttributes(), attrCtx);

        return rootAnimator;
    }

    public Animator createAnimatorObjectAndFillAttributes(DOMElemAnimator domElement,AttrAnimatorContext attrCtx)
    {
        XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();

        Animator animator = createAnimatorObject(xmlInflaterAnimator.getContext());

        fillAnimatorAttributes(animator, domElement.getDOMAttributes(), attrCtx);

        return animator;
    }

    protected abstract T createAnimatorObject(Context ctx);

    private void fillAnimatorAttributes(Animator animator,Map<String,DOMAttr> attribMap,AttrAnimatorContext attrCtx)
    {
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(animator, attr, attrCtx);
            }
        }
    }

    protected boolean setAttribute(final Animator animator, final DOMAttr attr, final AttrAnimatorContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        try
        {
            return setAttributeThisClass(animator,attr,attrCtx);
        }
        catch (Exception ex)
        {
            String namespaceURI = attr.getNamespaceURI();
            String name = attr.getName(); // El nombre devuelto no contiene el namespace
            String value = attr.getValue();
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + value + " in object " + animator, ex);
        }
    }

    private boolean setAttributeThisClass(final Animator animator, final DOMAttr attr, final AttrAnimatorContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();

        if (isAttributeIgnored(namespaceURI, name))
            return true; // Se trata de forma especial en otro lugar

        final AttrDesc<ClassDescAnimatorBased, Animator, AttrAnimatorContext> attrDesc = this.<ClassDescAnimatorBased, Animator, AttrAnimatorContext>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run()
                {
                    attrDesc.setAttribute(animator, attr, attrCtx);
                }
            };
            if (DOMAttrRemote.isPendingToDownload(attr)) // Ver comentarios en la clase equivalente de drawables, layouts etc
                AttrDesc.processDownloadTask((DOMAttrRemote) attr, task, attrCtx.getXMLInflater());
            else
                task.run();

            return true;
        }
        else
        {
            // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
            // y tiene prioridad la clase más derivada
            ClassDescAnimatorBased parentClass = getParentClassDescAnimatorBased();
            if (parentClass != null)
            {
                if (parentClass.setAttributeThisClass(animator, attr, attrCtx))
                    return true;

                return false;
            }
            else // if (parentClass == null) // Esto es para que se llame una sola vez al processAttrCustom al recorrer hacia arriba el árbol
            {
                XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();
                return processSetAttrCustom(animator, namespaceURI, name, value, xmlInflaterAnimator);
            }
        }

    }


    public boolean removeAttribute(Animator animator, String namespaceURI, String name, AttrAnimatorContext attrCtx)
    {
        try
        {
            return removeAttributeThisClass(animator,namespaceURI,name,attrCtx);
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error removing attribute: " + namespaceURI + " " + name + " in object " + animator, ex);
        }
    }

    //@SuppressWarnings("unchecked")
    private boolean removeAttributeThisClass(Animator animator, String namespaceURI, String name, AttrAnimatorContext attrCtx)
    {
        if (!isInit()) init();


            if (isAttributeIgnored(namespaceURI,name))
                return true; // Se trata de forma especial en otro lugar

            AttrDesc<ClassDescAnimatorBased,Animator,AttrAnimatorContext> attrDesc = this.<ClassDescAnimatorBased,Animator,AttrAnimatorContext>getAttrDesc(namespaceURI, name);
            if (attrDesc != null)
            {
                attrDesc.removeAttribute(animator,attrCtx);
                // No tiene mucho sentido añadir isPendingToDownload etc aquí, no encuentro un caso de que al eliminar el atributo el valor por defecto a definir sea remoto aunque sea un drawable lo normal será un "@null" o un drawable por defecto nativo de Android
                return true;
            }
            else
            {
                ClassDescAnimatorBased parentClass = getParentClassDescAnimatorBased();
                if (parentClass != null)
                {
                    if (parentClass.removeAttributeThisClass(animator, namespaceURI, name, attrCtx))
                        return true;
                    return false;
                }
                else
                {
                    XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();
                    return processRemoveAttrCustom(animator, namespaceURI, name, xmlInflaterAnimator);
                }
            }

    }

    private boolean processSetAttrCustom(Animator animator, String namespaceURI, String name, String value, XMLInflaterAnimator xmlInflaterAnimator)
    {
        // No se encuentra opción de proceso custom
        AttrAnimatorInflaterListener listener = xmlInflaterAnimator.getAttrAnimatorInflaterListener();
        if(listener!=null)
        {
            PageImpl page = PageImpl.getPageImpl(xmlInflaterAnimator); // Puede ser null
            return listener.setAttribute(page, animator, namespaceURI, name, value);
        }
        return false;
    }

    private boolean processRemoveAttrCustom(Animator animator, String namespaceURI, String name, XMLInflaterAnimator xmlInflaterAnimator)
    {
        // No se encuentra opción de proceso custom
        AttrAnimatorInflaterListener listener = xmlInflaterAnimator.getAttrAnimatorInflaterListener();
        if(listener!=null)
        {
            PageImpl page = PageImpl.getPageImpl(xmlInflaterAnimator); // Puede ser null
            return listener.removeAttribute(page, animator, namespaceURI, name);
        }
        return false;
    }

    protected boolean isAttributeIgnored(String namespaceURI, String name)
    {
        return false;
    }

}
