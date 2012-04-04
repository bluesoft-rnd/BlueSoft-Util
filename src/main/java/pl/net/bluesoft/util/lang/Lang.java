package pl.net.bluesoft.util.lang;

import org.apache.commons.beanutils.PropertyUtils;
import pl.net.bluesoft.util.lang.exception.UtilityInvocationException;

import java.lang.reflect.Field;
import java.util.*;

public final class Lang {
    private Lang() {}

    public static final FilterOut NORESULT = new FilterOut();

    private static class FilterOut {

    }

    private static final class Unpack {
        private List list;

        private Unpack(List list) {
            this.list = list;
        }

        public List getList() {
            return list;
        }
    }

    public static Unpack unpack(List list) {
        return new Unpack(list);
    }

    public static <T1, T2> List mapcar(Collection<T1> list, Lambda<T1, T2> func) {
        ArrayList<T2> ret = new ArrayList<T2>();
	    if (list == null) {
		    return ret;
	    }
        for (T1 obj : list) {
            Object res = func.lambda(obj);
            if(res.equals(NORESULT)) {
                continue;
            }
            if(res instanceof Unpack) {
                ret.addAll(((Unpack) res).getList());
            }
            else {
                ret.add(func.lambda(obj));
            }
        }
        return ret;
    }

    public static<T1, T2> List mapcar(Object[] list, Lambda<T1, T2> func) {
        return mapcar((Collection)Arrays.asList(list), func); 
    }

    //throw runtimeexception or return empty list?
    public static <T1, T2> List<T2> keyFilter(final String s, Collection<T1> list) {
        return mapcar(list, new Lambda<T1, T2>() {
            public T2 lambda(T1 val) {
                try {
                    return (T2) PropertyUtils.getProperty(val, s);
                } catch (Exception e) { throw new UtilityInvocationException(e.getMessage(), e); }
            }
        });
    }

    //throw runtimeexception or return empty list?
    public static <T1, T2> T2 reduce(List<T1> list, Lambda<T1, T2> func, T2 init) {
        if (list == null || list.size() <= 0) {
            return init;
        }
        return reduce(list.subList(1, list.size()), func, func.lambda(init, list.get(0)));
    }

    //throw runtimeexception or return empty list?
    public static List keyFilterReduce(final String key, List list) {
        return (List) reduce(keyFilter(key, list), new Lambda() {
            public Object lambda(Object val1, Object val2) {
                try {
                    ((Collection)val1).addAll((Collection) val2);
                    return val1;
                } catch (Exception e) { throw new UtilityInvocationException(e.getMessage(), e); }
            }
        }, new ArrayList());
    }

    public static <T1, T2> List<T2> filterValues(Map<T1, T2> map, T1... keys) {
        List<T2> ret = new ArrayList();
        Set goodKeys = new HashSet(Arrays.asList(keys));
        for(T1 key : keys) {
            if(goodKeys.contains(key)) {
                ret.add(map.get(key));
            }
        }
        return ret;
    }

    public static <T1, T2> T2 coalesce(Map<T1, T2> map, T1... keys) {
        return reduce(filterValues(map, keys), new Lambda<T2, T2>() {
            public T2 lambda(T2 val1, T2 val2) {
                return val1 == null ? val2 : val1;
            }
        }, null);
    }

    private static class PropertyException extends RuntimeException { PropertyException(Throwable e) {super(e);}};

    public static void set(Object o, String propertyName, Object value) {
        try {
            PropertyUtils.setProperty(o, propertyName, value);
        } catch (Exception e) { throw new PropertyException(e); }
    }

    public static Object get(Object o, String propertyName) {
        try {
            return PropertyUtils.getProperty(o, propertyName);
        } catch (Exception e) { throw new PropertyException(e); }
    }


    public static Field[] getFieldsFromClassAndAncestors(Class clazz) {
        List<Field> fields = new LinkedList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            fields.addAll(Arrays.asList(getFieldsFromClassAndAncestors(clazz.getSuperclass())));
        }
        return fields.toArray(new Field[fields.size()]);
    }

    public static Object[] array(Object...vals) {
        return mapcar(vals, new Lambda<Object, Object>() {
            public Object lambda(Object val) {
                return val;
            }
        }).toArray();
    }

    public static List list(Object...vals) {
        return mapcar(vals, new Lambda<Object, Object>() {
            public Object lambda(Object val) {
                return val;
            }
        });
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

	public static <T1, T2> boolean equals(T1 t1, T2 t2) {
		return t1 == t2 || t1 != null && t1.equals(t2);
	}

	public static <T extends Comparable<T>> int compare(T x, T y) {
		if (x == y) {
			return 0;
		}
		if (x == null) {
			return +1;
		}
		if (y == null) {
			return -1;
		}
		return +x.compareTo(y);
	}

	public static <T> int compare(T[] x, T[] y) {
		int len = Math.min(x.length, y.length);
		for (int i = 0; i < len; ++i) {
			int cmp = compare((Comparable)x[i], (Comparable)y[i]);
			if (cmp != 0) {
				return cmp;
			}
		}
		return x.length - y.length;
	}

	public static <T extends Comparable<T>> T keepInRange(T value, T from, T to) {
		if (value == null) {
			return null;
		}
		if (from != null && value.compareTo(from) < 0) {
			value = from;
		}
		if (to != null && value.compareTo(to) > 0) {
			value = to;
		}
		return value;
	}
}
