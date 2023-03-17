package linc.fun.dormitory.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/16 05:57
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeQuery extends Query {
    private String title;
}
