package vn.gomisellers.apps.data.source.model.api;

/**
 * Created by KHOI LE on 4/16/2020.
 */
public class AccountUpdateRequest extends BaseRequest {

    /**
     * UserId : a4c1dd3d-c271-47af-ba14-85e115c61719
     * FullName : Khôi Lê Ngọc
     * Gender : 1
     * BirthDayLong : 1586883600
     */

    private String FullName;
    private int Gender;
    private long BirthDayLong;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public long getBirthDayLong() {
        return BirthDayLong;
    }

    public void setBirthDayLong(long BirthDayLong) {
        this.BirthDayLong = BirthDayLong;
    }
}
