package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.page.XMLInflaterDrawablePage;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescDrawableOrElementDrawableChild extends ClassDesc<Drawable>
{
    public ClassDescDrawableOrElementDrawableChild(ClassDescDrawableOrElementDrawableChildMgr classMgr, String className, ClassDescDrawableOrElementDrawableChild parentClass)
    {
        super(classMgr, className, parentClass);
    }

    public ClassDescDrawableOrElementDrawableChildMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableOrElementDrawableChildMgr) classMgr;
    }

    public static PageImpl getPageImpl(XMLInflaterDrawable xmlInflaterDrawable)
    {
        return (xmlInflaterDrawable instanceof XMLInflaterDrawablePage) ? ((XMLInflaterDrawablePage) xmlInflaterDrawable).getPageImpl() : null;
    }

    @SuppressWarnings("unchecked")
    protected AttrDescDrawable getAttrDescDrawable(String name)
    {
        return (AttrDescDrawable) getAttrDesc(name);
    }

    @SuppressWarnings("unchecked")
    public ClassDescDrawableOrElementDrawableChild getParentClassDescDrawable()
    {
        return (ClassDescDrawableOrElementDrawableChild) getParentClassDesc();
    }


    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI, String name)
    {
        return false; // es redefinido
    }

    public boolean setAttribute(DrawableOrElementDrawableContainer draw,DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable, Context ctx)
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
                attrDesc.setAttribute(draw.getInstanceToSetAttributes(), attr,xmlInflaterDrawable,ctx);
            }
            else
            {
                // Es importante recorrer las clases de abajo a arriba pues algún atributo se repite en varios niveles tal y como minHeight y minWidth
                // y tiene prioridad la clase más derivada

                ClassDescDrawableOrElementDrawableChild parentClass = getParentClassDescDrawable();
                if (parentClass != null)
                {
                    parentClass.setAttribute(draw,attr, xmlInflaterDrawable,ctx);
                }
                else
                {
                    // No se encuentra opción de proceso custom
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
