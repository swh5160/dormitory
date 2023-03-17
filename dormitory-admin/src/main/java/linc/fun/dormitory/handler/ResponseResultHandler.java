package linc.fun.dormitory.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.dto.common.SingleResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/14 22:21
 * @description 全局处理返回结果
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        final var method = methodParameter.getMethod();
        final var clazz = Objects.requireNonNull(method, "method is null").getDeclaringClass();

        // 判断类上面是否加了@ResponseResult
        var annotation = clazz.getAnnotation(ResponseResult.class);

        if (Objects.isNull(annotation)) {
            annotation = method.getAnnotation(ResponseResult.class);
        }

        // 如果是FileSystemResource 则不拦截
        if (method.getAnnotatedReturnType().getType().getTypeName().equals(FileSystemResource.class.getTypeName())) {
            return false;
        }
        return annotation != null && !annotation.ignore();

    }


    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        var singleResponse = SingleResponse.of(data);

        if ((data instanceof String) && !MediaType.APPLICATION_XML_VALUE.equals(mediaType.toString())) {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(singleResponse);
        }

        if (Objects.isNull(data) && MediaType.TEXT_HTML_VALUE.equals(mediaType.toString())) {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(singleResponse);
        }

        return singleResponse;
    }
}
