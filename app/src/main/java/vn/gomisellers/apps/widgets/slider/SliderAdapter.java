package vn.gomisellers.apps.widgets.slider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<SliderView> sliderViews;

    public SliderAdapter(Context context) {
        this.context = context;
        sliderViews = new ArrayList<>();
    }

    public void addSliderView(SliderView sliderView) {
        sliderViews.add(sliderView);
        notifyDataSetChanged();
    }

    public void clearSliderViews() {
        sliderViews.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sliderViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SliderView sliderView = sliderViews.get(position);
        View view = sliderView.getView();
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
