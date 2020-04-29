package vn.gomisellers.apps.main.mypage.order.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.databinding.OrderProductItemBinding;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderProductAdapter extends RecyclerView.Adapter {
    private List<OrderDetail> orderDetailList;
    private ProductHandler productHandler;

    public OrderProductAdapter(List<OrderDetail> orderDetailList, ProductHandler productHandler) {
        this.orderDetailList = orderDetailList;
        this.productHandler = productHandler;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OrderProductItemHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((OrderProductItemHolder) holder).bind(orderDetailList.get(position), productHandler);
    }

    @Override
    public int getItemCount() {
        return orderDetailList == null ? 0 : orderDetailList.size();
    }

    static class OrderProductItemHolder extends RecyclerView.ViewHolder {

        private OrderProductItemBinding binding;

        static OrderProductItemHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            OrderProductItemBinding binding = OrderProductItemBinding.inflate(inflater, viewGroup, false);
            return new OrderProductItemHolder(binding);
        }

        private OrderProductItemHolder(OrderProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderDetail orderDetail, ProductHandler productHandler) {
            binding.setOrderDetail(orderDetail);
            binding.setProductHandler(productHandler);
            binding.executePendingBindings();
        }
    }
}
