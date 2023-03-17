package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/16 22:40
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentAttendanceQuery extends Query {
    /**
     * 是否打卡
     */
    private Boolean clock;

    /**
     * 学号
     */
    private String sno;

    /**
     * 姓名
     */
    private String name;
}
