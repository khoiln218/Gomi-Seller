package vn.gomicorp.seller.data.source.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class MegaCateListBean implements Parcelable {
    /**
     * Id : 1
     * Name : Trang sức - Phụ kiện
     * Icon : ic_trang-suc-phu-kien.png
     */

    private int Id;
    private String Name;
    private String Icon;

    protected MegaCateListBean(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        Icon = in.readString();
    }

    public static final Creator<MegaCateListBean> CREATOR = new Creator<MegaCateListBean>() {
        @Override
        public MegaCateListBean createFromParcel(Parcel in) {
            return new MegaCateListBean(in);
        }

        @Override
        public MegaCateListBean[] newArray(int size) {
            return new MegaCateListBean[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeString(Icon);
    }
}
