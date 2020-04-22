package vn.gomicorp.seller.main.mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseFragment;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.FragmentMypageBinding;
import vn.gomicorp.seller.main.mypage.info.AccountInformationActivity;
import vn.gomicorp.seller.main.mypage.setting.AccountSettingActivity;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;

public class MyPageFragment extends BaseFragment<MyPageViewModel, FragmentMypageBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        if (binding == null)
            binding = FragmentMypageBinding.bind(view);
        viewModel = ViewModelProviders.of(this).get(MyPageViewModel.class);
        getBinding().setViewModel(getViewModel());
        getBinding().setLifecycleOwner(this);
        initCmd();
        return binding.getRoot();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<MyPageEvent>() {
            @Override
            public void onChanged(MyPageEvent event) {
                switch (event.getCode()) {
                    case MyPageEvent.UPDATE_INFO:
                        startActivity(new Intent(getActivity(), AccountInformationActivity.class));
                        break;
                    case MyPageEvent.SETTING:
                        startActivityForResult(new Intent(getActivity(), AccountSettingActivity.class), GomiConstants.REQUEST_ACCOUNT_SIGN_OUT);
                        break;
                    case MyPageEvent.SIGN_OUT:
                        Intents.startLoginActivity(getActivity());
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().initAccountInformation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }

    public void setAvatar(Uri imageUri) {
        getViewModel().setAvatar(imageUri);
    }
}
