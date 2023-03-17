package linc.fun.dormitory.vo;

import linc.fun.dormitory.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yqlin
 * @date 2022/4/15 03:07
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVO extends VO {
    /**
     * ID
     */
    private Long id;

    /**
     * 学号
     */
    private String sno;

    /**
     * 学院
     */
    private String academy;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级(1:大一 2:大二 3:大三 4:大四)
     */
    private String grade;

    /**
     * 用户名(学号登录)
     */
    private String username;


    /**
     * 姓名
     */
    private String name;

    /**
     * 性别(0-女 1-男 2未知)
     */
    private Byte gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private RoleEnum role;
}
