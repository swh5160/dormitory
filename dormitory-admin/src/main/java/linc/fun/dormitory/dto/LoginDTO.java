package linc.fun.dormitory.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/15 01:26
 * @description 登录DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginDTO extends DTO {


    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "验证码唯一标识不能为空")
    private String uuid;
    /**
     * 0: 是管理员
     * 1: 是学生
     */
    @NotNull(message = "登录类型不能为空")
    private Integer type;
}
