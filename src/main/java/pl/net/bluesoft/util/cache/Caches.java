package pl.net.bluesoft.util.cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Caches {
    public static <K, V> Map<K, V> synchronizedCache(final int maxEntries) {
        Map<K, V> cache = new LinkedHashMap<K, V>(maxEntries + 1, .75F, true) {
            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxEntries;
            }
        };
        return Collections.synchronizedMap(cache);
    }

    public static <K, V> Map<K, V> concurrentCache(final int maxEntries) {
        return new ConcurrentHashMap<K, V>(maxEntries + 1, .75F);
    }

    public static String cachedObjectId(Object... tokens) {
        StringBuffer sb = new StringBuffer();
        if (tokens != null && tokens.length > 0) {
            for (int i = 0; i < tokens.length; ++i) {
                Object token = tokens[i];
                if (token != null) {
                    sb.append(token);
                    if (i < tokens.length - 1) {
                        sb.append('#');
                    }
                }
            }
        }
        return sb.toString();
    }

    public static abstract class CacheCallback<T> {
        public T run(Map<String, T> cache, Object... params) {
            String objectId = getCachedObjectId(params);
            T result = cache.get(objectId);
            if (result == null) {
                result = fetchObject();
                if (result != null) {
                    updateCache(cache, objectId, result);
                }
                else {
                    objectMissed(cache, objectId);
                }
            }
            return result;
        }

        protected String getCachedObjectId(Object... params) {
            return cachedObjectId(params);
        }

        protected void updateCache(Map<String, T> cache, String objectId, T result) {
            cache.put(objectId, result);
        }

        protected void objectMissed(Map<String, T> cache, String objectId) {
        }

        protected abstract T fetchObject();
    }
}
