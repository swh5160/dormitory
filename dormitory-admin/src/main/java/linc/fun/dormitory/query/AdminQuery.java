package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/14 22:09
 * @description 管理员查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminQuery extends Query{
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
}
