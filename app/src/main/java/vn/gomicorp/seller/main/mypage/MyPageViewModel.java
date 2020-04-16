package vn.gomicorp.seller.main.mypage;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.event.MultableLiveEvent;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageViewModel extends BaseViewModel {
    public MutableLiveData<Account> account;

    private MultableLiveEvent<MyPageEvent> cmd;

    public MyPageViewModel() {
        cmd = new MultableLiveEvent<>();
        account = new MutableLiveData<>();
        Account account = new Account();
        account.setFullName(EappsApplication.getPreferences().getFullName());
        account.setUserName(EappsApplication.getPreferences().getUserName());
        account.setAvatar(EappsApplication.getPreferences().getAvatar());
        this.account.setValue(account);
    }

    public void updateInfo() {
        cmd.call(new MyPageEvent(MyPageEvent.UPDATE_INFO));
    }

    MultableLiveEvent<MyPageEvent> getCmd() {
        return cmd;
    }
}
