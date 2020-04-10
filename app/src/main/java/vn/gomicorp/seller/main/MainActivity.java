package vn.gomicorp.seller.main;

import android.content.Intent;
import android.os.Bundle;
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

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.main.home.HomeFragment;
import vn.gomicorp.seller.main.home.HomeViewModel;
import vn.gomicorp.seller.main.market.MarketFragment;
import vn.gomicorp.seller.main.market.MarketViewModel;
import vn.gomicorp.seller.main.mypage.MyPageFragment;
import vn.gomicorp.seller.main.mypage.MyPageViewModel;
import vn.gomicorp.seller.main.notification.NotificationFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final int HOME = 1;
    public static final int MARKET = 2;
    public static final int NOTIFICATION = 3;
    public static final int MY_PAGE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_main);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

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
            case HOME:
                return ViewModelProviders.of(activity).get(HomeViewModel.class);
            case MARKET:
                return ViewModelProviders.of(activity).get(MarketViewModel.class);
            case MY_PAGE:
                return ViewModelProviders.of(activity).get(MyPageViewModel.class);
            default:
                return null;
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
        finish();
        super.onBackPressed();
    }
}
