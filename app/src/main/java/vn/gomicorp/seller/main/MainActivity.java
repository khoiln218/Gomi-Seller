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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.main.home.HomeFragment;
import vn.gomicorp.seller.main.market.MarketFragment;
import vn.gomicorp.seller.main.market.MarketViewModel;
import vn.gomicorp.seller.main.mypage.MyPageFragment;
import vn.gomicorp.seller.main.notification.NotificationFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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

        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static MarketViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(MarketViewModel.class);
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
                loadFragment(new HomeFragment());
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
