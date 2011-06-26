package pl.net.bluesoft.util.lang;

public class Pair<T1, T2> {
    T1 key;
    T2 entry;

    public Pair() {

    }

    public Pair(T1 key, T2 entry) {
        super();
        this.key = key;
        this.entry = entry;
    }

    public T2 getEntry() {
        return entry;
    }

    public T1 getKey() {
        return key;
    }

    public void setEntry(T2 entry) {
        this.entry = entry;
    }

    public void setKey(T1 key) {
        this.key = key;
    }
}

