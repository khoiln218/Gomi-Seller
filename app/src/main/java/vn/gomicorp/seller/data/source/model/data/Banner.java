package vn.gomicorp.seller.data.source.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class Banner implements Parcelable {
    protected Banner(Parcel in) {
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
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
