package vn.gomicorp.seller.main.mypage.info.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseFragment;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ChangePasswordFragmentBinding;
import vn.gomicorp.seller.main.mypage.info.AccountInfoListener;

public class ChangePasswordFragment extends BaseFragment<ChangePasswordViewModel, ChangePasswordFragmentBinding> {

    private AccountInfoListener listener;

    public static ChangePasswordFragment newInstant(AccountInfoListener listener) {
        return new ChangePasswordFragment(listener);
    }

    private ChangePasswordFragment(AccountInfoListener listener) {
        this.listener = listener;
    }

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
                        if (listener != null)
                            listener.showLoading();
                        break;
                    case ChangePasswordEvent.HIDE_LOADING:
                        if (listener != null)
                            listener.hideLoading();
                        break;
                    case ChangePasswordEvent.CHANGE_PASSWORD_DONE:
                        if (listener != null)
                            listener.done();
                        break;
                }
            }
        });
    }
}
