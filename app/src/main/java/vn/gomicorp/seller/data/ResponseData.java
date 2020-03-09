package vn.gomicorp.seller.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Sóc Nhí on 7/4/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private int status;
    private String message;
    private T data = null;
    private List<String> errors;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonProperty("errors")
    public List<String> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "{\n" +
                "status='" + status + '\'' + "\n" +
                ", data=" + data + "\n" +
                ", errors=" + getErrors() + "\n" +
                '}';
    }
}
