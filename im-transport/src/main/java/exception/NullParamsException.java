package exception;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:40
 **/
public class NullParamsException extends RuntimeException {
    public NullParamsException() {
        super();
    }

    public NullParamsException(String message) {
        super(message);
    }

    public NullParamsException(Throwable cause) {
        super(cause);
    }

    public NullParamsException(String message, Throwable cause) {
        super(message, cause);
    }

}
