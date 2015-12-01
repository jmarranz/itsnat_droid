package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawablePage;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawableStandalone;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutStandaloneImpl;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 25/06/14.
 */
public class XMLInflateRegistry
{
    protected ItsNatDroidImpl parent;
    private int sNextGeneratedId = 1; // No usamos AtomicInteger porque no lo usaremos en multihilo
    protected Map<String,Integer> newIdMap = new HashMap<String,Integer>();
    protected ClassDescViewMgr classDescViewMgr = new ClassDescViewMgr(this);
    protected ClassDescDrawableMgr classDescDrawableMgr = new ClassDescDrawableMgr(this);


    public XMLInflateRegistry(ItsNatDroidImpl parent)
    {
        this.parent = parent;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return parent;
    }

    public ClassDescViewMgr getClassDescViewMgr()
    {
        return classDescViewMgr;
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return classDescDrawableMgr;
    }


    public int generateViewId()
    {
        // Inspirado en el código fuente de Android View.generateViewId()
        final int result = sNextGeneratedId;
        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
        int newValue = result + 1;
        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
        // No usamos compareAndSet porque no se debe usar en multihilo
        this.sNextGeneratedId = newValue;
        return result;
    }

    public int findIdAddIfNecessary(String name)
    {
        int id = findId(name);
        if (id == 0)
            id = addNewId(name);
        return id;
    }

    public int findId(String name)
    {
        Integer res = newIdMap.get(name);
        if (res == null)
            return 0; // No existe
        return res;
    }

    private int addNewId(String name)
    {
        int newId = generateViewId();
        newIdMap.put(name,newId);
        return newId;
    }

    public int getIdentifierAddIfNecessary(String value, Context ctx)
    {
        // Procesamos aquí los casos de "@+id/...", la razón es que cualquier atributo que referencie un id (más allá
        // de android:id) puede registrar un nuevo atributo lo cual es útil si el android:id como tal está después,
        // después en android:id ya no hace falta que sea "@+id/...".
        // http://stackoverflow.com/questions/11029635/android-radiogroup-checkedbutton-property
        int id = 0;
        if (value.startsWith("@+id/") || value.startsWith("@id/")) // Si fuera el caso de "@+mypackage:id/name" ese caso no lo soportamos, no lo he visto nunca aunque en teoría está sintácticamente permitido
        {
            id = getIdentifier(value, ctx, false); // Tiene prioridad el recurso de Android, pues para qué generar un id nuevo si ya existe o bien ya fue registrado dinámicamente
            if (id <= 0)
            {
                int pos = value.indexOf('/');
                String idName = value.substring(pos + 1);
                if (value.startsWith("@+id/")) id = findIdAddIfNecessary(idName);
                else id = findId(idName);
                if (id <= 0) throw new ItsNatDroidException("Not found resource with id \"" + value + "\"");
            }
        }
        else id = getIdentifier(value, ctx);
        return id;
    }

    public int getIdentifier(String attrValue, Context ctx)
    {
        return getIdentifier(attrValue,ctx,true);
    }

    private int getIdentifier(String value, Context ctx,boolean throwErr)
    {
        if ("0".equals(value) || "-1".equals(value) || "@null".equals(value)) return 0;

        int id;
        char first = value.charAt(0);
        if (first == '?')
        {
            id = getIdentifierTheme(value, ctx);
        }
        else if (first == '@')
        {
            // En este caso es posible que se haya registrado dinámicamente el id via "@+id/..." Tiene prioridad el registro de Android que el de ItsNat, para qué generar un id si ya existe como recurso
            id = getIdentifierResource(value, ctx);
            if (id > 0)
                return id;
            id = getIdentifierDynamicallyAdded(value);
        }
        else
            throw new ItsNatDroidException("Bad format in id declaration: " + value);

        if (throwErr && id <= 0)
            throw new ItsNatDroidException("Not found resource with id \"" + value + "\"");
        return id;
    }

