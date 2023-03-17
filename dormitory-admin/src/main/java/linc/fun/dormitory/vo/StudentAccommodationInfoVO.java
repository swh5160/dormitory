package linc.fun.dormitory.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentAccommodationInfoVO extends VO {
    /**
     * 总人数
     */
    private Integer allCount;
    /**
     * 已住宿人数
     */
    private Integer accommodationCount;
}