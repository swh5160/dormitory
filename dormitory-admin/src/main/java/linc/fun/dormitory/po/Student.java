package linc.fun.dormitory.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description 学生
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student")
public class Student extends PO {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    @TableField(value = "sno")
    private String sno;

    /**
     * 学院
     */
    @TableField(value = "academy")
    private String academy;

    /**
     * 专业
     */
    @TableField(value = "major")
    private String major;

    /**
     * 年级(1:大一 2:大二 3:大三 4:大四)
     */
    @TableField(value = "grade")
    private Byte grade;

    /**
     * 所在班级
     */
    @TableField(value = "in_class")
    private String inClass;

    /**
     * 用户名(学号登录)
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 姓名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 性别(0-女 1-男 2未知)
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;
}