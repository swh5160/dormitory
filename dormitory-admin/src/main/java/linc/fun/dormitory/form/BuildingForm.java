package linc.fun.dormitory.form;

import com.baomidou.mybatisplus.annotation.TableField;
import linc.fun.dormitory.po.Building;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author yqlin
 * @date 2022/4/15 22:00
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingForm extends Building {

    @NotBlank(message = "宿舍楼号不能为空")
    private String bno;


    @NotBlank(message = "宿舍楼名不能为空")
    private String name;
}
