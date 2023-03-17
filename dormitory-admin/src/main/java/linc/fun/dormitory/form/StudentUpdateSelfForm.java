package linc.fun.dormitory.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/17 02:59
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentUpdateSelfForm extends Form {
    @NotNull(message = "学生id不能为空")
    private Long studentId;
    private String phone;
    private String email;
    private String avatar;
}
