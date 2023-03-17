package linc.fun.dormitory.form;

import com.baomidou.mybatisplus.annotation.TableField;
import linc.fun.dormitory.po.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author yqlin
 * @date 2022/4/16 06:02
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeForm extends Notice {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "发布者为空")
    private String author;
}
