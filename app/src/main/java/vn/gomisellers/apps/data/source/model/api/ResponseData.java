package vn.gomisellers.apps.data.source.model.api;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {

    /**
     * Success : true
     * Message : OK
     * Code : 200
     * Result : null
     * TotalRows : 0
     */

    private boolean Status;
    private String Message;
    private int Code;
    private T Result;
    private int TotalRows;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Success) {
        this.Status = Success;
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
