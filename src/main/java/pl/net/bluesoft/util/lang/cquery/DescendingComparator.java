package pl.net.bluesoft.util.lang.cquery;

import pl.net.bluesoft.util.lang.cquery.func.F;

import java.util.Comparator;

/**
* User: POlszewski
* Date: 2011-07-29
* Time: 19:14:45
*/
final class DescendingComparator<T, R extends Comparable<R>> implements Comparator<T> {
    private final F<? super T, R> selector;

    public DescendingComparator(F<? super T, R> selector) {
        this.selector = selector;
    }

    @Override
    public int compare(T x, T y) {
        if (x == y) {
            return 0;
        }
        R sx = selector.invoke(x);
        R sy = selector.invoke(y);
        if (sx == sy) {
            return 0;
        }
        if (sx == null) {
            return +1;
        }
        if (sy == null) {
            return -1;
        }
        return -sx.compareTo(sy);
    }
}
