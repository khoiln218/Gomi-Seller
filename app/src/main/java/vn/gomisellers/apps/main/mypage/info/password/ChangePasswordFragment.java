package vn.gomisellers.apps.main.mypage.info.password;

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
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ChangePasswordFragmentBinding;
import vn.gomisellers.apps.main.mypage.info.AccountEvent;
import vn.gomisellers.apps.utils.Utils;

public class ChangePasswordFragment extends BaseFragment<ChangePasswordViewModel, ChangePasswordFragmentBinding> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        if (binding == null)
            binding = ChangePasswordFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        getBinding().setViewModel(getViewModel());
        getBinding().setLifecycleOwner(this);
        initCmd();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ChangePasswordEvent>() {
            @Override
            public void onChanged(ChangePasswordEvent event) {
                switch (event.getCode()) {
                    case ChangePasswordEvent.SHOW_LOADING:
                        EventBus.getDefault().post(new AccountEvent<>(AccountEvent.SHOW_LOADDING));
                        break;
                    case ChangePasswordEvent.HIDE_LOADING:
                        EventBus.getDefault().post(new AccountEvent<>(AccountEvent.HIDE_LOADDING));
                        break;
                    case ChangePasswordEvent.CHANGE_PASSWORD_DONE:
                        EventBus.getDefault().post(new AccountEvent<>(AccountEvent.UPDATE_DONE));
                        break;
                    case ChangePasswordEvent.HIDE_KEY_BOARD:
                        if (getActivity() != null)
                            Utils.hideSoftKeyboard(getActivity());
                        break;
                }
            }
        });
    }
}
