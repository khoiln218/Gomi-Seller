package vn.gomisellers.apps.main.live.video;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelAttribute;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmMessage;
import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.stats.LocalStatsData;
import vn.gomisellers.apps.data.source.model.data.stats.RemoteStatsData;
import vn.gomisellers.apps.data.source.model.data.stats.StatsData;
import vn.gomisellers.apps.data.source.model.data.stats.StatsManager;
import vn.gomisellers.apps.event.EventHandler;
import vn.gomisellers.apps.main.live.message.ChatManager;
import vn.gomisellers.apps.main.live.message.MessageAdapter;
import vn.gomisellers.apps.main.live.message.MessageBean;
import vn.gomisellers.apps.utils.LiveConstants;
import vn.gomisellers.apps.utils.LogUtils;

/**
 * Created by KHOI LE on 5/21/2020.
 */
public class LiveMainViewModel extends BaseViewModel<LiveMainEvent> implements EventHandler, RtmChannelListener, TextView.OnEditorActionListener {

    public MutableLiveData<StatsManager> statsManagerMLD;
    public MutableLiveData<String> channelName;
    public MutableLiveData<String> avatar;
    public MutableLiveData<MessageAdapter> adapterMutableLiveData;
    public MutableLiveData<Integer> msgCount;

    private List<MessageBean> mMessageBeanList;
    private MessageAdapter mMessageAdapter;

    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;

    private VideoEncoderConfiguration.VideoDimensions mVideoDimension;
    private int mChannelMemberCount;

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
        mRtmClient = chatManager().getRtmClient();
    }

    private void createAndJoinChannel() {
        mRtmChannel = mRtmClient.createChannel(config().getChannelName(), this);
        if (mRtmChannel == null) {
            showToast(EappsApplication.getInstance().getString(R.string.join_channel_failed));
            return;
        }

        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                LogUtils.i("TAG", "join channel success");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.e("TAG", "join channel failed");
            }
        });
    }

    private void leaveChannel() {
        if (mRtmChannel != null) {
            mRtmChannel.leave(new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {

                }
            });
            mRtmChannel = null;
        }
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
        rtcEngine().joinChannel(null, config().getChannelName(), "", 0);
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
        createAndJoinChannel();
        initData();
    }

    void stopBroadcast() {
        rtcEngine().setClientRole(io.agora.rtc.Constants.CLIENT_ROLE_AUDIENCE);
        getCmd().call(new LiveMainEvent(LiveMainEvent.REMOVE_USER));
        statsManager().clearAllData();
        removeRtcEventHandler();
        rtcEngine().leaveChannel();
        leaveChannel();
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

    private ChatManager chatManager() {
        return application().getChatManager();
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
        sendChannelMessage(msg);
        MessageBean messageBean = new MessageBean(EappsApplication.getPreferences().getUserName(), msg, true);
        updateChatBox(messageBean);
    }

    void updateChatBox(MessageBean messageBean) {
        LogUtils.d("TAG", "updateChatBox: " + messageBean.getMessage());
        mMessageBeanList.add(messageBean);
        mMessageAdapter.notifyDataSetChanged();
        msgCount.setValue(mMessageBeanList.size());
    }

    private void sendChannelMessage(String content) {
        RtmMessage message = mRtmClient.createMessage();
        message.setText(content);
        mRtmChannel.sendMessage(message, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.d("TAG", "onFailure: " + errorInfo.toString());
            }
        });
    }

    @Override
    public void onMemberCountUpdated(int i) {

    }

    @Override
    public void onAttributesUpdated(List<RtmChannelAttribute> list) {

    }

    @Override
    public void onMessageReceived(RtmMessage message, RtmChannelMember fromMember) {
        LogUtils.d("TAG", "onMessageReceived - " + fromMember.getUserId() + ": " + message.getText());
        String account = fromMember.getUserId();
        String msg = message.getText();
        MessageBean messageBean = new MessageBean(account, msg, false);
        LiveMainEvent<MessageBean> event = new LiveMainEvent<>(LiveMainEvent.RECEIVE_MESSAGE);
        event.setData(messageBean);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onMemberJoined(RtmChannelMember rtmChannelMember) {
        mChannelMemberCount++;
        LogUtils.d("TAG", "onMemberJoined: " + mChannelMemberCount);
    }

    @Override
    public void onMemberLeft(RtmChannelMember rtmChannelMember) {
        mChannelMemberCount--;
        LogUtils.d("TAG", "onMemberLeft: " + mChannelMemberCount);
    }
}
