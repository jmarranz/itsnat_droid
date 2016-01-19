package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jmarranz on 19/01/2016.
 */
public abstract class XMLDOMLayoutPage extends XMLDOMLayout
{
    protected ArrayList<DOMScript> scriptList;

    public ArrayList<DOMScript> getDOMScriptList()
    {
        return scriptList;
    }

    public void addDOMScript(DOMScript script)
    {
        if (scriptList == null) this.scriptList = new ArrayList<DOMScript>();
        scriptList.add(script);
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayoutPage)cloned).scriptList = this.scriptList;
    }

    public String extractAttrNamespaceURI(String namespaceURI, String name)
    {
        if (namespaceURI != null) return namespaceURI; // De ItsNat server el script nunca contiene un android:localname (localname a secas) si el namespaceURI ha sido especificado explícitamente

        // El namespace puede estar en el name como prefijo por ejemplo "android:"
        String prefix = NamespaceUtil.getPrefix(name);
        if (prefix == null)
            return null;

        return getRootNamespaceByPrefix(prefix); // Puede ser null, no encontrado
    }

    public String extractAttrLocalName(String namespaceURI, String name)
    {
        if (namespaceURI != null) return name; // De ItsNat server el script nunca contiene un android:localname (localname a secas) si el namespaceURI ha sido especificado explícitamente

        // El namespace puede estar en el name como prefijo por ejemplo "android:"
        String prefix = NamespaceUtil.getPrefix(name);
        if (prefix == null)
            return name;

        namespaceURI = getRootNamespaceByPrefix(prefix);
        if (namespaceURI != null) // Sólo se soportan namespaces declarados en el View root, si es null se procesará lo consideramos como un atributo desconocido y conservamos el prefijo
            return NamespaceUtil.getLocalName(name);

        return name;
    }

    public DOMAttr toDOMAttrNotSyncResource(String namespaceURI,String name,String value)
    {
        String namespaceURIFinal = extractAttrNamespaceURI(namespaceURI, name);
        String localName = extractAttrLocalName(namespaceURI, name);
        DOMAttr attr = DOMAttr.create(namespaceURIFinal, localName, value);
        return attr;
    }

    public LinkedList<DOMAttrRemote> parseBSRemoteAttribs(String code)
    {
        LinkedList<DOMAttrRemote> attrRemoteList = null;

        // Caso 1: setAttributeNS y setAttribute
        //      Ej. de formato esperado: /*{*/NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"/*}*/
        //                               /*{*/"style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"/*}*/

        // Caso 2: setAttrBatch
        // Ej. de formato esperado /*[n*/null/*n]*/  /*[k*/"style"/*k]*/...  /*[v*/"@remote:style/droid/res/values/test_values_remote.xml:test_style_remote"/*v]*/...
        // En vez de null (namespace) puede ser NSAND o "some namespace" o null

        boolean searchMoreCase1 = true; // Sirve para evitar búsquedas inútiles
        boolean searchMoreCase2 = true; // Sirve para evitar búsquedas inútiles
        while(true)
        {
            // Vemos que caso está primero

            int posOpenCase1 = -1;
            int posOpenCase2 = -1;

            if (searchMoreCase1) posOpenCase1 = code.indexOf("/*{*/");
            if (searchMoreCase2) posOpenCase2 = code.indexOf("/*[n*/");

            if ( (posOpenCase1 != -1 && posOpenCase2 != -1 && posOpenCase1 < posOpenCase2) || (posOpenCase1 != -1 && posOpenCase2 == -1) )
            {
                // Caso 1

                if (attrRemoteList == null) attrRemoteList = new LinkedList<DOMAttrRemote>();
                code = processCase1(code,posOpenCase1,attrRemoteList);

            }
            else if ( (posOpenCase1 != -1 && posOpenCase2 != -1 && posOpenCase1 > posOpenCase2) || (posOpenCase1 == -1 && posOpenCase2 != -1) )
            {
                // Caso 2

                if (attrRemoteList == null) attrRemoteList = new LinkedList<DOMAttrRemote>();
                code = processCase2(code,posOpenCase2,attrRemoteList);
            }
            else
            {
                // NO hay más
                break;
            }

            if (searchMoreCase1 && posOpenCase1 == -1) searchMoreCase1 = false; // No hay más de case 1
            if (searchMoreCase2 && posOpenCase2 == -1) searchMoreCase2 = false; // No hay más de case 2
        }

        return attrRemoteList;
    }


    private String processCase1(String code,int posOpen,LinkedList<DOMAttrRemote> attrRemoteList)
    {
        // Ej. de formato esperado: /*{*/NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"/*}*/
        //                          /*{*/"style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"/*}*/

        int lenDelimiter = "/*{*/".length(); // idem que el sufijo "/*}*/"

        int posEnd = code.indexOf("/*}*/", posOpen + lenDelimiter);
        if (posEnd != -1)
        {
            String attrCode = code.substring(posOpen + lenDelimiter, posEnd);

            DOMAttrRemote domAttr = parseSingleRemoteAttr(attrCode);

            attrRemoteList.add(domAttr);

            code = code.substring(posEnd + lenDelimiter);
        }
        else
        {
            throw new ItsNatDroidException("Unexpected format " + posOpen); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final, salimos, está
        }

        return code;
    }

    private String processCase2(String code,int posOpenNS,LinkedList<DOMAttrRemote> attrRemoteList)
    {
        // Ej. de formato esperado /*[n*/null/*n]*/  /*[k*/"style"/*k]*/...  /*[v*/"@remote:style/droid/res/values/test_values_remote.xml:test_style_remote"/*v]*/...
        // En vez de null (namespace) puede ser NSAND o "some namespace" o null

        int lenDelimiter = "/*[n*/".length(); // idem que el sufijo "/*n]*/" e idem con k y v


        String namespaceURI;

        {
            int posEnd = code.indexOf("/*n]*/", posOpenNS + lenDelimiter);
            if (posEnd != -1)
            {
                String namespaceCode = code.substring(posOpenNS + lenDelimiter, posEnd);
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

    private DOMAttrRemote parseSingleRemoteAttr(String code)
    {
        // Ej. de formato esperado:
        //      NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"
        //      "android:textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"
        //      "style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"
        // En lugar de NSAND puede ser un "some namespace" o null

        String[] attrParts = code.split(",");
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
        value = extractStringLiteralContent(value);

        return createDOMAttrRemote(namespaceURI,name,value);
    }

    private DOMAttrRemote createDOMAttrRemote(String namespaceURI,String name,String value)
    {
        return (DOMAttrRemote)toDOMAttrNotSyncResource(namespaceURI, name, value);
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

}
