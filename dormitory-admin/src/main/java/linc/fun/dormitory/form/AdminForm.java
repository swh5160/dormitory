package linc.fun.dormitory.form;

import linc.fun.dormitory.po.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/15 15:39
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AdminForm extends Admin {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "姓名不能为空")
    private String name;


    @NotNull(message = "性别不能为空")
    private Byte gender;


    @NotNull(message = "年龄不能为空")
    private Integer age;


    @Length(min = 11, max = 11, message = "手机号为11位")
    private String phone;


    @Email(message = "邮箱不符合规范")
    private String email;
}
