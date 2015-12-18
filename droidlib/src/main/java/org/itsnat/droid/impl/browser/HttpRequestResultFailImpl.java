package org.itsnat.droid.impl.browser;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.util.MiscUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by Jose on 18/06/2015.
 */
public class HttpRequestResultFailImpl extends HttpRequestResultImpl
{
    public HttpRequestResultFailImpl(String url,HttpURLConnection conn,String mimeType, String encoding)
    {
        super(url, conn, mimeType, encoding);

        // Normalmente sera el texto del error que envia el servidor, por ejemplo el stacktrace
        this.responseByteArray = IOUtil.read(conn.getErrorStream());

        conn.disconnect();

        this.responseText = MiscUtil.toString(responseByteArray, getEncoding());
    }
}
