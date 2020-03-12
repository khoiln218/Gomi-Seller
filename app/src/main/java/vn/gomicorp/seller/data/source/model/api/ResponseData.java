package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> implements Serializable {

    /**
     * Result : {}
     * "Status":true,
     * "Message":null,
     * "TotalRows":0
     */

    T Result;
    boolean Status;
    String Message;
    int TotalRows;

    @JsonProperty("Result")
    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    @JsonProperty("Status")
    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @JsonProperty("TotalRows")
    public int getTotalRows() {
        return TotalRows;
    }

    public void setTotalRows(int totalRows) {
        TotalRows = totalRows;
    }
}
