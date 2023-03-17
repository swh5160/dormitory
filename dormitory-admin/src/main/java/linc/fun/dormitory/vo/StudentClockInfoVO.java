package linc.fun.dormitory.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentClockInfoVO extends VO {
    /**
     * 总人数
     */
    private Integer allCount;
    /**
     * 已打卡人数
     */
    private Integer clockCount;
}


