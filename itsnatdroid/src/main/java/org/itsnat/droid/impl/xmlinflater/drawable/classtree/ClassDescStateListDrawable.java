package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.StateListDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescStateListDrawable extends ClassDescElementDrawableContainerBased<StateListDrawable>
{
    protected MethodContainer<DrawableContainer.DrawableContainerState> methodGetStateListState;
    protected MethodContainer<Void> methodGetStateListStateIsConstantSize;

    public ClassDescStateListDrawable(ClassDescDrawableMgr classMgr, ClassDescElementDrawableContainer parent)
    {
        super(classMgr, "selector", parent);

        this.methodGetStateListState = new MethodContainer<DrawableContainer.DrawableContainerState>(StateListDrawable.class,"getStateListState");
        this.methodGetStateListStateIsConstantSize =
                new MethodContainer<Void>(DrawableContainer.class.getName() + "$DrawableContainerState","setConstantSize",boolean.class);
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();

        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> itemList = elementDrawableRoot.getElementDrawableChildList();

        StateListDrawable drawable = new StateListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            StateListDrawableChildItem item = (StateListDrawableChildItem)itemList.get(i);

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

            setCallback(drawableItem,drawable);   // Al final si se sigue hasta addChild(Drawable dr) lo encontramos
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public Class<StateListDrawable> getDrawableOrElementDrawableClass()
    {
        return StateListDrawable.class;
    }

    protected void init()
    {
        super.init();


    }


}
