package vn.gomicorp.seller.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.ProductItemHolder;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemHolder> {
    private List<Product> productList;
    private ProductHandler listener;
    private Product productChange = null;

    public interface ToggleProductHandler {
        void toggle(Product product);
    }

    public ToggleProductHandler toggleProductHandler = new ToggleProductHandler() {
        @Override
        public void toggle(Product product) {
            if (listener != null)
                listener.onPick(product);
            itemUpdate(product);
        }
    };

    private void itemUpdate(Product product) {
        productChange = product;
    }

    private int getPosition(Product product) {
        if (getItemCount() != 0)
            for (int i = 0; i < productList.size(); i++) {
                if (TextUtils.equals(productList.get(i).getId(), product.getId())) return i;
            }
        return 0;
    }

    public ProductItemAdapter(List<Product> productList, ProductHandler listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public void setProductList(List<Product> productList) {
        if (productChange == null) {
            this.productList = productList;
            notifyDataSetChanged();
        } else {
            int pos = getPosition(productChange);
            notifyItemChanged(pos);
        }
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ProductItemHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        holder.bind(productList.get(position), listener, toggleProductHandler);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }
}
