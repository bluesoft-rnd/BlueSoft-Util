package pl.net.bluesoft.util.lang;

import org.apache.commons.beanutils.PropertyUtils;
import pl.net.bluesoft.util.lang.exception.UtilityInvocationException;

import java.util.*;

/**
 * @author tlipski@bluesoft.net.pl
 */
public abstract class MapUtil {
    private MapUtil() {}


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
			list = new LinkedList();
			map.put(key, list);
		}
		return list;
	}
	public static <T1, T2> Set<T2> getSetFromMap(Map<T1,Set<T2>> map, T1 key) {
		Set<T2> list = map.get(key);
		if (list == null) {
			list = new HashSet();
			map.put(key, list);
		}
		return list;
	}
}
