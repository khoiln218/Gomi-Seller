package vn.gomisellers.apps.main.live.video;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.stats.LocalStatsData;
import vn.gomisellers.apps.data.source.model.data.stats.RemoteStatsData;
import vn.gomisellers.apps.data.source.model.data.stats.StatsData;
import vn.gomisellers.apps.data.source.model.data.stats.StatsManager;
import vn.gomisellers.apps.event.EventHandler;
import vn.gomisellers.apps.main.live.message.MessageAdapter;
import vn.gomisellers.apps.main.live.message.MessageBean;
import vn.gomisellers.apps.utils.LiveConstants;

/**
 * Created by KHOI LE on 5/21/2020.
 */
public class LiveMainViewModel extends BaseViewModel<LiveMainEvent> implements EventHandler, TextView.OnEditorActionListener {

    public MutableLiveData<StatsManager> statsManagerMLD;
    public MutableLiveData<String> channelName;
    public MutableLiveData<String> avatar;
    public MutableLiveData<MessageAdapter> adapterMutableLiveData;
    public MutableLiveData<Integer> msgCount;

    private List<MessageBean> mMessageBeanList;
    private MessageAdapter mMessageAdapter;

    private VideoEncoderConfiguration.VideoDimensions mVideoDimension;

    public LiveMainViewModel() {
        statsManagerMLD = new MutableLiveData<>();
        channelName = new MutableLiveData<>();
        avatar = new MutableLiveData<>();
        adapterMutableLiveData = new MutableLiveData<>();
        msgCount = new MutableLiveData<>();

        mMessageBeanList = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(mMessageBeanList);
        adapterMutableLiveData.setValue(mMessageAdapter);

        statsManagerMLD.setValue(statsManager());
        config().setChannelName(EappsApplication.getPreferences().getWebAddress());
        channelName.setValue(config().getChannelName());
        avatar.setValue(EappsApplication.getPreferences().getAvatar());
    }

    private void configVideo() {
        VideoEncoderConfiguration configuration = new VideoEncoderConfiguration(
                LiveConstants.VIDEO_DIMENSIONS[config().getVideoDimenIndex()],
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
        );
        configuration.mirrorMode = LiveConstants.VIDEO_MIRROR_MODES[config().getMirrorEncodeIndex()];
        rtcEngine().setVideoEncoderConfiguration(configuration);
    }

    private void joinChannel() {
        // Initialize token, extra info here before joining channel
        // 1. Users can only see each other after they join the
        // same channel successfully using the same app id.
        // 2. One token is only valid for the channel name and uid that
        // you use to generate this token.
        String token = EappsApplication.getInstance().getString(R.string.agora_access_token);
        if (TextUtils.isEmpty(token) || TextUtils.equals(token, "#YOUR ACCESS TOKEN#")) {
            token = null; // default, no token
        }
        rtcEngine().joinChannel(token, config().getChannelName(), "", 0);
    }

    private void initData() {
        mVideoDimension = LiveConstants.VIDEO_DIMENSIONS[config().getVideoDimenIndex()];
    }

    SurfaceView prepareRtcVideo(int uid, boolean local) {
        SurfaceView surface = RtcEngine.CreateRendererView(EappsApplication.getInstance().getApplicationContext());
        if (local) {
            rtcEngine().setupLocalVideo(
                    new VideoCanvas(
                            surface,
                            VideoCanvas.RENDER_MODE_HIDDEN,
                            0,
                            LiveConstants.VIDEO_MIRROR_MODES[config().getMirrorLocalIndex()]
                    )
            );
        } else {
            rtcEngine().setupRemoteVideo(
                    new VideoCanvas(
                            surface,
                            VideoCanvas.RENDER_MODE_HIDDEN,
                            uid,
                            LiveConstants.VIDEO_MIRROR_MODES[config().getMirrorRemoteIndex()]
                    )
            );
        }
        return surface;
    }

