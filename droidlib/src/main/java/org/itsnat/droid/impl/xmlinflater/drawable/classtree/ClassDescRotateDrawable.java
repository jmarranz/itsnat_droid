package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescRotateDrawable extends ClassDescDrawableWrapper<RotateDrawable>
{
    protected FieldContainer<Drawable.ConstantState> rotateStateField;
    protected FieldContainer<Drawable> mDrawableField;
    protected FieldContainer<Boolean> mPivotXRelField;
    protected FieldContainer<Float> mPivotXField;
    protected FieldContainer<Boolean> mPivotYRelField;
    protected FieldContainer<Float> mPivotYField;
    protected FieldContainer<Float> mFromDegreesField;
    protected FieldContainer<Float> mCurrentDegreesField;
    protected FieldContainer<Float> mToDegreesField;


    public ClassDescRotateDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"rotate");

        this.rotateStateField = new FieldContainer<Drawable.ConstantState>(RotateDrawable.class, "mState");
        Class rotateStateClass = MiscUtil.resolveClass(RotateDrawable.class.getName() + "$RotateState");

        this.mDrawableField = new FieldContainer<Drawable>(rotateStateClass, "mDrawable");
        this.mPivotXRelField = new FieldContainer<Boolean>(rotateStateClass, "mPivotXRel");
        this.mPivotXField = new FieldContainer<Float>(rotateStateClass, "mPivotX");
        this.mPivotYRelField = new FieldContainer<Boolean>(rotateStateClass, "mPivotYRel");
        this.mPivotYField = new FieldContainer<Float>(rotateStateClass, "mPivotY");
        this.mFromDegreesField = new FieldContainer<Float>(rotateStateClass, "mFromDegrees");
        this.mCurrentDegreesField = new FieldContainer<Float>(rotateStateClass, "mCurrentDegrees");
        this.mToDegreesField = new FieldContainer<Float>(rotateStateClass, "mToDegrees");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        RotateDrawable drawable = new RotateDrawable();

        inflaterDrawable.processChildElements(rootElem, elementDrawableRoot);
        ArrayList<ElementDrawable> childList = elementDrawableRoot.getChildElementDrawableList();

        XMLInflateRegistry xmlInflateRegistry = classMgr.getXMLInflateRegistry();
        Drawable.ConstantState rotateState = rotateStateField.get(drawable);

        Drawable childDrawable = getChildDrawable("drawable", rootElem, inflaterDrawable, ctx, childList);
        mDrawableField.set(rotateState,childDrawable);

        DOMAttr pivotXAttr = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "pivotX");
        PercFloat pivotXObj = pivotXAttr != null ? xmlInflateRegistry.getDimensionPercFloat(pivotXAttr.getValue(),ctx) : null;
        boolean pivotXRel;
        float pivotX;
        if (pivotXObj == null)
        {
            pivotXRel = true;
            pivotX = 0.5f;
        }
        else
        {
            pivotXRel = pivotXObj.getDataType() == TypedValue.TYPE_FRACTION;
            pivotX = pivotXObj.toFloatBasedOnDataType();
        }
        mPivotXRelField.set(rotateState,pivotXRel);
        mPivotXField.set(rotateState,pivotX);

        DOMAttr pivotYAttr = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "pivotY");
        PercFloat pivotYObj = pivotYAttr != null ? xmlInflateRegistry.getDimensionPercFloat(pivotYAttr.getValue(),ctx) : null;
        boolean pivotYRel;
        float pivotY;
        if (pivotYObj == null)
        {
            pivotYRel = true;
            pivotY = 0.5f;
        }
        else
        {
            pivotYRel = pivotYObj.getDataType() == TypedValue.TYPE_FRACTION;
            pivotY = pivotYObj.toFloatBasedOnDataType();
        }
        mPivotYRelField.set(rotateState,pivotYRel);
        mPivotYField.set(rotateState,pivotY);


        DOMAttr fromDegreesAttr = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "fromDegrees");
        float fromDegrees = fromDegreesAttr != null ? xmlInflateRegistry.getFloat(fromDegreesAttr.getValue(),ctx) : 0.0f;
        mFromDegreesField.set(rotateState,fromDegrees);
        mCurrentDegreesField.set(rotateState,fromDegrees);

        DOMAttr toDegreesAttr = rootElem.findDOMAttribute(InflatedXML.XMLNS_ANDROID, "toDegrees");
        float toDegrees = toDegreesAttr != null ? xmlInflateRegistry.getFloat(toDegreesAttr.getValue(),ctx) : 360.0f;
        mToDegreesField.set(rotateState,toDegrees);

        childDrawable.setCallback((Drawable.Callback)drawable); // childDrawable no puede ser nulo


        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public Class<RotateDrawable> getDrawableOrElementDrawableClass()
    {
        return RotateDrawable.class;
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        if (InflatedXML.XMLNS_ANDROID.equals(namespaceURI))
        {
            // Se usan en tiempo de construcción
            return ("drawable".equals(name) || "pivotX".equals(name) || "pivotY".equals(name) || "fromDegrees".equals(name) || "toDegrees".equals(name));
        }
        return false;
    }

    //@SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawable_Drawable_visible<RotateDrawable>(this));
    }

}
