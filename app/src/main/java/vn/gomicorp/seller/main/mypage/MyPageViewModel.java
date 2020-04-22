package vn.gomicorp.seller.main.mypage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.theartofdev.edmodo.cropper.CropImage;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.ChangeAvatarRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.MediaHelper;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageViewModel extends BaseViewModel {

    private AccountRepository mAccountRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<Account> account;
    public MutableLiveData<Uri> avatarUri;
    public MutableLiveData<Boolean> btnChageHide;

    private MultableLiveEvent<MyPageEvent> cmd;

    private Uri imageUri;

    public MyPageViewModel() {
        mAccountRepository = AccountRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();
        cmd = new MultableLiveEvent<>();
        account = new MutableLiveData<>();
        avatarUri = new MutableLiveData<>();
        btnChageHide = new MutableLiveData<>();
    }

    void initAccountInformation() {
        Account account = new Account();
        account.setFullName(EappsApplication.getPreferences().getFullName());
        account.setUserName(EappsApplication.getPreferences().getUserName());
        account.setAvatar(EappsApplication.getPreferences().getAvatar());
        this.account.setValue(account);
    }

    public void updateInfo() {
        cmd.call(new MyPageEvent(MyPageEvent.UPDATE_INFO));
    }

    public void changeAvatar() {
        cmd.call(new MyPageEvent(MyPageEvent.REQUEST_PERMISSION));
    }

    public void setting() {
        cmd.call(new MyPageEvent(MyPageEvent.SETTING));
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GomiConstants.REQUEST_CAMERA:

                    cropImage(imageUri);
                    break;

                case GomiConstants.REQUEST_GALLERY:

                    if (data != null)
                        cropImage(data.getData());
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null && result.getUri() != null) {
                        setAvatar(result.getUri());
                        requestChangeAvatar();
                    } else {
                        setAvatar(null);
                    }
                    break;
                case GomiConstants.REQUEST_ACCOUNT_SIGN_OUT:
                    cmd.call(new MyPageEvent(MyPageEvent.SIGN_OUT));
                    break;
            }
        } else {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                case GomiConstants.REQUEST_CAMERA:
                case GomiConstants.REQUEST_GALLERY:
                    setAvatar(null);
                    break;
            }
        }
    }

    private void requestChangeAvatar() {
        if (imageUri == null) return;
        btnChageHide.setValue(true);
        ChangeAvatarRequest request = new ChangeAvatarRequest();
        request.setAvatar(MediaHelper.getBase64FromImageUri(EappsApplication.getInstance(), imageUri, 1024, 1024));
        mAccountRepository.changeavatar(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                btnChageHide.setValue(false);
                if (result.getCode() == ResultCode.CODE_OK) {
                    mAppPreferences.setAccount(result.getResult());
                    initAccountInformation();
                    showToast(EappsApplication.getInstance().getString(R.string.change_avatar_success));
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                btnChageHide.setValue(false);
                showToast(error);
            }
        });

    }

    void setAvatar(Uri avatar) {
        imageUri = avatar;
        if (avatar == null) {
            Account account = new Account();
            account.setFullName(EappsApplication.getPreferences().getFullName());
            account.setUserName(EappsApplication.getPreferences().getUserName());
            account.setAvatar(EappsApplication.getPreferences().getAvatar());
            this.account.setValue(account);
        } else {
            avatarUri.setValue(avatar);
        }
    }

    private void cropImage(Uri uri) {
        MyPageEvent event = new MyPageEvent(MyPageEvent.START_CROPPER);
        event.setData(uri);
        cmd.call(event);
    }

    MultableLiveEvent<MyPageEvent> getCmd() {
        return cmd;
    }
}
