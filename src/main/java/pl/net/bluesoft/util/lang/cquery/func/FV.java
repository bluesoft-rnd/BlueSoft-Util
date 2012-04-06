package pl.net.bluesoft.util.lang.cquery.func;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public abstract class FV<T> implements F<T, Object> {
	@Override
    public final Object invoke(T x) {
        doInvoke(x);
        return null;
    }

    public abstract void doInvoke(T x);
}
