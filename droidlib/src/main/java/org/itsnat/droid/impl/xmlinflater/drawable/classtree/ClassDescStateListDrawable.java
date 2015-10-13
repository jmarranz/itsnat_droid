package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawable extends ClassDescRootElementDrawable<LayerDrawable>
{
    public ClassDescStateListDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"selector");
    }

    @Override
    public ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {

        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem,elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();
        Drawable[] drawableItems = new Drawable[itemList.size()];
        for(int i = 0; i < itemList.size(); i++)
        {
            StateListDrawableItem item = (StateListDrawableItem)itemList.get(i);
            drawableItems[i] = item.getDrawable();
        }

        StateListDrawable drawable = new StateListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            StateListDrawableItem item = (StateListDrawableItem)itemList.get(i);

            int definedCount = item.getDefinedCount();
            int[] definedStates = new int[definedCount];
            int definedCurrent = 0;

            if (definedCurrent < definedCount)
            {
                Boolean state = item.getState_pressed();
                if (state != null)
                {
                    definedStates[definedCurrent] = state.booleanValue() ? android.R.attr.state_pressed : -android.R.attr.state_pressed;
                    definedCurrent++;
                }
            }

            if (definedCurrent < definedCount)
            {
                Boolean state = item.getState_focused();
                if (state != null)
                {
                    definedStates[definedCurrent] = state.booleanValue() ? android.R.attr.state_focused : -android.R.attr.state_focused;
                    definedCurrent++;
                }
            }

            drawable.addState(definedStates,drawableItems[i]);

            /*
            protected Boolean state_pressed;
            protected Boolean state_focused;
            protected Boolean state_hovered;
            protected Boolean state_selected;
            protected Boolean state_checkable;
            protected Boolean state_checked;
            protected Boolean state_enabled;
            protected Boolean state_activated;
            protected Boolean state_window_focused;
            */

        }

        return new ElementDrawableRoot(drawable,itemList);
    }

    @Override
    protected boolean isAttributeIgnored(LayerDrawable draw,String namespaceURI,String name)
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
    public Class<LayerDrawable> getDrawableClass()
    {
        return LayerDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
