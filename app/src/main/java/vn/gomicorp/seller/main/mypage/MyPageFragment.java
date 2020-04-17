package vn.gomicorp.seller.main.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;

import vn.gomicorp.seller.BaseFragment;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.FragmentMypageBinding;
import vn.gomicorp.seller.main.MainActivity;
import vn.gomicorp.seller.main.mypage.info.AccountInformationActivity;

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
        viewModel = MainActivity.obtainViewModel(getActivity(), MainActivity.MY_PAGE);
        getBinding().setViewModel(getViewModel());
        getBinding().setLifecycleOwner(this);
        initCmd();
        return binding.getRoot();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<MyPageEvent>() {
            @Override
            public void onChanged(MyPageEvent event) {
                if (event.getCode() == MyPageEvent.UPDATE_INFO) {
                    startActivity(new Intent(getActivity(), AccountInformationActivity.class));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().initAccountInformation();
    }
}
