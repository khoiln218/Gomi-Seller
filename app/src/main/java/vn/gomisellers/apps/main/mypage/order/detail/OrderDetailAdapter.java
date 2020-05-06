package vn.gomisellers.apps.main.mypage.order.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.adapter.holder.LoadingHolder;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.databinding.OrderProductListItemBinding;
import vn.gomisellers.apps.databinding.OrderSummaryItemBinding;
import vn.gomisellers.apps.databinding.PaymentItemBinding;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter {
    private List<Integer> panelList;
    private Order order;
    private OrderProductAdapter orderProductAdapter;

    public OrderDetailAdapter(ProductHandler productHandler) {
        panelList = new ArrayList<>();
        orderProductAdapter = new OrderProductAdapter(new ArrayList<OrderDetail>(), productHandler);
        setPanelList();
    }

    public void setOrder(Order order) {
        this.order = order;
        setPanelList();
        notifyDataSetChanged();
    }

    private void setPanelList() {
        panelList.clear();
        if (order == null) {
            panelList.add(PanelType.LOADING);
            return;
        }
        panelList.add(PanelType.SUMMARY);
        panelList.add(PanelType.PRODUCT_LIST);
        panelList.add(PanelType.PAYMENT);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PanelType.SUMMARY:
                return SummaryHolder.getInstance(parent);
            case PanelType.PRODUCT_LIST:
                return ProductHolder.getInstance(parent);
            case PanelType.PAYMENT:
                return PaymentHolder.getInstance(parent);
        }
        return LoadingHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (panelList.get(position)) {
            case PanelType.SUMMARY:
                ((SummaryHolder) holder).bind(order);
                break;
            case PanelType.PRODUCT_LIST:
                ((ProductHolder) holder).bind(order.getOrderDetails(), orderProductAdapter);
                break;
            case PanelType.PAYMENT:
                ((PaymentHolder) holder).bind(order);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return panelList.get(position);
    }

    @Override
    public int getItemCount() {
        return panelList == null ? 0 : panelList.size();
    }

    static class SummaryHolder extends RecyclerView.ViewHolder {
        private OrderSummaryItemBinding binding;

        public static SummaryHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            OrderSummaryItemBinding binding = OrderSummaryItemBinding.inflate(inflater, viewGroup, false);
            return new SummaryHolder(binding);
        }

        private SummaryHolder(OrderSummaryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.setOrder(order);
            binding.executePendingBindings();
        }
    }

    static class ProductHolder extends RecyclerView.ViewHolder {
        private OrderProductListItemBinding binding;

        public static ProductHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            OrderProductListItemBinding binding = OrderProductListItemBinding.inflate(inflater, viewGroup, false);
            return new ProductHolder(binding);
        }

        private ProductHolder(OrderProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(List<OrderDetail> orderDetails, OrderProductAdapter adapter) {
            adapter.setOrderDetailList(orderDetails);
            binding.setOrderProductAdapter(adapter);
            binding.executePendingBindings();
        }
    }

    static class PaymentHolder extends RecyclerView.ViewHolder {
        private PaymentItemBinding binding;

        public static PaymentHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            PaymentItemBinding binding = PaymentItemBinding.inflate(inflater, viewGroup, false);
            return new PaymentHolder(binding);
        }

        private PaymentHolder(PaymentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.setOrder(order);
            binding.executePendingBindings();
        }
    }

    interface PanelType {
        int LOADING = 0;
        int SUMMARY = 1;
        int PRODUCT_LIST = 2;
        int PAYMENT = 3;
    }
}
