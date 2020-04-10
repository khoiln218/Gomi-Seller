package vn.gomicorp.seller.main.home.withdrawn.bank;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.data.source.model.data.BankAccount;
import vn.gomicorp.seller.utils.GomiConstants;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class BankAccountInformationViewModel extends BaseViewModel {
    public MutableLiveData<BankAccount> bankAccount = new MutableLiveData<>();

    private BankAccount mBankAccount;

    private BankAccountInformationListener listener;

    public void setListener(BankAccountInformationListener listener) {
        this.listener = listener;
    }

    public void submit() {
//        if (Strings.isNullOrEmpty(bankAccount.getId()))
        requestAddBankAccount();
//        else
        requestUpdateBankAccount();
    }

    public void selectBank() {
        if (listener != null)
            listener.selectBank();
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
