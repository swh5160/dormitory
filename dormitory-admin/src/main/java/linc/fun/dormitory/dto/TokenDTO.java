package linc.fun.dormitory.dto;

import linc.fun.dormitory.enums.RoleEnum;
import lombok.*;

/**
 * @author yqlin
 * @date 2022/4/14 23:46
 * @description tokenDTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenDTO extends DTO {
    private RoleEnum roleEnum;
    public Long id;
}
