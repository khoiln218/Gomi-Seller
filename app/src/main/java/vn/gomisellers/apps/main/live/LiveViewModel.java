package vn.gomisellers.apps.main.live;

import org.greenrobot.eventbus.EventBus;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmStatusCode;
import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.main.MainEvent;

public class LiveViewModel extends BaseViewModel<LiveEvent> {

    private RtmClient mRtmClient;

    public LiveViewModel() {
        mRtmClient = EappsApplication.getInstance().getChatManager().getRtmClient();
    }

    public void startBroadcast() {
        mRtmClient.login(null, EappsApplication.getPreferences().getUserName(), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                EventBus.getDefault().post(new MainEvent<>(MainEvent.REQUEST_PERMISSION_LIVE));
            }

            @Override
            public void onFailure(final ErrorInfo errorInfo) {
                if (errorInfo.getErrorCode() == RtmStatusCode.LoginError.LOGIN_ERR_ALREADY_LOGIN) {
                    EventBus.getDefault().post(new MainEvent<>(MainEvent.REQUEST_PERMISSION_LIVE));
                } else {
                    EventBus.getDefault().post(new LiveEvent(LiveEvent.LOGIN_FAILS, errorInfo.toString()));
                }
            }
        });
    }
}
