package vn.gomisellers.apps.main;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/22/2020.
 */
public class MainEvent<T> extends BaseEvent<T> {
    public static final int REQUEST_PERMISSION = 0;
    public static final int CROP_IMAGE = 1;

    public MainEvent(int code) {
        super(code);
    }
}
