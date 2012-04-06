package pl.net.bluesoft.util.lang.cquery.func;

/**
 * User: POlszewski
 * Date: 2011-07-29
 */
public abstract class F2V<T1, T2> implements F2<T1, T2, Object> {
	@Override
    public final Object invoke(T1 x, T2 y) {
        doInvoke(x, y);
        return null;
    }

    public abstract void doInvoke(T1 x, T2 y);
}
