package pl.net.bluesoft.util.lang.cquery;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public final class EqualityComparers {
	public static final EqualityComparer<String> CASE_INSENSITIVE = new EqualityComparer<String>() {		
		@Override
		public int hashCode(String t) {
			return t != null ? t.toLowerCase().hashCode() : 0;
		}
		
		@Override
		public boolean equals(String t1, String t2) {
			return t1 == t2 || t1 != null && t1.toLowerCase().equals(t2.toLowerCase());
		}
	};
	
	public static final EqualityComparer<?> NULLS_ARE_DIFFEENT = new EqualityComparer<Object>() {		
		@Override
		public int hashCode(Object t) {
			return t != null ? t.hashCode() : 0;
		}
		
		@Override
		public boolean equals(Object t1, Object t2) {
			return t1 != null && t2 != null && t1.equals(t2);
		}
	};
}
