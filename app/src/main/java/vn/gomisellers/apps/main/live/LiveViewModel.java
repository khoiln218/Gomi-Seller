package vn.gomisellers.apps.main.live;

import vn.gomisellers.apps.BaseViewModel;

public class LiveViewModel extends BaseViewModel<LiveEvent> {

    public void startBroadcast() {
        getCmd().call(new LiveEvent(LiveEvent.START_BROADCAST));
    }
}
