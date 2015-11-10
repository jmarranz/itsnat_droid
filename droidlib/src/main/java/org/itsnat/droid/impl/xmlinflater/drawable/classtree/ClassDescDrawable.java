package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.page.XMLInflaterDrawablePage;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescDrawable extends ClassDesc<Drawable>
{
    public ClassDescDrawable(ClassDescDrawableMgr classMgr, String elemName, ClassDescDrawable parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableMgr) classMgr;
    }

    public String getElementName()
    {
        return getClassName();
    }

    public static PageImpl getPageImpl(XMLInflaterDrawable xmlInflaterDrawable)
    {
        return (xmlInflaterDrawable instanceof XMLInflaterDrawablePage) ? ((XMLInflaterDrawablePage) xmlInflaterDrawable).getPageImpl() : null;
    }

    protected AttrDescDrawable getAttrDescDrawable(String name)
    {
        return (AttrDescDrawable) getAttrDesc(name);
    }

    public ClassDescDrawable getParentClassDescDrawable()
    {
        return (ClassDescDrawable) getParentClassDesc();
    }


    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI, String name)
    {
        return false; // es redefinido
    }


    @SuppressWarnings("unchecked")
    public boolean setAttribute(DrawableOrElementDrawableWrapper draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        if (!isInit()) init();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace

        if (isAttributeIgnored(draw,namespaceURI, name)) return false; // Se trata de forma especial en otro lugar

        if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
        {
            AttrDescDrawable attrDesc = getAttrDescDrawable(name);
            if (attrDesc != null)
            {
                attrDesc.setAttribute(draw.getInstanceToSetAttributes(), attr,attrCtx);
            }
            else
            {
                // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
                // y tiene prioridad la clase más derivada

                ClassDescDrawable parentClass = getParentClassDescDrawable();
                if (parentClass != null)
                {
                    parentClass.setAttribute(draw,attr,attrCtx);
                }
                else
                {
                    // No se encuentra opción de proceso custom
                    XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
                    AttrDrawableInflaterListener listener = xmlInflaterDrawable.getAttrDrawableInflaterListener();
                    if (listener != null)
                    {
                        PageImpl page = getPageImpl(xmlInflaterDrawable); // Puede ser nulo
                        String value = attr.getValue();
                        listener.setAttribute(page,draw.getDrawable(), namespaceURI, name, value);
                    }
                }
            }
        }
        else
        {
            // No se encuentra opción de proceso custom
            XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
            AttrDrawableInflaterListener listener = xmlInflaterDrawable.getAttrDrawableInflaterListener();
            if (listener != null)
            {
                PageImpl page = getPageImpl(xmlInflaterDrawable); // Puede ser nulo
                String value = attr.getValue();
                listener.setAttribute(page, draw.getDrawable(), namespaceURI, name, value);
            }
        }

        return true;
    }


    public abstract Class<?> getDrawableOrElementDrawableClass();
}
