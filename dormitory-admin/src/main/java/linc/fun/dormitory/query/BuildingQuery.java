package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/15 16:50
 * @description 宿舍楼查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildingQuery extends Query {
    /**
     * 楼号
     */
    private String bno;
    /**
     * 楼宇名字
     */
    private String name;

}
