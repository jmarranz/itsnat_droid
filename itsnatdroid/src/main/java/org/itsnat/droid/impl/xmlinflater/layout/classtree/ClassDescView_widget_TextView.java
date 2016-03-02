package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.ImeOptionsUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.InputTypeUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_autoLink;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_bufferType;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_compoundDrawables;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_ellipsize;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_imeActionId;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_imeActionLabel;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_lineSpacingExtra;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_lineSpacingMultiplier;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_marqueeRepeatLimit;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_maxLength;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_shadowLayer_base;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_textAllCaps;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_textAppearance;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_textSize;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_textStyle;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TextView_typeface;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodString;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_TextView extends ClassDescViewBased
{
    public ClassDescView_widget_TextView(ClassDescViewMgr classMgr,ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.widget.TextView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_TextView_autoLink(this));
        // android:autoText está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        addAttrDescAN(new AttrDescView_widget_TextView_bufferType(this));
        // android:capitalize está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "cursorVisible", true));
        // android:digits no se implementarlo y mi impresión es que es similar a autoText, capitalize etc

        addAttrDescAN(new AttrDescView_widget_TextView_compoundDrawables(this, "drawableLeft"));
        addAttrDescAN(new AttrDescView_widget_TextView_compoundDrawables(this, "drawableTop"));
        addAttrDescAN(new AttrDescView_widget_TextView_compoundDrawables(this, "drawableRight"));
        addAttrDescAN(new AttrDescView_widget_TextView_compoundDrawables(this, "drawableBottom"));
        // android:drawableStart y android:drawableEnd en teoría existen pero su acceso via métodos es desde Level 17 y no los veo relevantes
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "drawablePadding", "setCompoundDrawablePadding", 0f));
        // android:editable está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        // android:editorExtras tiene un bug y no funciona ni con un layout compilado: https://code.google.com/p/android/issues/detail?id=38122
        addAttrDescAN(new AttrDescView_widget_TextView_ellipsize(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "ems", -1));
        // android:fontFamily creo que es Level 16
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "freezesText", false));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.nameValueMap, "top|start"));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "height", -1f));
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "hint", ""));
        addAttrDescAN(new AttrDescView_widget_TextView_imeActionId(this));
        addAttrDescAN(new AttrDescView_widget_TextView_imeActionLabel(this));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "imeOptions", ImeOptionsUtil.nameValueMap, "actionUnspecified"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "includeFontPadding", true));
        // android:inputMethod lleva deprecated desde Level 3, mal documentado, es difícil de implementar y tiene substituto en inputType
        //    una clase de ejemplo podría ser android.text.method.DateTimeInputMethod
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "inputType", InputTypeUtil.nameValueMap, "text")); // No estoy seguro que el valor por defecto sea "text" pero parece el más razonable
        addAttrDescAN(new AttrDescView_widget_TextView_lineSpacingExtra(this));
        addAttrDescAN(new AttrDescView_widget_TextView_lineSpacingMultiplier(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "lines", -1));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "linksClickable", true));
        addAttrDescAN(new AttrDescView_widget_TextView_marqueeRepeatLimit(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "maxEms", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "maxHeight", -1f));
        addAttrDescAN(new AttrDescView_widget_TextView_maxLength(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "maxLines", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "maxWidth", -1f));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "minEms", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "minHeight", -1f));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "minLines", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "minWidth", -1f));
        // android:numeric está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        // android:password está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        // android:phoneNumber está deprecated desde Level 3, no se implementarlo y tiene alternativa (inputType)
        addAttrDescAN(new AttrDescReflecMethodString(this, "privateImeOptions", ""));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "scrollHorizontally", "setHorizontallyScrolling", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "selectAllOnFocus", false));
        addAttrDescAN(new AttrDescView_widget_TextView_shadowLayer_base(this, "shadowColor"));
        addAttrDescAN(new AttrDescView_widget_TextView_shadowLayer_base(this, "shadowDx"));
        addAttrDescAN(new AttrDescView_widget_TextView_shadowLayer_base(this, "shadowDy"));
        addAttrDescAN(new AttrDescView_widget_TextView_shadowLayer_base(this, "shadowRadius"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "singleLine", false));
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "text", "")); // El tipo de CharSequence resultante (Spannable etc) depende del bufferType definido pero el orden no importa pues al definir el bufferType exige dar el texto como param para "retransformarlo"
        addAttrDescAN(new AttrDescView_widget_TextView_textAllCaps(this));
        addAttrDescAN(new AttrDescView_widget_TextView_textAppearance(this)); // "textAppearance"
        addAttrDescAN(new AttrDescReflecMethodColor(this, "textColor", "#000000"));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "textColorHighlight", "setHighlightColor", 0));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "textColorHint", "setHintTextColor", 0));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "textColorLink", "setLinkTextColor", 0));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "textIsSelectable", false));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "textScaleX", 1.0f));
        // No, no es un error, no hay textScaleY (en Level 15 ni en superiores)
        addAttrDescAN(new AttrDescView_widget_TextView_textSize(this)); // textSize
        addAttrDescAN(new AttrDescView_widget_TextView_textStyle(this));
        addAttrDescAN(new AttrDescView_widget_TextView_typeface(this));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "width", -1f));
    }
}

