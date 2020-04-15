package vn.gomicorp.seller.main.mypage;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageViewModel extends BaseViewModel {
    public MutableLiveData<Account> account;

    public MyPageViewModel() {
        account = new MutableLiveData<>();
        Account account = new Account();
        account.setFullName(EappsApplication.getPreferences().getFullName());
        account.setUserName(EappsApplication.getPreferences().getUserName());
        account.setAvatar(EappsApplication.getPreferences().getAvatar());
        this.account.setValue(account);
    }
}
