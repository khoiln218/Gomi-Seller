package vn.gomicorp.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.databinding.CountryItemBinding;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class LocationAdapter extends BaseAdapter {
    public static final int COUNTRY = 0;
    public static final int PROVINCE = 1;
    public static final int DISTRICT = 2;

    private int type;

    List<Location> locations;
    Context context;

    public LocationAdapter(Context applicationContext, List<Location> locations) {
        context = applicationContext;
        setData(locations);
    }

    public void setData(List<Location> countries) {
        this.locations = countries;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int i) {
        return locations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
        CountryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.country_item, viewGroup, false);
        binding.setLocation(getItem(i));
        view = binding.getRoot();
//        } else {
//            view.setTag(getItem(i).getCode());
//        }
        return view;
    }
}
