package vn.gomicorp.seller.shopinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityShopInfomationBinding;
import vn.gomicorp.seller.event.OnClickListener;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.MediaHelper;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.widgets.dialog.ImageChooserDialogFragment;

public class ShopInformationActivity extends BaseActivity<ShopInformationViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initCmd();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ShopInfoEvent>() {
            @Override
            public void onChanged(ShopInfoEvent event) {
                switch (event.code) {
                    case ShopInfoEvent.CREATE_ERROR:
                    case ShopInfoEvent.VERIFY_ERROR:
                        ToastUtils.showToast(event.message);
                        break;
                    case ShopInfoEvent.CREATE_SUCCESS:
                        createSuccess();
                        break;
                    case ShopInfoEvent.VERIFY_SUCCESS:
                        break;
                    case ShopInfoEvent.START_CROPPER:
                        cropImage((Uri) event.getData());
                        break;
                    case ShopInfoEvent.SHOW_IMAGE_OPTION:
                        showImageOptions();
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
        ((ActivityShopInfomationBinding) binding).setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().requestLocationCountryId();
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
}
