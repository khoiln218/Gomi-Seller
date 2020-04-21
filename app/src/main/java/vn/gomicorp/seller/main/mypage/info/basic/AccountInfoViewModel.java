package vn.gomicorp.seller.main.mypage.info.basic;

import android.text.Editable;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.GenderAdapter;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.AccountRequest;
import vn.gomicorp.seller.data.source.model.api.AccountUpdateRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.DateTimes;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Inputs;

public class AccountInfoViewModel extends BaseViewModel {
    private AccountRepository mAccountRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<String> fullName;
    public MutableLiveData<String> fullNameError;
    public MutableLiveData<Boolean> fullNameErrorEnable;
    public MutableLiveData<Boolean> fullNameFocus;

    public MutableLiveData<String> email;
    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> birthday;
    public MutableLiveData<Integer> gender;

    public MutableLiveData<Boolean> updateEnable;
    public MutableLiveData<Boolean> changeInfoHide;
    public MutableLiveData<Boolean> changeInfoFocus;

    public MutableLiveData<GenderAdapter> genderAdapter;

    private MultableLiveEvent<ChangeInfoEvent> cmd;

    private boolean isInfoChanged;
    private Account account;
    private int selectGender;
    private Calendar selectBirthday;

    public AccountInfoViewModel() {
        mAccountRepository = AccountRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();

        fullName = new MutableLiveData<>();
        fullNameError = new MutableLiveData<>();
        fullNameErrorEnable = new MutableLiveData<>();
        fullNameFocus = new MutableLiveData<>();

        email = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        birthday = new MutableLiveData<>();
        gender = new MutableLiveData<>();

        updateEnable = new MutableLiveData<>();
        changeInfoHide = new MutableLiveData<>();
        changeInfoFocus = new MutableLiveData<>();

        genderAdapter = new MutableLiveData<>();

        cmd = new MultableLiveEvent<>();

        genderAdapter.setValue(new GenderAdapter());

        isInfoChanged = false;
    }

    public void changeBirthday() {
        hideKeyBoard();
        if (account == null) return;
        long birthday = selectBirthday != null ? selectBirthday.getTimeInMillis() : account.getBirthDayLong();
        ChangeInfoEvent event = new ChangeInfoEvent(ChangeInfoEvent.SHOW_DATE_PICKER);
        event.setData(birthday);
        cmd.call(event);
    }

    public void update() {
        hideKeyBoard();
        String msg = String.format("%s %s", EappsApplication.getInstance().getString(R.string.err_text_empty), EappsApplication.getInstance().getString(R.string.name));
        if (!Inputs.validateText(fullName.getValue(), fullNameError, fullNameErrorEnable, fullNameFocus, msg))
            return;

        requestUpdateInfo();
    }

    public void afterFullNameChanged(Editable s) {
        if (account == null) return;
        isInfoChanged = !s.toString().equals(account.getFullName());
        fullNameErrorEnable.setValue(false);
        updateEnable.setValue(isInfoChanged);
    }

    private void afterBirthdayChanged() {
        if (account == null) return;
        isInfoChanged = selectBirthday.getTimeInMillis() != account.getBirthDayLong();
        updateEnable.setValue(isInfoChanged);
    }

    public void onItemSelected(int position) {
        if (account == null) return;
        selectGender = position;
        isInfoChanged = position != account.getGender();
        updateEnable.setValue(isInfoChanged);
    }

    private void hideKeyBoard() {
        cmd.call(new ChangeInfoEvent(ChangeInfoEvent.HIDE_KEYBOARD));
    }

    void setBirthday(Calendar selectBirthday) {
        this.selectBirthday = selectBirthday;
        birthday.setValue(DateTimes.toString(selectBirthday.getTime(), GomiConstants.INFO_DATE_FORMAT));
        afterBirthdayChanged();
    }

    void requestAccountInformation() {
        showProgressing();
        final AccountRequest request = new AccountRequest();
        mAccountRepository.findbyid(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    account = result.getResult();
                    mAppPreferences.setAccount(result.getResult());
                    updateInfo();
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
            }
        });
    }

    private void updateInfo() {
        fullName.setValue(account.getFullName());
        birthday.setValue(account.getBirthDay());
        gender.setValue(account.getGender());
        email.setValue(account.getEmail());
        phoneNumber.setValue(account.getPhoneNumber());

        isInfoChanged = false;
        updateEnable.setValue(false);
    }

    private void requestUpdateInfo() {
        showProgressing();
        changeInfoHide.setValue(true);
        AccountUpdateRequest request = new AccountUpdateRequest();
        if (selectBirthday != null) {
            request.setBirthDayLong(selectBirthday.getTimeInMillis());
        } else {
            request.setBirthDayLong(account.getBirthDayLong());
        }
        request.setFullName(fullName.getValue());
        request.setGender(selectGender);
        mAccountRepository.updateinfo(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                changeInfoHide.setValue(false);
                changeInfoFocus.setValue(true);
                if (result.getCode() == ResultCode.CODE_OK) {
                    account = result.getResult();
                    mAppPreferences.setAccount(result.getResult());
                    updateInfo();
                    showToast(EappsApplication.getInstance().getString(R.string.account_update_success));
                    cmd.call(new ChangeInfoEvent(ChangeInfoEvent.UPDATE_DONE));
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
                changeInfoHide.setValue(false);
                changeInfoFocus.setValue(true);
            }
        });
    }

    @Override
    protected void showProgressing() {
        cmd.call(new ChangeInfoEvent(ChangeInfoEvent.SHOW_LOADING));
    }

    @Override
    protected void hideProgressing() {
        cmd.call(new ChangeInfoEvent(ChangeInfoEvent.HIDE_LOADING));
    }

    MutableLiveData<ChangeInfoEvent> getCmd() {
        return cmd;
    }
}
