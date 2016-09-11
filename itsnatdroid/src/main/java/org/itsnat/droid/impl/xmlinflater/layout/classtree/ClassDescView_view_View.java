package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_XMLId;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_fadeScrollbars;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layerType;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_alignWithParentIfMissing;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_column;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_columnSpan;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_gravity;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_height;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_margin;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginBottom;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginLeft;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginRight;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginTop;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_rellayout_byBoolean;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_rellayout_byId;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_row;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_rowSpan;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_span;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_weight;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_width;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_onClick;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_padding;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_requiresFadingEdge;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarAlwaysDrawHorizontalTrack;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarAlwaysDrawVerticalTrack;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarSize;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarStyle;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarThumbHorizontal;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarThumbVertical;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarTrackHorizontal;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbarTrackVertical;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_scrollbars;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_visibility;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodCharSequence;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionFloatFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodObject;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_view_View extends ClassDescViewBased
{
    public static final MapSmart<String,Integer> drawingCacheQualityMap = MapSmart.<String,Integer>create(3);
    static
    {
        // La documentación del atributo yo creo que está mal, los valores enteros 0, 1 y 2 NUNCA son usados ni externa ni internamente (y DRAWING_CACHE_QUALITY_AUTO etc NO son 0,1,2)
        drawingCacheQualityMap.put("auto", View.DRAWING_CACHE_QUALITY_AUTO);
        drawingCacheQualityMap.put("low",  View.DRAWING_CACHE_QUALITY_LOW);
        drawingCacheQualityMap.put("high", View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public static final MapSmart<String,Integer> importantForAccessibilityMap = MapSmart.<String,Integer>create(4);
    static
    {
        importantForAccessibilityMap.put("auto", View.IMPORTANT_FOR_ACCESSIBILITY_AUTO);
        importantForAccessibilityMap.put("yes",  View.IMPORTANT_FOR_ACCESSIBILITY_YES);
        importantForAccessibilityMap.put("no", View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        importantForAccessibilityMap.put("noHideDescendants", 4); // La constante View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS es introducida en Android 4.4 y es 4

        // importantForAccessibility admite además un número entero cualquiera, no lo soportamos
    }

    // NO SE USA EL API 16 pero lo dejamos para el futuro
    public static final MapSmart<String,Integer> layoutDirectionMap = MapSmart.<String,Integer>create(4);
    static
    {
        layoutDirectionMap.put("ltr", 0); // View.LAYOUT_DIRECTION_LTR etc no se porque pero no están en 4.1, no los necesitamos
        layoutDirectionMap.put("rtl", 1);
        layoutDirectionMap.put("inherit", 2);
        layoutDirectionMap.put("locale", 3);
    }

    public ClassDescView_view_View(ClassDescViewMgr classMgr)
    {
        super(classMgr,"android.view.View",null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init()
    {
        super.init();

        addAttrDescNoNS(new AttrDescView_view_View_XMLId(this));  // OJO, es el id="..." estandar de XML SIN "android:" es decir SIN namespace por eso llamamos a addAttrDescNoNS


        // android:accessibilityLiveRegion es Level 19 (Android 4.4)
        // android:accessibilityTraversalAfter es Level 21 (Android 5.1)
        // android:android:accessibilityTraversalBefore es Level 21 (Android 5.1)
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "alpha", 1f));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "background", "setBackground", "@null"));
        // android:backgroundTint es level 20 (Android 5.0)
        // android:backgroundTintMode es level 20 (Android 5.0)
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "clickable", true));
        addAttrDescAN(new AttrDescReflecMethodCharSequence(this, "contentDescription", ""));
        // android:contextClickable es level 23 (Android 6.0)
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "drawingCacheQuality", int.class, drawingCacheQualityMap, "auto"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "duplicateParentState", "setDuplicateParentStateEnabled", false)); // Según dice la doc no hace nada este flag a true si el atributo no se define antes de insertar en un ViewGroup
        // android:elevation es level 21 (Android 5.0)
        addAttrDescAN(new AttrDescView_view_View_fadeScrollbars(this));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "fadingEdgeLength", null));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "filterTouchesWhenObscured", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "fitsSystemWindows", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "focusable", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "focusableInTouchMode", false));
        // android:forceHasOverlappingRendering es level > 23 ???? es deprecated ???
        if (Build.VERSION.SDK_INT >= MiscUtil.MARSHMALLOW) // >= 23
        {
            addAttrDescAN(new AttrDescReflecMethodDrawable<ClassDescViewBased, View, AttrLayoutContext>(this, "foreground", "@null")); // A partir de MARSHMALLOW se define en View
        }
        // android:foregroundGravity es level 23
        // android:foregroundTint es level 23
        // android:foregroundTintMode es level 23
        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "hapticFeedbackEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "id", -1));
        addAttrDescAN(new AttrDescReflecMethodNameSingle<Integer,ClassDescViewBased, View, AttrLayoutContext>(this, "importantForAccessibility", int.class, importantForAccessibilityMap, "auto")); // El entero asociado a "auto" es 0

        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "isScrollContainer", "setScrollContainer", false)); // No estoy seguro de si el valor por defecto es false, dependerá seguramente del componente
        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "keepScreenOn", false));
        addAttrDescAN(new AttrDescView_view_View_layerType(this)); // layerType
        // android:layoutDirection está en el código de Android 4.1.1 (API 16) pero Android Studio NO reconoce el atributo y setLayoutDirection no está en API 16 (es API 17)
        // addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "layoutDirection", int.class, layoutDirectionMap, "ltr"));
        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "longClickable", false));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound<ClassDescViewBased, View, AttrLayoutContext>(this, "minHeight", "setMinimumHeight", null));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound<ClassDescViewBased, View, AttrLayoutContext>(this, "minWidth", "setMinimumWidth", null));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "nextFocusDown", "setNextFocusDownId", -1));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "nextFocusForward", "setNextFocusForwardId", -1));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "nextFocusLeft", "setNextFocusLeftId", -1));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "nextFocusRight", "setNextFocusRightId", -1));
        addAttrDescAN(new AttrDescReflecMethodId<ClassDescViewBased, View, AttrLayoutContext>(this, "nextFocusUp", "setNextFocusUpId", -1));
        addAttrDescAN(new AttrDescView_view_View_onClick(this));
        addAttrDescAN(new AttrDescView_view_View_padding(this, "padding"));
        addAttrDescAN(new AttrDescView_view_View_padding(this, "paddingBottom"));
        // android:paddingEnd es Level 17
        addAttrDescAN(new AttrDescView_view_View_padding(this, "paddingLeft"));
        addAttrDescAN(new AttrDescView_view_View_padding(this, "paddingRight"));
        // android:paddingStart es Level 17
        addAttrDescAN(new AttrDescView_view_View_padding(this, "paddingTop"));
        addAttrDescAN(new AttrDescView_view_View_requiresFadingEdge(this));
        addAttrDescAN(new AttrDescReflecMethodFloat<ClassDescViewBased, View, AttrLayoutContext>(this, "rotation", 0f));
        addAttrDescAN(new AttrDescReflecMethodFloat<ClassDescViewBased, View, AttrLayoutContext>(this, "rotationX", 0f));
        addAttrDescAN(new AttrDescReflecMethodFloat<ClassDescViewBased, View, AttrLayoutContext>(this, "rotationY", 0f));
        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "saveEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodFloat<ClassDescViewBased, View, AttrLayoutContext>(this, "scaleX", 1f));
        addAttrDescAN(new AttrDescReflecMethodFloat<ClassDescViewBased, View, AttrLayoutContext>(this, "scaleY", 1f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound<ClassDescViewBased, View, AttrLayoutContext>(this, "scrollX", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound<ClassDescViewBased, View, AttrLayoutContext>(this, "scrollY", 0f));
        // android:scrollIndicators es level 23
        addAttrDescAN(new AttrDescView_view_View_scrollbarAlwaysDrawHorizontalTrack(this));
        addAttrDescAN(new AttrDescView_view_View_scrollbarAlwaysDrawVerticalTrack(this));
        addAttrDescAN(new AttrDescReflecMethodInt<ClassDescViewBased, View, AttrLayoutContext>(this, "scrollbarDefaultDelayBeforeFade", "setScrollBarDefaultDelayBeforeFade",ViewConfiguration.getScrollDefaultDelay())); // ViewConfiguration.getScrollDefaultDelay() puede ser 300 por ejemplo
        addAttrDescAN(new AttrDescReflecMethodInt<ClassDescViewBased, View, AttrLayoutContext>(this, "scrollbarFadeDuration", "setScrollBarFadeDuration",ViewConfiguration.getScrollBarFadeDuration())); // ViewConfiguration.getScrollBarFadeDuration() puede ser 250 por ejemplo
        addAttrDescAN(new AttrDescView_view_View_scrollbarSize(this));



        addAttrDescAN(new AttrDescView_view_View_scrollbarStyle(this)); // scrollbarStyle

        addAttrDescAN(new AttrDescView_view_View_scrollbarThumbHorizontal(this));
        addAttrDescAN(new AttrDescView_view_View_scrollbarThumbVertical(this));
        addAttrDescAN(new AttrDescView_view_View_scrollbarTrackHorizontal(this));
        addAttrDescAN(new AttrDescView_view_View_scrollbarTrackVertical(this));

        addAttrDescAN(new AttrDescView_view_View_scrollbars(this));

        // android:scrollbars está basado en flags, es difícil de implementar
        addAttrDescAN(new AttrDescReflecMethodBoolean<ClassDescViewBased, View, AttrLayoutContext>(this, "soundEffectsEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodObject<ClassDescViewBased, View, AttrLayoutContext>(this, "tag"));
        // android:textAlignment es Level 17
        // android:textDirection es Level 17
        addAttrDescAN(new AttrDescReflecMethodDimensionFloatFloor<ClassDescViewBased, View, AttrLayoutContext>(this, "transformPivotX", "setPivotX", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionFloatFloor<ClassDescViewBased, View, AttrLayoutContext>(this, "transformPivotY", "setPivotY", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionFloatFloor<ClassDescViewBased, View, AttrLayoutContext>(this, "translationX", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionFloatFloor<ClassDescViewBased, View, AttrLayoutContext>(this, "translationY", 0f));
        addAttrDescAN(new AttrDescView_view_View_visibility(this)); // "visibility"

        // Debidos a ViewGroup.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_width(this));
        addAttrDescAN(new AttrDescView_view_View_layout_height(this));


        // Debidos a ViewGroup.MarginLayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_marginBottom(this));
        addAttrDescAN(new AttrDescView_view_View_layout_marginLeft(this));
        addAttrDescAN(new AttrDescView_view_View_layout_marginTop(this));
        addAttrDescAN(new AttrDescView_view_View_layout_marginRight(this));
        addAttrDescAN(new AttrDescView_view_View_layout_margin(this));

        // Debidos a LinearLayout.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_weight(this));

        // Debidos a LinearLayout.LayoutParams, FrameLayout.LayoutParams y GridLayout.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_gravity(this));

        // Debidos a GridLayout.LayoutParams, TableRow.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_column(this));

        // Debidos a GridLayout.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_columnSpan(this));
        addAttrDescAN(new AttrDescView_view_View_layout_row(this));
        addAttrDescAN(new AttrDescView_view_View_layout_rowSpan(this));

        // Debidos a RelativeLayout.LayoutParams

        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_above", RelativeLayout.ABOVE));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_alignBaseline", RelativeLayout.ALIGN_BASELINE));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_alignBottom", RelativeLayout.ALIGN_BOTTOM));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byId(this,"layout_alignEnd",RelativeLayout.ALIGN_END)); // API 17
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_alignLeft", RelativeLayout.ALIGN_LEFT));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_alignParentBottom", RelativeLayout.ALIGN_PARENT_BOTTOM));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byBoolean(this,"layout_alignParentEnd", RelativeLayout.ALIGN_PARENT_END)); // API 17
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_alignParentLeft", RelativeLayout.ALIGN_PARENT_LEFT));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_alignParentRight", RelativeLayout.ALIGN_PARENT_RIGHT));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byBoolean(this,"layout_alignParentStart", RelativeLayout.ALIGN_PARENT_START)); // API 17
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_alignParentTop", RelativeLayout.ALIGN_PARENT_TOP));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_alignRight", RelativeLayout.ALIGN_RIGHT));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byId(this,"layout_alignStart",RelativeLayout.ALIGN_START)); // API 17
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_alignTop", RelativeLayout.ALIGN_TOP));
        addAttrDescAN(new AttrDescView_view_View_layout_alignWithParentIfMissing(this));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_below", RelativeLayout.BELOW));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_centerHorizontal", RelativeLayout.CENTER_HORIZONTAL));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_centerInParent", RelativeLayout.CENTER_IN_PARENT));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byBoolean(this, "layout_centerVertical", RelativeLayout.CENTER_VERTICAL));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byId(this,"layout_toEndOf",RelativeLayout.END_OF));  // API 17
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_toLeftOf", RelativeLayout.LEFT_OF));
        addAttrDescAN(new AttrDescView_view_View_layout_rellayout_byId(this, "layout_toRightOf", RelativeLayout.RIGHT_OF));
        //addAttrDesc(new AttrDescView_view_View_layout_rellayout_byId(this,"layout_toStartOf",RelativeLayout.START_OF));  // API 17

        // Debidos a TableRow.LayoutParams
        addAttrDescAN(new AttrDescView_view_View_layout_span(this));

    }
}
