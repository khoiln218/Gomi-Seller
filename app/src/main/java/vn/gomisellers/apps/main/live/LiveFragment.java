package vn.gomisellers.apps.main.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.greenrobot.eventbus.EventBus;

import vn.gomisellers.apps.BaseFragment;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.LiveFragmentBinding;
import vn.gomisellers.apps.main.MainEvent;

public class LiveFragment extends BaseFragment<LiveViewModel, LiveFragmentBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_fragment, container, false);
        if (binding == null)
            binding = LiveFragmentBinding.bind(view);
        viewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
        initCmd();
        return binding.getRoot();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<LiveEvent>() {
            @Override
            public void onChanged(LiveEvent event) {
                if (event.getCode() == LiveEvent.START_BROADCAST) {
                    startBroadcast();
                }
            }
        });
    }

    private void startBroadcast() {
        EappsApplication.getInstance().engineConfig().setChannelName(EappsApplication.getPreferences().getWebAddress());
        EventBus.getDefault().post(new MainEvent<>(MainEvent.REQUEST_PERMISSION_LIVE));
    }
}
