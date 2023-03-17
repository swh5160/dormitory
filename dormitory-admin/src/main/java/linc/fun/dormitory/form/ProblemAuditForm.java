package linc.fun.dormitory.form;

import linc.fun.dormitory.po.Problem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/16 16:22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemAuditForm extends Problem {
    @NotNull(message = "问题id不能为空")
    private Long id;

    @NotNull(message = "审核不能为空")
    private Boolean pass;

    @NotBlank(message = "答复内容不能为空")
    private String reply;
}
