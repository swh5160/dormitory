package linc.fun.dormitory.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author linc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MultiResponse<T> extends Response {
    private int total;
    private Collection<T> data;

    public static <T> MultiResponse<T> of(Collection<T> data, int total) {
        MultiResponse<T> multiResponse = new MultiResponse<>();
        multiResponse.setSuccess(true);
        multiResponse.setData(data);
        multiResponse.setTotal(total);
        return multiResponse;
    }

    public static <T> MultiResponse<T> ofWithoutTotal(Collection<T> data) {
        return of(data, 0);
    }


    public List<T> getData() {
        return Objects.isNull(this.data) ? new ArrayList<>() : new ArrayList<>(this.data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public static MultiResponse<?> buildFailure(String errCode, String errMessage) {
        MultiResponse<?> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static MultiResponse<?> buildSuccess() {
        MultiResponse<?> response = new MultiResponse<>();
        response.setSuccess(true);
        return response;
    }
}