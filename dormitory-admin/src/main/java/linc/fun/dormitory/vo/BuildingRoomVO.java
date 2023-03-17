package linc.fun.dormitory.vo;

import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.po.RoomBed;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/15 22:28
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingRoomVO  extends BuildingRoom {
    /**
     * 床
     */
    private List<RoomBed> roomBeds;
}
