package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.cquery.func.F;

import java.util.Comparator;

/**
* User: POlszewski
* Date: 2011-07-29
* Time: 19:14:56
*/
final class CustomAscentingComparator<T, R> implements Comparator<T> {
    private final F<? super T, R> selector;
    private final Comparator<? super R> comparator;

    public CustomAscentingComparator(F<? super T, R> selector, Comparator<? super R> comparator) {
        this.selector = selector;
        this.comparator = comparator;
    }

    @Override
    public int compare(T x, T y) {
        if (x == y) {
            return 0;
        }
        R sx = selector.invoke(x);
        R sy = selector.invoke(y);
        return comparator.compare(sx, sy);
    }
}
