package vn.gomicorp.seller.event;

import androidx.annotation.MainThread;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class MultibleLiveEvent<T> extends SingleLiveEvent {
    @MainThread
    public void call(T data) {
        setValue(data);
    }
}
