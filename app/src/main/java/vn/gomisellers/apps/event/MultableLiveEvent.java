package vn.gomisellers.apps.event;

import androidx.annotation.MainThread;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class MultableLiveEvent<T> extends SingleLiveEvent {
    @MainThread
    public void call(T data) {
        setValue(data);
    }
}
