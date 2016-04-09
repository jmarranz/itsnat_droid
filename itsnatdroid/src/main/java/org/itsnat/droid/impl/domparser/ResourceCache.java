package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.dom.TimestampExtended;
import org.itsnat.droid.impl.dom.TimestampExtendedComparator;
import org.itsnat.droid.impl.util.MiscUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jmarranz on 27/10/14.
 */
public class ResourceCache<T>
{
    public static final int MAX_ENTRIES = 30;

    protected Map<String,ResourceTimestamped<T>> registryByKey = new HashMap<String,ResourceTimestamped<T>>();
    protected TreeMap<TimestampExtended,ResourceTimestamped<T>> registryByTimestamp = new TreeMap<TimestampExtended,ResourceTimestamped<T>>(new TimestampExtendedComparator()); // Recuerda que es un SortedMap de menor a mayor por defecto.

    public ResourceCache()
    {
    }

    public synchronized void clear()
    {
        registryByKey.clear();
        registryByTimestamp.clear();
    }

    public synchronized T get(String key)
    {
        ResourceTimestamped<T> resourceTimestamped = registryByKey.get(key);
        if (resourceTimestamped == null)
            return null;

        // Actualizamos el timestamp para que quede constancia de que al requerirlo es porque es de uso frecuente y no merece perderse respecto a otros con menos uso
        TimestampExtended timestamp = resourceTimestamped.getTimestampExtended();
        registryByTimestamp.remove(timestamp);
        timestamp.update();
        generateUniqueTimestamp(timestamp);
        registryByTimestamp.put(timestamp, resourceTimestamped); // Así hacemos ver que esta página se está usando recientemente y no será candidata a eliminarse

        return resourceTimestamped.getResource();
    }

    private synchronized void remove(String key)
    {
        ResourceTimestamped<T> resourceTimestamped = registryByKey.remove(key);
        if (resourceTimestamped == null) return;
        registryByTimestamp.remove(resourceTimestamped.getTimestampExtended());
    }

    public synchronized void put(String key,T resource)
    {
        remove(key);

        if (registryByKey.size() >= MAX_ENTRIES) // El > es por si acaso, un == sería suficiente
        {
            Iterator<Map.Entry<TimestampExtended,ResourceTimestamped<T>>> it = registryByTimestamp.entrySet().iterator();
            Map.Entry<TimestampExtended,ResourceTimestamped<T>> oldestItem = it.next();
            it.remove();
            ResourceTimestamped<T> resourceTimestampedToRemove = oldestItem.getValue();
            registryByKey.remove(resourceTimestampedToRemove.getKey());
        }
        ResourceTimestamped<T> resourceTimestamped = new ResourceTimestamped<T>(key,resource);
        ResourceTimestamped<T> res;
        res = registryByKey.put(key,resourceTimestamped);
        if (res != null) throw MiscUtil.internalError();
        TimestampExtended timestamp = resourceTimestamped.getTimestampExtended();
        generateUniqueTimestamp(timestamp);
        res = registryByTimestamp.put(timestamp, resourceTimestamped);
        if (res != null) throw MiscUtil.internalError();
    }

    private synchronized void generateUniqueTimestamp(TimestampExtended timestamp)
    {
        // Es posible cargar varios recursos en el mismo ms por ej en el caso de "values" contenidos en el mismo archivo
        // Evitamos que haya 2 con el nuevo timestamp extendido, de esta forma nos aseguramos la unicidad del registro dado un timestamp/complementary
        while (true)
        {
            ResourceTimestamped<T> resourceTimestampedOld = registryByTimestamp.get(timestamp);
            if (resourceTimestampedOld == null) break;
            timestamp.incComplementary();
        }
    }
}
