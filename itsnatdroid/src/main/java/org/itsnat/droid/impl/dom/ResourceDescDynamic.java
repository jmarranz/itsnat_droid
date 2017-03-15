package org.itsnat.droid.impl.dom;

import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.MimeUtil;

import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ANIM;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ANIMATOR;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DRAWABLE;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_LAYOUT;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class ResourceDescDynamic extends ResourceDesc
{
    protected final String resType;
    protected final String extension; // xml, png...
    protected final String valuesResourceName; // No nulo sólo en el caso de "values" tras el :
    protected final boolean ninePatch;
    protected final String mime;
    protected final String location;
    protected volatile ParsedResource parsedResource;

    public ResourceDescDynamic(String resourceDescValue)
    {
        super(resourceDescValue);

        // Ej. @assets:drawable/res/drawable/file.png   Path: res/drawable/file.png

        // Ej. @remote:drawable/res/drawable/file.png   Remote Path: res/drawable/file.png
        //     @remote:drawable//res/drawable/file.png  Remote Path: /res/drawable/file.png
        //     @remote:drawable/http://somehost/res/drawable/file.png  Remote Path: http://somehost/res/drawable/file.png
        //     @remote:drawable/ItsNatDroidServletExample?itsnat_doc_name=test_droid_remote_drawable

        // Ej. values:  @assets:dimen/res/values/filename.xml:size
        //              @remote:dimen/res/values/filename.xml:size

        int posType = resourceDescValue.indexOf(':');
        int posPath = resourceDescValue.indexOf('/');
        String resTypeTmp = resourceDescValue.substring(posType + 1,posPath); // Ej. "drawable"
        this.resType = resTypeTmp;

        String locationTmp;
        if (XMLDOMValues.isResourceTypeValues(resType))
        {
            int valuesResourcePos = resourceDescValue.lastIndexOf(':'); // Esperamos el de por ej "...filename.xml:size" pero puede devolvernos el de "@assets:dimen..." lo que significa que no existe valuesResourceName lo cual es erróneo
            if (valuesResourcePos > posType && isResourceNameValid(resourceDescValue,valuesResourcePos)) // Correcto, existe un segundo ":" para el valuesResourceName y es un nombre válido
            {
                locationTmp = resourceDescValue.substring(posPath + 1,valuesResourcePos); // incluye la extension

                this.valuesResourceName = resourceDescValue.substring(valuesResourcePos + 1);
            }
            else // No hay selector ":selector"
            {
                if (TYPE_ANIM.equals(resType) || TYPE_ANIMATOR.equals(resType) || TYPE_DRAWABLE.equals(resType) || TYPE_LAYOUT.equals(resType) )
                {
                    // En el caso "drawable" podemos tener un acceso a un <drawable> en archivo XML en /res/values o bien directamente acceder al XML en /res/drawable
                    // este es el caso de acceso DIRECTO al XML del drawable
                    // Idem con <item name="..." type="layout">, type="anim" y type="animator"
                    locationTmp = resourceDescValue.substring(posPath + 1);

                    this.valuesResourceName = null;
                }
                else throw new ItsNatDroidException("Bad format of attribute value, expected \"values\" resource ended with \":resname\" : " + resourceDescValue);
            }
        }
        else
        {
            locationTmp = resourceDescValue.substring(posPath + 1);

            this.valuesResourceName = null;
        }

        // locationTmp = processLocationSuffixes(locationTmp,xmlDOMParserContext.getConfiguration(),xmlDOMParserContext.getDisplayMetrics());

        this.location = locationTmp;

        int posExt = this.location.lastIndexOf('.');
        if (posExt != -1)
        {
            this.extension = this.location.substring(posExt + 1).toLowerCase(); // xml, png...
        }
        else
        {
            // Por ejemplo:  @remote:drawable/ItsNatDroidServletExample?itsnat_doc_name=test_droid_remote_drawable
            // Suponemos que se genera el XML por ej del drawable
            this.extension = null;
        }


        if (extension != null)
        {
            // http://www.sitepoint.com/web-foundations/mime-types-complete-list/
            String mime = MimeUtil.MIME_BY_EXT.get(extension);
            if (mime == null)
                throw new ItsNatDroidException("Unexpected extension: \"" + extension + "\" Remote resource: " + resourceDescValue);
            this.mime = mime;
            this.ninePatch = MimeUtil.MIME_PNG.equals(mime) && resourceDescValue.endsWith(".9.png");
        }
        else
        {
            this.mime = MimeUtil.MIME_XML;
            this.ninePatch = false;
        }
    }


    private boolean isResourceNameValid(String value,int valuesResourcePos)
    {
        String resName = value.substring(valuesResourcePos + 1);

        // El nombre de un recurso ya sea un path o un <item name="resname"> o un <string name="resname> se convierte en una variable Java en la clase R.java
        // (ejemplo public static final int prueba=0x7f090005; por lo que este es el criterio de validez
        int len = resName.length();
        for(int i = 0; i < len; i++)
        {
            char c = resName.charAt(0);
            if (i == 0 && !Character.isJavaIdentifierStart(c))
                return false;
            else if (!Character.isJavaIdentifierPart(c))
                return false;
        }

        return true;
    }

    public String getResourceType()
    {
        return resType;
    }

    public String getExtension()
    {
        return extension;
    }

    public String getValuesResourceName()
    {
        return valuesResourceName;
    }

    public boolean isNinePatch()
    {
        return ninePatch;
    }

    public String getLocation(XMLDOMParserContext xmlDOMParserContext)
    {
        String locationTmp = processLocationSuffixes(this.location,xmlDOMParserContext.getConfiguration(),xmlDOMParserContext.getDisplayMetrics());
        return locationTmp;
    }

    public String getResourceMime()
    {
        return mime;
    }

    public ParsedResource getParsedResource()
    {
        // Es sólo llamado en el hilo UI pero setParsedResource se llama en multihilo
        return parsedResource;
    }

    public void setParsedResource(ParsedResource parsedResource)
    {
        // Es llamado en multihilo en el caso de recurso remoto (por eso es volatile)
        // No pasa nada porque se llame e inmediatamente después se cambie el valor, puede ocurrir que se esté procesando
        // el mismo atributo a la vez por dos hilos, ten en cuenta que el template puede estar cacheado y reutilizado, pero no pasa nada
        // porque el nuevo remoteResource NUNCA es null y es siempre el mismo recurso como mucho actualizado si ha cambiado
        // en el servidor
        this.parsedResource = parsedResource;
    }


    private String processLocationSuffixes(String location,Configuration configuration,DisplayMetrics displayMetrics)
    {
        // http://developer.android.com/guide/topics/resources/providing-resources.html (el orden de la tabla 2 es el orden de los sufijos en el caso de múltiples sufijos)
        // http://developer.android.com/guide/topics/resources/localization.html

        // Los filtros de ItsNat Droid tienen dos modos de declaración y por tanto de funcionamiento:
        // 1) Caso con valor explícito: ej {lg-es}. Se detecta que hay valor especificado (el "es") y se reemplaza por el valor si se da la regla: "-es"  o por nada ""
        // 2) Caso sin valor explícito: ej {lg-}. Se detecta que NO hay valor especificado y se reemplaza por el valor actual del dispositivo: "-es"

        String suffix = "}";

        int posToSearchMore = 0;

        {
            // No soportamos MCC y MNC filtros, aportan muy poco valor
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo de lenguaje
            // Ej {lg-es}
            String prefix = "{lg-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                String currentLang = configuration.locale.getLanguage();
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-" + currentLang + location.substring(posEnd + 1);
                }
                else
                {
                    String lang = location.substring(posStart + prefix.length(), posEnd);
                    if (currentLang.equals(lang))
                    {
                        location = location.substring(0, posStart) + "-" + lang + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo de región
            // Ej {rg-rES}
            String prefix = "{rg-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                String currentRegion = configuration.locale.getCountry();
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-r" + currentRegion + location.substring(posEnd + 1);
                }
                else
                {
                    String region = location.substring(posStart + prefix.length() + 1 /* el +1 es la r de rES */, posEnd);
                    if (currentRegion.equals(region))
                    {
                        location = location.substring(0, posStart) + "-r" + region + location.substring(posEnd + 1);
                    }
                    else
                    {
                        // Quitamos el sufijo pues no se usa
                        location = location.substring(0, posStart) + location.substring(posEnd + 1);
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }


        {
            // Layout Direction es level 17 no lo soportamos todavía
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo smallestWidth
            // Ej {sw-sw720dp}
            String prefix = "{sw-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-sw" + deviceSmallestScreenWidthDp + "dp" + location.substring(posEnd + 1);
                }
                else
                {
                    String smallestScreenWidthDpStr = location.substring(posStart + prefix.length() + 2, posEnd - 2); // El +2 es para quitar el "sw" y el -2 es para quitar el "dp" y que smallestScreenWidthDpStr sea un entero
                    int smallestScreenWidthDp;
                    try { smallestScreenWidthDp = Integer.parseInt(smallestScreenWidthDpStr); }
                    catch (Exception ex) { throw new ItsNatDroidException("Bad smallest width suffix: " + smallestScreenWidthDpStr); }

                    if (deviceSmallestScreenWidthDp >= smallestScreenWidthDp)
                    {
                        location = location.substring(0, posStart) + "-sw" + smallestScreenWidthDp + "dp" + location.substring(posEnd + 1);
                    }
                    else
                    {
                        // Quitamos el sufijo pues no se usa
                        location = location.substring(0, posStart) + location.substring(posEnd + 1);
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo screenWidthDp (available width)
            // Ej {w-w720dp}
            String prefix = "{w-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);
                int deviceScreenWidthDp = configuration.screenWidthDp;
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-w" + deviceScreenWidthDp + "dp" + location.substring(posEnd + 1);
                }
                else
                {
                    String screenWidthDpStr = location.substring(posStart + prefix.length() + 1, posEnd - 2); // El +1 es para quitar el "w" y el -2 es para quitar el "dp" y que screenWidthDp sea un entero
                    int screenWidthDp;
                    try { screenWidthDp = Integer.parseInt(screenWidthDpStr); }
                    catch (Exception ex) { throw new ItsNatDroidException("Bad screen width dp suffix: " + screenWidthDpStr); }

                    if (deviceScreenWidthDp >= screenWidthDp)
                    {
                        location = location.substring(0, posStart) + "-w" + screenWidthDp + "dp" + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo screenWidthDp (available width)
            // Ej {h-h720dp}
            String prefix = "{h-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceScreenHeightDp = configuration.screenHeightDp;
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-h" + deviceScreenHeightDp + "dp" + location.substring(posEnd + 1);
                }
                else
                {
                    String screenHeightDpStr = location.substring(posStart + prefix.length() + 1, posEnd - 2); // El +1 es para quitar el "h" y el -2 es para quitar el "dp" y que screenWidthDp sea un entero
                    int screenHeightDp;
                    try { screenHeightDp = Integer.parseInt(screenHeightDpStr); }
                    catch (Exception ex) { throw new ItsNatDroidException("Bad screen height dp suffix: " + screenHeightDpStr); }

                    if (deviceScreenHeightDp >= screenHeightDp)
                    {
                        location = location.substring(0, posStart) + "-h" + screenHeightDp + "dp" + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo screen size (screenLayout)  http://developer.android.com/guide/practices/screens_support.html
            // Ej {ss-xlarge}
            String prefix = "{ss-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceScreenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceScreenLayoutStr;
                    switch(deviceScreenLayout)
                    {
                        case Configuration.SCREENLAYOUT_SIZE_SMALL: deviceScreenLayoutStr = "small"; break;
                        case Configuration.SCREENLAYOUT_SIZE_NORMAL: deviceScreenLayoutStr = "normal"; break;
                        case Configuration.SCREENLAYOUT_SIZE_LARGE: deviceScreenLayoutStr = "large"; break;
                        case Configuration.SCREENLAYOUT_SIZE_XLARGE: deviceScreenLayoutStr = "xlarge"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported screen size value: " + deviceScreenLayout);
                    }

                    location = location.substring(0, posStart) + "-" + deviceScreenLayoutStr + location.substring(posEnd + 1);
                }
                else
                {
                    String screenLayoutStr = location.substring(posStart + prefix.length(), posEnd);
                    int screenLayout;
                    if ("small".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_SIZE_SMALL;
                    else if ("normal".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL;
                    else if ("large".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE;
                    else if ("xlarge".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_SIZE_XLARGE;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + screenLayoutStr);

                    if (deviceScreenLayout >= screenLayout)
                    {
                        location = location.substring(0, posStart) + "-" + screenLayoutStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }


        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo screen aspect (screenLayout)   http://developer.android.com/guide/practices/screens_support.html
            // Ej {sa-long}
            String prefix = "{sa-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceScreenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_LONG_MASK;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceScreenLayoutStr;
                    switch(deviceScreenLayout)
                    {
                        case Configuration.SCREENLAYOUT_LONG_NO: deviceScreenLayoutStr = "notlong"; break;
                        case Configuration.SCREENLAYOUT_LONG_YES: deviceScreenLayoutStr = "long"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported screen aspect value: " + deviceScreenLayout);
                    }

                    location = location.substring(0, posStart) + "-" + deviceScreenLayoutStr + location.substring(posEnd + 1);
                }
                else
                {
                    String screenLayoutStr = location.substring(posStart + prefix.length(), posEnd);
                    int screenLayout;
                    if ("notlong".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_LONG_NO;
                    else if ("long".equals(screenLayoutStr)) screenLayout = Configuration.SCREENLAYOUT_LONG_YES;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + screenLayoutStr);

                    if (deviceScreenLayout >= screenLayout)
                    {
                        location = location.substring(0, posStart) + "-" + screenLayoutStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        // Quitamos el sufijo pues no se usa
                        location = location.substring(0, posStart) + location.substring(posEnd + 1);
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        {
            // Round screen es level 23 no lo soportamos todavía
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo screen orientation (orientation)
            // Ej {so-port}
            String prefix = "{so-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceOrientation = configuration.orientation;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceOrientationStr;
                    switch(deviceOrientation)
                    {
                        case Configuration.ORIENTATION_PORTRAIT: deviceOrientationStr = "port"; break;
                        case Configuration.ORIENTATION_LANDSCAPE: deviceOrientationStr = "land"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported screen orientation value: " + deviceOrientation);
                    }

                    location = location.substring(0, posStart) + "-" + deviceOrientationStr + location.substring(posEnd + 1);
                }
                else
                {
                    String orientationStr = location.substring(posStart + prefix.length(), posEnd);
                    int orientation;
                    if ("port".equals(orientationStr)) orientation = Configuration.ORIENTATION_PORTRAIT;
                    else if ("land".equals(orientationStr)) orientation = Configuration.ORIENTATION_LANDSCAPE;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + orientationStr); // Existe un ORIENTATION_SQUARE declarado en level 15 pero la doc oficial dice que está deprecado en level 16 http://developer.android.com/reference/android/content/res/Configuration.html#ORIENTATION_SQUARE

                    if (deviceOrientation == orientation)
                    {
                        location = location.substring(0, posStart) + "-" + orientationStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo UI Mode Type (uiMode)
            // Ej {uimt-port}
            String prefix = "{uimt-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceUiModeType = configuration.uiMode & Configuration.UI_MODE_TYPE_MASK;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceUiModeTypeStr;
                    switch(deviceUiModeType)
                    {
                        case Configuration.UI_MODE_TYPE_NORMAL: deviceUiModeTypeStr = "normal"; break;
                        case Configuration.UI_MODE_TYPE_DESK: deviceUiModeTypeStr = "desk"; break;
                        case Configuration.UI_MODE_TYPE_CAR: deviceUiModeTypeStr = "car"; break;
                        case Configuration.UI_MODE_TYPE_TELEVISION: deviceUiModeTypeStr = "television"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported UI Mode Type value: " + deviceUiModeType);
                    }

                    location = location.substring(0, posStart) + "-" + deviceUiModeTypeStr + location.substring(posEnd + 1);
                }
                else
                {
                    String uimtStr = location.substring(posStart + prefix.length(), posEnd);
                    int uimt;
                    if ("normal".equals(uimtStr)) uimt = Configuration.UI_MODE_TYPE_NORMAL; // Los móviles, tabletas etc son NORMAL
                    else if ("desk".equals(uimtStr)) uimt = Configuration.UI_MODE_TYPE_DESK;
                    else if ("car".equals(uimtStr)) uimt = Configuration.UI_MODE_TYPE_CAR;
                    else if ("television".equals(uimtStr)) uimt = Configuration.UI_MODE_TYPE_TELEVISION;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + uimtStr); // En versiones superiores hay más (appliance, watch)

                    if (deviceUiModeType == uimt)
                    {
                        location = location.substring(0, posStart) + "-" + uimtStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo UI Mode Night (uiMode)
            // Ej {uimn-notnight}
            String prefix = "{uimn-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceUiModeNight = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceUimnStr;
                    switch(deviceUiModeNight)
                    {
                        case Configuration.UI_MODE_NIGHT_NO: deviceUimnStr = "notnight"; break;
                        case Configuration.UI_MODE_NIGHT_YES: deviceUimnStr = "night"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported UI Mode Night value: " + deviceUiModeNight);
                    }

                    location = location.substring(0, posStart) + "-" + deviceUimnStr + location.substring(posEnd + 1);
                }
                else
                {
                    String uimnStr = location.substring(posStart + prefix.length(), posEnd);
                    int uimn;
                    if ("notnight".equals(uimnStr)) uimn = Configuration.UI_MODE_NIGHT_NO;
                    else if ("night".equals(uimnStr)) uimn = Configuration.UI_MODE_NIGHT_YES;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + uimnStr);

                    if (deviceUiModeNight == uimn)
                    {
                        location = location.substring(0, posStart) + "-" + uimnStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo Screen pixel density (dpi)
            // Ej {spd-xhdpi}
            String prefix = "{spd-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);
                int deviceDensityDpi = displayMetrics.densityDpi;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceDensityDpiStr;

                    if (deviceDensityDpi == 1)
                        deviceDensityDpiStr = "nodpi";
                    else if (deviceDensityDpi == 213)
                        deviceDensityDpiStr = "tvdpi"; // No tengo claro si hacer rango con este caso, en teoría sólo se aplica si es una televisión
                    else if (deviceDensityDpi < 120)
                        deviceDensityDpiStr = "ldpi";
                    else if (deviceDensityDpi >= 120 && deviceDensityDpi < 120 + (160 - 120)/2)
                        deviceDensityDpiStr = "ldpi";
                    else if (deviceDensityDpi >= 160 && deviceDensityDpi < 160 + (240 - 160)/2)
                        deviceDensityDpiStr = "mdpi";
                    else if (deviceDensityDpi >= 240 && deviceDensityDpi < 240 + (320 - 240)/2)
                        deviceDensityDpiStr = "hdpi";
                    else if (deviceDensityDpi >= 320 && deviceDensityDpi < 320 + (480 - 320)/2)
                        deviceDensityDpiStr = "xhdpi";
                    else if (deviceDensityDpi >= 480 && deviceDensityDpi < 480 + (640 - 480)/2)
                        deviceDensityDpiStr = "xxhdpi";
                    else // deviceDensityDpi >= 480 + (640 - 480)/2
                        deviceDensityDpiStr = "xxxhdpi";

                    location = location.substring(0, posStart) + "-" + deviceDensityDpiStr + location.substring(posEnd + 1);
                }
                else
                {
                    String densityDpiStr = location.substring(posStart + prefix.length(), posEnd);
                    int densityDpi;
                    if ("ldpi".equals(densityDpiStr)) densityDpi = 120; // low-density
                    else if ("mdpi".equals(densityDpiStr)) densityDpi = 160; // medium-density
                    else if ("hdpi".equals(densityDpiStr)) densityDpi = 240; // high-density
                    else if ("xhdpi".equals(densityDpiStr)) densityDpi = 320; // extra-high-density
                    else if ("xxhdpi".equals(densityDpiStr)) densityDpi = 480; // extra-extra-high-density
                    else if ("xxxhdpi".equals(densityDpiStr)) densityDpi = 640; // extra-extra-extra-high-density
                    else if ("nodpi".equals(densityDpiStr)) densityDpi = 1; // all densities. Por poner algo (no se que poner)
                    else if ("tvdpi".equals(densityDpiStr)) densityDpi = 213; // screens somewhere between mdpi and hdpi
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + densityDpiStr);

                    if (deviceDensityDpi >= densityDpi)
                    {
                        location = location.substring(0, posStart) + "-" + densityDpiStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }


        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo Touchscreen type (touchscreen)
            // Ej {tst-finger}
            String prefix = "{tst-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceTouchscreen = configuration.touchscreen;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceTouchscreenStr;
                    switch(deviceTouchscreen)
                    {
                        case Configuration.TOUCHSCREEN_NOTOUCH: deviceTouchscreenStr = "notouch"; break;
                        case Configuration.TOUCHSCREEN_FINGER: deviceTouchscreenStr = "finger"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported Touchscreen type value: " + deviceTouchscreen);
                    }

                    location = location.substring(0, posStart) + "-" + deviceTouchscreenStr + location.substring(posEnd + 1);
                }
                else
                {
                    String touchscreenStr = location.substring(posStart + prefix.length(), posEnd);
                    int touchscreen;
                    if ("notouch".equals(touchscreenStr)) touchscreen = Configuration.TOUCHSCREEN_NOTOUCH;
                    else if ("finger".equals(touchscreenStr)) touchscreen = Configuration.TOUCHSCREEN_FINGER;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + touchscreenStr);  // No está documentado el literal de "stylus"

                    if (deviceTouchscreen == touchscreen)
                    {
                        location = location.substring(0, posStart) + "-" + touchscreenStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        {
            // No soportamos el sufijo sufijo Keyboard availability, no lo entiendo bien (como implementarlo) y no aporta gran cosa hoy día
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo Primary text input method (keyboard)
            // Ej {ptim-qwerty}
            String prefix = "{ptim-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceKeyboard = configuration.keyboard;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceKeyboardStr;
                    switch(deviceKeyboard)
                    {
                        case Configuration.KEYBOARD_NOKEYS: deviceKeyboardStr = "nokeys"; break;
                        case Configuration.KEYBOARD_QWERTY: deviceKeyboardStr = "qwerty"; break;
                        case Configuration.KEYBOARD_12KEY: deviceKeyboardStr = "12key"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported Primary text input method value: " + deviceKeyboard);
                    }

                    location = location.substring(0, posStart) + "-" + deviceKeyboardStr + location.substring(posEnd + 1);
                }
                else
                {
                    String keyboardStr = location.substring(posStart + prefix.length(), posEnd);
                    int keyboard;
                    if ("nokeys".equals(keyboardStr)) keyboard = Configuration.KEYBOARD_NOKEYS;
                    else if ("qwerty".equals(keyboardStr)) keyboard = Configuration.KEYBOARD_QWERTY;
                    else if ("12key".equals(keyboardStr)) keyboard = Configuration.KEYBOARD_12KEY;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + keyboardStr);

                    if (deviceKeyboard == keyboard)
                    {
                        location = location.substring(0, posStart) + "-" + keyboardStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        {
            // No soportamos Navigation key availability (navigationHidden) porque parece que es algo obsoleto en pantallas táctiles, pero lo más importante
            // es que no está implementado al menos en los emuladores desde Level 15, como si estuviera deprecated aunque no lo pone en ningún sitio o no aplica en disp táctiles,
            // pues cuando añades -navexposed o -navhidden al folder de turno en ambos casos los recursos allí son ignorados.
            // http://developer.android.com/reference/android/content/res/Configuration.html#navigationHidden
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo Primary non-touch navigation method (navigation)
            // Ej {pntn-nonav}
            String prefix = "{pntn-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceNavigation = configuration.navigation;
                if (posEnd == posStart + prefix.length())
                {
                    String deviceNavigationStr;
                    switch(deviceNavigation)
                    {
                        case Configuration.NAVIGATION_NONAV: deviceNavigationStr = "nonav"; break;
                        case Configuration.NAVIGATION_DPAD: deviceNavigationStr = "dpad"; break;
                        case Configuration.NAVIGATION_TRACKBALL: deviceNavigationStr = "trackball"; break;
                        case Configuration.NAVIGATION_WHEEL: deviceNavigationStr = "wheel"; break;
                        default: throw new ItsNatDroidException("Unexpected or unsupported Primary non-touch navigation method value: " + deviceNavigation);
                    }

                    location = location.substring(0, posStart) + "-" + deviceNavigationStr + location.substring(posEnd + 1);
                }
                else
                {
                    String navigationStr  = location.substring(posStart + prefix.length(), posEnd);
                    int navigation;
                    if      ("nonav".equals(navigationStr)) navigation = Configuration.NAVIGATION_NONAV;
                    else if ("dpad".equals(navigationStr)) navigation = Configuration.NAVIGATION_DPAD;
                    else if ("trackball".equals(navigationStr)) navigation = Configuration.NAVIGATION_TRACKBALL;
                    else if ("wheel".equals(navigationStr)) navigation = Configuration.NAVIGATION_WHEEL;
                    else throw new ItsNatDroidException("Unexpected or unsupported prefix: " + navigationStr);

                    if (deviceNavigation == navigation)
                    {
                        location = location.substring(0, posStart) + "-" + navigationStr + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }
                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        if (location.indexOf("{",posToSearchMore) == -1) // Todos los filtros empiezan de la misma manera, evitamos así buscar a lo tonto
            return location;

        {
            // Soportamos la existencia de sufijo de versión de la plataforma
            // Ej {v-v21}
            String prefix = "{v-";
            int posStart = location.indexOf(prefix,posToSearchMore);
            if (posStart != -1)
            {
                int posEnd = location.indexOf(suffix, posStart);
                if (posEnd == -1) throw new ItsNatDroidException("Unfinished prefix: " + prefix);

                int deviceSDKVersion = Build.VERSION.SDK_INT;
                if (posEnd == posStart + prefix.length())
                {
                    location = location.substring(0, posStart) + "-v" + deviceSDKVersion + location.substring(posEnd + 1);
                }
                else
                {
                    String versionStr = location.substring(posStart + prefix.length() + 1 /* el +1 es para quitar el v */, posEnd);
                    int version;
                    try { version = Integer.parseInt(versionStr); }
                    catch (Exception ex) { throw new ItsNatDroidException("Bad platform version suffix: " + versionStr); }

                    if (deviceSDKVersion >= version)
                    {
                        location = location.substring(0, posStart) + "-v" + version + location.substring(posEnd + 1);
                    }
                    else
                    {
                        location = location.substring(0, posStart) + location.substring(posEnd + 1); // Quitamos el sufijo pues no se usa
                    }
                }

                posToSearchMore = posStart; // recuerda que se ha cambiado la cadena
            }
        }

        return location;
    }

}
