package vn.gomisellers.apps.main.mypage.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.databinding.OrderItemBinding;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class OrderAdapter extends RecyclerView.Adapter {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OrderHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((OrderHolder) holder).bind(orderList.get(position));
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

        public void bind(Order order) {
            binding.setOrder(order);
            binding.executePendingBindings();
        }
    }
}
