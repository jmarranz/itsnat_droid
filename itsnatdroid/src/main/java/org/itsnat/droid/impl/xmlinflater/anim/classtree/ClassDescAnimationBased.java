package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;

import org.itsnat.droid.AttrAnimationInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.XMLInflaterAnimation;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimationBased<T extends Animation> extends ClassDesc<T>
{
    @SuppressWarnings("unchecked")
    public ClassDescAnimationBased(ClassDescAnimationMgr classMgr, String tagName, ClassDescAnimationBased parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimationMgr getClassDescAnimationMgr()
    {
        return (ClassDescAnimationMgr) classMgr;
    }

    public ClassDescAnimationBased getParentClassDescAnimationBased()
    {
        return (ClassDescAnimationBased) getParentClassDesc(); // Puede ser null
    }

    public abstract Class<T> getDeclaredClass();

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

    public Animation createRootAnimationNativeAndFillAttributes(DOMElemAnimation rootDOMElemAnimation, AttrAnimationContext attrCtx)
    {
        XMLInflaterAnimation xmlInflaterAnimation = attrCtx.getXMLInflaterAnimation();

        Animation rootAnimation = createAnimationNative(xmlInflaterAnimation.getContext());
        xmlInflaterAnimation.getInflatedAnimation().setRootAnimation(rootAnimation); // Lo antes posible

        fillAnimationAttributes(rootAnimation, rootDOMElemAnimation, attrCtx);

        return rootAnimation;
    }

    public Animation createAnimationNativeAndFillAttributes(DOMElemAnimation domElement, AttrAnimationContext attrCtx)
    {
        XMLInflaterAnimation xmlInflaterAnimation = attrCtx.getXMLInflaterAnimation();

        Animation animation = createAnimationNative(xmlInflaterAnimation.getContext());

        fillAnimationAttributes(animation, domElement, attrCtx);

        return animation;
    }

    protected abstract T createAnimationNative(Context ctx);

    protected void fillAnimationAttributes(Animation animation,DOMElemAnimation domElement,AttrAnimationContext attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(animation, attr, attrCtx);
            }
        }
    }

    protected boolean setAttribute(final Animation animation, final DOMAttr attr, final AttrAnimationContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String value = attr.getValue();

        try
        {
            if (setAttributeThisClass(animation,attr,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processSetAttrCustom(animation, namespaceURI, name, value, xmlInflaterContext);
        }
        catch (Exception ex)
        {
            throw new ItsNatDroidException("Error setting attribute: " + namespaceURI + " " + name + " " + value + " in object " + animation, ex);
        }
    }

    private boolean setAttributeThisClass(final Animation animation, final DOMAttr attr, final AttrAnimationContext attrCtx)
    {
        // Devolvemos true si consideramos "procesado", esto incluye que sea ignorado o procesado custom

        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        //String value = attr.getValue();

        if (isAttributeIgnored(namespaceURI, name))
            return true; // Se trata de forma especial en otro lugar

        final AttrDesc<ClassDescAnimationBased, Animation, AttrAnimationContext> attrDesc = this.<ClassDescAnimationBased, Animation, AttrAnimationContext>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            Runnable task = new Runnable()
            {
                @Override
                public void run()
                {
                    attrDesc.setAttribute(animation, attr, attrCtx);
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
            ClassDescAnimationBased parentClass = getParentClassDescAnimationBased();
            if (parentClass != null)
            {
                return parentClass.setAttributeThisClass(animation, attr, attrCtx);
            }

            return false;
        }
    }


    public boolean removeAttribute(Animation animation, String namespaceURI, String name, AttrAnimationContext attrCtx)
    {
        try
        {
            if (removeAttributeThisClass(animation,namespaceURI,name,attrCtx))
                return true;

            XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();
            return processRemoveAttrCustom(animation, namespaceURI, name, xmlInflaterContext);
        }
        catch(Exception ex)
        {
            throw new ItsNatDroidException("Error removing attribute: " + namespaceURI + " " + name + " in object " + animation, ex);
        }
    }

    //@SuppressWarnings("unchecked")
    private boolean removeAttributeThisClass(Animation animation, String namespaceURI, String name, AttrAnimationContext attrCtx)
    {
        if (!isInit()) init();


        if (isAttributeIgnored(namespaceURI,name))
            return true; // Se trata de forma especial en otro lugar

        AttrDesc<ClassDescAnimationBased,Animation,AttrAnimationContext> attrDesc = this.<ClassDescAnimationBased,Animation,AttrAnimationContext>getAttrDesc(namespaceURI, name);
        if (attrDesc != null)
        {
            attrDesc.removeAttribute(animation,attrCtx);
            // No tiene mucho sentido añadir isPendingToDownload etc aquí, no encuentro un caso de que al eliminar el atributo el valor por defecto a definir sea remoto aunque sea un drawable lo normal será un "@null" o un drawable por defecto nativo de Android
            return true;
        }
        else
        {
            ClassDescAnimationBased parentClass = getParentClassDescAnimationBased();
            if (parentClass != null)
            {
                return parentClass.removeAttributeThisClass(animation, namespaceURI, name, attrCtx);
            }

            return false;
        }
    }

    private boolean processSetAttrCustom(Animation animation, String namespaceURI, String name, String value, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrAnimationInflaterListener listener = xmlInflaterContext.getAttrInflaterListeners().getAttrAnimationInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.setAttribute(page, animation, namespaceURI, name, value);
        }
        return false;
    }

    private boolean processRemoveAttrCustom(Animation animation, String namespaceURI, String name, XMLInflaterContext xmlInflaterContext)
    {
        // No se encuentra opción de proceso custom
        AttrAnimationInflaterListener listener = xmlInflaterContext.getAttrInflaterListeners().getAttrAnimationInflaterListener();
        if(listener!=null)
        {
            PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null
            return listener.removeAttribute(page, animation, namespaceURI, name);
        }
        return false;
    }

    protected boolean isAttributeIgnored(String namespaceURI, String name)
    {
        return false;
    }

}
