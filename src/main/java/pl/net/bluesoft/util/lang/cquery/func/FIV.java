package pl.net.bluesoft.util.lang.cquery.func;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public abstract class FIV<T> implements FI<T, Object> {
    @Override
    public final Object invoke(T x, int index) {
        doInvoke(x, index);
        return null;
    }

    public abstract void doInvoke(T x, int index);
}
