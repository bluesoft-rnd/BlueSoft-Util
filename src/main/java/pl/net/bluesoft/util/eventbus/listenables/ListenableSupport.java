package pl.net.bluesoft.util.eventbus.listenables;

import pl.net.bluesoft.util.eventbus.EventListener;
import pl.net.bluesoft.util.lang.Collections;
import pl.net.bluesoft.util.lang.Transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public class ListenableSupport<L extends EventListener> implements Listenable<L> {
    protected final Set<ListenerReference<L>> listeners = new LinkedHashSet<ListenerReference<L>>();
    protected final ListenableStrategy<L> listenableStrategy;

    public ListenableSupport() {
        this(null);
    }

    public ListenableSupport(ListenableStrategy<L> listenableStrategy) {
        this.listenableStrategy = listenableStrategy != null ? listenableStrategy : ListenableStrategy.<L>weakListenableStrategy();
    }

    public static <L extends EventListener> ListenableSupport<L> weakListenable() {
        return new ListenableSupport<L>(ListenableStrategy.<L>weakListenableStrategy());
    }

    public static <L extends EventListener> ListenableSupport<L> strongListenable() {
        return new ListenableSupport<L>(ListenableStrategy.<L>strongListenableStrategy());
    }

    protected void checkReferences(L listener) {
        for (Iterator<ListenerReference<L>> it = listeners.iterator(); it.hasNext(); ) {
            ListenerReference<L> ref = it.next();
            if (!ref.hasReference() || (listener != null && ref.hasReference(listener))) {
                it.remove();
            }
        }
    }

    @Override
    public void addListener(L listener) {
        checkReferences(listener);
        listeners.add(listenableStrategy.create(listener));
    }

    @Override
    public void removeListener(L listener) {
        checkReferences(listener);
    }

    public boolean containsListener(L listener) {
        return getListeners().contains(listener);
    }

    public Set<L> getListeners() {
        return java.util.Collections.unmodifiableSet(Collections.collect(listeners, new Transformer<ListenerReference<L>, L>() {
            @Override
            public L transform(ListenerReference<L> ref) {
                return ref.hasReference() ? ref.getListener() : null;
            }
        }, new HashSet<L>()));
    }

    public void fireEvent(Object event) {
        if (event != null) {
            for (Iterator<ListenerReference<L>> it = listeners.iterator(); it.hasNext(); ) {
                ListenerReference<L> ref = it.next();
                if (ref.hasReference()) {
                    ref.onEvent(event);
                }
                else {
                    it.remove();
                }
            }
        }
    }

    public boolean hasListeners() {
        return !listeners.isEmpty();
    }
}
