package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
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
import org.itsnat.droid.impl.dom.DOMAttrCompiledResource;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.StringUtil;
import org.itsnat.droid.impl.util.WeakMapWithValue;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawable;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageItsNatImpl;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesStyle;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValues;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.DrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.ViewMapByXMLId;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttr;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttrCompiled;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttrDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.page.XMLInflaterLayoutPage;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;
import org.itsnat.droid.impl.xmlinflater.values.XMLInflaterValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 25/06/14.
 */
public class XMLInflateRegistry
{
    private ItsNatDroidImpl parent;
    private int sNextGeneratedId = 1; // No usamos AtomicInteger porque no lo usaremos en multihilo
    private Map<String, Integer> newViewIdMap = new HashMap<String, Integer>();
    private ClassDescViewMgr classDescViewMgr = new ClassDescViewMgr(this);
    private ClassDescDrawableMgr classDescDrawableMgr = new ClassDescDrawableMgr(this);
    private ClassDescValuesMgr classDescValuesMgr = new ClassDescValuesMgr(this);
    private Map<XMLDOMValues,ElementValuesResources> cacheXMLDOMValuesXMLInflaterValuesMap = new HashMap<XMLDOMValues, ElementValuesResources>();

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

    public ClassDescValuesMgr getClassDescValuesMgr()
    {
        return classDescValuesMgr;
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

    private int findViewIdDynamicallyAddedAddIfNecessary(String name)
    {
        int id = findViewIdDynamicallyAdded(name);
        if (id == 0)
            id = addNewViewId(name);
        return id;
    }

    public int findViewIdDynamicallyAdded(String name)
    {
        Integer res = newViewIdMap.get(name);
        if (res == null)
            return 0; // No existe
        return res;
    }

    private int addNewViewId(String name)
    {
        int newId = generateViewId();
        newViewIdMap.put(name, newId);
        return newId;
    }

    public int getIdentifierAddIfNecessary(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getIdentifierAddIfNecessary(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getIdentifierAddIfNecessaryCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getIdentifierAddIfNecessaryCompiled(String value, Context ctx)
    {
        // Procesamos aquí los casos de "@+id/...", la razón es que cualquier atributo que referencie un id (más allá
        // de android:id) puede registrar un nuevo atributo lo cual es útil si el android:id como tal está después,
        // después en android:id ya no hace falta que sea "@+id/...".
        // http://stackoverflow.com/questions/11029635/android-radiogroup-checkedbutton-property
        int id = 0;
        if (value.startsWith("@+id/") || value.startsWith("@id/")) // Si fuera el caso de "@+mypackage:id/name" ese caso no lo soportamos, no lo he visto nunca aunque en teoría está sintácticamente permitido
        {
            id = getIdentifierCompiled(value, ctx, false); // Tiene prioridad el recurso de Android, pues para qué generar un id nuevo si ya existe o bien ya fue registrado dinámicamente
            if (id <= 0)
            {
                int pos = value.indexOf('/');
                String idName = value.substring(pos + 1);
                if (value.startsWith("@+id/")) id = findViewIdDynamicallyAddedAddIfNecessary(idName);
                else id = findViewIdDynamicallyAdded(idName);
                if (id <= 0)
                    throw new ItsNatDroidException("Not found resource with id \"" + value + "\" you could use @+id/ ");
            }
        }
        else id = getIdentifierCompiled(value, ctx);
        return id;
    }

    public int getIdentifier(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getIdentifier(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getIdentifierCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    public int getIdentifierCompiled(String attrValue, Context ctx)
    {
        return getIdentifierCompiled(attrValue, ctx, true);
    }

    private int getIdentifierCompiled(String value, Context ctx, boolean throwErr)
    {
        if ("0".equals(value) || "-1".equals(value) || "@null".equals(value)) return 0;


        int id;
        char first = value.charAt(0);
        if (first == '?')
        {
            id = getIdentifierCompiledAttrTheme(value, ctx);
            if (id > 0)
                return id;
        }
        else if (first == '@')
        {
            // Tiene prioridad el registro de Android que el de ItsNat en el caso de "@+id", para qué generar un id si ya existe como recurso
            id = getIdentifierResource(value, ctx);
            if (id > 0)
                return id;

            if (value.startsWith("@id/") || value.startsWith("@+id/"))
            {
                // En este caso es posible que se haya registrado dinámicamente el id via "@+id/..."
                id = getViewIdDynamicallyAdded(value);
                if (id > 0)
                    return id;
            }
        }
        else if (value.startsWith("android:"))
        {
            // Es el caso de style parent definido por Android ej: <style name="..." parent="android:Theme.Holo.Light.DarkActionBar"> que es la manera reducida de poner:
            // parent="@android:style/Theme.Holo.Light.DarkActionBar" que se procesaría en el caso anterior
            // Sinceramente no se como obtenerlo via Resources.getIdentifier, lo que hacemos es convertirlo en el formato parent="@android:style/Theme.Holo.Light.DarkActionBar"

            int pos = "android:".length();
            value = "@android:style/" +  value.substring(pos);

            id = getIdentifierResource(value,ctx);
            if (id > 0)
                return id;
        }
        else
        {
            throw new ItsNatDroidException("Bad format in identifier declaration: " + value);
        }

        if (throwErr && id <= 0)
            throw new ItsNatDroidException("Not found resource with id value \"" + value + "\"");
        return id;
    }

    private static int getIdentifierCompiledAttrTheme(String value, Context ctx)
    {
        // http://stackoverflow.com/questions/12781501/android-setting-linearlayout-background-programmatically
        // Ej. android:textAppearance="?android:attr/textAppearanceMedium"
        int id = getIdentifierResource(value, ctx);
        TypedValue outValue = new TypedValue();
        ctx.getTheme().resolveAttribute(id, outValue, true);
        return outValue.resourceId;
    }

    private static int getIdentifierResource(String value, Context ctx)
    {
        Resources res = ctx.getResources();

        char firstChar = value.charAt(0);
        if (firstChar == '@' || firstChar == '?')
            value = value.substring(1); // Quitamos el @ o ?
        if (value.startsWith("+id/"))
            value = value.substring(1); // Quitamos el +
        String packageName;
        if (value.indexOf(':') != -1) // Tiene package el value, ej "android:" delegamos en Resources.getIdentifier() que lo resuelva
        {
            packageName = null;
        }
        else
        {
            packageName = ctx.getPackageName(); // El package es necesario como parámetro sólo cuando no está en la string (recursos compilados)
        }

        return res.getIdentifier(value, null, packageName);
    }

    private int getViewIdDynamicallyAdded(String value)
    {
        if (value.indexOf(':') != -1) // Tiene package, ej "@+android:id/", no se encontrará un id registrado como "@+id/..." y los posibles casos con package NO los hemos contemplado
            return 0; // No encontrado

        // Fue añadido a través de "@+id/..."
        value = value.substring(1); // Quitamos el @
        int pos = value.indexOf('/');
        String idName = value.substring(pos + 1);

        return findViewIdDynamicallyAdded(idName);
    }

    public ViewStyleAttr getViewStyle(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr.getNamespaceURI() != null || !"style".equals(attr.getName())) throw MiscUtil.internalError();

        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            ElementValuesStyle elemStyle = elementResources.getViewStyle(attrDyn.getValuesResourceName());
            DOMAttr domAttrParent = elemStyle.getParentAttr();
            List<DOMAttr> domAttrValueList = elemStyle.getChildDOMAttrValueList();
            return new ViewStyleAttrDynamic(domAttrParent,domAttrValueList);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String attrValue = attr.getValue();
            int styleId = getIdentifierCompiled(attrValue, ctx);
            if (styleId == 0)
                return null;
            return new ViewStyleAttrCompiled(styleId);
        }
        else throw MiscUtil.internalError();
    }

    public static boolean isResource(String attrValue)
    {
        // No hace falta hacer un trim, un espacio al ppio invalida el atributo
        return attrValue.startsWith("@") || attrValue.startsWith("?");
    }

    public int getInteger(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getInteger(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getIntegerCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getIntegerCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
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

    public float getFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getFloat(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getFloatCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private float getFloatCompiled(String attrValue, Context ctx)
    {
        // Ojo, para valores sin sufijo de dimensión (por ej layout_weight o alpha)
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getDimension(resId); // No hay getFloat
        }
        else return parseFloat(attrValue);
    }

    public String getString(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getString(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getStringCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private String getStringCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getString(resId);
        }
        return StringUtil.convertEscapedStringLiteralToNormalString(attrValue);
    }

    public CharSequence getText(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getText(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getTextCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private CharSequence getTextCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getText(resId);
        }
        else
        {
            // Vemos si contiene HTML, nos ahorraremos el procesado como HTML sin serlo y además conseguiremos que funcionen los tests a nivel de misma clase devuelta, pues Android
            // parece que hace lo mismo, es decir cuando no es HTML devuelve un String en vez de un SpannedString.
            if (isHTML(attrValue))
            {
                Spanned spannedValue = Html.fromHtml(attrValue);
                return new SpannedString(spannedValue); // Para que el tipo devuelto sea el mismo que en el caso compilado y pasemos los tests
            }
            return StringUtil.convertEscapedStringLiteralToNormalString(attrValue);
        }
    }

    private static boolean isHTML(String markup)
    {
        // Vemos si hay un </tag> (pues lo normal será usar <b>text</b> y similares)
        // Es conveniente ver el código de Html.fromHtml(String)
        int posStart = markup.indexOf("</");
        if (posStart != -1)
        {
            int posEnd = markup.indexOf('>',posStart);
            if (posEnd != -1)
            {
                String tag = markup.substring(posStart + 2, posEnd);
                tag = tag.trim();
                boolean isTag = StringUtil.isTag(tag);
                if (isTag)
                    return true;
            }
        }

        // Consideramos el <br/> cerrado (no consideramos <br> pues no se admite en XML compilado)
        posStart = markup.indexOf("<");
        if (posStart != -1)
        {
            int posEnd = markup.indexOf("/>",posStart);
            if (posEnd != -1)
            {
                String tag = markup.substring(posStart + 1, posEnd);
                tag = tag.trim();
                boolean isTag = StringUtil.isTag(tag);
                if (isTag)
                    return true;
            }
        }

        boolean ENABLE_ENTITY_REF = true;
        if (ENABLE_ENTITY_REF)
        {
            // Vemos si al menos hay un entityref ej &lt;
            // https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references
            posStart = markup.indexOf('&');
            if (posStart != -1)
            {
                int posEnd = markup.indexOf(';', posStart);
                if (posEnd != -1 && posEnd - posStart > 2)
                {
                    String entity = markup.substring(posStart + 1, posEnd);
                    boolean isWord = true;
                    for (int i = 0; i < entity.length(); i++)
                    {
                        char c = entity.charAt(i);
                        if (!Character.isLetter(c))
                        {
                            isWord = false;
                            break;
                        }
                    }
                    if (isWord) return true;

                    // Ya está hecho y lo dejamos aunque desactivado pero he descubierto que el parser XML de Android no admite el formato &#xnumhex; o &numdec; sólo entities con nombre conocidas (&lt; etc)
                    if (false)
                    {
                        if (entity.length() >= 2)
                        {
                            if (entity.charAt(0) == '#')
                            {
                                if (entity.charAt(1) == 'x')
                                {
                                    String numberHex = entity.substring(2, entity.length());
                                    boolean isHexNumber = true;
                                    for (int i = 0; i < numberHex.length(); i++)
                                    {
                                        char c = entity.charAt(i);
                                        if (!Character.isLetterOrDigit(c))
                                        {
                                            isHexNumber = false;
                                            break;
                                        }
                                    }
                                    if (isHexNumber) return true;
                                }
                                else
                                {
                                    String number = entity.substring(1, entity.length());
                                    boolean isDecNumber = true;
                                    for (int i = 0; i < number.length(); i++)
                                    {
                                        char c = entity.charAt(i);
                                        if (!Character.isDigit(c))
                                        {
                                            isDecNumber = false;
                                            break;
                                        }
                                    }
                                    if (isDecNumber) return true;
                                }
                            }
                        }
                    }
                }
            }
        }


        return false;
    }

    public CharSequence[] getTextArray(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getTextArray(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getTextArrayCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private CharSequence[] getTextArrayCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getTextArray(resId);
        }
        else throw MiscUtil.internalError();
    }

    public boolean getBoolean(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getBoolean(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getBooleanCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private boolean getBooleanCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getBoolean(resId);
        }
        else return Boolean.parseBoolean(attrValue);
    }


    public Dimension getDimensionObject(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getDimensionObject(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String originalValue = attr.getValue();
            return getDimensionObjectCompiled(originalValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Dimension getDimensionObjectCompiled(String attrValue, Context ctx)
    {
        // El retorno es en px
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            float num = ctx.getResources().getDimension(resId);
            return new Dimension(TypedValue.COMPLEX_UNIT_PX, num);
        }
        else
        {
            return getDimensionObjectCompiled(attrValue);
        }
    }


    private static Dimension getDimensionObjectCompiled(String attrValue)
    {
        // Suponemos que NO es un recurso externo
        // El retorno es en px
        String valueTrim = attrValue.trim();
        String suffix = getDimensionSuffix(valueTrim);
        int complexUnit = getDimensionSuffixAsInt(suffix);
        float num = extractFloat(valueTrim, suffix);
        return new Dimension(complexUnit, num);
    }

    public int getDimensionIntFloor(DOMAttr attr,XMLInflater xmlInflater)
    {
        // TypedValue.complexToDimensionPixelOffset
        return (int) getDimensionFloat(attr, xmlInflater);
    }

    public int getDimensionIntRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        // TypedValue.complexToDimensionPixelSize
        float num = getDimensionFloat(attr, xmlInflater);
        int numInt = (int)(num + 0.5f);
        return numInt;
    }

    public float getDimensionFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        // El retorno es en px
        Dimension dimen = getDimensionObject(attr, xmlInflater);
        int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
        float num = dimen.getValue();

        Resources res = xmlInflater.getContext().getResources();
        return toPixelFloat(unit, num, res);
    }

    public float getDimensionFloatFloor(DOMAttr attr,XMLInflater xmlInflater)
    {
        // El retorno es en px
        float num = getDimensionFloat(attr, xmlInflater);
        // num = (float) Math.floor(num);
        int numInt = (int)num;
        return numInt;
    }

    public float getDimensionFloatRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        // El retorno es en px
        float num = getDimensionFloat(attr, xmlInflater);
        //num = Math.round(num);
        int numInt = (int)(num + 0.5f);
        return numInt;
    }

    public PercFloat getDimensionPercFloat(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getDimensionPercFloat(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String originalValue = attr.getValue();
            return getDimensionPercFloatCompiled(originalValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private PercFloat getDimensionPercFloatCompiled(String attrValue, Context ctx)
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
            int resId = getIdentifierCompiled(attrValue, ctx);
            String value = res.getString(resId);
            return parseDimensionPercFloat(value, ctx);
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

        int pos;
        pos = attrValue.lastIndexOf("%");
        if (pos != -1)
        {
            dataType = TypedValue.TYPE_FRACTION;
            boolean fractionParent = (attrValue.lastIndexOf("%p") != -1);
            attrValue = attrValue.substring(0, pos);
            float value = Float.parseFloat(attrValue);
            return new PercFloat(dataType, fractionParent, value);
        }
        else
        {
            final boolean fractionParent = false;
            dataType = TypedValue.TYPE_FLOAT;
            char last = attrValue.charAt(attrValue.length() - 1);
            if (Character.isDigit(last))
            {
                float value = Float.parseFloat(attrValue);
                return new PercFloat(dataType, fractionParent, value); // fractionParent es indiferente
            }
            else
            {
                Dimension dimen = getDimensionObjectCompiled(attrValue);
                int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
                float num = dimen.getValue();
                float value = toPixelFloat(unit, num, ctx.getResources());
                return new PercFloat(dataType, fractionParent, value); // fractionParent es indiferente
            }
        }
    }

    public int getDimensionWithNameIntRound(DOMAttr attr,XMLInflater xmlInflater)
    {
        int dimension;

        String value = attr.getValue();
        // No hace falta hacer trim en caso de "match_parent" etc un espacio fastidia el attr
        if ("match_parent".equals(value) || "fill_parent".equals(value))
            dimension = ViewGroup.LayoutParams.MATCH_PARENT;
        else if ("wrap_content".equals(value))
            dimension = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            dimension = getDimensionIntRound(attr, xmlInflater);

        return dimension;
    }

    private static int getDimensionSuffixAsInt(String suffix)
    {
        if (suffix.equals("dp") || suffix.equals("dip")) return TypedValue.COMPLEX_UNIT_DIP;
        else if (suffix.equals("px")) return TypedValue.COMPLEX_UNIT_PX;
        else if (suffix.equals("sp")) return TypedValue.COMPLEX_UNIT_SP;
        else if (suffix.equals("in")) return TypedValue.COMPLEX_UNIT_IN;
        else if (suffix.equals("mm")) return TypedValue.COMPLEX_UNIT_MM;
        else throw MiscUtil.internalError();
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

    private static float toPixelFloat(int unit, float value, Resources res)
    {
        // Nexus 4 tiene un scale 2 de dp a px (xhdpi),  con un valor de 0.3 devuelve 0.6 bien para probar si usar round/floor
        // Nexus 5 tiene un scale 3 de dp a px (xxhdpi), con un valor de 0.3 devuelve 0.9 bien para probar si usar round/floor
        // La VM ItsNatDroid es una Nexus 4
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    public int getColor(DOMAttr attr,XMLInflater xmlInflater)
    {
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getColor(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getColorCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getColorCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
            return ctx.getResources().getColor(resId);
        }
        else if (attrValue.startsWith("#")) // Color literal. No hace falta hacer trim
        {
            return Color.parseColor(attrValue);
        }

        throw new ItsNatDroidException("Cannot process " + attrValue);
    }


    public static String toStringColorTransparent(int value)
    {
        if (value != Color.TRANSPARENT) throw MiscUtil.internalError();
        return "#00000000";
    }

    public float getPercent(DOMAttr attr,XMLInflater xmlInflater)
    {
        // Leer notas en getPercentCompiled()
        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflater);
            return elementResources.getPercent(attrDyn.getValuesResourceName(), xmlInflater);
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            Context ctx = xmlInflater.getContext();
            String value = attr.getValue();
            return getPercentCompiled(value, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private float getPercentCompiled(String attrValue, Context ctx)
    {
        // Sólo se usa en ScaleDrawable, de hecho el método getPercent que usa Android es local en dicha clase, no se reutiliza para otros casos, el valor compilado se obtiene de Resources.getString()
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
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
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/graphics/drawable/ScaleDrawable.java#ScaleDrawable.getPercent%28android.content.res.TypedArray%2Cint%29
        if (s != null) {
            if (s.endsWith("%")) {
                String f = s.substring(0, s.length() - 1);
                return Float.parseFloat(f) / 100.0f;
            }
        }
        return -1;
    }

    public Drawable getDrawable(DOMAttr attr,XMLInflater xmlInflaterParent)
    {
        Context ctx = xmlInflaterParent.getContext();

        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic) attr;
            if (attrDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflaterParent);
                return elementResources.getDrawable(attrDyn.getValuesResourceName(), xmlInflaterParent);
            }
            else
            {
                int bitmapDensityReference = xmlInflaterParent.getBitmapDensityReference();

                String resourceMime = attrDyn.getResourceMime();
                if (MimeUtil.isMIMEResourceXML(resourceMime))
                {
                    // Esperamos un drawable
                    PageImpl page = PageImpl.getPageImpl(xmlInflaterParent);

                    if (attr instanceof DOMAttrRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

                    ItsNatDroidImpl itsNatDroid = xmlInflaterParent.getInflatedXML().getItsNatDroidImpl();
                    AttrLayoutInflaterListener attrLayoutInflaterListener = xmlInflaterParent.getAttrLayoutInflaterListener();
                    AttrDrawableInflaterListener attrDrawableInflaterListener = xmlInflaterParent.getAttrDrawableInflaterListener();

                    ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) attrDyn.getResource();
                    XMLDOMDrawable xmlDOMDrawable = (XMLDOMDrawable) resource.getXMLDOM();
                    InflatedDrawable inflatedDrawable = InflatedDrawable.createInflatedDrawable(itsNatDroid, xmlDOMDrawable, ctx, page);

                    XMLDOMParserContext xmlDOMParserContext = xmlInflaterParent.getXMLDOMParserContext();

                    XMLInflaterDrawable xmlInflaterDrawable = XMLInflaterDrawable.createXMLInflaterDrawable(inflatedDrawable, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener,xmlDOMParserContext);
                    return xmlInflaterDrawable.inflateDrawable();
                }
                else if (MimeUtil.isMIMEResourceImage(resourceMime))
                {
                    ParsedResourceImage resource = (ParsedResourceImage) attrDyn.getResource();
                    byte[] byteArray = resource.getImgBytes();
                    boolean expectedNinePatch = attrDyn.isNinePatch();
                    return DrawableUtil.createImageBasedDrawable(byteArray, bitmapDensityReference, expectedNinePatch, ctx.getResources());
                }
                else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
            }
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            String attrValue = attr.getValue();
            return getDrawableCompiled(attrValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Drawable getDrawableCompiled(String attrValue, Context ctx)
    {
        if (isResource(attrValue))
        {
            int resId = getIdentifierCompiled(attrValue, ctx);
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

    public View getLayout(DOMAttr attr, XMLInflaterLayout xmlInflaterParent,ViewGroup viewParent,int indexChild,ArrayList<DOMAttr> includeAttribs)
    {
        // Sólo es llamado al procesar <include> dinámicamente.
        // viewParent es por ahora NO nulo, no hay todavía un caso de uso con viewParent nulo pues esta llamada es para cargar un Layout a través de un <include> (por ahora)

        Context ctx = xmlInflaterParent.getContext();

        if (attr instanceof DOMAttrDynamic)
        {
            DOMAttrDynamic attrDyn = (DOMAttrDynamic)attr;
            if (attrDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(attrDyn, xmlInflaterParent);
                return elementResources.getLayout(attrDyn.getValuesResourceName(), xmlInflaterParent,viewParent,indexChild,includeAttribs);
            }
            else
            {
                int bitmapDensityReference = xmlInflaterParent.getBitmapDensityReference();

                String resourceMime = attrDyn.getResourceMime();
                if (MimeUtil.isMIMEResourceXML(resourceMime))
                {
                    PageImpl pageParent = PageImpl.getPageImpl(xmlInflaterParent);

                    if (attr instanceof DOMAttrRemote && pageParent == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

                    int countBefore = viewParent.getChildCount();

                    ItsNatDroidImpl itsNatDroid = xmlInflaterParent.getInflatedXML().getItsNatDroidImpl();
                    AttrLayoutInflaterListener attrLayoutInflaterListener = xmlInflaterParent.getAttrLayoutInflaterListener();
                    AttrDrawableInflaterListener attrDrawableInflaterListener = xmlInflaterParent.getAttrDrawableInflaterListener();

                    ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) attrDyn.getResource();
                    XMLDOMLayout xmlDOMLayout = (XMLDOMLayout) resource.getXMLDOM();

                    XMLInflaterLayout xmlInflaterLayout = XMLInflaterLayout.inflateLayout(itsNatDroid, xmlDOMLayout, viewParent, indexChild, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener, ctx, pageParent);
                    View rootView = xmlInflaterLayout.getInflatedLayoutImpl().getRootView();

                    if (pageParent != null) // existe página padre
                    {
                        XMLInflaterLayoutPage xmlInflaterLayoutPageParent = (XMLInflaterLayoutPage) xmlInflaterParent; // No esperamos que sea XMLInflaterDrawablePage
                        InflatedLayoutPageImpl inflatedLayoutPageParent = xmlInflaterLayoutPageParent.getInflatedLayoutPageImpl();

                        InflatedLayoutPageImpl inflatedLayoutPage = ((XMLInflaterLayoutPage) xmlInflaterLayout).getInflatedLayoutPageImpl();
                        List<String> scriptList = inflatedLayoutPage.getScriptList();

                        if (!scriptList.isEmpty())
                        {
                            inflatedLayoutPageParent.getScriptList().addAll(scriptList);
                        }

                        if (inflatedLayoutPage instanceof InflatedLayoutPageItsNatImpl)
                        {
                            String loadInitScript = ((InflatedLayoutPageItsNatImpl) inflatedLayoutPage).getLoadInitScript();
                            if (loadInitScript != null) throw new ItsNatDroidException("Scripting must be disabled in ItsNat Server document for referenced layouts"); // Pues el itsNatDoc es el del padre y la liamos al intentar iniciar un layout siendo incluido en el padre acaba cambiando la inicialización del padre, esto no quita que <script> normales sean permitidos como en web
                        }
                    }


                    if (rootView != viewParent) throw MiscUtil.internalError(); // rootView es igual a viewParent

                    int countAfter = viewParent.getChildCount();
                    int countInserted = countAfter - countBefore;
                    if (countInserted == 1 && includeAttribs != null)
                    {
                        View rootViewChild = viewParent.getChildAt(indexChild);

                        xmlInflaterLayout.fillIncludeAttributesFromGetLayout(rootViewChild, viewParent, includeAttribs);
                    }

                    InflatedLayoutImpl inflatedLayout = xmlInflaterLayout.getInflatedLayoutImpl();

                    ViewMapByXMLId viewMapByXMLId = inflatedLayout.getViewMapByXMLId();
                    WeakMapWithValue<String, View> weakMapWithValue = viewMapByXMLId.getMapIdViewXMLStdPureField();
                    if (weakMapWithValue != null)
                    {
                        InflatedLayoutImpl inflatedLayoutParent = (InflatedLayoutImpl) xmlInflaterParent.getInflatedXML();
                        weakMapWithValue.copyTo(inflatedLayoutParent.getViewMapByXMLId().getMapIdViewXMLStd());
                    }

                    return rootView;
                }
                else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
            }
        }
        else if (attr instanceof DOMAttrCompiledResource)
        {
            String attrValue = attr.getValue();
            return getLayoutCompiled(attrValue, xmlInflaterParent, viewParent, indexChild, includeAttribs);
        }
        else throw MiscUtil.internalError();
    }

    private View getLayoutCompiled(String attrValue, XMLInflater xmlInflater, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        // viewParent es por ahora NO nulo

        if (isResource(attrValue))
        {
            Context ctx = xmlInflater.getContext();

            int resId = getIdentifierCompiled(attrValue, ctx);
            if (resId <= 0) return null;
            int countBefore = viewParent.getChildCount();

            View rootView = LayoutInflater.from(ctx).inflate(resId, viewParent);

            if (rootView != viewParent) throw MiscUtil.internalError(); // rootView es igual a viewParent

            int countAfter = viewParent.getChildCount();
            int countInserted = countAfter - countBefore;
            if (countInserted == 1 && includeAttribs != null)
            {
                View rootViewChild = viewParent.getChildAt(indexChild);
                XMLInflaterLayout xmlInflaterLayout = (XMLInflaterLayout)xmlInflater;

                xmlInflaterLayout.fillIncludeAttributesFromGetLayout(rootViewChild,viewParent,includeAttribs);
            }

            return rootView;
        }

        throw new ItsNatDroidException("Cannot process " + attrValue);
    }

    private ElementValuesResources getElementValuesResources(DOMAttrDynamic attrDyn, XMLInflater xmlInflaterParent)
    {
        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM)attrDyn.getResource();
        XMLDOMValues xmlDOMValues = (XMLDOMValues)resource.getXMLDOM();

        // Una vez parseado XMLDOMValues y cargados los recursos remotos se cachea y NO se modifica (no hay un pre-clonado
        // El resultado de inflar es ElementValuesResources que básicamente contiene los valores de <item> <dim> etc ORIGINALES SIN RESOLVER RESPECTO AL Context, dichos valores sólo pueden
        // cambiar si cambia el XMLDOMValues original (lo cual es posible) pero entonces será un XMLDOMValues
        // A donde quiero llegar es que PODEMOS CACHEAR ElementValuesResources sin miedo respecto a XMLDOMValues, no es el caso de cachear InflatedValues (el objeto padre) el cual contiene el Context
        // Afortunadamente aunque InflatedValues es el objeto padre de ElementValuesResources, este último NO tiene referencia alguna a InflatedValues padre por lo que éste se pierde y no retiene el Context
        // Es importante cachear ElementValuesResources de otra manera inflar por cada obtención de un valor es costosísimo

        ElementValuesResources elementValuesResources = cacheXMLDOMValuesXMLInflaterValuesMap.get(xmlDOMValues);
        if (elementValuesResources != null)
        {
//System.out.println("CACHED elementValuesResources");
            return elementValuesResources;
        }

        Context ctx = xmlInflaterParent.getContext();

        String resourceMime = attrDyn.getResourceMime();
        if (!MimeUtil.isMIMEResourceXML(resourceMime))
            throw new ItsNatDroidException("Unsupported resource MIME in this context: " + resourceMime);

        PageImpl page = PageImpl.getPageImpl(xmlInflaterParent); // Puede ser null

        if (attrDyn instanceof DOMAttrRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        int bitmapDensityReference = xmlInflaterParent.getBitmapDensityReference();

        ItsNatDroidImpl itsNatDroid = xmlInflaterParent.getInflatedXML().getItsNatDroidImpl();
        AttrLayoutInflaterListener attrLayoutInflaterListener = xmlInflaterParent.getAttrLayoutInflaterListener();
        AttrDrawableInflaterListener attrDrawableInflaterListener = xmlInflaterParent.getAttrDrawableInflaterListener();

        InflatedValues inflatedValues = InflatedValues.createInflatedValues(itsNatDroid, xmlDOMValues, ctx, page);

        XMLDOMParserContext xmlDOMParserContext = xmlInflaterParent.getXMLDOMParserContext();

        XMLInflaterValues xmlInflaterValues = XMLInflaterValues.createXMLInflaterValues(inflatedValues, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener,xmlDOMParserContext);
        ElementValuesResources elementResources = xmlInflaterValues.inflateValues();

        cacheXMLDOMValuesXMLInflaterValuesMap.put(xmlDOMValues,elementResources);

        return elementResources;
    }
}