    private static int getIdentifierTheme(String value, Context ctx)
    {
        // http://stackoverflow.com/questions/12781501/android-setting-linearlayout-background-programmatically
        // Ej. android:textAppearance="?android:attr/textAppearanceMedium"
        TypedValue outValue = new TypedValue();
        ctx.getTheme().resolveAttribute(getIdentifierResource(value, ctx), outValue, true);
        return outValue.resourceId;
    }

    private static int getIdentifierResource(String value, Context ctx)
    {
        Resources res = ctx.getResources();

        value = value.substring(1); // Quitamos el @ o #
        if (value.startsWith("+id/"))
            value = value.substring(1); // Quitamos el +
        String packageName;
        if (value.indexOf(':') != -1) // Tiene package el value, ej "android:" delegamos en Resources.getIdentifier() que lo resuelva
        {
            packageName = null;
        }
        else
        {
            packageName = ctx.getPackageName(); // El package es necesario como parámetro sólo cuando no está en la string (recursos locales)
        }

        return res.getIdentifier(value, null, packageName);
    }

    public int getIdentifierDynamicallyAdded(String value)
    {
        if (value.indexOf(':') != -1) // Tiene package, ej "@+android:id/", no se encontrará un id registrado como "@+id/..." y los posibles casos con package NO los hemos contemplado
            return 0; // No encontrado

        value = value.substring(1); // Quitamos el @ o #
        int pos = value.indexOf('/');
        String idName = value.substring(pos + 1);

        return findId(idName);
    }

    public static boolean isResource(String attrValue)
    {
        // No hace falta hacer un trim, un espacio al ppio invalida el atributo
        return attrValue.startsWith("@") || attrValue.startsWith("?");
    }

