package pl.net.bluesoft.util.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: POlszewski
 * Date: 2012-01-09
 * Time: 16:24:29
 */
public class ExpiringCache<K, V> {
	private final long expirationMillis;
	private final long removeExpiredFrequency;
	private long lastRemoveExpiredTime;

	private final Map<K, V> items = new HashMap<K,V>();
	private final Map<K, Long> times = new HashMap<K, Long>();

	public static interface NewValueCallback<K, V> {
		V getNewValue(K key);
	}

	public ExpiringCache(long expirationMillis) {
		this(expirationMillis, expirationMillis);
	}

	public ExpiringCache(long expirationMillis, long removeExpiredFrequency) {
		this.expirationMillis = expirationMillis;
		this.removeExpiredFrequency = removeExpiredFrequency;
		this.lastRemoveExpiredTime = System.currentTimeMillis();
	}

	public synchronized boolean isValueAvailable(K key) {
		return times.containsKey(key) && !isExpired(key, System.currentTimeMillis());
	}

	public synchronized void put(K key, V value) {
		periodicRemoveExpired();
		items.put(key, value);
		times.put(key, System.currentTimeMillis());
	}

	public synchronized V get(K key) {
		periodicRemoveExpired();
		if (isValueAvailable(key)) {
			return items.get(key);
		}
		return null;
	}

	public synchronized V get(K key, NewValueCallback<K, V> newValueCallback) {
		periodicRemoveExpired();
		if (isValueAvailable(key)) {
			return items.get(key);
		}
		V value = newValueCallback.getNewValue(key);
		put(key, value);
		return value;
	}

	public synchronized void clear() {
		items.clear();
		times.clear();
	}

	public synchronized void removeExpired() {
		List<K> toRemove = new ArrayList<K>();
		long time = System.currentTimeMillis();
		for (K key : times.keySet()) {
			if (isExpired(key, time)) {
				toRemove.add(key);
			}
		}
		for (K key : toRemove) {
			items.remove(key);
			times.remove(key);
		}
		lastRemoveExpiredTime = System.currentTimeMillis();
	}

	private void periodicRemoveExpired() {
		if (System.currentTimeMillis() - lastRemoveExpiredTime >= removeExpiredFrequency) {
			removeExpired();
		}
	}

	private boolean isExpired(K key, long time) {
		return time - times.get(key) >= expirationMillis;
	}
}
