package linc.fun.dormitory.form;

import linc.fun.dormitory.po.BuildingRoom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.checker.units.qual.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yqlin
 * @date 2022/4/15 22:26
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingRoomForm extends BuildingRoom {

    /**
     * 宿舍楼ID
     */
    @NotNull(message = "宿舍楼ID不能为空")
    private Long buildingId;

    /**
     * 宿舍房间号
     */
    @NotBlank(message = "宿舍楼房间号不能为空")
    private String rno;

    /**
     * 楼层
     */
    @NotNull(message = "楼层不能为空")
    private Integer floor;

    /**
     * 房间最大入住人数
     */
    @NotNull(message = "房间最大入住人数不能为空")
    private Integer maxOccupancy;

}
