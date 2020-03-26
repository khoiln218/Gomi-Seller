package vn.gomicorp.seller.widgets.slider;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import vn.gomicorp.seller.R;

public class SliderLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final long DELAY_MS = 500;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SliderAdapter pagerAdapter;

    private Timer timer;
    private Handler handler = new Handler();
    private int scrollTimeSec = 2;

    private int currentPage = 0;

    public SliderLayout(Context context) {
        super(context);
        setLayout(context);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout(context);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout(context);
    }

    public void addSliderView(SliderView sliderView) {
        pagerAdapter.addSliderView(sliderView);
    }

    public void clearSliderView() {
        pagerAdapter.clearSliderViews();
    }

    public void setScrollTimeSec(int scrollTimeSec) {
        this.scrollTimeSec = scrollTimeSec;
        startAutoCycle();
    }

    private void setLayout(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_slider, this, true);
        viewPager = view.findViewById(R.id.pager_slider);
        tabLayout = view.findViewById(R.id.tab_indicator);

        pagerAdapter = new SliderAdapter(context);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void startAutoCycle() {
        if (timer != null)
            timer.cancel();

        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == pagerAdapter.getCount())
                    currentPage = 0;

                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, scrollTimeSec * 1000);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        this.currentPage = i;

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
