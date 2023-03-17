package linc.fun.dormitory.po;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author yqlin
 * @date 2022/4/16 01:29
 * @description 床位信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "room_bed")
public class RoomBed extends PO {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 宿舍ID
     */
    @TableField(value = "building_room_id")
    private Long buildingRoomId;

    /**
     * 学生ID
     */
    @TableField(value = "student_id", updateStrategy = FieldStrategy.IGNORED)
    private Long studentId;

    /**
     * 学号
     */
    @TableField(value = "sno", updateStrategy = FieldStrategy.IGNORED)
    private String sno;

    /**
     * 床位名称(如一号床)
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}