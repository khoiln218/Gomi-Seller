package vn.gomisellers.apps;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

/**
 * Created by KHOI LE on 4/15/2020.
 */
public class BaseFragment<VM extends ViewModel, V extends ViewDataBinding> extends Fragment {
    protected ViewDataBinding binding;
    protected ViewModel viewModel;

    protected VM getViewModel() {
        return (VM) viewModel;
    }

    protected V getBinding() {
        return (V) binding;
    }
}
