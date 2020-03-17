package vn.gomicorp.seller.data.source.model.api;

import java.io.Serializable;

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

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T Result) {
        this.Result = Result;
    }

    public int getTotalRows() {
        return TotalRows;
    }

    public void setTotalRows(int TotalRows) {
        this.TotalRows = TotalRows;
    }
}
