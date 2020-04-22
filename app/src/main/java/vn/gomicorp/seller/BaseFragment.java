package vn.gomicorp.seller;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

/**
 * Created by KHOI LE on 4/15/2020.
 */
public class BaseFragment<T, V> extends Fragment {
    protected ViewDataBinding binding;
    protected ViewModel viewModel;

    protected T getViewModel() {
        return (T) viewModel;
    }

    protected V getBinding() {
        return (V) binding;
    }
}
