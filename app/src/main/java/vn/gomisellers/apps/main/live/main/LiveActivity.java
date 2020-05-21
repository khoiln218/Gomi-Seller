package vn.gomisellers.apps.main.live.main;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivityLiveRoomBinding;
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
