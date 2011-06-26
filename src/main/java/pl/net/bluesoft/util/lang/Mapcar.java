package pl.net.bluesoft.util.lang;

import java.util.LinkedList;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;


public abstract class Mapcar<T1, T2> {

	private Collection<T1> list;

	protected Mapcar() {
	}

	protected Mapcar(Collection<T1> list) {
		this.list = list;
	}

	public List<T2> go() {
		return go(list);
	}

	public List<T2> go(Collection<T1> list) {
		List<T2> result = new ArrayList<T2>();
		if (list != null) {
			for (T1 x : list) {
				T2 t2 = lambda(x);
				if (t2 != null) {
                    result.add(t2);
                }
			}
		}
		return result;
	}
	public abstract T2 lambda(T1 x);
}
