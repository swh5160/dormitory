package linc.fun.dormitory.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SingleResponse<T> extends Response {
    private T data;


    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> singleResponse = new SingleResponse<>();
        singleResponse.setSuccess(true);
        singleResponse.setData(data);
        return singleResponse;
    }


    public static SingleResponse<?> buildFailure(String errCode, String errMessage) {
        SingleResponse<?> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static SingleResponse<?> buildSuccess() {
        SingleResponse<?> response = new SingleResponse<>();
        response.setSuccess(true);
        return response;
    }
}
