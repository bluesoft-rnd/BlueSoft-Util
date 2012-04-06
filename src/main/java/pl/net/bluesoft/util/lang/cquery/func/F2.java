package pl.net.bluesoft.util.lang.cquery.func;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public interface F2<T1, T2, R> {
	R invoke(T1 x, T2 y);
}
