package linc.fun.dormitory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yqlin
 * @date 2022/4/15 05:46
 * @description 验证码VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class CaptchaVO extends VO {
    /**
     * 唯一ID
     */
    private String uuid;
    /**
     * base64
     */
    private String img;
}
