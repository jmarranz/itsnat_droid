package org.itsnat.droid.impl.browser.serveritsnat;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.XMLDOMDownloader;
import org.itsnat.droid.impl.browser.XMLDOMLayoutPageDownloader;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMLayoutPageItsNatDownloader extends XMLDOMLayoutPageDownloader
{
    public XMLDOMLayoutPageItsNatDownloader(XMLDOMLayoutPageItsNat xmlDOM)
    {
        super(xmlDOM);
    }

    public XMLDOMLayoutPageItsNat getXMLDOMLayoutPageItsNat()
    {
        return (XMLDOMLayoutPageItsNat)xmlDOM;
    }

    public LinkedList<DOMAttrRemote> parseBeanShellAndDownloadRemoteResources(String code,String itsNatServerVersion,String pageURLBase,
                                                                                     HttpRequestData httpRequestData,XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        @SuppressWarnings("unchecked") LinkedList<DOMAttrRemote>[] attrRemoteListBSParsed = new LinkedList[1];
        @SuppressWarnings("unchecked") LinkedList<String>[] classNameListBSParsed = new LinkedList[1];
        @SuppressWarnings("unchecked") LinkedList<String>[] xmlMarkupListBSParsed = new LinkedList[1];

        XMLDOMLayoutPageItsNat xmlDOMLayoutPageItsNat = getXMLDOMLayoutPageItsNat();

        parseBSRemoteAttribs(code, attrRemoteListBSParsed, classNameListBSParsed, xmlMarkupListBSParsed);

        if (attrRemoteListBSParsed[0] != null)
        {
            // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde
            downloadResources(attrRemoteListBSParsed[0], pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        }

        if (classNameListBSParsed[0] != null)
        {
            XMLDOMLayoutPage[] xmlDOMLayoutPageArr = wrapAndParseMarkupFragment(classNameListBSParsed[0], xmlMarkupListBSParsed[0], itsNatServerVersion, xmlDOMRegistry, assetManager);
            for (XMLDOMLayoutPage xmlDOM : xmlDOMLayoutPageArr)
            {
                XMLDOMLayoutPageDownloader downloader = (XMLDOMLayoutPageDownloader) XMLDOMDownloader.createXMLDOMDownloader(xmlDOM);
                downloader.downloadRemoteResources(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
            }
        }

        return attrRemoteListBSParsed[0]; // Puede ser null
    }

    private XMLDOMLayoutPage[] wrapAndParseMarkupFragment(LinkedList<String> classNameListBSParsed, LinkedList<String> xmlMarkupListBSParsed, String itsNatServerVersion,
                                                                 XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {

        XMLDOMLayoutPageItsNat xmlDOMLayoutPageItsNatParent = getXMLDOMLayoutPageItsNat();

        XMLDOMLayoutPage[] xmlDOMLayoutPageFragmentArr = new XMLDOMLayoutPage[classNameListBSParsed.size()];

        if (classNameListBSParsed.size() != xmlMarkupListBSParsed.size()) throw new ItsNatDroidException("Internal Error");
        // Así se cachea pero sobre to_do se cargan los recursos remotos dentro del markup que trae el setInnerXML()
        Iterator<String> itClassName = classNameListBSParsed.iterator();
        Iterator<String> itMarkup = xmlMarkupListBSParsed.iterator();
        int i = 0;
        while(itClassName.hasNext())
        {
            String className = itClassName.next();
            String markup = itMarkup.next();
            markup = wrapMarkupFragment(className, markup, xmlDOMLayoutPageItsNatParent);
            XMLDOMLayoutPage xmlDOMFragment = parseMarkupFragment(markup, itsNatServerVersion, xmlDOMRegistry, assetManager);
            xmlDOMLayoutPageFragmentArr[i] = xmlDOMFragment;
        }
        return xmlDOMLayoutPageFragmentArr;
    }

    public static String wrapMarkupFragment(String parentClassName, String markup, XMLDOMLayoutPage xmlDOMLayoutPageParent)
    {
        // Preparamos primero el markup añadiendo un false parentView que luego quitamos, el false parentView es necesario
        // para declarar el namespace android, el false parentView será del mismo tipo que el de verdad para que los
        // LayoutParams se hagan bien.

        StringBuilder newMarkup = new StringBuilder();

        newMarkup.append("<" + parentClassName);
        newMarkup.append(" xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        MapLight<String, String> namespaceMap = xmlDOMLayoutPageParent.getRootNamespacesByPrefix();
        for (Map.Entry<String, String> entry : namespaceMap.getEntryList())
        {
            newMarkup.append(" xmlns:" + entry.getKey() + "=\"" + entry.getValue() + "\"");
        }
        newMarkup.append(">");
        newMarkup.append(markup);
        newMarkup.append("</" + parentClassName + ">");

        markup = newMarkup.toString();
        return markup;
    }

    public static XMLDOMLayoutPage parseMarkupFragment(String markup, String itsNatServerVersion, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        XMLDOMLayoutPage xmlDOMLayout = (XMLDOMLayoutPage) xmlDOMRegistry.getXMLDOMLayoutCache(markup,itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE_FRAGMENT, assetManager);
        return xmlDOMLayout;
    }


    private void parseBSRemoteAttribs(String code,LinkedList<DOMAttrRemote>[] attrRemoteList,LinkedList<String>[] classNameList,LinkedList<String>[] xmlMarkupList)
    {
        // Caso 1: setAttributeNS y setAttribute
        //      Ej. de formato esperado: /*[s*/NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"/*s]*/
        //                               /*[s*/"style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"/*s]*/

        // Caso 2: setAttrBatch
        // Ej. de formato esperado /*[n*/null/*n]*/  /*[k*/"style"/*k]*/...  /*[v*/"@remote:style/droid/res/values/test_values_remote.xml:test_style_remote"/*v]*/...
        // En vez de null (namespace) puede ser NSAND o "some namespace" o null

        // Caso 3: setInnerXML
        // Ej. de formato esperado /*[i*/"className","xml serialized"/*i]*/


        while(true)
        {
            code = gotoNextCase(code);

            if (code == null)
                break; // No hay más

            if (code.startsWith("/*[s*/"))
            {
                if (attrRemoteList[0] == null) attrRemoteList[0] = new LinkedList<DOMAttrRemote>();
                code = processCase1(code,attrRemoteList[0]);
            }
            else if (code.startsWith("/*[n*/"))
            {
                if (attrRemoteList[0] == null) attrRemoteList[0] = new LinkedList<DOMAttrRemote>();
                code = processCase2(code, attrRemoteList[0]);
            }
            else if (code.startsWith("/*[i*/"))
            {
                if (classNameList[0] == null) classNameList[0] = new LinkedList<String>();
                if (xmlMarkupList[0] == null) xmlMarkupList[0] = new LinkedList<String>();

                code = processCase3(code,classNameList[0], xmlMarkupList[0]);
            }
        }
    }

    private String gotoNextCase(String code)
    {
        int lenDelimiter = "/*[s*/".length(); // Mismo valor para todos los demás casos incluidos los sufijos

        while(true)
        {
            int posPrefix = code.indexOf("/*[");
            int len = code.length() - posPrefix;
            if (len < lenDelimiter) // Falso positivo, no hay suficientes caracteres ni siquiera para analizar el prefijo
                return null; // NO hay más

            // Tenemos la garantía de que hay caracteres suficientes para analizar el prefijo

            int pos = posPrefix + "/*[".length();
            char letter = code.charAt(pos);
            pos++;
            char asterisk = code.charAt(pos);
            pos++;
            char close = code.charAt(pos);

            if ((letter != 's' && letter != 'n' && letter != 'i') || asterisk != '*' || close != '/')
            {
                // No es un prefijo correcto
                if (pos == code.length() - 1)
                    return null; // Es el último caracter, no hay más
                code = code.substring(pos + 1);
                continue;
            }

            return code.substring(posPrefix);
        }
    }


    private String processCase1(String code,LinkedList<DOMAttrRemote> attrRemoteList)
    {
        // Ej. de formato esperado: /*[s*/NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"/*s]*/
        //                          /*[s*/"style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"/*s]*/

        int lenDelimiter = "/*[s*/".length(); // idem que el sufijo "/*s]*/"

        int posEnd = code.indexOf("/*s]*/", lenDelimiter);
        if (posEnd != -1)
        {
            String attrCode = code.substring(lenDelimiter, posEnd);

            DOMAttrRemote domAttr = parseSingleRemoteAttr(attrCode);

            attrRemoteList.add(domAttr);

            code = code.substring(posEnd + lenDelimiter);
        }
        else
        {
            throw new ItsNatDroidException("Unexpected format " + code); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final, salimos, está
        }

        return code;
    }

    private String processCase2(String code,LinkedList<DOMAttrRemote> attrRemoteList)
    {
        // Ej. de formato esperado /*[n*/null/*n]*/  /*[k*/"style"/*k]*/...  /*[v*/"@remote:style/droid/res/values/test_values_remote.xml:test_style_remote"/*v]*/...
        // En vez de null (namespace) puede ser NSAND o "some namespace" o null

        int lenDelimiter = "/*[n*/".length(); // idem que el sufijo "/*n]*/" e idem con k y v


        String namespaceURI;

        {
            int posEnd = code.indexOf("/*n]*/", lenDelimiter);
            if (posEnd != -1)
            {
                String namespaceCode = code.substring(lenDelimiter, posEnd);
                namespaceURI = parseNamespaceURI(namespaceCode);

                code = code.substring(posEnd + lenDelimiter);
            }
            else
            {
                throw new ItsNatDroidException("Unexpected format " + code); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
            }
        }

        // Atributos que tienen en común el namespace anteriormente obtenido

        int posFirstValue = code.indexOf("/*[v*/"); // No debemos buscar más allá de este punto porque estaremos encontrando los key de otra sentencia
        if (posFirstValue == -1) throw new ItsNatDroidException("Unexpected format " + code);

        ArrayList<String> nameList = null;
        while (true)
        {
            // name
            {
                int posOpenKey = code.indexOf("/*[k*/");
                if (posOpenKey != -1)
                {
                    if (posOpenKey < posFirstValue)
                    {
                        int posEnd = code.indexOf("/*k]*/", posOpenKey + lenDelimiter);
                        if (posEnd != -1)
                        {
                            String nameCode = code.substring(posOpenKey + lenDelimiter, posEnd);
                            String name = parseAttrName(nameCode);

                            if (nameList == null) nameList = new ArrayList<String>();
                            nameList.add(name);

                            code = code.substring(posEnd + lenDelimiter);
                        }
                        else
                        {
                            throw new ItsNatDroidException("Unexpected format " + code); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
                        }
                    }
                    else
                    {
                        break; // No hay más
                    }
                }
                else
                {
                    break; // No hay más
                }
            }
        }

        if (nameList == null) throw new ItsNatDroidException("Unexpected format " + code); // Si se encontró un metadato namespace es porque esperamos AL MENOS un atributo remoto

        int countExpected = nameList.size();
        ArrayList<String> valueList = new ArrayList<String>(countExpected);
        for(int i = 0; i < countExpected; i++)
        {
            // value
            {
                int posOpenValue = code.indexOf("/*[v*/");
                if (posOpenValue != -1)
                {
                    int posEnd = code.indexOf("/*v]*/", posOpenValue + lenDelimiter);
                    if (posEnd != -1)
                    {
                        String valueCode = code.substring(posOpenValue + lenDelimiter, posEnd);
                        String value = extractStringLiteralContent(valueCode);

                        if (valueList == null) valueList = new ArrayList<String>();
                        valueList.add(value);

                        code = code.substring(posEnd + lenDelimiter);
                    }
                    else
                    {
                        throw new ItsNatDroidException("Unexpected format " + code); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
                    }
                }
                else
                {
                    throw new ItsNatDroidException("Unexpected format " + code); // Esperamos un número dado de values que conocemos de antemano
                }
            }
        }

        if (nameList.size() != valueList.size()) throw new ItsNatDroidException("Unexpected format " + code);

        int count = nameList.size();
        for(int i = 0; i < count; i++)
        {
            String name = nameList.get(i);
            String value = valueList.get(i);
            DOMAttrRemote domAttr = createDOMAttrRemote(namespaceURI, name, value);

            attrRemoteList.add(domAttr);
        }

        return code;
    }

    private String processCase3(String code,LinkedList<String> classNameList,LinkedList<String> xmlMarkupList)
    {
        // Caso 3: setInnerXML
        // Ej. de formato esperado /*[i*/"className","xml serialized"/*i]*/

        int lenDelimiter = "/*[i*/".length(); // idem que el sufijo "/*i]*/"

        int posEnd = code.indexOf("/*i]*/", lenDelimiter);
        if (posEnd != -1)
        {
            String classNameCommAndxmlMarkupStrLiteral = code.substring(lenDelimiter, posEnd);
            // No usamos split(",") porque el markup puede contener comas (puede haber de to_do excepto los caracteres escapados necesarios para meter en "")
            // Lo seguro es que el className no tiene comas
            int posComma = classNameCommAndxmlMarkupStrLiteral.indexOf(',');
            if (posComma == -1)
                throw new ItsNatDroidException("Unexpected format " + classNameCommAndxmlMarkupStrLiteral);
            String classNameStrLiteral = classNameCommAndxmlMarkupStrLiteral.substring(0,posComma);
            String className = extractStringLiteralContent(classNameStrLiteral);
            classNameList.add(className);

            String xmlMarkupStrLiteral = classNameCommAndxmlMarkupStrLiteral.substring(posComma + 1);
            String xmlMarkup = extractStringLiteralContent(xmlMarkupStrLiteral);
            xmlMarkup = convertCodeStringLiteralToNormalString(xmlMarkup);
            xmlMarkupList.add(xmlMarkup);

            code = code.substring(posEnd + lenDelimiter);
        }
        else
        {
            throw new ItsNatDroidException("Unexpected format " + code); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final, salimos, está
        }

        return code;
    }

    private DOMAttrRemote parseSingleRemoteAttr(String code)
    {
        // Ej. de formato esperado:
        //      NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"
        //      "android:textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"
        //      "style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"
        // En lugar de NSAND puede ser un "some namespace" o null

        String[] attrParts = code.split(","); // No hay riesgo de que el namespace o el name o el value tengan alguna coma
        if (attrParts.length < 2 || attrParts.length > 3) throw new ItsNatDroidException("Unexpected format: " + code);

        int part = 0;
        String namespaceURI = null;
        if (attrParts.length == 3)
        {
            namespaceURI = parseNamespaceURI(attrParts[part]);
            part++;
        }

        String name = attrParts[part];
        name = parseAttrName(name);
        part++;

        String value = attrParts[part];
        value = extractStringLiteralContent(value); // Aunque es un valor entre comillas, esperamos un "@remote:path:name", no esperamos un texto normal literal que podría tener " o \n al serializar como string literal

        return createDOMAttrRemote(namespaceURI,name,value);
    }

    private DOMAttrRemote createDOMAttrRemote(String namespaceURI,String name,String value)
    {
        XMLDOMLayoutPageItsNat xmlDOMLayoutPageItsNat = getXMLDOMLayoutPageItsNat();

        return (DOMAttrRemote)xmlDOMLayoutPageItsNat.toDOMAttrNotSyncResource(namespaceURI, name, value);
    }

    private static String parseNamespaceURI(String code)
    {
        String namespaceURI = code;
        if (NamespaceUtil.XMLNS_ANDROID_ALIAS.equals(namespaceURI)) // la constante NSAND
            namespaceURI = NamespaceUtil.XMLNS_ANDROID;
        else if ("null".equals(namespaceURI))
            namespaceURI = null;
        else
            namespaceURI = extractStringLiteralContent(namespaceURI);

        return namespaceURI;
    }

    private static String parseAttrName(String code)
    {
        String name = extractStringLiteralContent(code);
        return name;
    }

    private static String extractStringLiteralContent(String code)
    {
        if (!code.startsWith("\"")) throw new ItsNatDroidException("Unexpected format: " + code);
        if (!code.endsWith("\"")) throw new ItsNatDroidException("Unexpected format: " + code);
        code = code.substring(1,code.length()-1);
        return code;
    }

    private static String convertCodeStringLiteralToNormalString(String code)
    {
        // Hemos extraido la cadena del código fuente beanshell (formada con "" esto no es JavaScript), para poder poner una cadena literal necesitamos poner
        // los LF como \n y las " como \", tenemos que deshacer eso o de otra manera NO tenemos el texto original de verdad.
        // En el caso de los nombres de las variables, namespaces etc con extractStringLiteralContent es suficiente
        // Ver código de ItsNat Server: JSAndBSRenderImpl::toTransportableStringLiteral(String text,boolean addQuotation,Browser browser)
        // '\r' '\n' '"' '\'' '\\'  '\t' '\f' '\b'
        // El caracter '"' está presente como \" en una string delimitada con "
        // El caracter '\'' está presente como ' en una string delimitada con "


        StringBuilder codeRes = new StringBuilder();

        int start = 0;
        while(true)
        {
            int pos = code.indexOf('\\', start);
            if (pos != -1)
            {
                String frag = code.substring(start,pos);
                codeRes.append(frag);

                char c = code.charAt(pos); // '\\' si o si
                pos++;
                if (pos == code.length()) break; // No hay un siguiente caracter

                char c2 = code.charAt(pos);
                switch (c2)
                {
                    case 'r':
                        codeRes.append('\r');
                        break;
                    case 'n':
                        codeRes.append('\n');
                        break;
                    case '"':
                        codeRes.append('\"');
                        break;
                    // No hacemos nada con '\'' pues no se necesita (el servidor cuando se pone entre "" no hace nada con este caracter
                    case '\\':
                        codeRes.append('\\');
                        break;
                    case 't':
                        codeRes.append('\t');
                        break;
                    case 'f':
                        codeRes.append('\f');
                        break;
                    case 'b':
                        codeRes.append('\b');
                        break;
                    default:
                        codeRes.append(c);
                        codeRes.append(c2);
                }

                pos++;

                start = pos;
            }
            else
            {
                String frag = code.substring(start);
                codeRes.append(frag);
                break;
            }
        }

        return codeRes.toString();
    }
}
