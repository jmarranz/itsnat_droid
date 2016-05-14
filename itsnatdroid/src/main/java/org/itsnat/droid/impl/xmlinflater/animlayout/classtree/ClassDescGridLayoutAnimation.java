package org.itsnat.droid.impl.xmlinflater.animlayout.classtree;

import android.content.Context;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.animlayout.ClassDescLayoutAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodPercFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescGridLayoutAnimation extends ClassDescLayoutAnimationBased<GridLayoutAnimationController>
{
    public static final MapSmart<String,Integer> directionMap = MapSmart.<String,Integer>create(4);
    static
    {
        directionMap.put("left_to_right", GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT); // Animates columns from left to right.
        directionMap.put("right_to_left", GridLayoutAnimationController.DIRECTION_RIGHT_TO_LEFT); // Animates columns from right to left.
        directionMap.put("top_to_bottom", GridLayoutAnimationController.DIRECTION_TOP_TO_BOTTOM); // Animates rows from top to bottom.
        directionMap.put("bottom_to_top", GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP); // Animates rows from bottom to top.
    }

    public static final MapSmart<String,Integer> directionPriorityMap = MapSmart.<String,Integer>create(3);
    static
    {
        directionPriorityMap.put("none",    GridLayoutAnimationController.PRIORITY_NONE);
        directionPriorityMap.put("column",  GridLayoutAnimationController.PRIORITY_COLUMN);
        directionPriorityMap.put("row",     GridLayoutAnimationController.PRIORITY_ROW);
    }

    public ClassDescGridLayoutAnimation(ClassDescLayoutAnimationMgr classMgr, ClassDescLayoutAnimationBased<LayoutAnimationController> parentClass)
    {
        super(classMgr, "gridLayoutAnimation", parentClass);
    }

    @Override
    public Class<GridLayoutAnimationController> getDeclaredClass()
    {
        return GridLayoutAnimationController.class;
    }

    @Override
    protected GridLayoutAnimationController createResourceNative(Context ctx)
    {
        return new GridLayoutAnimationController(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodPercFloat(this,"columnDelay",false,new PercFloat(0)));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this,"direction", directionMap, "left_to_right|top_to_bottom"));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this,"directionPriority",int.class,directionPriorityMap, "none"));
        addAttrDescAN(new AttrDescReflecMethodPercFloat(this,"rowDelay",false,new PercFloat(0)));
    }

}
