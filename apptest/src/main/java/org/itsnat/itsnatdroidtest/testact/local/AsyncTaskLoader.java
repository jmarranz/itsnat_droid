package org.itsnat.itsnatdroidtest.testact.local;

import org.itsnat.itsnatdroidtest.testact.TestActivity;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created by jmarranz on 21/05/2016.
 */
public class AsyncTaskLoader extends android.os.AsyncTask<Void, Void, Object>
{
    protected TestActivity act;

    public AsyncTaskLoader(TestActivity act)
    {
        this.act = act;
    }

    @Override
    protected Object doInBackground(Void... params)
    {
        String urlTestBase = act.getURLTestBase();
        String urlDownloadBase = urlTestBase + "/droid/download/";
        String urlList = urlDownloadBase + "resources.properties";

        try
        {
            URL urlListObj = new URL(urlList);
            URLConnection urlConnection = urlListObj.openConnection();
            urlConnection.setDoInput(true);
            InputStream input = urlConnection.getInputStream();

            Properties prop = new Properties();
            prop.load(input);
        }
        catch(Exception ex)
        {
            return ex;
        }
//SEGUIR


        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onPostExecute(Object result)
    {
        if (result instanceof Exception)
            onFinishError((Exception)result);
        else
            onFinishOk(result);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    protected void onFinishOk(Object result)
    {

    }

    protected void onFinishError(Exception ex)
    {

    }
}
