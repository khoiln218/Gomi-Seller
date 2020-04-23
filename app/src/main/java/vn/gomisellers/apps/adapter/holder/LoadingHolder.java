package vn.gomisellers.apps.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomisellers.apps.databinding.LayoutLoadingBinding;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class LoadingHolder extends RecyclerView.ViewHolder {
    public static LoadingHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        LayoutLoadingBinding binding = LayoutLoadingBinding.inflate(inflater, viewGroup, false);
        return new LoadingHolder(binding);
    }

    public LoadingHolder(LayoutLoadingBinding binding) {
        super(binding.getRoot());
    }
}
