package org.itsnat.itsnatdroidtest.testact.local.intern;

import android.content.Context;

import org.itsnat.itsnatdroidtest.testact.TestActivity;
import org.itsnat.itsnatdroidtest.testact.local.TestSetupLocalLayoutBase;
import org.itsnat.itsnatdroidtest.testact.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by jmarranz on 21/05/2016.
 */
public abstract class AsyncTaskInternLayoutResourcesLoader extends android.os.AsyncTask<Void, Void, Object>
{
    protected TestActivity act;

    public AsyncTaskInternLayoutResourcesLoader(TestActivity act)
    {
        this.act = act;
    }

    @Override
    protected Object doInBackground(Void... params)
    {
        String urlTestBase = act.getURLTestBase();
        String urlDownloadBase = urlTestBase + "/droid/download/";
        String urlList = urlDownloadBase + "resources.txt";

        try { cleanDownloadedResources(); }
        catch (Exception ex) { return ex; }

        InputStream input;
        try { input = openRemote(urlList); }
        catch (Exception ex) { return ex; }

        ArrayList<String> resourceLocationList;
        try { resourceLocationList = readRemoteResourceLocationList(input); }
        catch (Exception ex) { return ex; }

        ArrayList<byte[]> resources;
        try { resources = downloadResources(resourceLocationList,urlDownloadBase); }
        catch (Exception ex) { return ex; }

        try { writeResources(resourceLocationList,resources); }
        catch (Exception ex) { return ex; }

        return null;
    }

    private InputStream openRemote(String url) throws IOException
    {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(20000);
        urlConnection.setDoInput(true);
        return urlConnection.getInputStream();
    }

    private ArrayList<String> readRemoteResourceLocationList(InputStream input)
    {
        ArrayList<String> resourceList = new ArrayList<String>(30);

        Scanner sc = new Scanner(input,"UTF-8");
        while(sc.hasNextLine())
        {
            String res = sc.nextLine();
            String resTrim = res.trim();
            if (resTrim.isEmpty()) continue;
            if (resTrim.startsWith("#")) continue;
            resourceList.add(res);
        }
        sc.close();

        return resourceList;
    }

    private ArrayList<byte[]> downloadResources(List<String> resourceList, final String urlDownloadBase) throws Exception
    {
        ArrayList<byte[]> resourceBinList = new ArrayList<byte[]>(resourceList.size());

        List<Callable<byte[]>> listCallable = new ArrayList<Callable<byte[]>>(resourceList.size());
        for(final String resource : resourceList)
        {
            Callable<byte[]> callable = new Callable<byte[]>()
            {
                @Override
                public byte[] call() throws Exception
                {
                    String url = urlDownloadBase + resource;
                    InputStream input = openRemote(url);
                    return Util.read(input);
                }
            };
            listCallable.add(callable);
        }

        ExecutorService service = Executors.newCachedThreadPool();

        List<Future<byte[]>> listFuture = service.invokeAll(listCallable);
        for(Future<byte[]> future : listFuture)
        {
            byte[] data = future.get(30000, TimeUnit.MILLISECONDS); //http://stackoverflow.com/questions/12305667/how-is-exception-handling-done-in-a-callable
            resourceBinList.add(data);
        }

        return resourceBinList;
    }

    private void writeResources(ArrayList<String> resourceLocationList,ArrayList<byte[]> resources) throws Exception
    {
        // http://stackoverflow.com/questions/21326331/how-to-create-nested-folders-and-file-in-context-mode-private

        File rootDir = act.getDir(TestSetupLocalLayoutBase.internLocationBase,Context.MODE_PRIVATE); // Crea inicialmente el folder si es necesario añadiendo "app_" ej con "intern" como parámetro /data/data/org.itsnat.itsnatdroidtest/app_intern
        // A partir de aquí usar métodos normales pues openFileOutput no admite subdirectorios

        int size = resourceLocationList.size();
        for(int i = 0; i < size; i++)
        {
            String location = resourceLocationList.get(i);
            byte[] resource = resources.get(i);

            int index = location.lastIndexOf('/'); // Esperamos que location apunte a un archivo no a un folder y tenga formato UNIX y que tenga algún subdirectorio
            if (index == -1) throw new RuntimeException("Expected some character / in file location format " + location);
            String parentLocation = location.substring(0,index);
            String locationFileName = location.substring(index + 1);
            File internParentDir = new File(rootDir.getAbsolutePath(),parentLocation);
            internParentDir.mkdirs();

            String absLocation = internParentDir.getAbsolutePath() + "/" + locationFileName;
            FileOutputStream outputStream = new FileOutputStream(absLocation);
            outputStream.write(resource);
            outputStream.close();
        }
    }

    private void cleanDownloadedResources()
    {
        File dirRoot = act.getDir(TestSetupLocalLayoutBase.internLocationBase,Context.MODE_PRIVATE);

        Util.cleanFileTree(dirRoot);
        if (dirRoot.listFiles() != null) throw new RuntimeException("Unexpected");
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

    public abstract void onFinishOk(Object result);

    public abstract void onFinishError(Exception ex);
}
