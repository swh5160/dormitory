package linc.fun.dormitory.vo;

import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.po.RoomBed;
import linc.fun.dormitory.po.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/16 07:05
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSelfInfoVO extends Student {
    /**
     * 楼栋信息
     */
    private BuildingRoom buildingRoom;
    /**
     * 我的床位信息
     */
    private RoomBed myRoomBed;

    /**
     * 所在房间的所有床位信息
     */
    private List<RoomBed> roomBeds;
}
