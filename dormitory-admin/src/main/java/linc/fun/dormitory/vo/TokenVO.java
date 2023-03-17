package linc.fun.dormitory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yqlin
 * @date 2022/4/15 01:08
 * @description 登录成功之后返回的token
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVO extends VO {
    /**
     * jwtToken
     */
    private String token;
}
