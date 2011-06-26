package pl.net.bluesoft.util.lang;


public class Cons<T1,T2> {
    private T1 car;
    private T2 cdr;

    public Cons(T1 car, T2 cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public T1 car() {
        return car;
    }

    public T2 cdr() {
        return cdr;
    }

    public static <T1,T2> T1 car(Cons<T1,T2> cons) {
        return cons.car;
    }

    public static <T1,T2> T2 cdr(Cons<T1,T2> cons) {
        return cons.cdr;
    }
}
