package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawable extends ClassDescElementDrawableRoot<LayerDrawable>
{
    protected MethodContainer methodGetStateListState;
    protected MethodContainer methodGetStateListStateIsConstantSize;

    @SuppressWarnings("unchecked")
    public ClassDescStateListDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"selector");

        this.methodGetStateListState = new MethodContainer(StateListDrawable.class,"getStateListState");
        this.methodGetStateListStateIsConstantSize =
                new MethodContainer(MiscUtil.resolveClass(android.graphics.drawable.DrawableContainer.class.getName() + "$DrawableContainerState"),"setConstantSize",boolean.class);
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

            Boolean constantSize = item.getConstantSize();
            Boolean variablePadding = item.getVariablePadding();
            Boolean visible = item.getVisible();
            if (constantSize != null || variablePadding != null || visible != null)
            {
                Object stateListState = methodGetStateListState.invoke(drawable,(Object[]) null);
                if (constantSize != null)
                    methodGetStateListStateIsConstantSize.invoke(stateListState,constantSize);
                if (variablePadding != null)
                    methodGetStateListStateIsConstantSize.invoke(stateListState,variablePadding);
                if (visible != null)
                    methodGetStateListStateIsConstantSize.invoke(stateListState,visible);
            }

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
