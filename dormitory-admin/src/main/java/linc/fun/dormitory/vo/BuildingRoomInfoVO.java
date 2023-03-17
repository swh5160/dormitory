package linc.fun.dormitory.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingRoomInfoVO extends VO {
    /**
     * 总房间数
     */
    private Integer allCount;
    /**
     * 空宿舍数
     */
    private Integer emptyCount;
}