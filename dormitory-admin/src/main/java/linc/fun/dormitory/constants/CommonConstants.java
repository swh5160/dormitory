package linc.fun.dormitory.constants;

/**
 * @author yqlin
 * @date 2022/4/14 21:47
 * @description 通用常量
 */
public interface CommonConstants {

    /**
     * 失败标记
     */
    String FAIL = "9999";

    /**
     * token的Header
     */
    String HEADER = "token";
    /**
     * jwt token prefix
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 学生初始化密码
     */
    String STUDENT_INIT_PASSWORD = "123456";

    /**
     * 超级管理员初始密码
     */
    String SUPER_ADMIN_INIT_PASSWORD = "admin";
    /**
     * 过期时间,分
     */
    Long EXPIRE_TIME = 12 * 60L;
    /**
     * 验证码key
     */
    String CAPTCHA_CODE_KEY = "CAPTCHA_CODE_KEY:";

    /**
     * 超级管理员标识
     */
    Long SUPER_ADMIN_ID = 1L;
    /**
     * http
     */
    String HTTP = "http://";
    /**
     * 初始头像
     */
    String INIT_AVATAR = "https://linq-cool.oss-cn-shanghai.aliyuncs.com/20220415/7b5f536afcc44b9abbcb3e3714aa44f1.jpeg";
}
