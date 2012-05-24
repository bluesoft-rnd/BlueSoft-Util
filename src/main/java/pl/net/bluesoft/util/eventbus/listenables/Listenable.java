package pl.net.bluesoft.util.eventbus.listenables;

import pl.net.bluesoft.util.eventbus.EventListener;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public interface Listenable<L extends EventListener> {
    void addListener(L listener);
    void removeListener(L listener);
}
