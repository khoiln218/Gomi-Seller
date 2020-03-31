package vn.gomicorp.seller.main.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.FragmentMypageBinding;
import vn.gomicorp.seller.main.MainActivity;

public class MyPageFragment extends Fragment {
    FragmentMypageBinding binding;
    MyPageViewModel viewModel;

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
        viewModel = (MyPageViewModel) MainActivity.obtainViewModel(getActivity(), MainActivity.MY_PAGE);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
