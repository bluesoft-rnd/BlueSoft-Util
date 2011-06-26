package pl.net.bluesoft.util.lang.exception;

public class UtilityInvocationException extends RuntimeException {
    public UtilityInvocationException(Throwable cause) {
        super(cause);
    }

    public UtilityInvocationException(String msg) {
        super(msg);
    }

    public UtilityInvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
