package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/15 17:05
 * @description 学生查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQuery extends Query {
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private String sno;

}
