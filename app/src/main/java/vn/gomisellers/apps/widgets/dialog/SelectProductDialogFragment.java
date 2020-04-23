package vn.gomisellers.apps.widgets.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.DialogSelectProductBinding;
import vn.gomisellers.apps.event.OnSelectedListener;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class SelectProductDialogFragment extends BottomSheetDialogFragment {
    private static final String PRODUCT_EXTRA = "PRODUCT_EXTRA";
    private Product product;
    private DialogSelectProductBinding binding;
    private OnSelectedListener listener;

    public void setListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    public static SelectProductDialogFragment getInstance(Product product) {
        SelectProductDialogFragment selectProductDialogFragment = new SelectProductDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_EXTRA, product);
        selectProductDialogFragment.setArguments(bundle);
        return selectProductDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_select_product, container, false);
        if (binding == null)
            binding = DialogSelectProductBinding.bind(root);
        binding.setProduct(product);
        binding.setListener(listener);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
            dismiss();
        product = getArguments().getParcelable(PRODUCT_EXTRA);
    }
}
