package vn.gomicorp.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.databinding.GenderItemBinding;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public class GenderAdapter extends BaseAdapter {
    private List<String> genders;

    public GenderAdapter() {
        genders = new ArrayList<>();
        genders.add("Nữ");
        genders.add("Nam");
        genders.add("Khác");
    }

    @Override
    public int getCount() {
        return genders.size();
    }

    @Override
    public String getItem(int position) {
        return genders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GenderItemBinding binding;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = GenderItemBinding.inflate(inflater, parent, false);
        } else {
            binding = DataBindingUtil.bind(convertView);
        }
        binding.setName(getItem(position));
        binding.executePendingBindings();
        return binding.getRoot();
    }
}
