package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.Lang;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public interface EqualityComparer<T> {
	boolean equals(T t1, T t2);
	int hashCode(T t);

	public static final EqualityComparer<Object> DEFAULT = new EqualityComparer<Object>() {
		@Override
		public boolean equals(Object t1, Object t2) {
			return Lang.equals(t1, t2);
		}

		@Override
		public int hashCode(Object o) {
			return o != null ? o.hashCode() : 0;
		}
	};
}
