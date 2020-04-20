package vn.gomicorp.seller.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.event.OnClickListener;
import vn.gomicorp.seller.main.home.HomeFragment;
import vn.gomicorp.seller.main.market.MarketFragment;
import vn.gomicorp.seller.main.mypage.MyPageFragment;
import vn.gomicorp.seller.main.notification.NotificationFragment;
import vn.gomicorp.seller.utils.AlertDialogs;
import vn.gomicorp.seller.utils.MediaHelper;
import vn.gomicorp.seller.utils.PermissionHelper;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.widgets.dialog.ImageChooserDialogFragment;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainListener {

    private PermissionHelper permissionHelper;
    private boolean dialogShowing = false;

    private boolean isExit = false;
    private Handler handler;
    private Runnable exitApp;

    private HomeFragment homeFragment;
    private MarketFragment marketFragment;
    private NotificationFragment notificationFragment;
    private MyPageFragment myPageFragment;

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

        permissionHelper = new PermissionHelper(this, PermissionHelper.photo_permissions);

        handler = new Handler();
        exitApp = new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        };

        homeFragment = new HomeFragment();
        marketFragment = new MarketFragment();
        notificationFragment = new NotificationFragment();
        myPageFragment = new MyPageFragment();

        loadFragment(homeFragment);
    }

    @Override
    protected void initBinding() {

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
                loadFragment(homeFragment);
                return true;

            case R.id.nav_market:
                loadFragment(marketFragment);
                return true;

            case R.id.nav_notify:
                loadFragment(notificationFragment);
                return true;

            case R.id.nav_account:
                loadFragment(myPageFragment);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null)
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void cropImage(Uri uri) {
        CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                .setAutoZoomEnabled(true)
                .setShowCropOverlay(true)
                .start(this);
    }

    /**
     * Pick & Crop Image
     */
    private void showImageOptions() {

        String title = getString(R.string.cover);

        ImageChooserDialogFragment fragment = ImageChooserDialogFragment.instance(title, false);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
        fragment.setClickListener(new OnClickListener() {
            @Override
            public void onTakePhoto() {
                try {
                    File file = MediaHelper.createImageFile();
                    Uri imageUri = MediaHelper.uriFromFile(file);
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                    if (fragment instanceof MyPageFragment)
                        ((MyPageFragment) fragment).setAvatar(imageUri);
                    MediaHelper.dispatchTakePictureIntent(MainActivity.this, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChooseImage() {
                MediaHelper.dispatchPickPictureIntent(MainActivity.this);
            }

            @Override
            public void onViewPhoto() {
            }

            @Override
            public void onRemovePhoto() {
            }
        });
    }

    /**
     * Check Photo Permissions
     */
    @Override
    public void requestPermission() {
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                showImageOptions();
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                showPermissionDialog();
            }

            @Override
            public void onPermissionDenied() {
                showPermissionDialog();
            }

            @Override
            public void onPermissionDeniedBySystem() {
                showPermissionDialog();
            }
        });
    }

    private void showPermissionDialog() {
        if (!dialogShowing) {
            dialogShowing = true;

            AlertDialogs.show(this, String.format(getString(R.string.title_need_camera_permission), getString(R.string.app_name)), getString(R.string.msg_need_camera_permission), getString(R.string.btn_cancel), getString(R.string.btn_setting), new AlertDialogs.OnClickListener() {
                @Override
                public void onNegativeButtonClick(DialogInterface dialog, int which) {
                    dialogShowing = false;
                    dialog.dismiss();
                }

                @Override
                public void onPositiveButtonClick(DialogInterface dialog, int which) {
                    dialogShowing = false;
                    permissionHelper.launchAppDetailsSettings();
                    dialog.dismiss();
                }
            });
        }
    }
}
