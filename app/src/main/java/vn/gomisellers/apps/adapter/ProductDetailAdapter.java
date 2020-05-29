package vn.gomisellers.apps.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Attribute;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.HolderProductDescriptionBinding;
import vn.gomisellers.apps.databinding.LayoutNoResultBinding;
import vn.gomisellers.apps.databinding.LayoutProductAttributeBinding;
import vn.gomisellers.apps.databinding.LayoutSummaryBinding;
import vn.gomisellers.apps.event.ProductDetailHandler;
import vn.gomisellers.apps.event.ProductHandler;
import vn.gomisellers.apps.utils.Strings;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class ProductDetailAdapter extends RecyclerView.Adapter {
    private Product product;
    private List<Integer> panelList;
    private ProductHandler productHandler;
    private ProductDetailHandler productDetailHandler;

    public ProductDetailAdapter(Product product, ProductHandler productHandler, ProductDetailHandler productDetailHandler) {
        this.panelList = new ArrayList<>();
        this.product = product;
        this.productHandler = productHandler;
        this.productDetailHandler = productDetailHandler;
    }

    public void setProduct(Product product) {
        if (product == null || Strings.isNullOrEmpty(product.getId())) {
            panelList.clear();
            panelList.add(PanelType.NOT_FOUND);
        } else {
            this.product = product;
            panelList.clear();
            panelList.add(PanelType.SUMMARY);

            if (product.getAttributeList() != null && product.getAttributeList().size() > 0)
                panelList.add(PanelType.ATTRIBUTE);

            if (!TextUtils.isEmpty(product.getDescription()))
                panelList.add(PanelType.DESCRIPTION);
        }
        notifyDataSetChanged();
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
                return DescriptionHolder.getInstance(parent);
            default:
                return NoResultHolder.getInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SummaryHolder) {
            ((SummaryHolder) holder).bind(product, productHandler);
        } else if (holder instanceof AttributeHolder) {
            ((AttributeHolder) holder).bind(product.getAttributeList());
        } else if (holder instanceof DescriptionHolder) {
            ((DescriptionHolder) holder).bind(product.getDescription(), productDetailHandler);
        }
    }

    @Override
    public int getItemCount() {
        return panelList == null ? 0 : panelList.size();
    }

    static class SummaryHolder extends RecyclerView.ViewHolder {
        private LayoutSummaryBinding binding;

        public static SummaryHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            LayoutSummaryBinding binding = LayoutSummaryBinding.inflate(inflater, viewGroup, false);
            return new SummaryHolder(binding);
        }

        SummaryHolder(LayoutSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product, ProductHandler productHandler) {
            binding.setProduct(product);
            binding.setProductHandler(productHandler);
            binding.executePendingBindings();
        }
    }

    static class DescriptionHolder extends RecyclerView.ViewHolder {
        private HolderProductDescriptionBinding binding;

        public static DescriptionHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            HolderProductDescriptionBinding binding = HolderProductDescriptionBinding.inflate(inflater, viewGroup, false);
            return new DescriptionHolder(binding);
        }

        private DescriptionHolder(HolderProductDescriptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final String description, final ProductDetailHandler productDetailHandler) {
            binding.setDescription(description);
            binding.setProductDetailHandler(productDetailHandler);
            binding.executePendingBindings();
        }
    }

    static class AttributeHolder extends RecyclerView.ViewHolder {
        private LayoutProductAttributeBinding binding;

        public static AttributeHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            LayoutProductAttributeBinding binding = LayoutProductAttributeBinding.inflate(inflater, viewGroup, false);
            return new AttributeHolder(binding);
        }

        private AttributeHolder(LayoutProductAttributeBinding binding) {
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

    interface PanelType {
        int NOT_FOUND = 0;
        int SUMMARY = 1;
        int ATTRIBUTE = 2;
        int DESCRIPTION = 3;
    }
}
