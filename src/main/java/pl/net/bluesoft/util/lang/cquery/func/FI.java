package pl.net.bluesoft.util.lang.cquery.func;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public interface FI<T, R> {
	R invoke(T x, int index);
}
