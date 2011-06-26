package pl.net.bluesoft.util.eventbus;

import pl.net.bluesoft.util.lang.MapUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple observer pattern implementation. It does not handle errors, etc.
 *
 * @author tlipski@bluesoft.net.pl
 */
public class EventBusManager {
	protected Map<Class, Set<WeakReference<EventListener>>> listenerMap = new HashMap<Class, Set<WeakReference<EventListener>>>();

	public void subscribe(Class cls, EventListener listener) {
		MapUtil.getSetFromMap(listenerMap, cls).add(new WeakReference(listener));
	}

	public void unsubscribe(Class cls, EventListener listener) {
		MapUtil.getSetFromMap(listenerMap, cls).remove(new WeakReference(listener));
	}

	public void publish(Object event) {
		Class cls = event.getClass();
		while (cls != null) {
			for (WeakReference<EventListener> ref : MapUtil.getSetFromMap(listenerMap, cls)) {
				if (ref != null && ref.get() != null)
					ref.get().onEvent(event);
			}
			cls = cls.getSuperclass();
		}
	}

}
