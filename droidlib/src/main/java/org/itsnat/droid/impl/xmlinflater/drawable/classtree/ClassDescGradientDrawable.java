package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemCorners;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemPadding;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemSize;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemSolid;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemStroke;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawable extends ClassDescElementDrawableRoot<GradientDrawable>
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


    protected FieldContainer<Object> gradientStateField;
    protected FieldContainer<Integer> gradientRadiusTypeField;  // LOLLIPOP y sup
    protected FieldContainer<GradientDrawable.Orientation> orientationField;
    protected FieldContainer<int[]> gradientColorsField;
    protected FieldContainer<float[]> gradientPositionsField;
    protected FieldContainer<Rect> gradientPaddingField;
    protected FieldContainer<Rect> gradientPaddingField2;

    public ClassDescGradientDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape");

        this.gradientStateField = new FieldContainer<Object>(GradientDrawable.class, "mGradientState");
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
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        GradientDrawable drawable = new GradientDrawable();

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();

        Object gradientState = gradientStateField.get(drawable);

        DOMAttr attrShape = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "shape");
        int shape = attrShape != null ? AttrDesc.<Integer>parseSingleName(attrShape.getValue(), shapeValueMap) : RECTANGLE;

        DOMAttr attrDither = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "dither");
        boolean dither = attrDither != null ? xmlInflateRegistry.getBoolean(attrDither.getValue(),ctx) : false;


        if (shape == RING)
        {
            DOMAttr attrInnerRadius = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "innerRadius");
            int innerRadius = attrInnerRadius != null ? xmlInflateRegistry.getDimensionIntRound(attrInnerRadius.getValue(), ctx) : -1; // Hay que iniciar en -1 para que no se use
            innerRadiusField.set(gradientState,innerRadius);
            if (innerRadius == -1)
            {
                DOMAttr attrInnerRadiusRatio = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "innerRadiusRatio");
                if (attrInnerRadiusRatio != null)
                {
                    float innerRadiusRatio = xmlInflateRegistry.getFloat(attrInnerRadiusRatio.getValue(), ctx);
                    innerRadiusRatioField.set(gradientState,innerRadiusRatio);
                }
            }

            DOMAttr attrThickness = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "thickness");
            int thickness = attrThickness != null ? xmlInflateRegistry.getDimensionIntRound(attrThickness.getValue(), ctx) : -1;   // Hay que iniciar en -1 para que no se use
            thicknessField.set(gradientState,thickness);
            if (thickness == -1)
            {
                DOMAttr attrThicknessRatio = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "thicknessRatio");
                if (attrThicknessRatio != null)
                {
                    float thicknessRatio = xmlInflateRegistry.getFloat(attrThicknessRatio.getValue(), ctx);
                    thicknessRatioField.set(gradientState,thicknessRatio);
                }
            }

            DOMAttr attrUseLevelForShape = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "useLevel");
            boolean useLevelForShape = attrUseLevelForShape != null ? xmlInflateRegistry.getBoolean(attrUseLevelForShape.getValue(), ctx) : true;
            useLevelForShapeField.set(gradientState,useLevelForShape);
        }

        drawable.setShape(shape);
        drawable.setDither(dither);

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        for(int i = 0; i < itemList.size(); i++)
        {
            ElementDrawable item = itemList.get(i);
            if (item instanceof GradientDrawableItemCorners)
            {
                processCorners(drawable,(GradientDrawableItemCorners)item);
            }
            else if (item instanceof GradientDrawableItemGradient)
            {
                processGradient(drawable,(GradientDrawableItemGradient)item,gradientState);
            }
            else if (item instanceof GradientDrawableItemPadding)
            {
                processPadding(drawable,(GradientDrawableItemPadding)item, gradientState);
            }
            else if (item instanceof GradientDrawableItemSize)
            {
                processSize(drawable,(GradientDrawableItemSize)item);
            }
            else if (item instanceof GradientDrawableItemSolid)
            {
                processSolid(drawable,(GradientDrawableItemSolid)item);
            }
            else if (item instanceof GradientDrawableItemStroke)
            {
                processStroke(drawable, (GradientDrawableItemStroke)item);
            }
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    private void processCorners(GradientDrawable drawable,GradientDrawableItemCorners item)
    {
        Integer radiusObj = item.getRadius();
        int radius = radiusObj != null ? radiusObj.intValue() : 0;
        drawable.setCornerRadius(radius);

        Integer topLeftRadiusObj = item.getTopLeftRadius();
        int topLeftRadius = topLeftRadiusObj != null ? topLeftRadiusObj.intValue() : radius;

        Integer topRightRadiusObj = item.getTopRightRadius();
        int topRightRadius = topRightRadiusObj != null ? topRightRadiusObj.intValue() : radius;

        Integer bottomLeftRadiusObj = item.getBottomLeftRadius();
        int bottomLeftRadius = bottomLeftRadiusObj != null ? bottomLeftRadiusObj.intValue() : radius;

        Integer bottomRightRadiusObj = item.getBottomRightRadius();
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

    private void processGradient(GradientDrawable drawable,GradientDrawableItemGradient item,Object gradientState)
    {
        PercFloat centerXObj = item.getCenterX();
        PercFloat centerYObj = item.getCenterY();
        float centerX = centerXObj != null ? toFloat(centerXObj) : 0.5f;
        float centerY = centerYObj != null ? toFloat(centerYObj) : 0.5f;
        drawable.setGradientCenter(centerX, centerY);

        Integer startColorObj = item.getStartColor();
        Integer centerColorObj = item.getCenterColor();
        Integer endColorObj = item.getEndColor();

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

        Boolean useLevelObj = item.getUseLevel();
        boolean useLevel = useLevelObj != null ?  useLevelObj.booleanValue() : false;
        drawable.setUseLevel(useLevel);

        Integer gradientTypeObj = item.getType();
        int gradientType = gradientTypeObj != null ? gradientTypeObj.intValue() : GradientDrawable.LINEAR_GRADIENT;
        drawable.setGradientType(gradientType);

        if (gradientType == GradientDrawable.LINEAR_GRADIENT)
        {
            int angle = 0;
            Float angleFloat = item.getAngle();
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
            PercFloat gradRadius = item.getGradientRadius();
            if (gradRadius != null)
            {
                float value = toFloat(gradRadius); // gradRadius.getValue();
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
                        throw new ItsNatDroidException("Unexpected");

                    gradientRadiusTypeField.set(gradientState,radiusType);
                }
            }
            else if (gradientTypeObj == GradientDrawable.RADIAL_GRADIENT)
                throw new ItsNatDroidException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }

    }

    private void processPadding(GradientDrawable drawable,GradientDrawableItemPadding item,Object gradientState)
    {
        Integer leftObj = item.getLeft();
        Integer topObj = item.getTop();
        Integer rightObj = item.getRight();
        Integer bottomObj = item.getBottom();

        int left = leftObj != null ? leftObj.intValue() : 0;
        int top = topObj != null ? topObj.intValue() : 0;
        int right = rightObj != null ? rightObj.intValue() : 0;
        int bottom = bottomObj != null ? bottomObj.intValue() : 0;

        Rect rect = new Rect(left,top,right,bottom);

        // Son necesarios los dos al menos el primero, de otra manera no se manifiesta visualmente el cambio
        gradientPaddingField.set(drawable, rect);
        gradientPaddingField2.set(gradientState, rect);
    }

    private void processSize(GradientDrawable drawable,GradientDrawableItemSize item)
    {
        Integer widthObj = item.getWidth();
        Integer heightObj = item.getHeight();
        int width = widthObj != null ? widthObj.intValue() : -1;
        int height = heightObj != null ? heightObj.intValue() : -1;

        drawable.setSize(width, height);
    }

    private void processSolid(GradientDrawable drawable,GradientDrawableItemSolid item)
    {
        Integer colorObj = item.getColor();
        int color = colorObj != null ? colorObj.intValue() : 0;
        drawable.setColor(colorObj);
    }

    private void processStroke(GradientDrawable drawable,GradientDrawableItemStroke item)
    {
        Integer widthObj = item.getWidth();
        Integer colorObj = item.getColor();
        Float dashWidthObj = item.getDashWidth();

        int width = widthObj != null ? widthObj.intValue() : 0;
        int color = colorObj != null ? colorObj.intValue() : 0;
        float dashWidth = dashWidthObj != null ? dashWidthObj.floatValue() : 0;

        if (dashWidth != 0.0f)
        {
            Float dashGapObj = item.getDashGap();
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
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;
        return InflatedXML.XMLNS_ANDROID.equals(namespaceURI) &&
                (name.equals("shape") || name.equals("dither") ||
                 name.equals("innerRadius") || name.equals("innerRadiusRatio") ||
                 name.equals("thickness") || name.equals("thicknessRatio") ||
                 name.equals("useLevel"));
    }

    protected void init()
    {
        super.init();

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


