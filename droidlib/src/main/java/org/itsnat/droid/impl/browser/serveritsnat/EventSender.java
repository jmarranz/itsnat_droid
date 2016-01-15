package org.itsnat.droid.impl.browser.serveritsnat;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.browser.HttpResourceDownloader;
import org.itsnat.droid.impl.browser.HttpUtil;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.NameValue;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 10/07/14.
 */
public class EventSender
{
    protected EventManager evtManager;

    public EventSender(EventManager evtManager)
    {
        this.evtManager = evtManager;
    }

    public EventManager getEventManager()
    {
        return evtManager;
    }

    public ItsNatDocItsNatImpl getItsNatDocItsNatImpl()
    {
        return evtManager.getItsNatDocItsNatImpl();
    }

    public void requestSync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();
        PageImpl page = itsNatDoc.getPageImpl();

        HttpRequestData httpRequestData = new HttpRequestData(page);
        int timeoutInt = (int) timeout;
        if (timeoutInt < 0)
            timeoutInt = Integer.MAX_VALUE;
        httpRequestData.setReadTimeout(timeoutInt);

        HttpRequestResultOKImpl result = null;
        try
        {
            result = executeInBackground(this, servletPath, httpRequestData, paramList);
        }
        catch (Exception ex)
        {
            int errorMode = getItsNatDocItsNatImpl().getClientErrorMode();
            onFinishError(this, ex, evt, errorMode);

            return; // No podemos continuar
        }

