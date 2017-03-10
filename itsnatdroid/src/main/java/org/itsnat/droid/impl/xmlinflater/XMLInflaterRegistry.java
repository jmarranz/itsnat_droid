package org.itsnat.droid.impl.xmlinflater;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescCompiled;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.util.StringUtil;
import org.itsnat.droid.impl.util.WeakMapWithValue;
import org.itsnat.droid.impl.xmlinflated.anim.InflatedXMLAnimation;
import org.itsnat.droid.impl.xmlinflated.animator.InflatedXMLAnimator;
import org.itsnat.droid.impl.xmlinflated.animinterp.InflatedXMLInterpolator;
import org.itsnat.droid.impl.xmlinflated.animlayout.InflatedXMLLayoutAnimation;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedXMLDrawable;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageImpl;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageItsNatImpl;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesStyle;
import org.itsnat.droid.impl.xmlinflated.values.InflatedXMLValues;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.XMLInflaterAnimation;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.XMLInflaterInterpolator;
import org.itsnat.droid.impl.xmlinflater.animlayout.ClassDescLayoutAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.animlayout.XMLInflaterLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.drawable.BitmapDrawableUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueCompiled;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValueDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.ViewMapByXMLId;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribs;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribsCompiled;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribsDynamic;
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
public class XMLInflaterRegistry
{
    private ItsNatDroidImpl itsNatDroid;
    private int sNextGeneratedId = 1; // No usamos AtomicInteger porque no lo usaremos en multihilo
    private Map<String, Integer> newViewIdMap = new HashMap<String, Integer>();
    private ClassDescViewMgr classDescViewMgr = new ClassDescViewMgr(this);
    private ClassDescDrawableMgr classDescDrawableMgr = new ClassDescDrawableMgr(this);
    private ClassDescValuesMgr classDescValuesMgr = new ClassDescValuesMgr(this);
    private ClassDescAnimationMgr classDescAnimationMgr = new ClassDescAnimationMgr(this);
    private ClassDescAnimatorMgr classDescAnimatorMgr = new ClassDescAnimatorMgr(this);
    private ClassDescInterpolatorMgr classDescInterpolatorMgr = new ClassDescInterpolatorMgr(this);
    private ClassDescLayoutAnimationMgr classDescLayoutAnimationMgr = new ClassDescLayoutAnimationMgr(this);
    private Map<XMLDOMValues,ElementValuesResources> cacheXMLDOMValuesXMLInflaterValuesMap = new HashMap<XMLDOMValues, ElementValuesResources>();

