package linc.fun.dormitory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yqlin
 * @date 2022/4/17 00:41
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalVO extends VO {
    /**
     * 学生打卡信息
     */
    private StudentClockInfoVO studentClockInfo;

    /**
     * 学生住宿信息
     */
    private StudentAccommodationInfoVO studentAccommodationInfo;

    /**
     * 宿舍信息
     */
    private BuildingRoomInfoVO buildingRoomInfo;



}