    public int getInteger(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getInteger(resId);
        }
        else
        {
            if (attrValue.startsWith("0x"))
            {
                attrValue = attrValue.substring(2);
                return Integer.parseInt(attrValue, 16);
            }
            return Integer.parseInt(attrValue);
        }
    }

    public float getFloat(String attrValue, Context ctx)
    {
        // Ojo, para valores sin sufijo de dimensión (por ej layout_weight o alpha)
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getDimension(resId); // No hay getFloat
        }
        else return parseFloat(attrValue);
    }

    public String getString(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getString(resId);
        }
        else return attrValue;
    }

    public CharSequence getText(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getText(resId);
        }
        else return attrValue;
    }

    public CharSequence[] getTextArray(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getTextArray(resId);
        }
        else return null;
    }

    public boolean getBoolean(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            return ctx.getResources().getBoolean(resId);
        }
        else return Boolean.parseBoolean(attrValue);
    }

    private static int getDimensionSuffixAsInt(String suffix)
    {
        if (suffix.equals("dp") || suffix.equals("dip")) return TypedValue.COMPLEX_UNIT_DIP;
        else if (suffix.equals("px")) return TypedValue.COMPLEX_UNIT_PX;
        else if (suffix.equals("sp")) return TypedValue.COMPLEX_UNIT_SP;
        else if (suffix.equals("in")) return TypedValue.COMPLEX_UNIT_IN;
        else if (suffix.equals("mm")) return TypedValue.COMPLEX_UNIT_MM;
        else throw new ItsNatDroidException("Internal error");
    }

    private static String getDimensionSuffix(String value)
    {
        String valueTrim = value.trim();

        if (valueTrim.endsWith("dp")) return "dp";
        if (valueTrim.endsWith("dip")) // Concesión al pasado
            return "dip";
        else if (valueTrim.endsWith("px")) return "px";
        else if (valueTrim.endsWith("sp")) return "sp";
        else if (valueTrim.endsWith("in")) return "in";
        else if (valueTrim.endsWith("mm")) return "mm";
        else throw new ItsNatDroidException("ERROR unrecognized dimension: " + valueTrim);
    }

    private static float parseFloat(String value)
    {
        return Float.parseFloat(value);
    }

    private static float extractFloat(String value, String suffix)
    {
        int pos = value.lastIndexOf(suffix);
        value = value.substring(0, pos);
        return parseFloat(value);
    }

    private static float toPixelFloat(int unit,float value, Resources res)
    {
        // Nexus 4 tiene un scale 2 de dp a px (xhdpi),  con un valor de 0.3 devuelve 0.6 bien para probar si usar round/floor
        // Nexus 5 tiene un scale 3 de dp a px (xxhdpi), con un valor de 0.3 devuelve 0.9 bien para probar si usar round/floor
        // La VM ItsNatDroid es una Nexus 4
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    private Dimension getDimensionObject(String attrValue)
    {
        // Suponemos que NO es un recurso externo
        // El retorno es en px
        String valueTrim = attrValue.trim();
        String suffix = getDimensionSuffix(valueTrim);
        int complexUnit = getDimensionSuffixAsInt(suffix);
        float num = extractFloat(valueTrim, suffix);
        return new Dimension(complexUnit, num);
    }

    private Dimension getDimensionObject(String attrValue, Context ctx)
    {
        // El retorno es en px
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            float num = ctx.getResources().getDimension(resId);
            return new Dimension(TypedValue.COMPLEX_UNIT_PX, num);
        }
        else
        {
            return getDimensionObject(attrValue);
        }
    }


    public int getDimensionIntFloor(String attrValue, Context ctx)
    {
        // TypedValue.complexToDimensionPixelOffset
        return (int)getDimensionFloat(attrValue,ctx);
    }

    public int getDimensionIntRound(String attrValue, Context ctx)
    {
        // TypedValue.complexToDimensionPixelSize
        return Math.round(getDimensionFloat(attrValue, ctx));
    }

    public float getDimensionFloat(String attrValue, Context ctx)
    {
        // El retorno es en px
        Resources res = ctx.getResources();

        Dimension dimen = getDimensionObject(attrValue, ctx);
        int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
        float num = dimen.getValue();
        return toPixelFloat(unit, num, res);
    }

    public float getDimensionFloatFloor(String attrValue, Context ctx)
    {
        // El retorno es en px
        float num = getDimensionFloat(attrValue, ctx);
        num = (float)Math.floor(num);
        return num;
    }

    public float getDimensionFloatRound(String attrValue, Context ctx)
    {
        // El retorno es en px
        float num = getDimensionFloat(attrValue, ctx);
        num = Math.round(num);
        return num;
    }

    public PercFloat getDimensionPercFloat(String attrValue, Context ctx)
    {
        // Este método y PercFloat sólo se usa para el gradientRadius de GradientDrawable <shape> <gradient android:gradientRadius android:centerX y centerY>

        // Las notas se refieren a gradientRadius:

        // La documentación dice que puede tener tres posibles valores:
        // Una referencia a un recurso externo "@..." "?..." cuyo valor en el recurso puede ser cualquiera de los siguientes
        // Un valor float como tal ej "20.3"
        // Un valor porcentual con dos variantes: "10.3%" "10.3%p"
        // Un valor dimension: ej "10.3dp"

        // El caso es que la documentación está ACTUALIZADA a las versiones últimas y no distingue entre versiones, el problema es que la versión 15 (4.0.3) soporta
        // todos los casos excepto el dimension, el editor visual da error y el compilador también. Si se usa un resource y se pone un dimension, en 4.0.3 se ignora.
        // Lo que haremos para adelantarnos al futuro es implementar también el caso de dimension, no nos cuesta apenas nada y en los ejemplos NO usar dimension
        // Notas:
        // No soy capaz de distinguir entre % y %p
        // El "valor float como tal" yo creo que son pixels, y esa es la razón por la que no existía la variante dimension inicialmente, luego se añadió para homogeneizar y mejorar la portabilidad

        // Recuerda que gradientRadius sólo se usa en el caso de RADIAL_GRADIENT, en dicho caso de hecho es obligatorio

        Resources res = ctx.getResources();
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            String value = res.getString(resId);
            return parseDimensionPercFloat(value,ctx);
        }
        else
        {
            return parseDimensionPercFloat(attrValue, ctx);
        }
    }


    private PercFloat parseDimensionPercFloat(String attrValue, Context ctx)
    {
        // El retorno es en px
        int dataType;
        boolean fractionParent = false;
        int pos;
        pos = attrValue.lastIndexOf("%");
        if (pos != -1)
        {
            dataType = TypedValue.TYPE_FRACTION;
            fractionParent = (attrValue.lastIndexOf("%p") != -1);
            attrValue = attrValue.substring(0,pos);
            float value = Float.parseFloat(attrValue);
            return new PercFloat(dataType,fractionParent,value);
        }
        else
        {
            dataType = TypedValue.TYPE_FLOAT;
            char last = attrValue.charAt(attrValue.length() - 1);
            if (Character.isDigit(last))
            {
                float value = Float.parseFloat(attrValue);
                return new PercFloat(dataType,fractionParent,value); // fractionParent es indiferente
            }
            else
            {
                Dimension dimen = getDimensionObject(attrValue);
                int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
                float num = dimen.getValue();
                float value = toPixelFloat(unit, num, ctx.getResources());
                return new PercFloat(dataType,fractionParent,value); // fractionParent es indiferente
            }
        }
    }

    public int getDimensionWithNameIntRound(String value, Context ctx)
    {
        int dimension;

        // No hace falta hacer trim en caso de "match_parent" etc un espacio fastidia el attr
        if ("fill_parent".equals(value)) dimension = ViewGroup.LayoutParams.MATCH_PARENT;
        else if ("match_parent".equals(value)) dimension = ViewGroup.LayoutParams.MATCH_PARENT;
        else if ("wrap_content".equals(value)) dimension = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
        {
            dimension = getDimensionIntRound(value, ctx);
        }
        return dimension;
    }

    public int getColor(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue,ctx);
            return ctx.getResources().getColor(resId);
        }
        else if (attrValue.startsWith("#")) // Color literal. No hace falta hacer trim
        {
            return Color.parseColor(attrValue);
        }

        throw new ItsNatDroidException("Cannot process " + attrValue);
    }

    public static String toStringColor(int value)
    {
        if (value != Color.TRANSPARENT) throw new ItsNatDroidException("Unexpected");
        return "#00000000";
    }

    public Drawable getDrawable(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            if (resId <= 0) return null;
            return ctx.getResources().getDrawable(resId);
        }
        else if (attrValue.startsWith("#")) // Color literal. No hace falta hacer trim
        {
            int color = Color.parseColor(attrValue);
            return new ColorDrawable(color);
        }

        throw new ItsNatDroidException("Cannot process " + attrValue);
    }

    public Drawable getDrawable(DOMAttr attr, Context ctx,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;

            PageImpl page = null;
            if (xmlInflater instanceof XMLInflaterPage)
                page = ((XMLInflaterPage)xmlInflater).getPageImpl();

            if (attr instanceof DOMAttrRemote && page == null) throw new ItsNatDroidException("Unexpected"); // Si es remote hay page por medio

            int bitmapDensityReference = xmlInflater.getBitmapDensityReference();

            String resourceMime = attrDyn.getResourceMime();
            if (MimeUtil.isMIMEResourceXML(resourceMime))
            {
                // Esperamos un drawable no una animación
                ItsNatDroidImpl itsNatDroid = xmlInflater.getInflatedXML().getItsNatDroidImpl();
                AttrLayoutInflaterListener attrLayoutInflaterListener = xmlInflater.getAttrLayoutInflaterListener();
                AttrDrawableInflaterListener attrDrawableInflaterListener = xmlInflater.getAttrDrawableInflaterListener();

                XMLDOMDrawable xmlDOMDrawable = (XMLDOMDrawable) attrDyn.getResource();
                InflatedDrawable inflatedDrawable = page != null ? new InflatedDrawablePage(itsNatDroid, xmlDOMDrawable, ctx) : new InflatedDrawableStandalone(itsNatDroid, xmlDOMDrawable, ctx);
                XMLInflaterDrawable xmlInflaterDrawable = XMLInflaterDrawable.createXMLInflaterDrawable(inflatedDrawable,bitmapDensityReference,attrLayoutInflaterListener, attrDrawableInflaterListener, ctx, page);
                return xmlInflaterDrawable.inflateDrawable();
            }
            else if (MimeUtil.isMIMEResourceImage(resourceMime))
            {
                byte[] byteArray = (byte[])attrDyn.getResource();
                boolean expectedNinePatch = attrDyn.isNinePatch();
                return DrawableUtil.createImageBasedDrawable(byteArray,bitmapDensityReference,expectedNinePatch,ctx.getResources());
            }
            else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
        }
        else if (attr instanceof DOMAttrLocalResource)
        {
            String attrValue = attr.getValue();
            return getDrawable(attrValue,ctx);
        }
        else throw new ItsNatDroidException("Internal Error");
    }

    public View getLayout(String attrValue, Context ctx,ViewGroup viewParent)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            if (resId <= 0) return null;
            return LayoutInflater.from(ctx).inflate(resId, viewParent);
        }

        throw new ItsNatDroidException("Cannot process " + attrValue);
    }

    public View getLayout(DOMAttr attr, Context ctx,XMLInflater xmlInflater,ViewGroup viewParent)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;

            PageImpl page = null;
            if (xmlInflater instanceof XMLInflaterPage)
                page = ((XMLInflaterPage)xmlInflater).getPageImpl();

            if (attr instanceof DOMAttrRemote && page == null) throw new ItsNatDroidException("Unexpected"); // Si es remote hay page por medio

            int bitmapDensityReference = xmlInflater.getBitmapDensityReference();

            String resourceMime = attrDyn.getResourceMime();
            if (MimeUtil.isMIMEResourceXML(resourceMime))
            {
                ItsNatDroidImpl itsNatDroid = xmlInflater.getInflatedXML().getItsNatDroidImpl();
                AttrLayoutInflaterListener attrLayoutInflaterListener = xmlInflater.getAttrLayoutInflaterListener();
                AttrDrawableInflaterListener attrDrawableInflaterListener = xmlInflater.getAttrDrawableInflaterListener();

                XMLDOMLayout xmlDOMLayout = (XMLDOMLayout) attrDyn.getResource();

                String[] loadScript = null;
                List<String> scriptList = null;
                XMLInflaterLayout xmlInflaterLayout = XMLInflaterLayout.inflateLayout(itsNatDroid,xmlDOMLayout,loadScript,scriptList,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener,ctx,page);
                return xmlInflaterLayout.getInflatedLayoutImpl().getRootView();
            }
            else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
        }
        else if (attr instanceof DOMAttrLocalResource)
        {
            String attrValue = attr.getValue();
            return getLayout(attrValue,ctx,viewParent);
        }
        else throw new ItsNatDroidException("Internal Error");
    }


    public float getPercent(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifier(attrValue, ctx);
            String str = ctx.getResources().getString(resId);
            return getPercent(str);
        }
        else
        {
            return getPercent(attrValue);
        }
    }

    private static float getPercent(String s)
    {
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/ScaleDrawable.java#ScaleDrawable.getPercent
        if (s != null) {
            if (s.endsWith("%")) {
                String f = s.substring(0, s.length() - 1);
                return Float.parseFloat(f) / 100.0f;
            }
        }
        return -1;
    }
}

