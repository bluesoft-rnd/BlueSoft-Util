package pl.net.bluesoft.util.eventbus.listenables;

import pl.net.bluesoft.util.eventbus.EventListener;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public abstract class ListenableStrategy<L extends EventListener> {
    public abstract ListenerReference<L> create(L listener);

    public static class WeakListenableStrategy<L extends EventListener> extends ListenableStrategy<L> {
        @Override
        public ListenerReference<L> create(L listener) {
            return ListenerReference.weakReference(listener);
        }
    }

    public static class StrongListenableStrategy<L extends EventListener> extends ListenableStrategy<L> {
        @Override
        public ListenerReference<L> create(L listener) {
            return ListenerReference.strongReference(listener);
        }
    }

    public static <L extends EventListener> ListenableStrategy<L> weakListenableStrategy() {
        return new WeakListenableStrategy<L>();
    }

    public static <L extends EventListener> ListenableStrategy<L> strongListenableStrategy() {
        return new StrongListenableStrategy<L>();
    }
}
