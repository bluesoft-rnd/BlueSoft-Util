package pl.net.bluesoft.util.lang;

public class Pair<T1, T2> implements Comparable<Pair<T1, T2>> {
    T1 first;
    T2 second;

    public Pair() {

    }

    public Pair(T1 first, T2 second) {
        super();
        this.first = first;
        this.second = second;
    }

    public T2 getSecond() {
        return second;
    }

    public T1 getFirst() {
        return first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			Pair pair = (Pair)obj;
			return Lang.equals(first, pair.first) && Lang.equals(second, pair.second);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (first != null ? first.hashCode() : 0) ^ (second != null ? second.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "[" + first + "," + second + "]";
	}

	@Override
	public int compareTo(Pair<T1, T2> pair) {
		return Lang.compare(new Object[]{ first, pair.first }, new Object[]{ second, pair.second });
    }
}

