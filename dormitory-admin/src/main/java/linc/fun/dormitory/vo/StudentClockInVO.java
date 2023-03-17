package linc.fun.dormitory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/16 21:19
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentClockInVO extends VO {
    /**
     * 打卡列表
     */
    List<ClockIn> clockInfo;


    /**
     * 打卡
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClockIn {
        /**
         * 日期
         * 2022-04-06
         */
        private String day;
        /**
         * 是否打卡
         */
        private Boolean clock;
    }

}
