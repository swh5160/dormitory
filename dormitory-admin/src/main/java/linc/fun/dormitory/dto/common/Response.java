package linc.fun.dormitory.dto.common;

import linc.fun.dormitory.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Response extends DTO {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String errCode;
    private String errMessage;


    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    @Override
    public String toString() {
        return "Response [isSuccess=" + this.success + ", errCode=" + this.errCode + ", errMessage=" + this.errMessage + "]";
    }
}
