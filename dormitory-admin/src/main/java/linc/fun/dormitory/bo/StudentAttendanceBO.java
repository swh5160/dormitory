package linc.fun.dormitory.bo;

import linc.fun.dormitory.po.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/16 22:40
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentAttendanceBO extends Student {
    /**
     * 是否打卡
     */
    private Boolean clock;
}
