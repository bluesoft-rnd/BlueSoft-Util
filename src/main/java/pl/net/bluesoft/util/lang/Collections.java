package pl.net.bluesoft.util.lang;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public class Collections {
    public static <T> T firstMatching(Collection<T> collection, Predicate<? super T> predicate) {
        if (collection != null) {
            for (T obj : collection) {
                if (predicate.apply(obj)) {
                    return obj;
                }
            }
        }
        return null;
    }

    public static <T> Collection<T> filter(Collection<T> collection, Predicate<? super T> predicate) {
        return filter(collection, predicate, new ArrayList<T>());
    }

    public static <T> Set<T> complement(Set<T> firstSet, final Set<T> secondSet) {
        return filter(firstSet, new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return !secondSet.contains(input);
            }
        }, new HashSet<T>());
    }

    public static <T, C extends Collection> C filter(Collection<T> collection, Predicate<? super T> predicate, C result) {
        if (collection != null) {
            for (T obj : collection) {
                if (predicate.apply(obj)) {
                    result.add(obj);
                }
            }
        }
        return result;
    }

    public static <T> Pair<Collection<T>, Collection<T>> halve(Collection<T> collection, Predicate<? super T> predicate) {
        Pair<Collection<T>, Collection<T>> pair = new Pair<Collection<T>, Collection<T>>(new ArrayList<T>(), new ArrayList<T>());
        if (collection != null) {
            for (T obj : collection) {
                if (predicate.apply(obj)) {
                    pair.getFirst().add(obj);
                }
                else {
                    pair.getSecond().add(obj);
                }
            }
        }
        return pair;
    }

    public static <T> List<Collection<T>> splitExclusive(Collection<T> collection, Predicate<? super T>... predicates) {
        List<Collection<T>> result = new LinkedList<Collection<T>>();
        if (collection != null) {
            for (int i = 0; i < predicates.length + 1; ++i) {
                result.add(new ArrayList<T>());
            }
            for (T obj : collection) {
                boolean applied = false;
                for (int i = 0; i < predicates.length; ++i) {
                    Predicate<? super T> predicate = predicates[i];
                    if (predicate.apply(obj)) {
                        result.get(i).add(obj);
                        applied = true;
                        break;
                    }
                }
                if (!applied) {
                    result.get(predicates.length).add(obj);
                }
            }
        }
        return result;
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        Map<K, V> filteredMap = new HashMap<K, V>();
        if (map != null) {
            for (Entry<K, V> entry : map.entrySet()) {
                if (predicate.apply(entry.getValue())) {
                    filteredMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return filteredMap;
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<Entry<K, V>> predicate) {
        Map<K, V> filteredMap = new HashMap<K, V>();
        if (map != null) {
            for (Entry<K, V> entry : map.entrySet()) {
                if (predicate.apply(entry)) {
                    filteredMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return filteredMap;
    }

    public static <K, V> Map<K, V> transform(Collection<V> collection, Transformer<V, K> transformer) {
        Map<K, V> transformedMap = new HashMap<K, V>();
        if (collection != null) {
            for (V obj : collection) {
                K key = transformer.transform(obj);
                if (key != null) {
                    transformedMap.put(key, obj);
                }
            }
        }
        return transformedMap;
    }

    public static <K, V> Map<K, List<V>> group(Collection<V> collection, Transformer<V, K> transformer) {
        Map<K, List<V>> result = new HashMap<K, List<V>>();
        if (collection != null) {
            for (V obj : collection) {
                K key = transformer.transform(obj);
                List<V> list = result.get(key);
                if (list == null) {
                    list = new ArrayList<V>();
                    result.put(key, list);
                }
                list.add(obj);
            }
        }
        return result;
    }

    public static <F, T, R extends Collection> R collect(Collection<F> collection, Transformer<F, T> transformer, R result) {
        if (collection != null) {
            for (F obj : collection) {
                T to = transformer.transform(obj);
                if (to != null) {
                    result.add(to);
                }
            }
        }
        return result;
    }

    public static <F, T> Collection<T> collect(Collection<F> collection, Transformer<F, T> transformer) {
        Collection<T> result = new ArrayList<T>();
        collect(collection, transformer, result);
        return result;
    }
}
