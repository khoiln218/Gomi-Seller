package vn.gomicorp.seller.data.source.model.api;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public class AccountChangePasswordRequest extends BaseRequest {
    /**
     * UserId : a4c1dd3d-c271-47af-ba14-85e115c61719
     * Password : 123456
     * NewPassword : 123456@
     */

    private String Password;
    private String NewPassword;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }
}
