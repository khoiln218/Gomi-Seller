package vn.gomicorp.seller.data.source.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class BankAccount implements Parcelable {
    protected BankAccount(Parcel in) {
    }

    public static final Creator<BankAccount> CREATOR = new Creator<BankAccount>() {
        @Override
        public BankAccount createFromParcel(Parcel in) {
            return new BankAccount(in);
        }

        @Override
        public BankAccount[] newArray(int size) {
            return new BankAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
