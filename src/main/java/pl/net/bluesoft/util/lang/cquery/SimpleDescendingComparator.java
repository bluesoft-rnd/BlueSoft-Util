package pl.net.bluesoft.util.lang.cquery;

import java.util.Comparator;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public class SimpleDescendingComparator<T extends Comparable<T>> implements Comparator<T> {
	@Override
    public int compare(T x, T y) {        
        if (x == y) {
            return 0;
        }
        if (x == null) {
            return +1;
        }
        if (y == null) {
            return -1;
        }
        return -x.compareTo(y);
    }
}
