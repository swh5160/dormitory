package linc.fun.dormitory.exception;

/**
 * @author yqlin
 * @date 2022/4/15 01:39
 * @description 业务异常
 */
public class BizException extends RuntimeException {
    public BizException() {
    }

    public BizException(String message, Object... args) {
        super(String.format(message, args));
    }

    public BizException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
