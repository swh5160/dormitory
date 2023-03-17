package linc.fun.dormitory.vo;

import linc.fun.dormitory.po.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/14 22:11
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminVO extends Admin {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
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
}
