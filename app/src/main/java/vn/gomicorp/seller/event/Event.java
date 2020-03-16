package vn.gomicorp.seller.event;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class Event<T> {
    public int code;
    public String message;
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
