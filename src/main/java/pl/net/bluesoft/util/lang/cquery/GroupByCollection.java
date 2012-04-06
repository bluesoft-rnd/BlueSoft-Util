package pl.net.bluesoft.util.lang.cquery;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public final class GroupByCollection<K, V> extends CQueryCollection<Grouping<K, V>> {
	private final Map<K, List<V>> groups;
	
	public GroupByCollection(Map<K, List<V>> groups) {
		this.groups = groups;
	}

	@Override
	public Iterator<Grouping<K, V>> iterator() {
		return new CQueryIterator<Map.Entry<K, List<V>>, Grouping<K, V>>(groups.entrySet().iterator()) {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Grouping<K, V> next() {
				Map.Entry<K, List<V>> kv = iterator.next();
				return new Grouping<K, V>(kv.getKey(), kv.getValue());
			}
		};
	}
	
	public Map<K, List<V>> toMap() {
		return groups;
	}
}