    public XMLInflaterRegistry(ItsNatDroidImpl itsNatDroid)
    {
        this.itsNatDroid = itsNatDroid;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
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

    public ClassDescAnimationMgr getClassDescAnimationMgr()
    {
        return classDescAnimationMgr;
    }

    public ClassDescAnimatorMgr getClassDescAnimatorMgr()
    {
        return classDescAnimatorMgr;
    }

    public ClassDescInterpolatorMgr getClassDescInterpolatorMgr()
    {
        return classDescInterpolatorMgr;
    }

    public ClassDescLayoutAnimationMgr getClassDescLayoutAnimationMgr()
    {
        return classDescLayoutAnimationMgr;
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

    public int getIdentifier(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            String type = resourceDescDyn.getResourceType();
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getIdentifier(resourceDescDyn.getValuesResourceName(), type, xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getIdentifierAddIfNecessaryCompiled(resourceDescValue,null, ctx);
        }
        else throw MiscUtil.internalError();
    }

    public int getIdentifier(ResourceDesc resourceDesc, String type, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            // Es raro que pase por aquí, sería cuando un <item type="id"> en un XML values tiene como valor un path remoto/dinámico
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            type = resourceDescDyn.getResourceType();
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn,xmlInflaterContext);
            return elementResources.getIdentifier(resourceDescDyn.getValuesResourceName(), type, xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getIdentifierAddIfNecessaryCompiled(resourceDescValue,type, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getIdentifierAddIfNecessaryCompiled(String resourceDescValue,String type, Context ctx)
    {
        // type puede ser null
        // Procesamos aquí los casos de "@+id/...", la razón es que cualquier atributo que referencie un id (más allá
        // de android:id) puede registrar un nuevo atributo lo cual es útil si el android:id como tal está después,
        // después en android:id ya no hace falta que sea "@+id/...".
        // http://stackoverflow.com/questions/11029635/android-radiogroup-checkedbutton-property
        if (("+id".equals(type) || "id".equals(type)) && (!resourceDescValue.startsWith("@+id/") && !resourceDescValue.startsWith("@id/")))
        {
            if ("+id".equals(type)) resourceDescValue = "@+id/" + resourceDescValue;
            else resourceDescValue = "@id/" + resourceDescValue;
        }

        int id;
        if (resourceDescValue.startsWith("@+id/") || resourceDescValue.startsWith("@id/")) // Si fuera el caso de "@+mypackage:id/name" ese caso no lo soportamos, no lo he visto nunca aunque en teoría está sintácticamente permitido
        {
            id = getIdentifierCompiled(resourceDescValue, ctx, false); // Tiene prioridad el recurso de Android, pues para qué generar un id nuevo si ya existe o bien ya fue registrado dinámicamente
            if (id <= 0)
            {
                int pos = resourceDescValue.indexOf('/');
                String idName = resourceDescValue.substring(pos + 1);
                if (resourceDescValue.startsWith("@+id/")) id = findViewIdDynamicallyAddedAddIfNecessary(idName);
                else id = findViewIdDynamicallyAdded(idName);
                if (id <= 0)
                    throw new ItsNatDroidException("Not found resource with id \"" + resourceDescValue + "\" you could use @+id/ ");
            }
        }
        else id = getIdentifierCompiled(resourceDescValue, ctx);
        return id;
    }

    public int getIdentifierCompiled(String resourceDescValue, Context ctx)
    {
        return getIdentifierCompiled(resourceDescValue, ctx, true);
    }

    private boolean isIdentifierCompiledValueNull(String resourceDescValue)
    {
        return ("0".equals(resourceDescValue) || "-1".equals(resourceDescValue) || "@null".equals(resourceDescValue));
    }

    private int getIdentifierCompiled(String resourceDescValue, Context ctx, boolean throwErr)
    {
        if (isIdentifierCompiledValueNull(resourceDescValue)) return 0; // Si throwErr es true, es el único caso en el que se devuelve 0

        int id;
        char first = resourceDescValue.charAt(0);
        if (first == '?')
        {
            id = getIdentifierCompiledAttrTheme(resourceDescValue, ctx);
            if (id > 0)
                return id;
        }
        else if (first == '@')
        {
            // Tiene prioridad el registro de Android que el de ItsNat en el caso de "@+id", para qué generar un id si ya existe como recurso
            id = getIdentifierResource(resourceDescValue, ctx);
            if (id > 0)
                return id;

            if (resourceDescValue.startsWith("@id/") || resourceDescValue.startsWith("@+id/"))
            {
                // En este caso es posible que se haya registrado dinámicamente el id via "@+id/..."
                id = getViewIdDynamicallyAdded(resourceDescValue);
                if (id > 0)
                    return id;
            }
        }
        else if (resourceDescValue.startsWith("android:"))
        {
            // Es el caso de style parent definido por Android ej: <style name="..." parent="android:Theme.Holo.Light.DarkActionBar"> que es la manera reducida de poner:
            // parent="@android:style/Theme.Holo.Light.DarkActionBar" que se procesaría en el caso anterior
            // Sinceramente no se como obtenerlo via Resources.getIdentifier, lo que hacemos es convertirlo en el formato parent="@android:style/Theme.Holo.Light.DarkActionBar"

            int pos = "android:".length();
            resourceDescValue = "@android:style/" +  resourceDescValue.substring(pos);

            id = getIdentifierResource(resourceDescValue,ctx);
            if (id > 0)
                return id;
        }
        else
        {
            throw new ItsNatDroidException("Bad format in identifier declaration: " + resourceDescValue);
        }

        if (throwErr && id <= 0)
            throw new ItsNatDroidException("Not found resource with id value \"" + resourceDescValue + "\"");
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

    public ViewStyleAttribs getViewStyle(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // Ya no solo es para el atributo "style": if (attr.getNamespaceURI() != null || !"style".equals(attr.getName())) throw MiscUtil.internalError();

        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            ElementValuesStyle elemStyle = elementResources.getViewStyle(resourceDescDyn.getValuesResourceName());
            DOMAttr domAttrParent = elemStyle.getParentAttr();
            List<DOMAttr> domAttrValueList = elemStyle.getChildDOMAttrValueList();
            return new ViewStyleAttribsDynamic(domAttrParent,domAttrValueList);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            int styleId = getIdentifierCompiled(resourceDescValue, ctx);
            return new ViewStyleAttribsCompiled(styleId);
        }
        else throw MiscUtil.internalError();
    }


    public int getViewStyle(ViewStyleAttribs style,List<DOMAttr> styleItemsDynamicAttribs,Context ctx)
    {
        // El retorno es el id del style compilado si existe o el del parent en caso de <style name="..." parent="...">
        if (style == null)
            return 0;

        if (style instanceof ViewStyleAttribsCompiled)
        {
            return ((ViewStyleAttribsCompiled)style).getIdentifier();
        }
        else if (style instanceof ViewStyleAttribsDynamic)
        {
            ViewStyleAttribsDynamic dynStyle = (ViewStyleAttribsDynamic)style;
            List<DOMAttr> styleItemAttrs = dynStyle.getDOMAttrItemList();
            if (styleItemAttrs != null) // Si es null es raro, es el caso de <style> vacío
                styleItemsDynamicAttribs.addAll(styleItemAttrs);

            DOMAttr parentStyleDOMAttr = dynStyle.getDOMAttrParentStyle(); // Puede ser null
            if (parentStyleDOMAttr == null)
                return 0;
            return getIdentifierCompiled(parentStyleDOMAttr.getValue(),ctx);
        }
        else throw MiscUtil.internalError();
    }

    public static boolean isResource(String attrValue)
    {
        // No hace falta hacer un trim, un espacio al ppio invalida el atributo
        return attrValue.startsWith("@") || attrValue.startsWith("?");
    }

    public int getInteger(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getInteger(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getIntegerCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getIntegerCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for an integer resource");
            return ctx.getResources().getInteger(resId);
        }
        else
        {
            if (resourceDescValue.startsWith("0x"))
            {
                resourceDescValue = resourceDescValue.substring(2);
                return Integer.parseInt(resourceDescValue, 16);
            }
            return Integer.parseInt(resourceDescValue);
        }
    }

    public float getFloat(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getFloat(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getFloatCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private float getFloatCompiled(String resourceDescValue, Context ctx)
    {
        // Ojo, para valores sin sufijo de dimensión (por ej layout_weight o alpha)
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a dimension resource");
            return ctx.getResources().getDimension(resId); // No hay getFloat
        }
        else return parseFloat(resourceDescValue);
    }

    public String getString(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getString(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getStringCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private String getStringCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a integer resource");
            return ctx.getResources().getString(resId);
        }
        return StringUtil.convertEscapedStringLiteralToNormalString(resourceDescValue);
    }

    public CharSequence getText(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getText(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getTextCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private CharSequence getTextCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a text resource");
            return ctx.getResources().getText(resId);
        }
        else
        {
            // Vemos si contiene HTML, nos ahorraremos el procesado como HTML sin serlo y además conseguiremos que funcionen los tests a nivel de misma clase devuelta, pues Android
            // parece que hace lo mismo, es decir cuando no es HTML devuelve un String en vez de un SpannedString.
            if (isHTML(resourceDescValue))
            {
                Spanned spannedValue = Html.fromHtml(resourceDescValue);
                return new SpannedString(spannedValue); // Para que el tipo devuelto sea el mismo que en el caso compilado y pasemos los tests
            }
            return StringUtil.convertEscapedStringLiteralToNormalString(resourceDescValue);
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

    public CharSequence[] getTextArray(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn,xmlInflaterContext);
            return elementResources.getTextArray(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getTextArrayCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private CharSequence[] getTextArrayCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a text array resource");
            return ctx.getResources().getTextArray(resId);
        }
        else throw MiscUtil.internalError();
    }

    public boolean getBoolean(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getBoolean(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getBooleanCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private boolean getBooleanCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a boolean resource");
            return ctx.getResources().getBoolean(resId);
        }
        else return Boolean.parseBoolean(resourceDescValue);
    }

    public boolean isDimension(ResourceDesc resourceDesc)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            return "dimen".equals(resourceDescDyn.getResourceType());
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            String value = resourceDesc.getResourceDescValue();
            if (value.startsWith("?android:attr/"))
                throw new ItsNatDroidException("Resource type cannot be determined using \"?android:attr/\" in this context");
            if (value.startsWith("@android:dimen/") || value.startsWith("@dimen/"))
                return true;
            return getDimensionSuffixNotError(value) != null;
        }
        else throw MiscUtil.internalError();
    }

    public Dimension getDimensionObject(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getDimensionObject(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String originalValue = resourceDesc.getResourceDescValue();
            return getDimensionObjectCompiled(originalValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Dimension getDimensionObjectCompiled(String resourceDescValue, Context ctx)
    {
        // El retorno es en px
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a dimension resource");
            float num = ctx.getResources().getDimension(resId);
            return new Dimension(TypedValue.COMPLEX_UNIT_PX, num);
        }
        else
        {
            return getDimensionObjectCompiled(resourceDescValue);
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

    public int getDimensionIntFloor(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // TypedValue.complexToDimensionPixelOffset
        return (int) getDimensionFloat(resourceDesc, xmlInflaterContext);
    }

    public int getDimensionIntRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // TypedValue.complexToDimensionPixelSize
        float num = getDimensionFloat(resourceDesc, xmlInflaterContext);
        int numInt = (int)(num + 0.5f);
        return numInt;
    }

    public float getDimensionFloat(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // El retorno es en px
        Dimension dimen = getDimensionObject(resourceDesc, xmlInflaterContext);
        int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
        float num = dimen.getValue();

        Resources res = xmlInflaterContext.getContext().getResources();
        return toPixelFloat(unit, num, res);
    }

    public float getDimensionFloatFloor(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // El retorno es en px
        float num = getDimensionFloat(resourceDesc, xmlInflaterContext);
        // num = (float) Math.floor(num);
        int numInt = (int)num;
        return numInt;
    }

    public float getDimensionFloatRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        // El retorno es en px
        float num = getDimensionFloat(resourceDesc, xmlInflaterContext);
        //num = Math.round(num);
        int numInt = (int)(num + 0.5f);
        return numInt;
    }

    public PercFloatImpl getDimensionPercFloat(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getDimensionPercFloat(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String originalValue = resourceDesc.getResourceDescValue();
            return getDimensionPercFloatCompiled(originalValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private PercFloatImpl getDimensionPercFloatCompiled(String resourceDescValue, Context ctx)
    {
        return getDimensionPercFloatCompiled(resourceDescValue,ctx,true);
    }

    private PercFloatImpl getDimensionPercFloatCompiled(String resourceDescValue, Context ctx, boolean acceptDimension)
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
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a dimension resource");
            String value = res.getString(resId);
            return parseDimensionPercFloat(value, ctx, acceptDimension);
        }
        else
        {
            return parseDimensionPercFloat(resourceDescValue, ctx, acceptDimension);
        }
    }


    private PercFloatImpl parseDimensionPercFloat(String attrValue, Context ctx, boolean acceptDimension)
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
            return new PercFloatImpl(dataType, fractionParent, value);
        }
        else
        {
            final boolean fractionParent = false; // Es indiferente
            dataType = TypedValue.TYPE_FLOAT;
            char last = attrValue.charAt(attrValue.length() - 1);
            if (Character.isDigit(last) || last == '.')  // 3. es un float válido
            {
                float value = Float.parseFloat(attrValue);
                return new PercFloatImpl(dataType, fractionParent, value); // fractionParent es indiferente
            }
            else
            {
                if (!acceptDimension) throw new ItsNatDroidException("Dimensions like 20dp are not accepted");

                Dimension dimen = getDimensionObjectCompiled(attrValue);
                int unit = dimen.getComplexUnit(); // TypedValue.COMPLEX_UNIT_DIP etc
                float num = dimen.getValue();
                float value = toPixelFloat(unit, num, ctx.getResources());
                return new PercFloatImpl(dataType, fractionParent, value); // fractionParent es indiferente
            }
        }
    }

    public PercFloatImpl getPercFloat(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getPercFloat(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String originalValue = resourceDesc.getResourceDescValue();
            return getDimensionPercFloatCompiled(originalValue, ctx, false);
        }
        else throw MiscUtil.internalError();
    }

    public String getDimensionOrString(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getDimensionOrString(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getStringCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }


    public int getDimensionWithNameIntRound(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        int dimension;

        String value = resourceDesc.getResourceDescValue();
        // No hace falta hacer trim en caso de "match_parent" etc un espacio fastidia el attr
        if ("match_parent".equals(value) || "fill_parent".equals(value))
            dimension = ViewGroup.LayoutParams.MATCH_PARENT;
        else if ("wrap_content".equals(value))
            dimension = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            dimension = getDimensionIntRound(resourceDesc, xmlInflaterContext);

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
        String suffix = getDimensionSuffixNotError(value);
        if (suffix == null) throw new ItsNatDroidException("Unrecognized dimension: " + value);
        return suffix;
    }

    public static String getDimensionSuffixNotError(String value)
    {
        if (value.endsWith(" ")) throw new ItsNatDroidException("Dimension cannot end with space: \"" + value + "\"");

        if (value.endsWith("dp")) return "dp";
        if (value.endsWith("dip")) // Concesión al pasado
            return "dip";
        else if (value.endsWith("px")) return "px";
        else if (value.endsWith("sp")) return "sp";
        else if (value.endsWith("in")) return "in";
        else if (value.endsWith("mm")) return "mm";
        else return null;
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

    public boolean isColor(ResourceDesc resourceDesc)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            return "color".equals(resourceDescDyn.getResourceType());
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            String value = resourceDesc.getResourceDescValue();
            if (value.startsWith("?android:attr/"))
                throw new ItsNatDroidException("Resource type cannot be determined using \"?android:attr/\" in this context");
            if (value.startsWith("@android:color/") || value.startsWith("@color/"))
                return true;
            return value.startsWith("#");
        }
        else throw MiscUtil.internalError();
    }

    public int getColor(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
            return elementResources.getColor(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getColorCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private int getColorCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) throw new ItsNatDroidException("Resource id value cannot be @null for a color resource");
            return ctx.getResources().getColor(resId);
        }
        else if (resourceDescValue.startsWith("#")) // Color literal. No hace falta hacer trim
        {
            return Color.parseColor(resourceDescValue);
        }

        throw new ItsNatDroidException("Cannot process " + resourceDescValue);
    }


    public static String toStringColorTransparent(int value)
    {
        if (value != Color.TRANSPARENT) throw MiscUtil.internalError();
        return "#00000000";
    }

    public Drawable getDrawable(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        Context ctx = xmlInflaterContext.getContext();

        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getDrawable(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
            }
            else
            {
                int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

                String resourceMime = resourceDescDyn.getResourceMime();
                if (MimeUtil.isMIMEResourceXML(resourceMime))
                {
                    // Esperamos un drawable
                    PageImpl page = xmlInflaterContext.getPageImpl();

                    if (resourceDesc instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

                    AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

                    ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDescDyn.getParsedResource();
                    XMLDOMDrawable xmlDOMDrawable = (XMLDOMDrawable) resource.getXMLDOM();
                    InflatedXMLDrawable inflatedDrawable = InflatedXMLDrawable.createInflatedDrawable(itsNatDroid, xmlDOMDrawable, ctx, page);

                    XMLInflaterDrawable xmlInflaterDrawable = XMLInflaterDrawable.createXMLInflaterDrawable(inflatedDrawable, bitmapDensityReference,attrResourceInflaterListener);
                    return xmlInflaterDrawable.inflateDrawable();
                }
                else if (MimeUtil.isMIMEResourceImage(resourceMime))
                {
                    ParsedResourceImage resource = (ParsedResourceImage) resourceDescDyn.getParsedResource();
                    byte[] byteArray = resource.getImgBytes();
                    boolean expectedNinePatch = resourceDescDyn.isNinePatch();
                    return BitmapDrawableUtil.createImageBasedDrawable(byteArray, bitmapDensityReference, expectedNinePatch, ctx.getResources());
                }
                else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getDrawableCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Drawable getDrawableCompiled(String resourceDescValue, Context ctx)
    {
        if (isResource(resourceDescValue))
        {
            int resId = getIdentifierCompiled(resourceDescValue, ctx);
            if (resId == 0) return null;
            return ctx.getResources().getDrawable(resId);
        }
        else if (resourceDescValue.startsWith("#")) // Color literal. No hace falta hacer trim
        {
            int color = Color.parseColor(resourceDescValue);
            return new ColorDrawable(color);
        }

        throw new ItsNatDroidException("Cannot process " + resourceDescValue);
    }

    public View getLayout(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild)
    {
        return getLayout(resourceDesc,xmlInflaterContext,xmlInflaterParent, viewParent, indexChild, null);
    }

    public View getLayout(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        LayoutValue lv = getLayoutValue(resourceDesc,xmlInflaterContext,xmlInflaterParent, viewParent, indexChild, includeAttribs);
        return lv.getView();
    }

    public LayoutValue getLayoutValue(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getLayoutValue(resourceDescDyn.getValuesResourceName(), xmlInflaterContext, xmlInflaterParent, viewParent, indexChild, includeAttribs);
            }
            else
            {
                View rootViewOrViewParent = getViewLayoutDynamicFromXML(resourceDescDyn, xmlInflaterContext, xmlInflaterParent, viewParent,indexChild, includeAttribs);
                return new LayoutValueDynamic(rootViewOrViewParent);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterParent.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            int layoutId = getIdentifierCompiled(resourceDescValue, ctx);
            View rootViewOrViewParent = getViewLayoutCompiled(layoutId,xmlInflaterContext.getContext(),xmlInflaterParent, viewParent, indexChild, includeAttribs);
            return new LayoutValueCompiled(rootViewOrViewParent,layoutId);
        }
        else throw MiscUtil.internalError();
    }


    private View getViewLayoutCompiled(int resId,Context ctx,XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        // viewParent es por ahora NO nulo

        int countBefore = -1;
        if (viewParent != null)
        {
            countBefore = viewParent.getChildCount();
        }

        View rootView = LayoutInflater.from(ctx).inflate(resId, viewParent);

        if (viewParent != null)
        {
            if (rootView != viewParent) throw MiscUtil.internalError(); // rootView es igual a viewParent

            int countAfter = viewParent.getChildCount();
            int countInserted = countAfter - countBefore;
            if (countInserted == 1 && includeAttribs != null)
            {
                View rootViewChild = viewParent.getChildAt(indexChild);

                xmlInflaterParent.fillIncludeAttributesFromGetLayout(rootViewChild, viewParent, includeAttribs);
            }
        }

        return rootView;
    }

    private View getViewLayoutDynamicFromXML(ResourceDescDynamic resourceDesc,XMLInflaterContext xmlInflaterContext, XMLInflaterLayout xmlInflaterParent, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        if (resourceDesc.getValuesResourceName() != null) throw MiscUtil.internalError();

        Context ctx = xmlInflaterContext.getContext();

        String resourceMime = resourceDesc.getResourceMime();
        if (MimeUtil.isMIMEResourceXML(resourceMime))
        {
            int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

            PageImpl page = xmlInflaterContext.getPageImpl();

            if (resourceDesc instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

            int countBefore = 0;
            if (viewParent != null)
            {
                countBefore = viewParent.getChildCount();
            }

            AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

            ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDesc.getParsedResource();
            XMLDOMLayout xmlDOMLayout = (XMLDOMLayout) resource.getXMLDOM();

            XMLInflaterLayout xmlInflaterLayout = XMLInflaterLayout.createXMLInflaterLayout(itsNatDroid, xmlDOMLayout, bitmapDensityReference, attrResourceInflaterListener, ctx, page);
            //View rootViewOrViewParent = xmlInflaterLayout.getInflatedXMLLayoutImpl().getRootView();
            View rootViewOrViewParent = xmlInflaterLayout.inflateLayout(viewParent, indexChild);

            if (page != null) // existe página padre
            {
                XMLInflaterLayoutPage xmlInflaterLayoutPageParent = (XMLInflaterLayoutPage) xmlInflaterParent;
                InflatedXMLLayoutPageImpl inflatedLayoutPageParent = xmlInflaterLayoutPageParent.getInflatedXMLLayoutPageImpl();

                InflatedXMLLayoutPageImpl inflatedLayoutPage = ((XMLInflaterLayoutPage) xmlInflaterLayout).getInflatedXMLLayoutPageImpl();
                List<String> scriptList = inflatedLayoutPage.getScriptList();

                if (!scriptList.isEmpty())
                {
                    inflatedLayoutPageParent.getScriptList().addAll(scriptList);
                }

                if (inflatedLayoutPage instanceof InflatedXMLLayoutPageItsNatImpl)
                {
                    String loadInitScript = ((InflatedXMLLayoutPageItsNatImpl) inflatedLayoutPage).getLoadInitScript();
                    if (loadInitScript != null) throw new ItsNatDroidException("Scripting must be disabled in ItsNat Server document for referenced layouts"); // Pues el itsNatDoc es el del padre y la liamos al intentar iniciar un layout siendo incluido en el padre acaba cambiando la inicialización del padre, esto no quita que <script> normales sean permitidos como en web
                }
            }

            if (viewParent != null)
            {
                if (rootViewOrViewParent != viewParent) throw MiscUtil.internalError(); // rootViewOrViewParent es igual a viewParent

                int countAfter = viewParent.getChildCount();
                int countInserted = countAfter - countBefore;
                if (countInserted == 1 && includeAttribs != null)
                {
                    View rootViewChild = viewParent.getChildAt(indexChild);

                    xmlInflaterLayout.fillIncludeAttributesFromGetLayout(rootViewChild, viewParent, includeAttribs);
                }
            }

            InflatedXMLLayoutImpl inflatedLayout = xmlInflaterLayout.getInflatedXMLLayoutImpl();

            ViewMapByXMLId viewMapByXMLId = inflatedLayout.getViewMapByXMLId();
            WeakMapWithValue<String, View> weakMapWithValue = viewMapByXMLId.getMapIdViewXMLStdPureField();
            if (weakMapWithValue != null)
            {
                InflatedXMLLayoutImpl inflatedLayoutParent = (InflatedXMLLayoutImpl) xmlInflaterParent.getInflatedXML();
                weakMapWithValue.copyTo(inflatedLayoutParent.getViewMapByXMLId().getMapIdViewXMLStd());
            }

            return rootViewOrViewParent;
        }
        else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
    }

    public LayoutAnimationController getLayoutAnimation(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getLayoutAnimation(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
            }
            else
            {
                return getLayoutAnimationDynamicFromXML(resourceDescDyn,xmlInflaterContext);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getLayoutAnimationCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private LayoutAnimationController getLayoutAnimationDynamicFromXML(ResourceDescDynamic resourceDescDyn, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDescDyn.getValuesResourceName() != null) throw MiscUtil.internalError();

        Context ctx = xmlInflaterContext.getContext();

        int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

        AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

        // Esperamos un LayoutAnimationController
        PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null

        if (resourceDescDyn instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDescDyn.getParsedResource();
        if (resource == null)
            throw NamespaceUtil.resourceStillNotLoadedException(resourceDescDyn.getResourceDescValue());
        XMLDOMLayoutAnimation xmlDOMLayoutAnimation = (XMLDOMLayoutAnimation) resource.getXMLDOM();
        InflatedXMLLayoutAnimation inflatedLayoutAnimation = InflatedXMLLayoutAnimation.createInflatedLayoutAnimation(itsNatDroid, xmlDOMLayoutAnimation, ctx, page);

        XMLInflaterLayoutAnimation xmlInflaterLayoutAnimation = XMLInflaterLayoutAnimation.createXMLInflaterLayoutAnimation(inflatedLayoutAnimation, bitmapDensityReference, attrResourceInflaterListener);
        return xmlInflaterLayoutAnimation.inflateLayoutAnimation();
    }

    private LayoutAnimationController getLayoutAnimationCompiled(String resourceDescValue,Context ctx)
    {
        int id = getIdentifierCompiled(resourceDescValue, ctx);
        if (id <= 0)
            return null;

        return AnimationUtils.loadLayoutAnimation(ctx, id);
    }

    public Animation getAnimation(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getAnimation(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
            }
            else
            {
                return getAnimationDynamicFromXML(resourceDescDyn,xmlInflaterContext);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getAnimationCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Animation getAnimationDynamicFromXML(ResourceDescDynamic resourceDescDyn, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDescDyn.getValuesResourceName() != null) throw MiscUtil.internalError();

        Context ctx = xmlInflaterContext.getContext();

        int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

        AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

        // Esperamos un Animator
        PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null

        if (resourceDescDyn instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDescDyn.getParsedResource();
        if (resource == null)
            throw NamespaceUtil.resourceStillNotLoadedException(resourceDescDyn.getResourceDescValue());
        XMLDOMAnimation xmlDOMAnimation = (XMLDOMAnimation) resource.getXMLDOM();
        InflatedXMLAnimation inflatedAnimation = InflatedXMLAnimation.createInflatedAnimation(itsNatDroid, xmlDOMAnimation, ctx, page);

        XMLInflaterAnimation xmlInflaterAnimation = XMLInflaterAnimation.createXMLInflaterAnimation(inflatedAnimation, bitmapDensityReference, attrResourceInflaterListener);
        return xmlInflaterAnimation.inflateAnimation();
    }

    private Animation getAnimationCompiled(String resourceDescValue,Context ctx)
    {
        int id = getIdentifierCompiled(resourceDescValue, ctx);
        if (id <= 0)
            return null;

        return AnimationUtils.loadAnimation(ctx, id);
    }


    public Animator getAnimator(ResourceDesc resourceDesc,XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getAnimator(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
            }
            else
            {
                return getAnimatorDynamicFromXML(resourceDescDyn,xmlInflaterContext);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getAnimatorCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Animator getAnimatorDynamicFromXML(ResourceDescDynamic resourceDescDyn, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDescDyn.getValuesResourceName() != null) throw MiscUtil.internalError();

        Context ctx = xmlInflaterContext.getContext();

        int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

        AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

        // Esperamos un Animator
        PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null

        if (resourceDescDyn instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDescDyn.getParsedResource();
        if (resource == null)
            throw NamespaceUtil.resourceStillNotLoadedException(resourceDescDyn.getResourceDescValue());
        XMLDOMAnimator xmlDOMAnimator = (XMLDOMAnimator) resource.getXMLDOM();
        InflatedXMLAnimator inflatedAnimator = InflatedXMLAnimator.createInflatedAnimator(itsNatDroid, xmlDOMAnimator, ctx, page);

        XMLInflaterAnimator xmlInflaterAnimator = XMLInflaterAnimator.createXMLInflaterAnimator(inflatedAnimator, bitmapDensityReference, attrResourceInflaterListener);
        return xmlInflaterAnimator.inflateAnimator();
    }

    private Animator getAnimatorCompiled(String resourceDescValue,Context ctx)
    {
        int id = getIdentifierCompiled(resourceDescValue, ctx);
        if (id <= 0)
            return null;

        return AnimatorInflater.loadAnimator(ctx, id);
    }

    public Interpolator getInterpolator(ResourceDesc resourceDesc, XMLInflaterContext xmlInflaterContext)
    {
        // http://developer.android.com/guide/topics/resources/animation-resource.html#Interpolators
        if (resourceDesc instanceof ResourceDescDynamic)
        {
            ResourceDescDynamic resourceDescDyn = (ResourceDescDynamic)resourceDesc;
            if (resourceDescDyn.getValuesResourceName() != null)
            {
                ElementValuesResources elementResources = getElementValuesResources(resourceDescDyn, xmlInflaterContext);
                return elementResources.getInterpolator(resourceDescDyn.getValuesResourceName(), xmlInflaterContext);
            }
            else
            {
                return getInterpolatorDynamicFromXML(resourceDescDyn,xmlInflaterContext);
            }
        }
        else if (resourceDesc instanceof ResourceDescCompiled)
        {
            Context ctx = xmlInflaterContext.getContext();
            String resourceDescValue = resourceDesc.getResourceDescValue();
            return getInterpolatorCompiled(resourceDescValue, ctx);
        }
        else throw MiscUtil.internalError();
    }

    private Interpolator getInterpolatorDynamicFromXML(ResourceDescDynamic resourceDescDyn, XMLInflaterContext xmlInflaterContext)
    {
        if (resourceDescDyn.getValuesResourceName() != null) throw MiscUtil.internalError();

        Context ctx = xmlInflaterContext.getContext();

        int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

        AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

        // Esperamos un Animator
        PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null

        if (resourceDescDyn instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM) resourceDescDyn.getParsedResource();
        if (resource == null)
            throw NamespaceUtil.resourceStillNotLoadedException(resourceDescDyn.getResourceDescValue());

        XMLDOMInterpolator xmlDOMInterpolator = (XMLDOMInterpolator) resource.getXMLDOM();
        InflatedXMLInterpolator inflatedInterpolator = InflatedXMLInterpolator.createInflatedInterpolator(itsNatDroid, xmlDOMInterpolator, ctx, page);

        XMLInflaterInterpolator xmlInflaterInterpolator = XMLInflaterInterpolator.createXMLInflaterInterpolator(inflatedInterpolator, bitmapDensityReference, attrResourceInflaterListener);

        return xmlInflaterInterpolator.inflateInterpolator();
    }

    private Interpolator getInterpolatorCompiled(String resourceDescValue,Context ctx)
    {
        int id = getIdentifierCompiled(resourceDescValue, ctx);
        if (id <= 0)
            return null;

        return AnimationUtils.loadInterpolator(ctx, id);
    }


    private ElementValuesResources getElementValuesResources(ResourceDescDynamic resourceDescDynamic, XMLInflaterContext xmlInflaterContext)
    {
        ParsedResourceXMLDOM resource = (ParsedResourceXMLDOM)resourceDescDynamic.getParsedResource();
        XMLDOMValues xmlDOMValues = (XMLDOMValues)resource.getXMLDOM();

        // Una vez parseado XMLDOMValues y cargados los recursos remotos se cachea y NO se modifica (no hay un pre-clonado
        // El resultado de inflar es ElementValuesResources que básicamente contiene los valores de <item> <dim> etc ORIGINALES SIN RESOLVER RESPECTO AL Context, dichos valores sólo pueden
        // cambiar si cambia el XMLDOMValues original (lo cual es posible) pero entonces será un XMLDOMValues
        // A donde quiero llegar es que PODEMOS CACHEAR ElementValuesResources sin miedo respecto a XMLDOMValues, no es el caso de cachear InflatedValues (el objeto padre) el cual contiene el Context
        // Afortunadamente aunque InflatedValues es el objeto padre de ElementValuesResources, este último NO tiene referencia alguna a InflatedValues padre por lo que éste se pierde y no retiene el Context
        // Es importante cachear ElementValuesResources de otra manera inflar por cada obtención de un valor es costosísimo

        ElementValuesResources elementValuesResources = cacheXMLDOMValuesXMLInflaterValuesMap.get(xmlDOMValues);
        if (elementValuesResources != null)
            return elementValuesResources;

        Context ctx = xmlInflaterContext.getContext();

        String resourceMime = resourceDescDynamic.getResourceMime();
        if (!MimeUtil.isMIMEResourceXML(resourceMime))
            throw new ItsNatDroidException("Unsupported resource MIME in this context: " + resourceMime);

        PageImpl page = xmlInflaterContext.getPageImpl(); // Puede ser null

        if (resourceDescDynamic instanceof ResourceDescRemote && page == null) throw MiscUtil.internalError(); // Si es remote hay page por medio

        int bitmapDensityReference = xmlInflaterContext.getBitmapDensityReference();

        AttrResourceInflaterListener attrResourceInflaterListener = xmlInflaterContext.getAttrResourceInflaterListener();

        InflatedXMLValues inflatedValues = InflatedXMLValues.createInflatedValues(itsNatDroid, xmlDOMValues, ctx, page);

        XMLInflaterValues xmlInflaterValues = XMLInflaterValues.createXMLInflaterValues(inflatedValues, bitmapDensityReference, attrResourceInflaterListener);
        ElementValuesResources elementResources = xmlInflaterValues.inflateValues();

        cacheXMLDOMValuesXMLInflaterValuesMap.put(xmlDOMValues,elementResources);

        return elementResources;
    }

}

