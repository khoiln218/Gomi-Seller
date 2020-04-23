package vn.gomisellers.apps.shopinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
import vn.gomisellers.apps.databinding.ActivityShopInfomationBinding;
import vn.gomisellers.apps.event.OnClickListener;
import vn.gomisellers.apps.utils.AlertDialogs;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.MediaHelper;
import vn.gomisellers.apps.utils.PermissionHelper;
import vn.gomisellers.apps.utils.ToastUtils;
import vn.gomisellers.apps.widgets.dialog.ImageChooserDialogFragment;

public class ShopInformationActivity extends BaseActivity<ShopInformationViewModel, ActivityShopInfomationBinding> {
    private PermissionHelper permissionHelper;
    private boolean dialogShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initCmd();
        permissionHelper = new PermissionHelper(this, PermissionHelper.photo_permissions);
        getViewModel().requestLocationCountryId();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ShopInfoEvent>() {
            @Override
            public void onChanged(ShopInfoEvent event) {
                switch (event.getCode()) {
                    case ShopInfoEvent.CREATE_ERROR:
                    case ShopInfoEvent.VERIFY_ERROR:
                        ToastUtils.showToast(event.getMessage());
                        break;
                    case ShopInfoEvent.CREATE_SUCCESS:
                        createSuccess();
                        break;
                    case ShopInfoEvent.VERIFY_SUCCESS:
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
