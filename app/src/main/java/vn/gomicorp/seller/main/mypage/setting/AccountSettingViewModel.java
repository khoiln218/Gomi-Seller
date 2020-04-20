package vn.gomicorp.seller.main.mypage.setting;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignOutRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.GomiConstants;

/**
 * Created by KHOI LE on 4/20/2020.
 */
public class AccountSettingViewModel extends BaseViewModel {

    private AccountRepository mAccountRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<String> version;
    public MultableLiveEvent<AccountSettingEvent> cmd;

    public AccountSettingViewModel() {
        version = new MutableLiveData<>();
        mAccountRepository = AccountRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();
        cmd = new MultableLiveEvent<>();
        version.setValue(GomiConstants.VERSION);
    }

    public void signOut() {
        cmd.call(new AccountSettingEvent(AccountSettingEvent.SIGN_OUT));
    }

    void requestSignOut() {
        SignOutRequest request = new SignOutRequest();
        mAccountRepository.logout(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    showToast(EappsApplication.getInstance().getString(R.string.logout));
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                showToast(error);
            }
        });

        mAppPreferences.clear();
    }

    MultableLiveEvent<AccountSettingEvent> getCmd() {
        return cmd;
    }
}
