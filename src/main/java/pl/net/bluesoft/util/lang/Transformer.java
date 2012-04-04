package pl.net.bluesoft.util.lang;

public interface Transformer<F, T> {
    T transform(F obj);
}
