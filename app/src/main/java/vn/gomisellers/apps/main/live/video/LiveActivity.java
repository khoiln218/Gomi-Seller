package vn.gomisellers.apps.main.live.video;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivityLiveRoomBinding;
import vn.gomisellers.apps.main.live.message.MessageBean;
import vn.gomisellers.apps.utils.Utils;
import vn.gomisellers.apps.utils.WindowUtil;

public class LiveActivity extends BaseActivity<LiveMainViewModel, ActivityLiveRoomBinding> {
    private VideoGridContainer mVideoGridContainer;

    protected DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    protected int mStatusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.hideWindowStatusBar(getWindow());
        setGlobalLayoutListener();
        getDisplayMetrics();
        initStatusBarHeight();
        initBinding();
        initCmd();

        mVideoGridContainer = findViewById(R.id.live_video_grid_layout);

        EventBus.getDefault().register(this);
        getViewModel().startBroadcast();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<LiveMainEvent>() {
            @Override
            public void onChanged(LiveMainEvent event) {
                switch (event.getCode()) {
                    case LiveMainEvent.RENDER_REMOTE_USER:
                        renderRemoteUser((int) event.getData());
                        break;
                    case LiveMainEvent.USER_OFFLINE:
                        removeRemoteUser((int) event.getData());
                        break;
                    case LiveMainEvent.FINISH:
                        finish();
                        break;
                    case LiveMainEvent.ADD_USER:
                        addUser();
                        break;
                    case LiveMainEvent.REMOVE_USER:
                        removeUser();
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final LiveMainEvent event) {
        if (event.getCode() == LiveMainEvent.RECEIVE_MESSAGE) {
            getViewModel().updateChatBox((MessageBean) event.getData());
        }
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_room);
        viewModel = ViewModelProviders.of(this).get(LiveMainViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    private void onGlobalLayoutCompleted() {
        RelativeLayout topLayout = findViewById(R.id.live_room_top_layout);
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) topLayout.getLayoutParams();
        params.height = mStatusBarHeight + topLayout.getMeasuredHeight();
        topLayout.setLayoutParams(params);
        topLayout.setPadding(0, mStatusBarHeight, 0, 0);

        RecyclerView recyclerView = findViewById(R.id.chat_box);
        ViewGroup.LayoutParams paramsMsg = recyclerView.getLayoutParams();
        paramsMsg.width = Utils.getScreenWidth() * 5 / 6;
        recyclerView.setLayoutParams(paramsMsg);
    }

    private void renderRemoteUser(int uid) {
        SurfaceView surface = getViewModel().prepareRtcVideo(uid, false);
        mVideoGridContainer.addUserVideoSurface(uid, surface, false);
    }

    private void removeRemoteUser(int uid) {
        getViewModel().removeRtcVideo(uid, false);
        mVideoGridContainer.removeUserVideo(uid, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModel().stopBroadcast();
        EventBus.getDefault().unregister(this);
    }

    private void addUser() {
        SurfaceView surface = getViewModel().prepareRtcVideo(0, true);
        mVideoGridContainer.addUserVideoSurface(0, surface, true);
    }

    private void removeUser() {
        getViewModel().removeRtcVideo(0, true);
        mVideoGridContainer.removeUserVideo(0, true);
    }

    private void setGlobalLayoutListener() {
        final View layout = findViewById(Window.ID_ANDROID_CONTENT);
        ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                onGlobalLayoutCompleted();
            }
        });
    }

    private void getDisplayMetrics() {
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
    }

    private void initStatusBarHeight() {
        mStatusBarHeight = WindowUtil.getSystemStatusBarHeight(this);
    }
}
