package vn.gomicorp.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.data.source.model.data.Attribute;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.LayoutNoResultBinding;
import vn.gomicorp.seller.databinding.LayoutProductAttributeBinding;
import vn.gomicorp.seller.databinding.LayoutSummaryBinding;
import vn.gomicorp.seller.utils.Strings;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class ProductDetailAdapter extends RecyclerView.Adapter {
    private Product product;
    private List<Integer> panelList;

    public ProductDetailAdapter(Product product) {
        this.panelList = new ArrayList<>();
        this.product = product;
    }

    public void setProduct(Product product) {
        if (product == null || Strings.isNullOrEmpty(product.getId())) {
            panelList.clear();
            panelList.add(PanelType.NOT_FOUND);
        } else {
            this.product = product;
            panelList.clear();
            panelList.add(PanelType.SUMMARY);

            if (product.getAttributeList().size() > 0)
                panelList.add(PanelType.ATTRIBUTE);

//            panelList.add(PanelType.DESCRIPTION);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return panelList.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PanelType.SUMMARY:
                return SummaryHolder.getInstance(parent);
            case PanelType.ATTRIBUTE:
                return AttributeHolder.getInstance(parent);
            case PanelType.DESCRIPTION:
//                return new DescriptionHolder(parent);
            default:
                return NoResultHolder.getInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SummaryHolder) {
            ((SummaryHolder) holder).bind(product);
        } else if (holder instanceof AttributeHolder) {
            ((AttributeHolder) holder).bind(product.getAttributeList());
        } else if (holder instanceof DescriptionHolder) {
//            ((DescriptionHolder) holder).bind(product.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return panelList == null ? 0 : panelList.size();
    }

    static class SummaryHolder extends RecyclerView.ViewHolder {
        LayoutSummaryBinding binding;

        public static SummaryHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            LayoutSummaryBinding binding = LayoutSummaryBinding.inflate(inflater, viewGroup, false);
            return new SummaryHolder(binding);
        }

        SummaryHolder(LayoutSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.setProduct(product);
            binding.executePendingBindings();
        }
    }

    static class DescriptionHolder extends RecyclerView.ViewHolder {

        public DescriptionHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(String description) {
        }
    }

    static class AttributeHolder extends RecyclerView.ViewHolder {
        LayoutProductAttributeBinding binding;

        public static AttributeHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            LayoutProductAttributeBinding binding = LayoutProductAttributeBinding.inflate(inflater, viewGroup, false);
            return new AttributeHolder(binding);
        }

        AttributeHolder(LayoutProductAttributeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(List<Attribute> attributeList) {
            binding.setAttributes(attributeList);
            binding.executePendingBindings();
        }
    }

    static class NoResultHolder extends RecyclerView.ViewHolder {

        public static NoResultHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            LayoutNoResultBinding binding = LayoutNoResultBinding.inflate(inflater, viewGroup, false);
            return new NoResultHolder(binding);
        }

        NoResultHolder(LayoutNoResultBinding binding) {
            super(binding.getRoot());
        }
    }
}
