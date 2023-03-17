package linc.fun.dormitory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author linc
 */

@AllArgsConstructor
@Getter
public enum RoleEnum {
    /**
     * STUDENT: 学生
     * ADMIN: 管理员
     */
    STUDENT(1),
    ADMIN(0);
    private final Integer code;

}