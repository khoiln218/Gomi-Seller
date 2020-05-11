package vn.gomisellers.apps.main.mypage.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.adapter.holder.LoadingHolder;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.databinding.OrderItemBinding;
import vn.gomisellers.apps.event.OnLoadMoreListener;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class OrderAdapter extends RecyclerView.Adapter {
    private List<Order> orderList;
    private OrderHandler orderHandler;
    private OnLoadMoreListener onLoadMoreListener;

    private int pastVisibleItems;
    private int totalItemCount;

    boolean isLoading = true;

    public OrderAdapter(List<Order> orderList, OrderHandler orderHandler, OnLoadMoreListener onLoadMoreListener) {
        this.orderList = orderList;
        this.orderHandler = orderHandler;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addOnScrollListener(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && (pastVisibleItems + 5) >= totalItemCount) {
                        if (onLoadMoreListener != null)
                            onLoadMoreListener.onLoadMore();
                    }
                }
            });
        }
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.VIEW_ITEM) {
            return OrderHolder.getInstance(parent);
        }
        return LoadingHolder.getInstance(parent);
    }

    public void setLoading() {
        isLoading = true;
    }

    @Override
    public int getItemViewType(int position) {
        Order order = orderList.get(position);
        if (order == null)
            return ItemType.VIEW_LOADING;
        else
            return ItemType.VIEW_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderHolder)
            ((OrderHolder) holder).bind(orderList.get(position), orderHandler);
        if (position == getItemCount() - 1)
            isLoading = false;
    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    static class OrderHolder extends RecyclerView.ViewHolder {
        private OrderItemBinding binding;

        public static OrderHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            OrderItemBinding binding = OrderItemBinding.inflate(inflater, viewGroup, false);
            return new OrderHolder(binding);
        }

        private OrderHolder(OrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order, OrderHandler orderHandler) {
            binding.setOrder(order);
            binding.setOrderHandler(orderHandler);
            binding.executePendingBindings();
        }
    }

    interface ItemType {
        int VIEW_LOADING = 0;
        int VIEW_ITEM = 1;
    }
}
