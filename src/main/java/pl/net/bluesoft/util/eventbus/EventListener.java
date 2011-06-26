package pl.net.bluesoft.util.eventbus;

/**
 * @author tlipski@bluesoft.net.pl
 */
public interface EventListener<E> {
	void onEvent(E e);
}
