package org.itsnat.droid.impl.util;

import org.itsnat.droid.ItsNatDroidException;

import java.io.UnsupportedEncodingException;

/**
 * Created by jmarranz on 20/02/2016.
 */
public class StringUtil
{
    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    public static String toString(byte[] data,String encoding)
    {
        try { return new String(data,encoding); }
        catch (UnsupportedEncodingException ex) { throw new ItsNatDroidException(ex); }
    }

    public static boolean equalsEmptyAllowed(String value1, String value2)
    {
        if (isEmpty(value1))  // null y "" son iguales en este caso
            return isEmpty(value2);
        else
            return value1.equals(value2);
    }

    public static boolean isTag(String tag)
    {
        // Debe estar tag trimed antes de llamar, no se consideran los espacios
        boolean isTag = true;
        for (int i = 0; i < tag.length(); i++)
        {
            char c = tag.charAt(i);
            if (i == 0 && Character.toLowerCase(c) == 'h' && tag.length() == 2)
            {
                char c2 = tag.charAt(1);
                if (c2 >= '1' && c2 <= '6')
                    return true;
            }

            if (!Character.isLetter(c))
            {
                isTag = false;
                break;
            }
        }
        return isTag;
    }

    public static String convertEscapedStringLiteralToNormalString(String code)
    {
        // Hemos extraido la cadena del código fuente beanshell (formada con "" esto no es JavaScript), para poder poner una cadena literal necesitamos poner
        // los LF como \n y las " como \", tenemos que deshacer eso o de otra manera NO tenemos el texto original de verdad.
        // En el caso de los nombres de las variables, namespaces etc con extractStringLiteralContent es suficiente
        // Ver código de ItsNat Server: JSAndBSRenderImpl::toTransportableStringLiteral(String text,boolean addQuotation,Browser browser)
        // '\r' '\n' '"' '\'' '\\'  '\t' '\f' '\b'
        // El caracter '"' está presente como \" en una string delimitada con "
        // El caracter '\'' está presente como ' en una string delimitada con "

        // Se usa también en XMLInflateRegistry.getString() y getText() porque en XML se pueden poner \n, \t etc y el compilador de Android los procesa y los deja como caracteres normales


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
