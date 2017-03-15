package org.itsnat.droid.impl.xmlinflater.menu;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescAnimationDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescAnimationDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescBitmapDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescClipDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescColorDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildCorners;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildGradient;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildPadding;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildSize;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildSolid;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescGradientDrawableChildStroke;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescInsetDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLayerDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLayerDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLevelListDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescLevelListDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescNinePatchDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescRotateDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescScaleDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescStateListDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescStateListDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescTransitionDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescTransitionDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescMenuMgr extends ClassDescMgr<ClassDescResourceBased>
{
    public ClassDescMenuMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescResourceBased get(String classOrDOMElemName)
    {
        return classes.get(classOrDOMElemName);
    }


    @Override
    protected void initClassDesc()
    {
        // https://developer.android.com/guide/topics/resources/drawable-resource.html
        // Las clases suelen tener un método para "inflar": public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
        // en vez de inflarse en el constructor
        // En la documentación del código fuente al principio se listan los atributos que definen la clase y los item hijo (se añade al nombre del atributo "Item")
        // Ej Attr:
        //    ref android.R.styleable#AnimationDrawableItem_drawable

        ClassDescElementDrawableChildDrawableBridge childBridge = new ClassDescElementDrawableChildDrawableBridge(this);
        addClassDesc(childBridge);

        ClassDescElementDrawable drawable = new ClassDescElementDrawable(this); // no tiene atributos ni Drawable clase padre

            // 	android.graphics.drawable.AnimatedVectorDrawable es level 21

            ClassDescBitmapDrawable bitmap = new ClassDescBitmapDrawable(this,drawable);
            addClassDesc(bitmap);

            ClassDescColorDrawable color = new ClassDescColorDrawable(this,drawable);
            addClassDesc(color);

            // DrawableContainer y derivadas
            // No llamamos a addClassDesc() porque no hay clase que se instancie, es clase base
            ClassDescElementDrawableContainer drawableContainer = new ClassDescElementDrawableContainer(this,drawable);

                ClassDescAnimationDrawable animation = new ClassDescAnimationDrawable(this,drawableContainer);
                addClassDesc(animation);
                ClassDescAnimationDrawableChildItem animationItem = new ClassDescAnimationDrawableChildItem(this);
                addClassDesc(animationItem);

                ClassDescLevelListDrawable levelList = new ClassDescLevelListDrawable(this,drawableContainer);
                addClassDesc(levelList);
                ClassDescLevelListDrawableChildItem levelListItem = new ClassDescLevelListDrawableChildItem(this);
                addClassDesc(levelListItem);

                ClassDescStateListDrawable stateList = new ClassDescStateListDrawable(this,drawableContainer);
                addClassDesc(stateList);
                ClassDescStateListDrawableChildItem stateListItem = new ClassDescStateListDrawableChildItem(this);
                addClassDesc(stateListItem);

                    // AnimatedStateListDrawable hereda de StateListDrawable pero es level 21

            // android.graphics.drawable.DrawableWrapper  es level 23 pero lo vamos teniendo en cuenta
            //      estas són sus derivadas

            ClassDescClipDrawable clip = new ClassDescClipDrawable(this,drawable);
            addClassDesc(clip);

            ClassDescInsetDrawable inset = new ClassDescInsetDrawable(this,drawable);
            addClassDesc(inset);

            ClassDescRotateDrawable rotate = new ClassDescRotateDrawable(this,drawable);
            addClassDesc(rotate);

            ClassDescScaleDrawable scale = new ClassDescScaleDrawable(this,drawable);
            addClassDesc(scale);

            // Derivadas directas de Drawable NO DrawableWrapper

            ClassDescGradientDrawable gradient = new ClassDescGradientDrawable(this,drawable);
            addClassDesc(gradient);
            ClassDescGradientDrawableChildCorners gradientCornersItem = new ClassDescGradientDrawableChildCorners(this);
            addClassDesc(gradientCornersItem);
            ClassDescGradientDrawableChildGradient gradientGradientItem = new ClassDescGradientDrawableChildGradient(this);
            addClassDesc(gradientGradientItem);
            ClassDescGradientDrawableChildPadding gradientPaddingItem = new ClassDescGradientDrawableChildPadding(this);
            addClassDesc(gradientPaddingItem);
            ClassDescGradientDrawableChildSize gradientSizeItem = new ClassDescGradientDrawableChildSize(this);
            addClassDesc(gradientSizeItem);
            ClassDescGradientDrawableChildSolid gradientSolidItem = new ClassDescGradientDrawableChildSolid(this);
            addClassDesc(gradientSolidItem);
            ClassDescGradientDrawableChildStroke gradientStrokeItem = new ClassDescGradientDrawableChildStroke(this);
            addClassDesc(gradientStrokeItem);

            ClassDescLayerDrawable layer = new ClassDescLayerDrawable(this,drawable);
            addClassDesc(layer);
            ClassDescLayerDrawableChildItem layerItem = new ClassDescLayerDrawableChildItem(this);
            addClassDesc(layerItem);

                ClassDescTransitionDrawable transition = new ClassDescTransitionDrawable(this,layer);
                addClassDesc(transition);
                ClassDescTransitionDrawableChildItem transitionItem = new ClassDescTransitionDrawableChildItem(this,layerItem);
                addClassDesc(transitionItem);

                // RippleDrawable es level 21

            ClassDescNinePatchDrawable ninePatch = new ClassDescNinePatchDrawable(this,drawable);
            addClassDesc(ninePatch);

            // ShapeDrawable no se como usarlo como XML pues <shape> se refiere a GradientDrawable, parece que ShapeDrawable es una antigualla o bien realmente sólo se puede usar via objeto Java
    }
}
