package pl.net.bluesoft.util.lang.cquery;

import java.util.Iterator;

/**
* User: POlszewski
* Date: 2012-03-12
* Time: 11:51
*/
public abstract class ReadOnlyIterator<T> implements Iterator<T> {
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
