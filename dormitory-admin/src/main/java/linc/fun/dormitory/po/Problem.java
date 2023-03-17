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
 * @description 问题
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "problem")
public class Problem extends PO {
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
     * 楼宇号
     */
    @TableField(value = "bno")
    private String bno;

    /**
     * 宿舍ID
     */
    @TableField(value = "building_room_id")
    private Long buildingRoomId;
    /**
     * 房间号
     */
    @TableField(value = "rno")
    private String rno;
    /**
     * 学生ID
     */
    @TableField(value = "student_id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @TableField(value = "student_name")
    private String studentName;

    /**
     * 问题类型(0: 宿舍问题 1: 学生问题)
     */
    @TableField(value = "`type`")
    private Byte type;

    /**
     * 问题标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否通过(0: 未通过 1:通过)
     */
    @TableField(value = "pass")
    private Boolean pass;


    /**
     * 回复
     */
    @TableField(value = "reply")
    private String reply;
}