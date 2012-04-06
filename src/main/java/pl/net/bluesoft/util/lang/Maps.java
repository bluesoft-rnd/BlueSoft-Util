package pl.net.bluesoft.util.lang;

import org.apache.commons.beanutils.PropertyUtils;
import pl.net.bluesoft.util.lang.exception.UtilityInvocationException;

import java.util.*;

/**
 * @author tlipski@bluesoft.net.pl
 */
public abstract class Maps {
    private Maps() {}


	public static <T1, T2> Map<T1,T2> collectionToMap(Collection<T2> collection, String key) {
		Map<T1,T2> m = new HashMap(collection.size());
		for (T2 o : collection) {
			try {
				m.put((T1) PropertyUtils.getProperty(o, key), o);
			} catch (Exception e) {
				throw new UtilityInvocationException(key, e);
			}
		}
		return m;
	}
    public static <T1, T2> List<T2> getListFromMap(Map<T1,List<T2>> map, T1 key) {
		List<T2> list = map.get(key);
		if (list == null) {
			list = new LinkedList<T2>();
			map.put(key, list);
		}
		return list;
	}
	public static <T1, T2> Set<T2> getSetFromMap(Map<T1,Set<T2>> map, T1 key) {
		Set<T2> list = map.get(key);
		if (list == null) {
			list = new HashSet<T2>();
			map.put(key, list);
		}
		return list;
    }

    public static boolean equals(Map m1, Map m2) {
        if (m1 == m2) {
            return true;
        }
        if (m1 == null || m2 == null) {
            return false;
        }
        if (m1.size() != m2.size()) {
            return false;
        }
        Set keys = m1.keySet();
        if (!keys.equals(m2.keySet())) {
            return false;
        }
        for (Object key : keys) {
            Object o1 = m1.get(key);
            Object o2 = m2.get(key);
            if (!Lang.equals(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