    void removeRtcVideo(int uid, boolean local) {
        if (local) {
            rtcEngine().setupLocalVideo(null);
        } else {
            rtcEngine().setupRemoteVideo(new VideoCanvas(null, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        }
    }

    void startBroadcast() {
        rtcEngine().setClientRole(io.agora.rtc.Constants.CLIENT_ROLE_BROADCASTER);
        getCmd().call(new LiveMainEvent(LiveMainEvent.ADD_USER));

        registerRtcEventHandler();
        configVideo();
        joinChannel();
        initData();
    }

    void stopBroadcast() {
        rtcEngine().setClientRole(io.agora.rtc.Constants.CLIENT_ROLE_AUDIENCE);
        getCmd().call(new LiveMainEvent(LiveMainEvent.REMOVE_USER));
        statsManager().clearAllData();
        removeRtcEventHandler();
        rtcEngine().leaveChannel();
    }

    public void afterTextChanged() {

    }

    public void onLeaveClicked() {
        getCmd().call(new LiveMainEvent(LiveMainEvent.FINISH));
    }

    public void onMoreClicked() {
    }

    public void onShareClicked() {
    }

    private EappsApplication application() {
        return EappsApplication.getInstance();
    }

    private RtcEngine rtcEngine() {
        return application().rtcEngine();
    }

    private EngineConfig config() {
        return application().engineConfig();
    }

    private StatsManager statsManager() {
        return application().statsManager();
    }

    private void registerRtcEventHandler() {
        application().registerEventHandler(this);
    }

    private void removeRtcEventHandler() {
        application().removeEventHandler(this);
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        LiveMainEvent<Integer> event = new LiveMainEvent<>(LiveMainEvent.RENDER_REMOTE_USER);
        event.setData(uid);
        getCmd().call(event);
    }

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {

    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {

    }

    @Override
    public void onUserOffline(int uid, int reason) {
        LiveMainEvent<Integer> event = new LiveMainEvent<>(LiveMainEvent.USER_OFFLINE);
        event.setData(uid);
        getCmd().call(event);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {

    }

    @Override
    public void onLastmileQuality(int quality) {

    }

    @Override
    public void onLastmileProbeResult(IRtcEngineEventHandler.LastmileProbeResult result) {

    }

    @Override
    public void onLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setWidth(mVideoDimension.width);
        data.setHeight(mVideoDimension.height);
        data.setFramerate(stats.sentFrameRate);
    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setLastMileDelay(stats.lastmileDelay);
        data.setVideoSendBitrate(stats.txVideoKBitRate);
        data.setVideoRecvBitrate(stats.rxVideoKBitRate);
        data.setAudioSendBitrate(stats.txAudioKBitRate);
        data.setAudioRecvBitrate(stats.rxAudioKBitRate);
        data.setCpuApp(stats.cpuAppUsage);
        data.setCpuTotal(stats.cpuAppUsage);
        data.setSendLoss(stats.txPacketLossRate);
        data.setRecvLoss(stats.rxPacketLossRate);
    }

    @Override
    public void onNetworkQuality(int uid, int txQuality, int rxQuality) {
        if (!statsManager().isEnabled()) return;

        StatsData data = statsManager().getStatsData(uid);
        if (data == null) return;

        data.setSendQuality(statsManager().qualityToString(txQuality));
        data.setRecvQuality(statsManager().qualityToString(rxQuality));
    }

    @Override
    public void onRemoteVideoStats(IRtcEngineEventHandler.RemoteVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setWidth(stats.width);
        data.setHeight(stats.height);
        data.setFramerate(stats.rendererOutputFrameRate);
    }

    @Override
    public void onRemoteAudioStats(IRtcEngineEventHandler.RemoteAudioStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setAudioNetDelay(stats.networkTransportDelay);
        data.setAudioNetJitter(stats.jitterBufferDelay);
        data.setAudioLoss(stats.audioLossRate);
        data.setAudioQuality(statsManager().qualityToString(stats.quality));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND && !TextUtils.isEmpty(v.getText())) {
            sendMessage(v.getText().toString());
            v.setText("");
        }
        return false;
    }

    private void sendMessage(String msg) {
        MessageBean messageBean = new MessageBean(EappsApplication.getPreferences().getUserName(), msg, true);
        mMessageBeanList.add(messageBean);
        mMessageAdapter.notifyItemRangeChanged(mMessageBeanList.size(), 1);
        msgCount.setValue(mMessageBeanList.size());
    }
}
