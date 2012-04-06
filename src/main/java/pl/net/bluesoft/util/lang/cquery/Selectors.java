package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.Pair;
import pl.net.bluesoft.util.lang.cquery.func.F;
import pl.net.bluesoft.util.lang.cquery.func.F2;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public final class Selectors<T> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static F<Object, Object> prop(final String property) {
        return new Get(property);
    }

    public static <T, P> F<T, P> prop(final String property, Class<P> clazz) {
        return new Get<T, P>(property);
    }
	
    private static class Get<T, P> implements F<T, P> {
        private final String property;
		private Method getAccessor;
		
        public Get(String property) {
            this.property = property;
        }

		@SuppressWarnings("unchecked")
        @Override
        public P invoke(T x) {
            if (getAccessor == null) {
				getAccessor = extractAccessor(x, property);
            }
            try {
                return (P)getAccessor.invoke(x);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }		
    }    
    
	private static Method extractAccessor(Object x, String property) {
		String Property = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        try {
            return x.getClass().getMethod("get" + Property);
        } 
		catch (Exception e) {
			try {
				return x.getClass().getMethod("is" + Property);
			}	
			catch (Exception e1) {
				throw new RuntimeException(e);
			}
        }
	}

	@SuppressWarnings("unchecked")
	public static F<Object, Object> identity() {
		return (F<Object, Object>)identity;
	}

    @SuppressWarnings("unchecked")
	public static <T> F<T, T> identity(Class<T> clazz) {
		return (F<T,T>)identity;
	}
	
	public static <T> F<T, String> format(final String format) {
		return new F<T, String>() {
			@Override
			public String invoke(T x) {
				return MessageFormat.format(format, x);
			}
		};
	}
	
	public static F2<Object, Object, Object[]> arrayPair() {
		return arrayPair;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T1, T2> F2<T1, T2, Pair<T1, T2>> pair(Class<T1> leftClazz, Class<T2> rightClazz) {
		return (F2)pair;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static F2<Object, Object, Pair<Object, Object>> pair() {
		return (F2)pair;
	}
	
	private static final F2<Object, Object, Object[]> arrayPair = new F2<Object, Object, Object[]>() {
		@Override
		public Object[] invoke(Object x, Object y) {
			return new Object[] { x, y };
		}			
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final F2<?, ?, Pair> pair = new F2() {
		@Override
		public Object invoke(Object x, Object y) {
			return new Pair(x, y);
		}	
	};
	
	@SuppressWarnings("rawtypes")
	private static final F<?,?> identity = new F() {
		@Override
		public Object invoke(Object x) {
			return x;
		}		
	};	
}
