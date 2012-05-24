package pl.net.bluesoft.util.eventbus;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public interface SupportingEventListener<E> extends EventListener<E> {
    boolean supports(Class eventClass);
}
