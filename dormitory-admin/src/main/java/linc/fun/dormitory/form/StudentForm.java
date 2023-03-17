package linc.fun.dormitory.form;

import linc.fun.dormitory.po.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/15 17:43
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentForm extends Student {
    @NotBlank(message = "学号不能为空")
    @Length(min = 10, max = 10, message = "学号为10位")
    private String sno;

    @NotBlank(message = "姓名不能为空")
    private String name;


    @NotNull(message = "性别不能为空")
    private Byte gender;


    @NotNull(message = "年龄不能为空")
    private Integer age;

    @NotBlank(message = "学院不能为空")
    private String academy;


    @NotBlank(message = "专业不能为空")
    private String major;

    @NotNull(message = "年级不能为空")
    private Byte grade;

    @NotBlank(message = "所在班级不能为空")
    private String inClass;


    @Length(min = 11, max = 11, message = "手机号为11位")
    private String phone;


    @Email(message = "邮箱不符合规范")
    private String email;
}
