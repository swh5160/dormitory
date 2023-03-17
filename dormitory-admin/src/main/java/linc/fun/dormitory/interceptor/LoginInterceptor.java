package linc.fun.dormitory.interceptor;

import com.alibaba.fastjson.JSON;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.dto.common.SingleResponse;
import linc.fun.dormitory.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author yqlin
 * @date 2022/4/14 23:07
 * @description
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("intercept uri: {}: {}", request.getMethod(), request.getRequestURI());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(TokenCheck.class)) {
                TokenCheck tokenCheck = method.getAnnotation(TokenCheck.class);
                if (tokenCheck.required()) {
                    String token = JwtUtils.getToken(request);
                    if (StringUtils.containsWhitespace(token)) {
                        customResponse(response, CommonConstants.FAIL, "令牌不能为空");
                        return false;
                    }
                    if (!JwtUtils.verify(token)) {
                        customResponse(response, CommonConstants.FAIL, "令牌失效或令牌有误");
                        return false;
                    }
                    return true;
                }
                return true;
            }
        }
        return true;
    }


    private void customResponse(HttpServletResponse response, String errCode, String errMessage) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(SingleResponse.buildFailure(errCode, errMessage)));
    }
}
