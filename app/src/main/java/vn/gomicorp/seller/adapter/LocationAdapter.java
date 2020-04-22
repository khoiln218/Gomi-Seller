package vn.gomicorp.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.databinding.CountryItemBinding;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class LocationAdapter extends BaseAdapter {
    private List<Location> locations;

    public LocationAdapter(List<Location> locations) {
        setData(locations);
    }

    public void setData(List<Location> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return locations == null ? 0 : locations.size();
    }

    @Override
    public Location getItem(int i) {
        return locations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return locations.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CountryItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = CountryItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.bind(view);
        }
        binding.setLocation(getItem(i));
        binding.executePendingBindings();
        return binding.getRoot();
    }
}
