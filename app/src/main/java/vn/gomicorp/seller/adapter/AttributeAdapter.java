package vn.gomicorp.seller.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Attribute;
import vn.gomicorp.seller.databinding.ListAttributeItemBinding;
import vn.gomicorp.seller.utils.Utils;

/**
 * Created by KHOI LE on 3/30/2020.
 */
public class AttributeAdapter extends RecyclerView.Adapter {

    private List<Attribute> attributes;

    public AttributeAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AttributeItemHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AttributeItemHolder) holder).bind(attributes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return attributes == null ? 0 : attributes.size();
    }

    static class AttributeItemHolder extends RecyclerView.ViewHolder {
        ListAttributeItemBinding binding;

        public static AttributeItemHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            ListAttributeItemBinding binding = ListAttributeItemBinding.inflate(inflater, viewGroup, false);
            return new AttributeItemHolder(binding);
        }

        public AttributeItemHolder(ListAttributeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Attribute attribute, int position) {
            binding.setAttribute(attribute);
            binding.executePendingBindings();
            binding.textAttributeContent.post(new Runnable() {
                @Override
                public void run() {
                    if (binding.textAttributeContent.getLineCount() > 3) {
                        binding.layoutAttribute.setOrientation(LinearLayout.VERTICAL);
                    }
                }
            });
            binding.layoutAttribute.setBackgroundColor(position % 2 != 0 ? Utils.getColorResources(R.color.colorBackground) : Color.WHITE);
        }
    }
}
