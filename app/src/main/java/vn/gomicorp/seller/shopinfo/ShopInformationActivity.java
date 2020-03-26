package vn.gomicorp.seller.shopinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityShopInfomationBinding;
import vn.gomicorp.seller.event.OnClickListener;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.MediaHelper;
import vn.gomicorp.seller.widgets.dialog.ImageChooserDialogFragment;

public class ShopInformationActivity extends AppCompatActivity {
    private ShopInformationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initCmd();
    }

    private void initCmd() {
        viewModel.getCmd().observe(this, new Observer<ShopInfoEvent>() {
            @Override
            public void onChanged(ShopInfoEvent event) {
                switch (event.code) {
                    case ShopInfoEvent.CREATE_ERROR:
                        Toast.makeText(ShopInformationActivity.this, "Create Error: " + event.message, Toast.LENGTH_SHORT).show();
                        break;
                    case ShopInfoEvent.CREATE_SUCCESS:
                        createSuccess();
                        break;
                    case ShopInfoEvent.VERIFY_ERROR:
                        Toast.makeText(ShopInformationActivity.this, "Verify Error: " + event.message, Toast.LENGTH_SHORT).show();
                        break;
                    case ShopInfoEvent.VERIFY_SUCCESS:
                        Toast.makeText(ShopInformationActivity.this, "Verify success", Toast.LENGTH_SHORT).show();
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

    private void initDataBinding() {
        ActivityShopInfomationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_infomation);
        viewModel = ViewModelProviders.of(this).get(ShopInformationViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.requestLocationCountryId();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.releaseAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
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
                    viewModel.setCover(imageUri);
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
