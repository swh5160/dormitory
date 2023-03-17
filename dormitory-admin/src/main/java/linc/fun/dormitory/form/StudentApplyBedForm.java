package linc.fun.dormitory.form;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentApplyBedForm extends Form {

    @NotNull(message = "房间id不能为空")
    private Long roomBedId;

    @NotBlank(message = "学号不能为空")
    private String sno;
}
