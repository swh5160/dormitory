package linc.fun.dormitory.handler;

import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.dto.common.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/14 22:32
 * @description 全局异常拦截
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 异常信息拦截
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public SingleResponse<?> exception(Exception ex) {
        log.error("Exception info:{}", ex.getMessage(), ex);
        return SingleResponse.buildFailure(CommonConstants.FAIL, ex.getMessage());
    }

    /**
     * 参数绑定错误
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public SingleResponse<?> exception(BindException ex) {
        String defaultMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("Exception info:{}", defaultMessage, ex);
        return SingleResponse.buildFailure(CommonConstants.FAIL, defaultMessage);
    }
}
