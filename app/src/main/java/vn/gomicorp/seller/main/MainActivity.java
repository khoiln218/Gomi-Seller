package vn.gomicorp.seller.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.main.home.HomeFragment;
import vn.gomicorp.seller.main.home.HomeViewModel;
import vn.gomicorp.seller.main.market.MarketFragment;
import vn.gomicorp.seller.main.market.MarketViewModel;
import vn.gomicorp.seller.main.mypage.MyPageFragment;
import vn.gomicorp.seller.main.mypage.MyPageViewModel;
import vn.gomicorp.seller.main.notification.NotificationFragment;
import vn.gomicorp.seller.utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final int HOME = 1;
    public static final int MARKET = 2;
    public static final int NOTIFICATION = 3;
    public static final int MY_PAGE = 4;

    private boolean isExit = false;
    private Handler handler;
    private Runnable exitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_main);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        handler = new Handler();
        exitApp = new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        };

        loadFragment(HomeFragment.getInstance());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static ViewModel obtainViewModel(FragmentActivity activity, int screen) {
        switch (screen) {
            case MARKET:
                return ViewModelProviders.of(activity).get(MarketViewModel.class);
            case MY_PAGE:
                return ViewModelProviders.of(activity).get(MyPageViewModel.class);
            default:
                return ViewModelProviders.of(activity).get(HomeViewModel.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                loadFragment(HomeFragment.getInstance());
                return true;

            case R.id.nav_market:
                loadFragment(MarketFragment.getInstance());
                return true;

            case R.id.nav_notify:
                loadFragment(new NotificationFragment());
                return true;

            case R.id.nav_account:
                loadFragment(new MyPageFragment());
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            handler.removeCallbacks(exitApp);
            finish();
        } else {
            isExit = true;
            ToastUtils.showToast(getString(R.string.exit_app));
            handler.removeCallbacks(exitApp);
            handler.postDelayed(exitApp, 2000);
        }
    }
}
