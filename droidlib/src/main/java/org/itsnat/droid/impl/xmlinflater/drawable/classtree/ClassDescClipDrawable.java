package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescClipDrawable extends ClassDescElementDrawableRoot<ClipDrawable>
{
    // Para el atributo clipOrientation
    // No podemos usar OrientationUtil porque los valores numéricos SON DIFERENTES (1 y 2 en vez de 0 y 1), hay que joderse con la falta de homogeneidad
    private static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create(2);
    static
    {
        valueMap.put("horizontal", ClipDrawable.HORIZONTAL /* 1 */);
        valueMap.put("vertical", ClipDrawable.VERTICAL /* 2 */);
    }

    public ClassDescClipDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"clip");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, ctx, childList);

        //XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        DOMAttr attrGravity = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "gravity");
        int gravity = attrGravity != null ? AttrDesc.parseMultipleName(attrGravity.getValue(), GravityUtil.valueMap) : Gravity.LEFT; // Valor concreto no puede ser un recurso

        DOMAttr attrClipOrientation = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "clipOrientation");
        int orientation = attrClipOrientation != null ? AttrDesc.<Integer>parseSingleName(attrClipOrientation.getValue(), valueMap) : ClipDrawable.HORIZONTAL; // Valor concreto no puede ser un recurso

        elementDrawableRoot.setDrawable(new ClipDrawable(childDrawable,gravity,orientation));

        return elementDrawableRoot;
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("clipOrientation".equals(name) || "drawable".equals(name) || "gravity".equals(name));
        }
        return false;
    }


    @Override
    public Class<ClipDrawable> getDrawableOrElementDrawableClass()
    {
        return ClipDrawable.class;
    }

    protected void init()
    {
        super.init();

    }
}
