package linc.fun.dormitory.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/16 03:15
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomBedPlaceHolderForm extends Form {
    /**
     * 床位ID
     */
    @NotNull(message = "床位ID不能为空")
    private Long roomBedId;
    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    private String sno;
}
