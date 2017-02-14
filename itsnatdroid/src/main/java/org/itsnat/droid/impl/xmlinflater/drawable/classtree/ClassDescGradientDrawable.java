package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildCorners;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildPadding;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildSize;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildSolid;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildStroke;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawable extends ClassDescElementDrawableBased<GradientDrawable>
{
    public static final MapSmart<String,Integer> shapeValueMap = MapSmart.<String,Integer>create(4);
    static
    {
        shapeValueMap.put("rectangle", 0);
        shapeValueMap.put("oval", 1);
        shapeValueMap.put("line", 2);
        shapeValueMap.put("ring", 3);
    }

    // shape type
    private static final int RECTANGLE = 0;
    private static final int OVAL = 1;
    private static final int LINE = 2;
    private static final int RING = 3;

    // Sólo LOLLIPOP y superiores
    private static final int RADIUS_TYPE_PIXELS = 0;
    private static final int RADIUS_TYPE_FRACTION = 1;
    private static final int RADIUS_TYPE_FRACTION_PARENT = 2;
    // Fin LOLLIPOP y superiores

    protected FieldContainer<Integer> innerRadiusField;
    protected FieldContainer<Float> innerRadiusRatioField;
    protected FieldContainer<Integer> thicknessField;
    protected FieldContainer<Float> thicknessRatioField;
    protected FieldContainer<Boolean> useLevelForShapeField;


    protected FieldContainer<Drawable.ConstantState> gradientStateField;
    protected FieldContainer<Integer> gradientRadiusTypeField;  // LOLLIPOP y sup
    protected FieldContainer<GradientDrawable.Orientation> orientationField;
    protected FieldContainer<int[]> gradientColorsField;
    protected FieldContainer<float[]> gradientPositionsField;
    protected FieldContainer<Rect> gradientPaddingField;
    protected FieldContainer<Rect> gradientPaddingField2;

    public ClassDescGradientDrawable(ClassDescDrawableMgr classMgr, ClassDescElementDrawableBased<? super GradientDrawable> parent)
    {
        super(classMgr,"shape",parent);

        this.gradientStateField = new FieldContainer<Drawable.ConstantState>(GradientDrawable.class, "mGradientState");
        Class gradientStateClass = MiscUtil.resolveClass(GradientDrawable.class.getName() + "$GradientState");

        this.innerRadiusField = new FieldContainer<Integer>(gradientStateClass, "mInnerRadius");
        this.innerRadiusRatioField = new FieldContainer<Float>(gradientStateClass, "mInnerRadiusRatio");
        this.thicknessField = new FieldContainer<Integer>(gradientStateClass, "mThickness");
        this.thicknessRatioField = new FieldContainer<Float>(gradientStateClass, "mThicknessRatio");
        this.useLevelForShapeField = new FieldContainer<Boolean>(gradientStateClass, "mUseLevelForShape");

        if (Build.VERSION.SDK_INT >= MiscUtil.LOLLIPOP) // 5.0
        {
            gradientRadiusTypeField = new FieldContainer<Integer>(gradientStateClass, "mGradientRadiusType");
        }
        this.orientationField = new FieldContainer<GradientDrawable.Orientation>(gradientStateClass, "mOrientation");
        if (Build.VERSION.SDK_INT >= MiscUtil.MARSHMALLOW) // 6.0
            this.gradientColorsField = new FieldContainer<int[]>(gradientStateClass, "mGradientColors");
        else
            this.gradientColorsField = new FieldContainer<int[]>(gradientStateClass, "mColors");
        this.gradientPositionsField = new FieldContainer<float[]>(gradientStateClass, "mPositions");
        this.gradientPaddingField = new FieldContainer<Rect>(GradientDrawable.class, "mPadding");
        this.gradientPaddingField2 = new FieldContainer<Rect>(gradientStateClass, "mPadding");
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        GradientDrawable drawable = new GradientDrawable();

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        XMLInflaterRegistry xmlInflaterRegistry = classMgr.getXMLInflaterRegistry();

        Drawable.ConstantState gradientState = gradientStateField.get(drawable);

        DOMAttr attrShape = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "shape");
        int shape = attrShape != null ? AttrDesc.<Integer>parseSingleName(attrShape.getValue(), shapeValueMap) : RECTANGLE;

        DOMAttr attrDither = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "dither");
        boolean dither = attrDither != null ? xmlInflaterRegistry.getBoolean(attrDither.getResourceDesc(),xmlInflaterContext) : false;


        if (shape == RING)
        {
            DOMAttr attrInnerRadius = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "innerRadius");
            int innerRadius = attrInnerRadius != null ? xmlInflaterRegistry.getDimensionIntRound(attrInnerRadius.getResourceDesc(), xmlInflaterContext) : -1; // Hay que iniciar en -1 para que no se use
            innerRadiusField.set(gradientState,innerRadius);
            if (innerRadius == -1)
            {
                DOMAttr attrInnerRadiusRatio = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "innerRadiusRatio");
                if (attrInnerRadiusRatio != null)
                {
                    float innerRadiusRatio = xmlInflaterRegistry.getFloat(attrInnerRadiusRatio.getResourceDesc(), xmlInflaterContext);
                    innerRadiusRatioField.set(gradientState,innerRadiusRatio);
                }
            }

            DOMAttr attrThickness = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "thickness");
            int thickness = attrThickness != null ? xmlInflaterRegistry.getDimensionIntRound(attrThickness.getResourceDesc(), xmlInflaterContext) : -1;   // Hay que iniciar en -1 para que no se use
            thicknessField.set(gradientState,thickness);
            if (thickness == -1)
            {
                DOMAttr attrThicknessRatio = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "thicknessRatio");
                if (attrThicknessRatio != null)
                {
                    float thicknessRatio = xmlInflaterRegistry.getFloat(attrThicknessRatio.getResourceDesc(),xmlInflaterContext);
                    thicknessRatioField.set(gradientState,thicknessRatio);
                }
            }

            DOMAttr attrUseLevelForShape = rootElem.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "useLevel");
            boolean useLevelForShape = attrUseLevelForShape != null ? xmlInflaterRegistry.getBoolean(attrUseLevelForShape.getResourceDesc(), xmlInflaterContext) : true;
            useLevelForShapeField.set(gradientState,useLevelForShape);
        }

        drawable.setShape(shape);
        drawable.setDither(dither);


        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem, elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> childList = elementDrawableRoot.getElementDrawableChildList();

        for(int i = 0; i < childList.size(); i++)
        {
            ElementDrawableChildBase child = childList.get(i);
            if (child instanceof GradientDrawableChildCorners)
            {
                processCorners(drawable,(GradientDrawableChildCorners)child);
            }
            else if (child instanceof GradientDrawableChildGradient)
            {
                processGradient(drawable,(GradientDrawableChildGradient)child,gradientState);
            }
            else if (child instanceof GradientDrawableChildPadding)
            {
                processPadding(drawable,(GradientDrawableChildPadding)child, gradientState);
            }
            else if (child instanceof GradientDrawableChildSize)
            {
                processSize(drawable,(GradientDrawableChildSize)child);
            }
            else if (child instanceof GradientDrawableChildSolid)
            {
                processSolid(drawable,(GradientDrawableChildSolid)child);
            }
            else if (child instanceof GradientDrawableChildStroke)
            {
                processStroke(drawable, (GradientDrawableChildStroke)child);
            }
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    private void processCorners(GradientDrawable drawable,GradientDrawableChildCorners child)
    {
        Integer radiusObj = child.getRadius();
        int radius = radiusObj != null ? radiusObj.intValue() : 0;
        drawable.setCornerRadius(radius);

        Integer topLeftRadiusObj = child.getTopLeftRadius();
        int topLeftRadius = topLeftRadiusObj != null ? topLeftRadiusObj.intValue() : radius;

        Integer topRightRadiusObj = child.getTopRightRadius();
        int topRightRadius = topRightRadiusObj != null ? topRightRadiusObj.intValue() : radius;

        Integer bottomLeftRadiusObj = child.getBottomLeftRadius();
        int bottomLeftRadius = bottomLeftRadiusObj != null ? bottomLeftRadiusObj.intValue() : radius;

        Integer bottomRightRadiusObj = child.getBottomRightRadius();
        int bottomRightRadius = bottomRightRadiusObj != null ? bottomRightRadiusObj.intValue() : radius;

        if (topLeftRadius != radius || topRightRadius != radius ||
            bottomLeftRadius != radius || bottomRightRadius != radius)
        {
            drawable.setCornerRadii(new float[]{
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            });
        }
    }

    private void processGradient(GradientDrawable drawable, GradientDrawableChildGradient child, Object gradientState)
    {
        PercFloatImpl centerXObj = child.getCenterX();
        PercFloatImpl centerYObj = child.getCenterY();
        float centerX = centerXObj != null ? centerXObj.toFloatBasedOnDataType() : 0.5f;
        float centerY = centerYObj != null ? centerYObj.toFloatBasedOnDataType() : 0.5f;
        drawable.setGradientCenter(centerX, centerY);

        Integer startColorObj = child.getStartColor();
        Integer centerColorObj = child.getCenterColor();
        Integer endColorObj = child.getEndColor();

        if (centerColorObj != null) // hasCenterColor
        {
            int[] colors = new int[3];
            colors[0] = startColorObj != null ? startColorObj.intValue() : 0;
            colors[1] = centerColorObj.intValue();
            colors[2] = endColorObj != null ? endColorObj.intValue() : 0;
            gradientColorsField.set(gradientState, colors);

            // Necessary for correct center color position in rectangle case
            float[] positions = new float[3];
            positions[0] = 0.0f;
            // Since 0.5f is default value, try to take the one that isn't 0.5f
            positions[1] = centerX != 0.5f ? centerX : centerY;
            positions[2] = 1f;
            gradientPositionsField.set(gradientState, positions);
        }
        else
        {
            int[] colors = new int[2];
            colors[0] = startColorObj != null ? startColorObj.intValue() : 0;
            colors[1] = endColorObj != null ? endColorObj.intValue() : 0;
            gradientColorsField.set(gradientState, colors);
        }

        Boolean useLevelObj = child.getUseLevel();
        boolean useLevel = useLevelObj != null ?  useLevelObj.booleanValue() : false;
        drawable.setUseLevel(useLevel);

        Integer gradientTypeObj = child.getType();
        int gradientType = gradientTypeObj != null ? gradientTypeObj.intValue() : GradientDrawable.LINEAR_GRADIENT;
        drawable.setGradientType(gradientType);

        if (gradientType == GradientDrawable.LINEAR_GRADIENT)
        {
            int angle = 0;
            Float angleFloat = child.getAngle();
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
            PercFloatImpl gradRadius = child.getGradientRadius();
            if (gradRadius != null)
            {
                float value = gradRadius.toFloatBasedOnDataType(); // gradRadius.getValue();
                drawable.setGradientRadius(value);

                if (Build.VERSION.SDK_INT >= MiscUtil.LOLLIPOP)
                {
                    int radiusType;

                    int dataType = gradRadius.getDataType();
                    if (dataType == TypedValue.TYPE_FRACTION)
                    {
                        radiusType = gradRadius.isFractionParent() ? RADIUS_TYPE_FRACTION_PARENT : RADIUS_TYPE_FRACTION;
                    }
                    else if (dataType == TypedValue.TYPE_FLOAT)
                        radiusType = RADIUS_TYPE_PIXELS;
                    else
                        throw MiscUtil.internalError();

                    gradientRadiusTypeField.set(gradientState,radiusType);
                }
            }
            else if (gradientTypeObj == GradientDrawable.RADIAL_GRADIENT)
                throw new ItsNatDroidException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }

    }

    private void processPadding(GradientDrawable drawable, GradientDrawableChildPadding child, Object gradientState)
    {
        Integer leftObj = child.getLeft();
        Integer topObj = child.getTop();
        Integer rightObj = child.getRight();
        Integer bottomObj = child.getBottom();

        int left = leftObj != null ? leftObj.intValue() : 0;
        int top = topObj != null ? topObj.intValue() : 0;
        int right = rightObj != null ? rightObj.intValue() : 0;
        int bottom = bottomObj != null ? bottomObj.intValue() : 0;

        Rect rect = new Rect(left,top,right,bottom);

        // Son necesarios los dos al menos el primero, de otra manera no se manifiesta visualmente el cambio
        gradientPaddingField.set(drawable, rect);
        gradientPaddingField2.set(gradientState, rect);
    }

    private void processSize(GradientDrawable drawable,GradientDrawableChildSize child)
    {
        Integer widthObj = child.getWidth();
        Integer heightObj = child.getHeight();
        int width = widthObj != null ? widthObj.intValue() : -1;
        int height = heightObj != null ? heightObj.intValue() : -1;

        drawable.setSize(width, height);
    }

    private void processSolid(GradientDrawable drawable,GradientDrawableChildSolid child)
    {
        Integer colorObj = child.getColor();
        int color = colorObj != null ? colorObj.intValue() : 0;
        drawable.setColor(color);
    }

    private void processStroke(GradientDrawable drawable,GradientDrawableChildStroke child)
    {
        Integer widthObj = child.getWidth();
        Integer colorObj = child.getColor();
        Float dashWidthObj = child.getDashWidth();

        int width = widthObj != null ? widthObj.intValue() : 0;
        int color = colorObj != null ? colorObj.intValue() : 0;
        float dashWidth = dashWidthObj != null ? dashWidthObj.floatValue() : 0;

        if (dashWidth != 0.0f)
        {
            Float dashGapObj = child.getDashGap();
            float dashGap = dashGapObj != null ? dashGapObj.floatValue() : 0;
            drawable.setStroke(width, color, dashWidthObj.floatValue(), dashGap);
        }
        else
        {
            drawable.setStroke(width, color);
        }
    }

    @Override
    public Class<GradientDrawable> getDrawableOrElementDrawableClass()
    {
        return GradientDrawable.class;
    }

    @Override
    public boolean isAttributeIgnored(GradientDrawable resource, String namespaceURI, String name)
    {
        if (super.isAttributeIgnored(resource,namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) &&
                (name.equals("shape") || name.equals("dither") ||
                 name.equals("innerRadius") || name.equals("innerRadiusRatio") ||
                 name.equals("thickness") || name.equals("thicknessRatio") ||
                 name.equals("useLevel"));
    }

    protected void init()
    {
        super.init();


    }

}


