package pl.net.bluesoft.util.eventbus.listenables;

import pl.net.bluesoft.util.eventbus.EventListener;
import pl.net.bluesoft.util.eventbus.SupportingEventListener;

import java.lang.ref.WeakReference;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public abstract class ListenerReference<L extends EventListener> implements SupportingEventListener {

    public abstract L getListener();

    public boolean hasReference() {
        return getListener() != null;
    }

    public boolean hasReference(L listener) {
        return getListener().equals(listener);
    }

    @Override
    public boolean supports(Class eventClass) {
        L listener = getListener();
        return listener != null && (!(listener instanceof SupportingEventListener)
                || ((SupportingEventListener) listener).supports(eventClass));
    }

    @Override
    public void onEvent(Object o) {
        if (!hasReference()) {
            throw new IllegalStateException(getClass().getName() + " lost reference to event listener");
        }
        if (supports(o.getClass())) {
            getListener().onEvent(o);
        }
    }

    public static class EventListenerStrongReference<L extends EventListener> extends ListenerReference<L> {
        private final L eventListener;

        public EventListenerStrongReference(L eventListener) {
            this.eventListener = eventListener;
        }

        @Override
        public L getListener() {
            return eventListener;
        }
    }

    public static class EventListenerWeakReference<L extends EventListener> extends ListenerReference<L> {
        private final WeakReference<L> reference;

        public EventListenerWeakReference(L listener) {
            this.reference = new WeakReference<L>(listener);
        }

        @Override
        public L getListener() {
            return reference.get();
        }
    }

    public static <L extends EventListener> ListenerReference<L> weakReference(L listener) {
        return new EventListenerWeakReference<L>(listener);
    }

    public static <L extends EventListener> ListenerReference<L> strongReference(L listener) {
        return new EventListenerStrongReference<L>(listener);
    }
}
