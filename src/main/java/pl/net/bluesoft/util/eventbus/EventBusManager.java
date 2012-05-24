package pl.net.bluesoft.util.eventbus;

/**
 * Event observer pattern interface.
 * @author amichalak@bluesoft.net.pl
 */
public interface EventBusManager {

    /**
     * Subscribe for an event class.
     * @param eventClass Event class
     * @param listener Callback listener
     */
    void subscribe(Class eventClass, EventListener listener);

/**
     * Unsubscribe listener.
     * @param eventClass Event class
     * @param listener Callback listener
 */
    void unsubscribe(Class eventClass, EventListener listener);

    /**
     * Publish event using current thread.
     * @param event Event instance
     */
    void publish(Object event);

    /**
     * Publish event concurrently. The method should return immediately after call.
     * The underlying implementation should handle the publishing process in a separate thread.
     * @param event Event instance
     */
    void post(Object event);
}
