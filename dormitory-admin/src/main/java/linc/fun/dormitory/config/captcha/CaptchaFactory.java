package linc.fun.dormitory.config.captcha;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;

import java.awt.*;
import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/15 01:19
 * @description
 */
@Data
public class CaptchaFactory {
    private CaptchaCode captchaCode;

    /**
     * 获取验证码生产类
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(captchaCode)) {
            captchaCode = new CaptchaCode();
            if (Objects.isNull(captchaCode.getCodeType())) {
                captchaCode.setCodeType(CaptchaCode.LoginCodeEnum.ARITHMETIC);
            }
        }
        return switchCaptcha(captchaCode);
    }

    /**
     * 依据配置信息生产验证码
     */
    private Captcha switchCaptcha(CaptchaCode captchaCode) {
        Captcha captcha = null;
        synchronized (this) {
            switch (captchaCode.getCodeType()) {
                case ARITHMETIC:
                    captcha = new ArithmeticCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case CHINESE:
                    captcha = new ChineseCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case CHINESE_GIF:
                    captcha = new ChineseGifCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                case GIF:
                    captcha = new GifCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                case SPEC:
                    captcha = new SpecCaptcha(captchaCode.getWidth(), captchaCode.getHeight());
                    captcha.setLen(captchaCode.getLength());
                    break;
                default:
                    break;
            }
        }
        if (StringUtils.isNotBlank(captchaCode.getFontName())) {
            captcha.setFont(new Font(captchaCode.getFontName(), Font.PLAIN, captchaCode.getFontSize()));
        }
        return captcha;
    }


}
