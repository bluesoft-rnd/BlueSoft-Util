package pl.net.bluesoft.util.eventbus;

import pl.net.bluesoft.util.lang.Maps;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * A thread-safe observer pattern implementation. It does not handle errors.
 *
 * @author amichalak@bluesoft.net.pl
 */
public class ConcurrentEventBusManager implements EventBusManager {
    private static final Logger logger = Logger.getLogger(ConcurrentEventBusManager.class.getName());
    protected final Map<Class, Set<WeakReference<EventListener>>> listenerMap = new HashMap<Class, Set<WeakReference<EventListener>>>();
    protected final ExecutorService executorService;

    public ConcurrentEventBusManager() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public ConcurrentEventBusManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void subscribe(Class cls, EventListener listener) {
        Map<Class, Set<WeakReference<EventListener>>> map = getListenerMap();
        synchronized (map) {
            cleanListenerMap(map, cls, listener).add(new WeakReference(listener));
        }
    }

    public void unsubscribe(Class cls, EventListener listener) {
        Map<Class, Set<WeakReference<EventListener>>> map = getListenerMap();
        synchronized (map) {
            cleanListenerMap(map, cls, listener);
        }
    }

    private Set<WeakReference<EventListener>> cleanListenerMap(Map<Class, Set<WeakReference<EventListener>>> map, Class cls, EventListener listener) {
        Set<WeakReference<EventListener>> set = Maps.getSetFromMap(map, cls);
        for (Iterator<WeakReference<EventListener>> it = set.iterator(); it.hasNext();) {
            WeakReference<EventListener> ref = it.next();
            if (ref == null || ref.get() == null || ref.get() == listener) {
                it.remove();
            }
        }
        return set;
    }

    public void publish(Object event) {
        logger.info("Publishing event: " + event.getClass());
        Map<Class, Set<WeakReference<EventListener>>> map = getListenerMap();
        Class cls = event.getClass();
        while (cls != null) {
            synchronized (map) {
                Set<WeakReference<EventListener>> set = Maps.getSetFromMap(map, cls);
                for (Iterator<WeakReference<EventListener>> it = set.iterator(); it.hasNext(); ) {
                    WeakReference<EventListener> ref = it.next();
                    if (ref != null && ref.get() != null) {
                        EventListener listener = ref.get();
                        logger.info("Receiving event by listener: " + listener.getClass().getName());
                        listener.onEvent(event);
                    }
                    else {
                        it.remove();
                    }
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public void post(Object event) {
        logger.info("Concurrent event posted: " + event.getClass());
        executorService.submit(getEventRunnable(event));
    }

    protected Map<Class, Set<WeakReference<EventListener>>> getListenerMap() {
        return listenerMap;
    }

    protected Runnable getEventRunnable(final Object event) {
        return new Runnable() {
            @Override
            public void run() {
                publish(event);
            }
        };
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
