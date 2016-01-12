package org.itsnat.droid.impl.domparser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.drawable.XMLDOMDrawableParser;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.domparser.values.XMLDOMValuesParser;

/**
 * Created by Jose on 01/12/2015.
 */
public class XMLDOMRegistry
{
    protected ItsNatDroidImpl parent;
    protected XMLDOMCache<XMLDOMLayout> domLayoutCache = new XMLDOMCache<XMLDOMLayout>();
    protected XMLDOMCache<XMLDOMDrawable> domDrawableCache = new XMLDOMCache<XMLDOMDrawable>();
    protected XMLDOMCache<XMLDOMValues> domValuesCache = new XMLDOMCache<XMLDOMValues>();

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

        XMLDOMLayout cachedXMLDOMLayout = domLayoutCache.get(markupWithoutLoadScript[0]);
        if (cachedXMLDOMLayout == null)
        {
            XMLDOMLayoutParser layoutParser = XMLDOMLayoutParser.createXMLDOMLayoutParser(itsNatServerVersion,loadingRemotePage,this,assetManager);

            cachedXMLDOMLayout = layoutParser.parse(markup);
            cachedXMLDOMLayout.setLoadScript(null); // Que quede claro que no se puede utilizar directamente en el cacheado guardado
            domLayoutCache.put(markupWithoutLoadScript[0], cachedXMLDOMLayout);
        }
        else // cachedDOMLayout != null
        {
            // Recuerda que cachedDOMLayout devuelto tiene el timestamp actualizado por el hecho de llamar al get()
        }

        XMLDOMLayout clonedDOMLayout = cachedXMLDOMLayout.partialClone(loadScript); // Necesitamos un clone parcial porque el loadScript necesitamos alojarlo en un objeto nuevo pues no puede cachearse
        return clonedDOMLayout;
    }

    public XMLDOMDrawable getXMLDOMDrawableCache(String markup,AssetManager assetManager)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMDrawable cachedXMLDOMDrawable = domDrawableCache.get(markup);
        if (cachedXMLDOMDrawable != null) return cachedXMLDOMDrawable;

        XMLDOMDrawableParser parser = XMLDOMDrawableParser.createXMLDOMDrawableParser(this, assetManager);
        cachedXMLDOMDrawable = parser.parse(markup);
        domDrawableCache.put(markup, cachedXMLDOMDrawable);
        return cachedXMLDOMDrawable;
    }

    public XMLDOMValues getXMLDOMValuesCache(String markup,AssetManager assetManager)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMValues cachedXMLDOMValues = domValuesCache.get(markup);
        if (cachedXMLDOMValues != null)
        {
//System.out.println("CACHED cachedXMLDOMValues");
            return cachedXMLDOMValues;
        }

        XMLDOMValuesParser parser = XMLDOMValuesParser.createXMLDOMValuesParser(this, assetManager);
        cachedXMLDOMValues = parser.parse(markup);
        domValuesCache.put(markup, cachedXMLDOMValues);
        return cachedXMLDOMValues;
    }
}
