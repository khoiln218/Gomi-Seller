package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> implements Serializable {

    /**
     * Success : true
     * Message : OK
     * Code : 200
     * Result : null
     * TotalRows : 0
     */

    private boolean Success;
    private String Message;
    private int Code;
    private T Result;
    private int TotalRows;

    @JsonProperty("Success")
    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @JsonProperty("Code")
    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    @JsonProperty("Result")
    public T getResult() {
        return Result;
    }

    public void setResult(T Result) {
        this.Result = Result;
    }

    @JsonProperty("TotalRows")
    public int getTotalRows() {
        return TotalRows;
    }

    public void setTotalRows(int TotalRows) {
        this.TotalRows = TotalRows;
    }
}
