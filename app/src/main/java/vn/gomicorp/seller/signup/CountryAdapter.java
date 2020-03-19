package vn.gomicorp.seller.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Country;
import vn.gomicorp.seller.databinding.CountryItemBinding;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class CountryAdapter extends BaseAdapter {
    List<Country> contries;
    Context context;

    CountryAdapter(Context applicationContext, List<Country> contries) {
        context = applicationContext;
        setData(contries);
    }

    public void setData(List<Country> countries) {
        this.contries = countries;
    }

    @Override
    public int getCount() {
        return contries.size();
    }

    @Override
    public Country getItem(int i) {
        return contries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            CountryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.country_item, viewGroup, false);
            binding.setCountry(getItem(i));
            view = binding.getRoot();
        } else {
            view.setTag(getItem(i));
        }
        return view;
    }
}
