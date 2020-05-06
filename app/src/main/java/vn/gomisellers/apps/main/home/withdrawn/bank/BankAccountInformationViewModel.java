package vn.gomisellers.apps.main.home.withdrawn.bank;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.source.model.data.BankAccount;
import vn.gomisellers.apps.utils.GomiConstants;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class BankAccountInformationViewModel extends BaseViewModel<BankAccountEvent> {
    public MutableLiveData<BankAccount> bankAccount = new MutableLiveData<>();

    private BankAccount mBankAccount;

    public BankAccountInformationViewModel() {
    }

    public void submit() {
//        if (Strings.isNullOrEmpty(bankAccount.getId()))
        requestAddBankAccount();
//        else
        requestUpdateBankAccount();
    }

    public void selectBank() {
        getCmd().call(new BankAccountEvent(BankAccountEvent.SELECT_BANK));
    }

    void requestAddBankAccount() {

    }

    void requestUpdateBankAccount() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GomiConstants.RC_SELLER_BANK && resultCode == Activity.RESULT_OK) {
            mBankAccount = data.getParcelableExtra(GomiConstants.EXTRA_PARCELABLE);
            updateBank();
        }
    }

    private void updateBank() {
        bankAccount.setValue(mBankAccount);
    }
}
