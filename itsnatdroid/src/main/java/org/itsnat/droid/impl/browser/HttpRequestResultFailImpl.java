package org.itsnat.droid.impl.browser;

import org.apache.http.HttpResponse;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.util.StringUtil;

import java.io.InputStream;

/**
 * Created by Jose on 18/06/2015.
 */
public class HttpRequestResultFailImpl extends HttpRequestResultImpl
{
    public HttpRequestResultFailImpl(String url,HttpResponse httpResponse,InputStream input,String mimeType, String encoding)
    {
        super(url,httpResponse, mimeType, encoding);

        // Normalmente sera el texto del error que envia el servidor, por ejemplo el stacktrace
        this.responseByteArray = IOUtil.read(input);
        this.responseText = StringUtil.toString(responseByteArray, getEncoding());
    }
}
