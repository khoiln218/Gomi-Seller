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
    private ProductHandler productHandler;

    private int getPosition(Product product) {
        if (getItemCount() > 0)
            for (int i = 0; i < productList.size(); i++) {
                if (TextUtils.equals(productList.get(i).getId(), product.getId())) return i;
            }
        return 0;
    }

    public ProductItemAdapter(List<Product> productList, ProductHandler productHandler) {
        this.productList = productList;
        this.productHandler = productHandler;
    }

    public void notifyItemChanged(Product product) {
        if (product != null) {
            int pos = getPosition(product);
            notifyItemChanged(pos);
        }
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ProductItemHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        holder.bind(productList.get(position), productHandler);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }
}
