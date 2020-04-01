package vn.gomicorp.seller.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.ProductItemHolder;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemHolder> {
    final int NOT_FOUND = -1;
    private List<Product> productList;
    private ProductHandler productHandler;
    private OnLoadMoreListener onLoadMoreListener;

    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    boolean isLoading = true;

    private int getPosition(Product product) {
        if (getItemCount() > 0)
            for (int i = 0; i < productList.size(); i++) {
                if (TextUtils.equals(productList.get(i).getId(), product.getId())) return i;
            }
        return NOT_FOUND;
    }

    public ProductItemAdapter(List<Product> productList, ProductHandler productHandler, final OnLoadMoreListener onLoadMoreListener) {
        this.productList = productList;
        this.productHandler = productHandler;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addOnScrollListener(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();

                    int[] firstVisibleItems = null;
                    firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    if (firstVisibleItems != null && firstVisibleItems.length > 0)
                        pastVisibleItems = firstVisibleItems[0];

                    if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        if (onLoadMoreListener != null)
                            onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void notifyItemChanged(Product product) {
        int pos = getPosition(product);
        if (pos != NOT_FOUND)
            notifyItemChanged(pos);
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

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading() {
        isLoading = true;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        holder.bind(productList.get(position), productHandler);
        if (position == getItemCount() - 1)
            isLoading = false;
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }
}
