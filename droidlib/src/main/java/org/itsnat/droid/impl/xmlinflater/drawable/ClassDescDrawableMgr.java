package org.itsnat.droid.impl.xmlinflater.drawable;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescBitmapDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescClipDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescColorDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescInsetDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLayerDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLayerDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLevelListDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescNinePatchDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescScaleDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescStateListDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescStateListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescTransitionDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescTransitionDrawableItem;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescDrawableMgr extends ClassDescMgr<ClassDescDrawable>
{
    public ClassDescDrawableMgr(XMLInflateRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    public ClassDescDrawable get(String className)
    {
        return classes.get(className);
    }

    @Override
    protected void initClassDesc()
    {
        ClassDescElementDrawableChildDrawableBridge childBridge = new ClassDescElementDrawableChildDrawableBridge(this);
        addClassDesc(childBridge);

        ClassDescBitmapDrawable bitmap = new ClassDescBitmapDrawable(this);
        addClassDesc(bitmap);

        ClassDescNinePatchDrawable ninePatch = new ClassDescNinePatchDrawable(this);
        addClassDesc(ninePatch);

        ClassDescClipDrawable clip = new ClassDescClipDrawable(this);
        addClassDesc(clip);

        ClassDescColorDrawable color = new ClassDescColorDrawable(this);
        addClassDesc(color);

        ClassDescLayerDrawable layer = new ClassDescLayerDrawable(this);
        addClassDesc(layer);
        ClassDescLayerDrawableItem layerItem = new ClassDescLayerDrawableItem(this);
        addClassDesc(layerItem);

            ClassDescTransitionDrawable transition = new ClassDescTransitionDrawable(this,layer);
            addClassDesc(transition);
            ClassDescTransitionDrawableItem transitionItem = new ClassDescTransitionDrawableItem(this,layerItem);
            addClassDesc(transitionItem);

        ClassDescStateListDrawable stateList = new ClassDescStateListDrawable(this);
        addClassDesc(stateList);
        ClassDescStateListDrawableItem stateListItem = new ClassDescStateListDrawableItem(this);
        addClassDesc(stateListItem);

        ClassDescLevelListDrawable levelList = new ClassDescLevelListDrawable(this);
        addClassDesc(levelList);
        ClassDescLevelListDrawableItem levelListItem = new ClassDescLevelListDrawableItem(this);
        addClassDesc(levelListItem);

        ClassDescInsetDrawable inset = new ClassDescInsetDrawable(this);
        addClassDesc(inset);

        ClassDescScaleDrawable scale = new ClassDescScaleDrawable(this);
        addClassDesc(scale);

        ClassDescGradientDrawable gradient = new ClassDescGradientDrawable(this);
        addClassDesc(gradient);
        ClassDescGradientDrawableItemGradient gradientGradientItem = new ClassDescGradientDrawableItemGradient(this);
        addClassDesc(gradientGradientItem);

    }
}
