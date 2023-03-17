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
 * @description 宿舍楼
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "building")
public class Building extends PO {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 宿舍楼号
     */
    @TableField(value = "bno")
    private String bno;

    /**
     * 宿舍楼名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}