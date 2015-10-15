package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawable extends ClassDescRootElementDrawable<LayerDrawable>
{
    public ClassDescStateListDrawable(ClassDescDrawableOrElementDrawableChildMgr classMgr)
    {
        super(classMgr,"selector");
    }

    @Override
    public ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem,elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        StateListDrawable drawable = new StateListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            StateListDrawableItem item = (StateListDrawableItem)itemList.get(i);
            Drawable drawableItem = item.getDrawable();

            Map<Integer,Boolean> stateMap = item.getStateMap();
            int definedStateCount = stateMap.size();
            int[] definedStates = new int[definedStateCount];

            int j = 0;
            for(Map.Entry<Integer,Boolean> stateEntry : stateMap.entrySet())
            {
                int currentState = stateEntry.getKey(); // Ej android.R.attr.state_pressed
                Boolean state = stateEntry.getValue(); // No puede ser null
                definedStates[j] = state.booleanValue() ? currentState : -currentState;
                j++;
            }

            drawable.addState(definedStates,drawableItem);
        }

        return new ElementDrawableRoot(drawable,itemList);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;
        return isSrcAttribute(namespaceURI, name); // Se trata de forma especial en otro lugar
    }

    private static boolean isSrcAttribute(String namespaceURI,String name)
    {
        return InflatedXML.XMLNS_ANDROID.equals(namespaceURI) && name.equals("src");
    }

    @Override
    public Class<LayerDrawable> getDrawableOrElementDrawableClass()
    {
        return LayerDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
