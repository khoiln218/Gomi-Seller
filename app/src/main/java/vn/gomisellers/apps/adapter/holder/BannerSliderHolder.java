package vn.gomisellers.apps.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomisellers.apps.data.source.model.data.Collection;
import vn.gomisellers.apps.databinding.SliderBannerBinding;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class BannerSliderHolder extends RecyclerView.ViewHolder {
    SliderBannerBinding binding;

    public static BannerSliderHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        SliderBannerBinding binding = SliderBannerBinding.inflate(inflater, viewGroup, false);
        return new BannerSliderHolder(binding);
    }

    public BannerSliderHolder(SliderBannerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setBannerSlider(Collection collection) {
        binding.setCollection(collection);
        binding.executePendingBindings();
    }
}
