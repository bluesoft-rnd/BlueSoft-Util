package pl.net.bluesoft.util.lang.cquery;

import java.util.Iterator;

/**
 * User: POlszewski
 * Date: 2012-03-12
 * Time: 13:18
 */
public abstract class CQueryIterator<T, R> extends ReadOnlyIterator<R> {
	protected Iterator<? extends T> iterator;
	
	public CQueryIterator(Iterable<T> iterable) {
		this(iterable.iterator());
	}
	
	public CQueryIterator(Iterator<T> iterator) {
		this.iterator = iterator;	
	}
}
