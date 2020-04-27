package vn.gomisellers.apps.shopinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Location;
import vn.gomisellers.apps.databinding.ActivityShopInfomationBinding;
import vn.gomisellers.apps.event.OnClickListener;
import vn.gomisellers.apps.utils.AlertDialogs;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.MediaHelper;
import vn.gomisellers.apps.utils.PermissionHelper;
import vn.gomisellers.apps.utils.ToastUtils;
import vn.gomisellers.apps.widgets.CountryView;
import vn.gomisellers.apps.widgets.LocationView;
import vn.gomisellers.apps.widgets.WheelView;
import vn.gomisellers.apps.widgets.dialog.ImageChooserDialogFragment;

public class ShopInformationActivity extends BaseActivity<ShopInformationViewModel, ActivityShopInfomationBinding> {
    private PermissionHelper permissionHelper;
    private boolean dialogShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null) finish();
        boolean isUpdate = getIntent().getBooleanExtra(GomiConstants.EXTRA_UPDATE, false);
        initBinding();
        initCmd();
        permissionHelper = new PermissionHelper(this, PermissionHelper.photo_permissions);
        getViewModel().setUpdate(isUpdate);
        if (isUpdate) {
            initToolbar(getString(R.string.update_shop_info));
            getViewModel().requestShopInformation();
        } else {
            getViewModel().requestLocationCountryId();
        }
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ShopInfoEvent>() {
            @Override
            public void onChanged(ShopInfoEvent event) {
                switch (event.getCode()) {
                    case ShopInfoEvent.CREATE_SUCCESS:
                        createSuccess();
                        break;
                    case ShopInfoEvent.UPDATE_SUCCESS:
                        ToastUtils.showToast("Cập nhật cửa hàng thành công");
                        setResult(Activity.RESULT_OK);
                        finish();
                        break;
                    case ShopInfoEvent.SHOW_COUNTRY_DIALOG:
                        showCountryDialog((LocationList) event.getData());
                        break;
                    case ShopInfoEvent.SHOW_PROVINCE_DIALOG:
                        showProvinceDialog((LocationList) event.getData());
                        break;
                    case ShopInfoEvent.SHOW_DISTRICT_DIALOG:
                        showDistrictDialog((LocationList) event.getData());
                        break;
                    case ShopInfoEvent.START_CROPPER:
                        cropImage((Uri) event.getData());
                        break;
                    case ShopInfoEvent.REQUEST_PERMISSION:
                        requestPermission();
                        break;
                }
            }
        });
    }

    private void createSuccess() {
        Intents.directToMainActivity(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_infomation);
        viewModel = ViewModelProviders.of(this).get(ShopInformationViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null)
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }

    private void cropImage(Uri uri) {
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
                    getViewModel().setCover(imageUri);
                    MediaHelper.dispatchTakePictureIntent(ShopInformationActivity.this, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChooseImage() {
                MediaHelper.dispatchPickPictureIntent(ShopInformationActivity.this);
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
    private void requestPermission() {
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

            AlertDialogs.show(this, String.format(getString(R.string.title_need_camera_permission), getString(R.string.app_name)), String.format(getString(R.string.msg_need_camera_permission), getString(R.string.app_name)), getString(R.string.btn_cancel), getString(R.string.btn_setting), new AlertDialogs.OnClickListener() {
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

    private void showCountryDialog(LocationList locationList) {
        final int[] selectIndex = {locationList.getSelectIndex()};
        View outerView = LayoutInflater.from(this).inflate(R.layout.country_view, null);
        final CountryView wv = outerView.findViewById(R.id.wheel_view_wv);
        wv.setItems(locationList.getLocations());
        wv.setSeletion(locationList.getSelectIndex());
        wv.setOnWheelViewListener(new CountryView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, Location item) {
                selectIndex[0] = wv.getSeletedIndex();
            }
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.country_title)
                .setView(outerView)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getViewModel().selectCountry(selectIndex[0]);
                    }
                })
                .show();
    }

    private void showProvinceDialog(LocationList locationList) {
        final int[] selectIndex = {locationList.getSelectIndex()};
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        final LocationView wv = outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(3);
        wv.setLocations(locationList.getLocations());
        wv.setSeletion(locationList.getSelectIndex());
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectIndex[0] = wv.getSeletedIndex();
            }
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.province_title)
                .setView(outerView)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getViewModel().selectProvince(selectIndex[0]);
                    }
                })
                .show();
    }

    private void showDistrictDialog(LocationList locationList) {
        final int[] selectIndex = {locationList.getSelectIndex()};
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        final LocationView wv = outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(3);
        wv.setLocations(locationList.getLocations());
        wv.setSeletion(locationList.getSelectIndex());
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectIndex[0] = wv.getSeletedIndex();
            }
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.district_title)
                .setView(outerView)
                .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getViewModel().selectDistrict(selectIndex[0]);
                    }
                })
                .show();
    }
}