        onFinishOk(this, evt, result);
    }

    public void requestAsync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        HttpPostEventAsyncTask task = new HttpPostEventAsyncTask(this, evt, servletPath, paramList, timeout);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static HttpRequestResultOKImpl executeInBackground(EventSender eventSender, String servletPath, HttpRequestData httpRequestData, List<NameValue> paramList) throws Exception
    {
        // Ejecutado en multihilo en el caso async
        HttpRequestResultOKImpl result = HttpUtil.httpPost(servletPath, httpRequestData, paramList, null);

        if (MimeUtil.MIME_BEANSHELL.equals(result.getMimeType())) // No es necesario pero por si acaso
        {
            List<DOMAttrRemote> attrRemoteList = parseRemoteAttribs(result.getResponseText());

            if (attrRemoteList != null)
            {
                ItsNatDocItsNatImpl itsNatDocItsNat = eventSender.getItsNatDocItsNatImpl();
                String pageURLBase = itsNatDocItsNat.getPageURLBase();
                PageItsNatImpl pageItsNat = itsNatDocItsNat.getPageItsNatImpl();
                XMLDOMRegistry xmlDOMRegistry = pageItsNat.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
                AssetManager assetManager = pageItsNat.getContext().getResources().getAssets();
                HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
                resDownloader.downloadResources(attrRemoteList);
            }
        }

        return result;
    }

    public static void onFinishOk(EventSender eventSender, EventGenericImpl evt, HttpRequestResultOKImpl result)
    {
        try
        {
            eventSender.processResult(evt, result, true);
        }
        catch (Exception ex)
        {
            PageImpl page = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl();
            OnEventErrorListener errorListener = page.getOnEventErrorListener();
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException) ex).getHttpRequestResult() : result;
                errorListener.onError(page, evt, ex, resultError);
            } else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException) ex;
                else throw new ItsNatDroidException(ex);
            }
        }
    }


    public static void onFinishError(EventSender eventSender, Exception ex, EventGenericImpl evt, int errorMode)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException) ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = eventSender.convertExceptionAndFireEventMonitors(evt, ex);

        PageImpl page = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl();
        OnEventErrorListener errorListener = page.getOnEventErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(page, evt, exFinal, result);
        } else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocItsNatImpl itsNatDoc = eventSender.getItsNatDocItsNatImpl();
                itsNatDoc.showErrorMessage(true, result, exFinal, errorMode);
            } else throw exFinal;
        }

    }

    private static List<DOMAttrRemote> parseRemoteAttribs(String code)
    {
        ArrayList<DOMAttrRemote> attrRemoteList = null;

        // Caso 1: setAttributeNS y setAttribute
        {
            String codeCase1 = code;

            // Ej. de formato esperado: /*{*/NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"/*}*/
            int lenDelimiter = "/*{*/".length(); // idem que el sufijo "/*}*/"

            while (true)
            {
                int posOpen = codeCase1.indexOf("/*{*/");
                if (posOpen != -1)
                {
                    int posEnd = codeCase1.indexOf("/*}*/", posOpen + lenDelimiter);
                    if (posEnd != -1)
                    {
                        String attrCode = codeCase1.substring(posOpen + lenDelimiter, posEnd);

                        DOMAttrRemote domAttr = parseSingleRemoteAttr(attrCode);
                        if (attrRemoteList == null) attrRemoteList = new ArrayList<DOMAttrRemote>();
                        attrRemoteList.add(domAttr);

                        codeCase1 = codeCase1.substring(posEnd + lenDelimiter);
                    }
                    else
                    {
                        break; // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
                    }
                }
                else
                {
                    break; // No hay más
                }
            }
        }

        // Caso 2 setAttrBatch
        {
            String codeCase2 = code;

            // Ej. de formato esperado /*[n*/null/*n]*/  /*[k*/"style"/*k]*/...  /*[v*/"@remote:style/droid/res/values/test_values_remote.xml:test_style_remote"/*v]*/...
            // En vez de null (namespace) puede ser NSAND o "some namespace" o null

            int lenDelimiter = "/*[n*/".length(); // idem que el sufijo "/*n]*/" e idem con k y v

            while (true)
            {
                String namespaceURI;

                {
                    int posOpen = codeCase2.indexOf("/*[n*/");
                    if (posOpen != -1)
                    {
                        int posEnd = codeCase2.indexOf("/*n]*/", posOpen + lenDelimiter);
                        if (posEnd != -1)
                        {
                            String namespaceCode = codeCase2.substring(posOpen + lenDelimiter, posEnd);
                            namespaceURI = parseNamespaceURI(namespaceCode);

                            codeCase2 = codeCase2.substring(posEnd + lenDelimiter);
                        }
                        else
                        {
                            throw new ItsNatDroidException("Unexpected format " + codeCase2); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
                        }
                    }
                    else
                    {
                        break; // No hay más namespaces por tanto no hay más atributos de este tipo
                    }
                }

                // Atributos que tienen en común el namespace anteriormente obtenido

                int posFirstValue = codeCase2.indexOf("/*[v*/"); // No debemos buscar más allá de este punto porque estaremos encontrando los key de otra sentencia
                if (posFirstValue == -1) throw new ItsNatDroidException("Unexpected format " + codeCase2);

                ArrayList<String> nameList = null;
                while (true)
                {
                    // name
                    {
                        int posOpen = codeCase2.indexOf("/*[k*/");
                        if (posOpen != -1)
                        {
                            if (posOpen < posFirstValue)
                            {
                                int posEnd = codeCase2.indexOf("/*k]*/", posOpen + lenDelimiter);
                                if (posEnd != -1)
                                {
                                    String nameCode = codeCase2.substring(posOpen + lenDelimiter, posEnd);
                                    String name = extractStringLiteralContent(nameCode);

                                    if (nameList == null) nameList = new ArrayList<String>();
                                    nameList.add(name);

                                    codeCase2 = codeCase2.substring(posEnd + lenDelimiter);
                                }
                                else
                                {
                                    throw new ItsNatDroidException("Unexpected format " + codeCase2); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
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

                if (nameList == null) throw new ItsNatDroidException("Unexpected format " + codeCase2); // Si se encontró un metadato namespace es porque esperamos AL MENOS un atributo remoto

                int countExpected = nameList.size();
                ArrayList<String> valueList = new ArrayList<String>(countExpected);
                for(int i = 0; i < countExpected; i++)
                {
                    // value
                    {
                        int posOpen = codeCase2.indexOf("/*[v*/");
                        if (posOpen != -1)
                        {
                            int posEnd = codeCase2.indexOf("/*v]*/", posOpen + lenDelimiter);
                            if (posEnd != -1)
                            {
                                String valueCode = codeCase2.substring(posOpen + lenDelimiter, posEnd);
                                String value = extractStringLiteralContent(valueCode);

                                if (valueList == null) valueList = new ArrayList<String>();
                                valueList.add(value);

                                codeCase2 = codeCase2.substring(posEnd + lenDelimiter);
                            }
                            else
                            {
                                throw new ItsNatDroidException("Unexpected format " + codeCase2); // JODER que raro, no hay terminador, o es un bug o un intento de liarla por parte del programador final
                            }
                        }
                        else
                        {
                            throw new ItsNatDroidException("Unexpected format " + codeCase2); // Esperamos un número dado de values que conocemos de antemano
                        }
                    }
                }

                if (nameList.size() != valueList.size()) throw new ItsNatDroidException("Unexpected format " + codeCase2);

                int count = nameList.size();
                for(int i = 0; i < count; i++)
                {
                    String name = nameList.get(i);
                    String value = valueList.get(i);
                    DOMAttrRemote domAttr = (DOMAttrRemote)DOMAttr.create(namespaceURI, name, value);
                    if (attrRemoteList == null) attrRemoteList = new ArrayList<DOMAttrRemote>();
                    attrRemoteList.add(domAttr);
                }

            }
        }


        return attrRemoteList;
    }

    private static DOMAttrRemote parseSingleRemoteAttr(String code)
    {
        // Ej. de formato esperado:
        //      NSAND,"textSize","@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_textSize"
        //      "style","@remote:dimen/droid/res/values/test_values_remote.xml:test_style"
        // En lugar de NSAND puede ser un "some namespace" o null

        String[] attrParts = code.split(",");
        if (attrParts.length < 2 || attrParts.length > 3) throw new ItsNatDroidException("Unexpected format: " + code);

        int part = 0;
        String namespaceURI = null;
        if (attrParts.length == 3)
        {
            namespaceURI = attrParts[part];
            if (NamespaceUtil.XMLNS_ANDROID_ALIAS.equals(namespaceURI)) // la constante NSAND
                namespaceURI = NamespaceUtil.XMLNS_ANDROID;
            else if ("null".equals(namespaceURI))
                namespaceURI = null;
            else
                namespaceURI = extractStringLiteralContent(namespaceURI);
            part++;
        }

        String name = attrParts[part];
        name = extractStringLiteralContent(name);
        part++;

        String value = attrParts[part];
        value = extractStringLiteralContent(value);

        return (DOMAttrRemote)DOMAttr.create(namespaceURI,name,value);
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

    private static String extractStringLiteralContent(String code)
    {
        if (!code.startsWith("\"")) throw new ItsNatDroidException("Unexpected format: " + code);
        if (!code.endsWith("\"")) throw new ItsNatDroidException("Unexpected format: " + code);
        code = code.substring(1,code.length()-1);
        return code;
    }


    public void processResult(EventGenericImpl evt,HttpRequestResultOKImpl result,boolean async)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();
        itsNatDoc.fireEventMonitors(false,false,evt);

        String responseText = result.getResponseText();
        itsNatDoc.eval(responseText,evt);

        if (async) evtManager.returnedEvent(evt);
    }

    public ItsNatDroidException convertExceptionAndFireEventMonitors(EventGenericImpl evt, Exception ex)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();

        if (ex instanceof ItsNatDroidException && ex.getCause() instanceof SocketTimeoutException)
        {
            // Esperamos este error en el caso de timeout y lo tratamos de forma singular en fireEventMonitors (el segundo param a true)
            itsNatDoc.fireEventMonitors(false, true, evt);
        }
        else
        {
            itsNatDoc.fireEventMonitors(false, false, evt);
        }

        if (ex instanceof ItsNatDroidException) return (ItsNatDroidException)ex;
        else return new ItsNatDroidException(ex);
    }


}
