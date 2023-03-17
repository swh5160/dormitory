package linc.fun.dormitory.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description 宿舍
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "building_room")
public class BuildingRoom extends PO {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 宿舍楼ID
     */
    @TableField(value = "building_id")
    private Long buildingId;
    /**
     * 宿舍楼号
     */
    @TableField(value = "bno")
    private String bno;

    /**
     * 宿舍房间号
     */
    @TableField(value = "rno")
    private String rno;

    /**
     * 楼层
     */
    @TableField(value = "`floor`")
    private Integer floor;

    /**
     * 房间最大入住人数
     */
    @TableField(value = "max_occupancy")
    private Integer maxOccupancy;

    /**
     * 当前房间入住人数
     */
    @TableField(value = "current_occupancy")
    private Integer currentOccupancy;

}