package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/16 08:16
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemQuery extends Query {
    /**
     * 类型
     */
    private Byte type;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 楼栋号
     */
    private String bno;
    /**
     * 房间号
     */
    private String rno;
    /**
     * 问题
     */
    private String title;
    /**
     * 是否通过
     */
    private Boolean pass;
}
