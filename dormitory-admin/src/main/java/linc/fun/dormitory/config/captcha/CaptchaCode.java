package linc.fun.dormitory.config.captcha;

import lombok.Data;

/**
 * @author yqlin
 * @date 2022/4/15 01:17
 * @description
 */
@Data
public class CaptchaCode {

    /**
     * 验证码配置
     */
    private LoginCodeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

    /**
     * 验证码配置枚举
     */
    enum LoginCodeEnum {
        /**
         * 算数
         */
        ARITHMETIC,
        /**
         * 中文
         */
        CHINESE,
        /**
         * 中文闪图
         */
        CHINESE_GIF,
        /**
         * 闪图
         */
        GIF,
        SPEC
    }

}
