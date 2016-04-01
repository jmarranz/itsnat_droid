package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.animator.XMLDOMAnimatorParser;
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
    protected XMLDOMCache<XMLDOMAnimator> domAnimatorCache = new XMLDOMCache<XMLDOMAnimator>();
    //protected XMLDOMCache<XMLDOMAnim> domAnimCache = new XMLDOMCache<XMLDOMAnim>();
    // Menues???
    protected XMLDOMCache<XMLDOMValues> domValuesCache = new XMLDOMCache<XMLDOMValues>();

    public XMLDOMRegistry(ItsNatDroidImpl parent)
    {
        this.parent = parent;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return parent;
    }

    public void cleanCaches()
    {
        domLayoutCache.clear();
        domDrawableCache.clear();
        domAnimatorCache.clear();
        // domAnimCache.clear();
        // Menues???
        domValuesCache.clear();
    }

    public XMLDOMLayout getXMLDOMLayoutCache(String markup, String itsNatServerVersion,XMLDOMLayoutParser.LayoutType layoutType,XMLDOMParserContext xmlDOMParserContext)
    {
        // Este método DEBE ser multihilo, el objeto domLayoutCache ya lo es.
        // No pasa nada si por una rarísima casualidad dos Layout idénticos hacen put, quedará el último, ten en cuenta que esto
        // es un caché.

        // Extraemos el markup sin el script de carga porque dos páginas generadas "iguales" SIEMPRE serán diferentes a nivel
        // de markup en el loadInitScript porque el id cambia y algún token aleatorio, sin el loadInitScript podemos conseguir
        // muchos más aciertos de cacheo y acelerar un montón al tener el parseo ya hecho.
        // Si el template no es generado por ItsNat server o bien el scripting está desactivado (itsNatServerVersion puede
        // ser no null pues es un header), loadInitScript será null y no pasa nada markupNoLoadScript[0] es el markup original
        String[] markupWithoutLoadScript = new String[1];
        String loadScript = null;
        if (itsNatServerVersion != null && layoutType == XMLDOMLayoutParser.LayoutType.PAGE)
            loadScript = XMLDOMLayout.extractLoadScriptMarkup(markup, markupWithoutLoadScript);
        else
            markupWithoutLoadScript[0] = markup;

        XMLDOMLayout cachedXMLDOMLayout = domLayoutCache.get(markupWithoutLoadScript[0]);
        if (cachedXMLDOMLayout == null)
        {
            XMLDOMLayoutParser layoutParser = XMLDOMLayoutParser.createXMLDOMLayoutParser(itsNatServerVersion,layoutType,xmlDOMParserContext);
            cachedXMLDOMLayout = layoutParser.createXMLDOMLayout();
            domLayoutCache.put(markupWithoutLoadScript[0], cachedXMLDOMLayout); // Cacheamos cuanto antes pues puede haber recursividad

            layoutParser.parse(markup,cachedXMLDOMLayout);
            if (cachedXMLDOMLayout instanceof XMLDOMLayoutPageItsNat)
                ((XMLDOMLayoutPageItsNat)cachedXMLDOMLayout).setLoadInitScript(null); // Que quede claro que no se puede utilizar directamente en el cacheado guardado

        }
        else // cachedDOMLayout != null
        {
            // Recuerda que cachedDOMLayout devuelto tiene el timestamp actualizado por el hecho de llamar al get()
        }

        XMLDOMLayout clonedDOMLayout = cachedXMLDOMLayout.partialClone(); // Necesitamos un clone parcial porque el loadInitScript necesitamos alojarlo en un objeto nuevo pues no puede cachearse
        if (clonedDOMLayout instanceof XMLDOMLayoutPageItsNat)
            ((XMLDOMLayoutPageItsNat)clonedDOMLayout).setLoadInitScript(loadScript);
        return clonedDOMLayout;
    }

    public XMLDOMDrawable getXMLDOMDrawableCache(String markup,XMLDOMParserContext xmlDOMParserContext)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMDrawable cachedXMLDOMDrawable = domDrawableCache.get(markup);
        if (cachedXMLDOMDrawable != null) return cachedXMLDOMDrawable;

        cachedXMLDOMDrawable = new XMLDOMDrawable();
        domDrawableCache.put(markup, cachedXMLDOMDrawable); // Cacheamos cuanto antes pues puede haber recursividad

        XMLDOMDrawableParser parser = XMLDOMDrawableParser.createXMLDOMDrawableParser(xmlDOMParserContext);
        parser.parse(markup,cachedXMLDOMDrawable);
        return cachedXMLDOMDrawable;
    }

    public XMLDOMAnimator getXMLDOMAnimatorCache(String markup,XMLDOMParserContext xmlDOMParserContext)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMAnimator cachedXMLDOMAnimator = domAnimatorCache.get(markup);
        if (cachedXMLDOMAnimator != null)
            return cachedXMLDOMAnimator;

        cachedXMLDOMAnimator = new XMLDOMAnimator();
        domAnimatorCache.put(markup, cachedXMLDOMAnimator); // Cacheamos cuanto antes pues puede haber recursividad

        XMLDOMAnimatorParser parser = XMLDOMAnimatorParser.createXMLDOMAnimatorParser(xmlDOMParserContext);
        parser.parse(markup,cachedXMLDOMAnimator);
        return cachedXMLDOMAnimator;
    }

    public XMLDOMValues getXMLDOMValuesCache(String markup,XMLDOMParserContext xmlDOMParserContext)
    {
        // Ver notas de getXMLDOMLayoutCache()
        XMLDOMValues cachedXMLDOMValues = domValuesCache.get(markup);
        if (cachedXMLDOMValues != null)
        {
//System.out.println("CACHED cachedXMLDOMValues");
            return cachedXMLDOMValues;
        }

        cachedXMLDOMValues = new XMLDOMValues();
        domValuesCache.put(markup, cachedXMLDOMValues); // Cacheamos cuanto antes pues puede haber recursividad

        XMLDOMValuesParser parser = XMLDOMValuesParser.createXMLDOMValuesParser(xmlDOMParserContext);
        parser.parse(markup,cachedXMLDOMValues);
        return cachedXMLDOMValues;
    }


}
