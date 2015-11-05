package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodMultipleName;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodSingleName;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_GradientDrawable_shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawable extends ClassDescElementDrawableRoot<GradientDrawable>
{
    protected FieldContainer gradientStateField;
    protected FieldContainer<Integer> gradientTypeField;
    protected FieldContainer<Float> gradientRadiusField;
    protected FieldContainer<GradientDrawable.Orientation> orientationField;
    protected FieldContainer<int[]> colorsField;
    protected FieldContainer<Float> centerXField;
    protected FieldContainer<Float> centerYField;
    protected FieldContainer<Boolean> useLevelField;

    @SuppressWarnings("unchecked")
    public ClassDescGradientDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape");

        this.gradientStateField = new FieldContainer(GradientDrawable.class, "mGradientState");
        Class gradientStateClass = MiscUtil.resolveClass(GradientDrawable.class.getName() + "$GradientState");
        this.gradientTypeField  = new FieldContainer<Integer>(gradientStateClass, "mGradient");
        this.gradientRadiusField = new FieldContainer<Float>(gradientStateClass, "mGradientRadius");
        this.orientationField = new FieldContainer<GradientDrawable.Orientation>(gradientStateClass, "mOrientation");
        this.colorsField = new FieldContainer<int[]>(gradientStateClass, "mColors");
        this.centerXField = new FieldContainer<Float>(gradientStateClass, "mCenterX");
        this.centerYField = new FieldContainer<Float>(gradientStateClass, "mCenterY");
        this.useLevelField = new FieldContainer<Boolean>(gradientStateClass, "mUseLevel");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        GradientDrawable drawable = new GradientDrawable();

        inflaterDrawable.processChildElements(rootElem,elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        Object gradientState = gradientStateField.get(drawable);

        for(int i = 0; i < itemList.size(); i++)
        {
            ElementDrawable item = itemList.get(i);
            if (item instanceof GradientDrawableItemGradient)
            {
                GradientDrawableItemGradient itemGradient = (GradientDrawableItemGradient)item;

                Integer startColorObj = itemGradient.getStartColor();
                Integer centerColorObj = itemGradient.getCenterColor();
                Integer endColorObj = itemGradient.getEndColor();

                if (centerColorObj != null) // hasCenterColor
                {
                    int[] colors = new int[3];
                    colors[0] = startColorObj != null ? startColorObj.intValue() : 0;
                    colors[1] = centerColorObj.intValue();
                    colors[2] = endColorObj != null ? endColorObj.intValue() : 0;
                    colorsField.set(gradientState,colors);
                }
                else
                {
                    int[] colors = new int[2];
                    colors[0] = startColorObj != null ? startColorObj.intValue() : 0;
                    colors[1] = endColorObj != null ? endColorObj.intValue() : 0;
                    colorsField.set(gradientState,colors);
                }

                PercFloat centerX = itemGradient.getCenterX();
                if (centerX != null)
                {
                    float value = toFloat(centerX);
                    centerXField.set(gradientState,value);
                }

                PercFloat centerY = itemGradient.getCenterY();
                if (centerY != null)
                {
                    float value = toFloat(centerY);
                    centerYField.set(gradientState,value);
                }

                Boolean useLevelObj = itemGradient.getUseLevel();
                if (useLevelObj != null)
                {
                    useLevelField.set(gradientState,useLevelObj);
                }

                Integer gradientType = itemGradient.getType();
                if (gradientType != null)
                {
                    gradientTypeField.set(gradientState,gradientType);
                }
                else gradientType = GradientDrawable.LINEAR_GRADIENT;  // LINEAR_GRADIENT es por defecto

                if (gradientType == GradientDrawable.LINEAR_GRADIENT)
                {
                    int angle = 0;
                    Float angleFloat = itemGradient.getAngle();
                    if (angleFloat != null)
                    {
                        angle = (int) angleFloat.floatValue();
                        angle %= 360; // Deja el valor entre 0-360
                        if (angle % 45 != 0)
                            throw new ItsNatDroidException("<gradient> tag requires 'angle' attribute to be a multiple of 45");

                        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                        switch (angle)
                        {
                            case 0:
                                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                                break;
                            case 45:
                                orientation = GradientDrawable.Orientation.BL_TR;
                                break;
                            case 90:
                                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                                break;
                            case 135:
                                orientation = GradientDrawable.Orientation.BR_TL;
                                break;
                            case 180:
                                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                                break;
                            case 225:
                                orientation = GradientDrawable.Orientation.TR_BL;
                                break;
                            case 270:
                                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                                break;
                            case 315:
                                orientation = GradientDrawable.Orientation.TL_BR;
                                break;
                        }

                        orientationField.set(gradientState, orientation);
                    }
                }
                else // RADIAL_GRADIENT, SWEEP_GRADIENT
                {
                    PercFloat gradRadius = itemGradient.getGradientRadius();
                    if (gradRadius != null)
                    {
                        float value = toFloat(gradRadius); // gradRadius.getValue();
                        gradientRadiusField.set(gradientState,value);
                    }
                    else if (gradientType == GradientDrawable.RADIAL_GRADIENT)
                                throw new ItsNatDroidException("<gradient> tag requires 'gradientRadius' attribute with radial type");
                }
            }

        }



        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }


    @Override
    public Class<GradientDrawable> getDrawableOrElementDrawableClass()
    {
        return GradientDrawable.class;
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawable_GradientDrawable_shape(this));

    }

    private float toFloat(PercFloat valueObj)
    {
        float value = valueObj.getValue();
        int dataType = valueObj.getDataType();
        if (dataType == TypedValue.TYPE_FRACTION)
        {
            // El caso isFractionParent() no se distingue en esta versión
            value = value / 100;
        }
        else if (dataType != TypedValue.TYPE_FLOAT) throw new ItsNatDroidException("Unexpected");
        return value;
    }
}


