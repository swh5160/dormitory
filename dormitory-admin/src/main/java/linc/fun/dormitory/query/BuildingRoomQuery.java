package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/15 22:30
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingRoomQuery extends Query {
    /**
     * 房间号
     */
    private String rno;

    /**
     * 楼号
     */
    private String bno;
}
