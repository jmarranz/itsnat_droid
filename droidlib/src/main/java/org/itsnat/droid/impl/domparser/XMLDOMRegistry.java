package org.itsnat.droid.impl.domparser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.drawable.XMLDOMDrawableParser;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;

/**
 * Created by Jose on 01/12/2015.
 */
public class XMLDOMRegistry
{
    protected ItsNatDroidImpl parent;
    protected XMLDOMCache<XMLDOMLayout> domLayoutCache = new XMLDOMCache<XMLDOMLayout>();
    protected XMLDOMCache<XMLDOMDrawable> domDrawableCache = new XMLDOMCache<XMLDOMDrawable>();

    public XMLDOMRegistry(ItsNatDroidImpl parent)
    {
        this.parent = parent;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return parent;
    }

    public XMLDOMLayout getXMLDOMLayoutCache(String markup, String itsNatServerVersion,boolean loadingRemotePage,AssetManager assetManager)
    {
        // Este método DEBE ser multihilo, el objeto domLayoutCache ya lo es.
        // No pasa nada si por una rarísima casualidad dos Layout idénticos hacen put, quedará el último, ten en cuenta que esto
        // es un caché.

        // Extraemos el markup sin el script de carga porque dos páginas generadas "iguales" SIEMPRE serán diferentes a nivel
        // de markup en el loadScript porque el id cambia y algún token aleatorio, sin el loadScript podemos conseguir
        // muchos más aciertos de cacheo y acelerar un montón al tener el parseo ya hecho.
        // Si el template no es generado por ItsNat server o bien el scripting está desactivado (itsNatServerVersion puede
        // ser no null pues es un header), loadScript será null y no pasa nada markupNoLoadScript[0] es el markup original
        String[] markupWithoutLoadScript = new String[1];
        String loadScript = null;
        if (itsNatServerVersion != null && loadingRemotePage)
            loadScript = XMLDOMLayout.extractLoadScriptMarkup(markup, markupWithoutLoadScript);
        else
            markupWithoutLoadScript[0] = markup;

        XMLDOMLayout cachedDOMLayout = domLayoutCache.get(markupWithoutLoadScript[0]);
        if (cachedDOMLayout != null)
        {
            // Recuerda que cachedLayout tiene el timestamp actualizado por el hecho de llamar al get()
        }
        else
        {
            XMLDOMLayoutParser layoutParser = XMLDOMLayoutParser.createXMLDOMLayoutParser(itsNatServerVersion,loadingRemotePage,this,assetManager);

            cachedDOMLayout = layoutParser.parse(markup);
            cachedDOMLayout.setLoadScript(null); // Que quede claro que no se puede utilizar en el cacheado
            domLayoutCache.put(markupWithoutLoadScript[0], cachedDOMLayout);
        }

        XMLDOMLayout cloned = cachedDOMLayout.partialClone(loadScript); // Necesitamos un clone parcial porque el loadScript necesitamos alojarlo en un objeto nuevo pues no puede cachearse
        return cloned;
    }

    public XMLDOMLayout getXMLDOMLayoutCache(String markup,AssetManager assetManager)
    {
        return getXMLDOMLayoutCache(markup,null,false,assetManager); // loadingRemotePage es ignorado si itsNatServerVersion es nulo
    }


    public XMLDOMDrawable getXMLDOMDrawableCache(String markup,AssetManager assetManager)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMDrawable cachedDrawable = domDrawableCache.get(markup);
        if (cachedDrawable != null) return cachedDrawable;
        else
        {
            XMLDOMDrawableParser parser = XMLDOMDrawableParser.createXMLDOMDrawableParser(this,assetManager);
            XMLDOMDrawable xmlDOMDrawable = parser.parse(markup);
            domDrawableCache.put(markup, xmlDOMDrawable);
            return xmlDOMDrawable;
        }
    }

}
