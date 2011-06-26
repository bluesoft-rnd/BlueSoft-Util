package pl.net.bluesoft.util.lang;

import org.apache.commons.beanutils.PropertyUtils;
import pl.net.bluesoft.util.lang.exception.UtilityInvocationException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static pl.net.bluesoft.util.lang.Lang.mapcar;


public abstract class NullUtil {
    private NullUtil() {}

    public static String toString(Object o) {
        if (o == null) {
            return null;
        }
        return String.valueOf(o).trim();
    }

    public static Integer toInteger(Object o) {
        if (o == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(o));
    }

    public static Float toFloat(Object o) {
        if (o == null) {
            return null;
        }
        return Float.valueOf(String.valueOf(o));
    }

    public static Double toDouble(Object o) {
        if (o == null) {
            return null;
        }
        return Double.valueOf(String.valueOf(o));
    }

    public static boolean hasOnlyNullFields(final Object o) {
        for(Field f : getFieldsFromClassAndAncestors(o.getClass())) {
            try {
                if(PropertyUtils.getProperty(o, f.getName())!=null) {
                    return false;
                }
            } catch (Exception e) {
                throw new UtilityInvocationException(e);
            }
        }
        return true;
    }

    public static List withoutNullValues(final List list) {
        return mapcar(list, new Lambda() {
            public Object lambda(Object val) {
                return val!=null ? val : Lang.NORESULT;
            }
        });
    }


    public static Field[] getFieldsFromClassAndAncestors(Class clazz) {
        List<Field> fields = new LinkedList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            fields.addAll(Arrays.asList(getFieldsFromClassAndAncestors(clazz.getSuperclass())));
        }
        return fields.toArray(new Field[fields.size()]);
    }

    public static Timestamp toTimestamp(Object o) {
        if(o==null) {
            return null;
        }
        return Timestamp.valueOf(String.valueOf(o));
    }

    public static Long toLong(Object o) {
        if(o==null) {
            return null;
        }
        return Long.valueOf(String.valueOf(o));
    }

    public static Date toDate(Timestamp timestamp) {
        if(timestamp==null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    public static BigDecimal toBigDecimal(String number) {
        return number==null || "".equals(number.trim()) ? null : new BigDecimal(number);
    }
}
