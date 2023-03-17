package linc.fun.dormitory.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author yqlin
 * @date 2022/4/17 04:04
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentUpdatePasswordForm extends Form {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
