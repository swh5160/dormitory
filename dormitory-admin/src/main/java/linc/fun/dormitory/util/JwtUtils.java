package linc.fun.dormitory.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.dto.TokenDTO;
import linc.fun.dormitory.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author yqlin
 * @date 2022/4/14 23:22
 * @description JWT校验工具
 */
@Slf4j
public class JwtUtils extends JWTUtil {
    private static final String SECRET = "18061877017@163.com";


    /**
     * 生成签名
     */
    public static String sign(Long id, RoleEnum role, Long minutes) {
        final long expireTime = minutes * 60 * 1000;
        var payload = new HashMap<String, Object>(2);
        payload.put("id", id);
        payload.put("role", role.getCode());
        payload.put("expire_time", expireTime);
        return JWTUtil.createToken(payload, SECRET.getBytes());
    }

    /**
     * 验证token是否有效
     */
    public static boolean verify(String token) {
        try {
            return JWTUtil.verify(token, SECRET.getBytes());
        } catch (JWTException ex) {
            log.error("{}", ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 获取payload
     */
    public static TokenDTO getPayload(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        Integer code = (Integer) jwt.getPayload("role");
        if (RoleEnum.STUDENT.getCode().equals(code)) {
            return new TokenDTO(RoleEnum.STUDENT, Long.valueOf(jwt.getPayload("id").toString()));
        }
        return new TokenDTO(RoleEnum.ADMIN, Long.valueOf(jwt.getPayload("id").toString()));
    }

    /**
     * 获取payload
     */
    public static TokenDTO getPayloadOfRequest(HttpServletRequest request) {
        String token = getToken(request);
        return getPayload(token);
    }


    /**
     * 获取header中的token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(CommonConstants.HEADER);
        if (!StringUtils.isBlank(token) && token.startsWith(CommonConstants.TOKEN_PREFIX)) {
            token = token.replace(CommonConstants.TOKEN_PREFIX, "");
        }
        return token;
    }


    public static void main(String[] args) {
        String token = sign(1L, RoleEnum.STUDENT, 10L);
        System.out.println(getPayload(token));
        System.out.println(CommonConstants.TOKEN_PREFIX + token);
        System.out.println(verify(token));
    }
}
