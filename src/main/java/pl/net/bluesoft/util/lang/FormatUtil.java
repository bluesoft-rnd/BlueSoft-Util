package pl.net.bluesoft.util.lang;

import pl.net.bluesoft.util.lang.exception.UtilityInvocationException;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


public abstract class FormatUtil {
    private FormatUtil() {
    }

    public static class DateParseException extends RuntimeException {
        DateParseException(Throwable e) {
            super(e);
        }
    }

    public static Date parseDate(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            throw new DateParseException(e);
        }
    }

    public static String formatFullDate(Date d) {
        return d == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
    }

    public static String nvl(String s, String def) {
        return s != null ? s : def;
    }

	public static<T> T nvl(T... ts) {
		for (T t : ts) {
			if (t != null) return t;
		}
		return null;
	}
	public static<T> T nvl(T s, T def) {
        return s != null ? s : def;
    }

    public static String nvl(String s) {
        return nvl(s, "");
    }

    public static String formatShortDate(Date d) {
        return d == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public static String underscoreToCamel(String str) {
        String ret = join(Lang.mapcar(Arrays.asList(str.split("_")), new Lambda<String, String>() {
            public String lambda(String val) {
                return val.substring(0, 1).toUpperCase() + val.substring(1);
            }
        }), "");
        return ret.substring(0, 1).toLowerCase() + ret.substring(1);
    }

    public static String join(String separator, Object... tokens) {
        return join(Arrays.asList(tokens), separator);
    }

    public static String join(Collection tokens, String separator) {
        StringBuffer sb = new StringBuffer();
        for (Iterator it = tokens.iterator(); it.hasNext();) {
            Object o = it.next();
            if (o != null) {
                sb.append(o.toString());
            }
            else {
                sb.append("NULL");
            }
            if (it.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static Date parseShortDate(String val) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(val);
        } catch (ParseException e) {
            throw new UtilityInvocationException(e);
        }

    }
}
