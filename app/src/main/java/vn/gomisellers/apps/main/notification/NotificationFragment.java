package vn.gomisellers.apps.main.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseFragment;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.FragmentNotificationBinding;
import vn.gomisellers.apps.main.mypage.order.detail.OrderDetailActivity;
import vn.gomisellers.apps.utils.GomiConstants;

public class NotificationFragment extends BaseFragment<NotificationViewModel, FragmentNotificationBinding> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        if (binding == null)
            binding = FragmentNotificationBinding.bind(view);
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
        initCmd();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().requestNotifications();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<NotificationEvent>() {
            @Override
            public void onChanged(NotificationEvent event) {
                if (event.getCode() == NotificationEvent.ONCLICK) {
                    int id = ((Notification) event.getData()).getId();
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra(GomiConstants.EXTRA_ID, id);
                    startActivity(intent);
                }
            }
        });
    }
}
