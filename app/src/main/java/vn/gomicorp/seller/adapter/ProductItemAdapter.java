package vn.gomicorp.seller.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.LoadingHolder;
import vn.gomicorp.seller.adapter.holder.ProductItemHolder;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class ProductItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

    @Override
    public int getItemViewType(int position) {
        Product product = productList.get(position);
        if (product == null)
            return ProductItemType.VIEW_LOADING;
        else
            return ProductItemType.VIEW_ITEM;
    }

    public void notifyItemChanged(Product product) {
        int pos = getPosition(product);
        if (pos != NOT_FOUND)
            notifyItemChanged(pos);
    }

    public void notifyItemRemoved(Product product) {
        int pos = getPosition(product);
        if (pos != NOT_FOUND) {
            productList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ProductItemType.VIEW_ITEM:
                return ProductItemHolder.getInstance(parent);
            default:
                return LoadingHolder.getInstance(parent);
        }
    }

    public void setLoading() {
        isLoading = true;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductItemHolder) {
            ((ProductItemHolder) holder).bind(productList.get(position), productHandler);
        }
        if (position == getItemCount() - 1)
            isLoading = false;
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    interface ProductItemType {
        int VIEW_LOADING = 0;
        int VIEW_ITEM = 1;
    }
}
